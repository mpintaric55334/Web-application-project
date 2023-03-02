package hr.fer.progi.sinappsa.backend.service.impl;

import hr.fer.progi.sinappsa.backend.entity.*;
import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;
import hr.fer.progi.sinappsa.backend.repository.AppUserRepository;
import hr.fer.progi.sinappsa.backend.repository.CourseRepository;
import hr.fer.progi.sinappsa.backend.repository.MajorRepository;
import hr.fer.progi.sinappsa.backend.repository.NoticeRepository;
import hr.fer.progi.sinappsa.backend.service.NoticeService;
import hr.fer.progi.sinappsa.backend.service.dto.course.CourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.filter.FilterDTO;
import hr.fer.progi.sinappsa.backend.service.dto.major.MajorDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.CreateNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.DisplayNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.NoticeActivationDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.NoticeId;
import hr.fer.progi.sinappsa.backend.service.dto.noticecategory.NoticeCategoryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class NoticeServiceJpa implements NoticeService {

    private final NoticeRepository noticeRepo;

    private final MajorRepository majorRepo;

    private final CourseRepository courseRepo;

    private final AppUserRepository appUserRepo;

    @Autowired
    public NoticeServiceJpa(NoticeRepository noticeRepo, CourseRepository courseRepo, MajorRepository majorRepo,
        AppUserRepository appUserRepo) {
        this.noticeRepo = noticeRepo;
        this.courseRepo = courseRepo;
        this.majorRepo = majorRepo;
        this.appUserRepo = appUserRepo;
    }

    @Override
    public List<Notice> listAll() {
        return noticeRepo.findAll();
    }

    @Override
    public List<Notice> listAllActive() { //returns a list of all currently active notices
        List<Notice> all = listAll();
        List<Notice> active = new ArrayList<>();
        for (Notice notice : all) {
            if (notice.getIsActive()) {
                active.add(notice);
            }
        }

        return active;
    }

    @Override
    public Long deleteNotice(Long noticeId) {
        Notice notice = noticeRepo.findById(noticeId).get();
        Long noticeOwnerId = notice.getAppUser().getId();
        noticeRepo.deleteById(noticeId);

        return noticeOwnerId;
    }

    @Override
    public ResponseEntity<List<NoticeCategoryDTO>> getCategories() {
        List<NoticeCategory> categories = Arrays.asList(NoticeCategory.values());
        List<NoticeCategoryDTO> dto = categories.stream().map(NoticeCategoryDTO::new).toList();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courses = courseRepo.allCourses();
        List<CourseDTO> dto = courses.stream().map(CourseDTO::new).toList();

        return new ResponseEntity<List<CourseDTO>>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CourseDTO>> getSpecifiedCourses(Long majorId) {
        List<Course> courses = courseRepo.findAllByMajor_Id(majorId);
        List<CourseDTO> dto = courses.stream().map(CourseDTO::new).toList();

        return new ResponseEntity<List<CourseDTO>>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MajorDTO>> getMajors() {
        List<Major> majors = majorRepo.allMajors();
        List<MajorDTO> dto = majors.stream().map(MajorDTO::new).toList();

        return new ResponseEntity<List<MajorDTO>>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> noticePost(CreateNoticeDTO dto) {
        Notice notice;
        if(dto.getCategory()==null || dto.getCourseId()==null ||  dto.getMajorId()==null){
            return new ResponseEntity<ErrorMsg>(
                    new ErrorMsg("Niste unijeli sve potrebne podatke"),
                    HttpStatus.BAD_REQUEST
            );
        }
        Course course = courseRepo.findByCourseId(dto.getCourseId());
        AppUser appUser = appUserRepo.findByUserId(dto.getUserId());
        Major major = majorRepo.findByMajorId(dto.getMajorId());

        if((long)course.getMajor().getId()!=(long)major.getId()){
            return new ResponseEntity<ErrorMsg>(
                    new ErrorMsg("Predmet ne pripada tom smjeru"),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getNoticeId() == null) {
            notice = new Notice(dto.getTitle(), dto.getDescription(), course, dto.getCategory(), major, true,
                dto.getIsAskingHelp(), appUser);
        } else {
            notice = noticeRepo.findById(dto.getNoticeId()).get();
            notice.setMajor(major);
            notice.setTitle(dto.getTitle());
            notice.setCategory(dto.getCategory());
            notice.setAppUser(appUser);
            notice.setDescription(dto.getDescription());
            notice.setCourse(course);
        }

        LocalDateTime dateTime = LocalDateTime.now();
        String currentTimeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").format(dateTime);
        notice.setCreationTime(Timestamp.valueOf(currentTimeStamp));

        noticeRepo.save(notice);

        return new ResponseEntity<NoticeId>(
            new NoticeId(notice.getId()),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<DisplayNoticeDTO>> homeNotices(FilterDTO filter) {
        final String noticeTitle = filter.getNoticeTitle();
        final Long majorId = filter.getMajorId();
        final Long courseId = filter.getCourseId();
        final NoticeCategory category = filter.getCategory();
        final List<Notice> noticeList = getFilteredNotices(noticeTitle, majorId, courseId, category);

        List<DisplayNoticeDTO> dto = noticeList.stream()
            .filter(Notice::getIsActive)
            .sorted(Comparator.comparing(Notice::getCreationTime).reversed())
            .map(DisplayNoticeDTO::new)
            .toList();

        return new ResponseEntity<List<DisplayNoticeDTO>>(
            dto,
            HttpStatus.OK
        );

    }

    @Override
    public ResponseEntity<?> userNotices(UserId Id) {
        List<Notice> noticeList = noticeRepo.findByAppUser_Id(Id.getId());
        List<DisplayNoticeDTO> dto = noticeList.stream()
            .sorted(Comparator.comparing(Notice::getCreationTime))
            .map(DisplayNoticeDTO::new)
            .toList();

        return new ResponseEntity<List<DisplayNoticeDTO>>(
            dto,
            HttpStatus.OK
        );

    }

    @Override
    public Boolean checkForOwner(Long userId, Long noticeId) {
        Notice notice = noticeRepo.findById(noticeId).get();
        return Objects.equals(notice.getAppUser().getId(), userId);
    }

    @Override
    public ResponseEntity<?> deactivateNotice(NoticeActivationDTO dto) {
        final Notice notice = noticeRepo.findById(dto.getNoticeId()).get();

        if (!Objects.equals(dto.getUserId(), notice.getAppUser().getId())) {
            return new ResponseEntity<>(
                "Ne može se deaktivirati oglas jer osoba nije vlasnik oglasa",
                HttpStatus.BAD_REQUEST
            );
        }

        notice.setIsActive(false);
        noticeRepo.save(notice);
        return new ResponseEntity<>(
            "Uspješno deaktiviran oglas",
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> activateNotice(NoticeActivationDTO dto) {
        final Notice notice = noticeRepo.findById(dto.getNoticeId()).get();

        if (!Objects.equals(dto.getUserId(), notice.getAppUser().getId())) {
            return new ResponseEntity<>(
                "Ne može se aktivirati oglas jer osoba nije vlasnik oglasa",
                HttpStatus.BAD_REQUEST
            );
        }

        notice.setIsActive(true);
        noticeRepo.save(notice);
        return new ResponseEntity<>(
            "Uspješno aktiviran oglas",
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getNotice(NoticeId noticeId) {
        Notice notice;
        try {
       notice = noticeRepo.findById(noticeId.getId()).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                "Ne postoji takav notice",
                HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
            new DisplayNoticeDTO(notice),
            HttpStatus.OK
        );
    }

    private List<Notice> getFilteredNotices(String noticeTitle, Long majorId, Long courseId, NoticeCategory category) {
        List<Notice> noticeList = noticeRepo.findAll();

        if (noticeTitle != null) {
            noticeList.retainAll(noticeRepo.findByTitleIsLikeIgnoreCase(noticeTitle));
        }

        if (majorId != null) {
            noticeList.retainAll(noticeRepo.findByMajor_Id(majorId));
        }

        if (courseId != null) {
            noticeList.retainAll(noticeRepo.findByCourse_Id(courseId));
        }

        if (category != null) {
            noticeList.retainAll(noticeRepo.findByCategory(category));
        }

        return noticeList;
    }

}

