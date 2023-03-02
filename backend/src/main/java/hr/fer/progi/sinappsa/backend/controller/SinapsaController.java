package hr.fer.progi.sinappsa.backend.controller;

import hr.fer.progi.sinappsa.backend.entity.AppUser;
import hr.fer.progi.sinappsa.backend.entity.ErrorMsg;
import hr.fer.progi.sinappsa.backend.entity.InfoMsg;
import hr.fer.progi.sinappsa.backend.service.AppUserService;
import hr.fer.progi.sinappsa.backend.service.CourseService;
import hr.fer.progi.sinappsa.backend.service.NoticeService;
import hr.fer.progi.sinappsa.backend.service.QueryService;
import hr.fer.progi.sinappsa.backend.service.RequestDeniedException;
import hr.fer.progi.sinappsa.backend.service.dto.course.CourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.course.CreateCourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.course.MajorToCourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.filter.FilterDTO;
import hr.fer.progi.sinappsa.backend.service.dto.major.MajorIdDTO;
import hr.fer.progi.sinappsa.backend.service.dto.nickname.NicknameDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.CreateNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.DeleteNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.DisplayNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.NoticeActivationDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.NoticeId;
import hr.fer.progi.sinappsa.backend.service.dto.noticecategory.NoticeCategoryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.profile.EditProfileDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.CreateQueryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetNoticeQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetUserQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.RejectQueryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.ChangeUserAttributesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.DeleteUserDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.GradeUserDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/sinapsa")
@CrossOrigin
public class SinapsaController {

    private final AppUserService appUserService;

    private final NoticeService noticeService;

    private final CourseService courseService;

    private final QueryService queryService;

    @Autowired
    public SinapsaController(AppUserService appUserService, NoticeService noticeService, CourseService courseService,
        QueryService queryService) {
        this.appUserService = appUserService;
        this.noticeService = noticeService;
        this.courseService = courseService;
        this.queryService = queryService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> UserCreator(@RequestBody AppUser user, HttpServletRequest request)
        throws UnsupportedEncodingException, RequestDeniedException, MessagingException {

        return appUserService.createAppUser(user, getSiteURL(request));

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public RedirectView verifyUser(@RequestParam(value = "code") String code) {
        if (appUserService.verify(code)) {
            return new RedirectView("https://sinappsafront.onrender.com/sinapsa/verify");
        } else {
            return null;
        }
    }

    
    @PostMapping("/deleteProfile")
    public ResponseEntity<?> deleteProfile(@RequestBody DeleteUserDTO data) {
        return appUserService.deleteUser(data);
    }

    @PostMapping("/editProfile")
    public ResponseEntity<?> editProfile(@RequestBody EditProfileDTO data) {
        return appUserService.updateProfile(data);
    }
    @PostMapping("/logout")
    public void logout() {
    }


    @GetMapping("rankings")
    public ResponseEntity<?> rankings() {
        return appUserService.rankings();
    }

    @PostMapping("StudentProfile")
    public ResponseEntity<?> displayStudentProfile(@RequestBody NicknameDTO nickname) {
        return appUserService.displayStudentProfile(nickname.getNickname());
    }

    @PostMapping("/moderatorDeleteNotice")
    public ResponseEntity<?> deleteNoticeModerator(@RequestBody DeleteNoticeDTO obj) {

        Boolean isModerator = appUserService.checkForModerator(obj.getUserId());
        Boolean isOwner = noticeService.checkForOwner(obj.getUserId(), obj.getNoticeId());

        if (!isModerator && !isOwner) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Korisnik ne može brisati oglase jer nije moderator niti vlasnik oglasa!"),
                HttpStatus.BAD_REQUEST);
        }
        Long noticeOwnerId = noticeService.deleteNotice(obj.getNoticeId());

        try {
            if (!isOwner) {
                return appUserService.mailAfterDelete(noticeOwnerId, obj.getMessage());
            }
        } catch (Exception e) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Ne može se poslati mail!"),
                HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<InfoMsg>(new InfoMsg("oglas je obrisan"), HttpStatus.OK);
    }

    @GetMapping("/majors")
    public ResponseEntity<?> getMajors() {
        return noticeService.getMajors();
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return noticeService.getAllCourses();
    }

    @PostMapping("/specified-courses")
    public ResponseEntity<List<CourseDTO>> getSpecifiedCourses(@RequestBody MajorIdDTO majorIdDTO) {
        return noticeService.getSpecifiedCourses(majorIdDTO.getMajorId());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<NoticeCategoryDTO>> getCategories() {
        return noticeService.getCategories();
    }

    @PostMapping("homeNotices")
    public ResponseEntity<List<DisplayNoticeDTO>> homeNotices(@RequestBody FilterDTO filter) {
        return noticeService.homeNotices(filter);
    }

    @PostMapping("/getNotice")
    public ResponseEntity<?> getNotice(@RequestBody NoticeId noticeId) {
        return noticeService.getNotice(noticeId);
    }

    @PostMapping("userNotices")
    public ResponseEntity<?> userNotices(@RequestBody UserId id) {
        return noticeService.userNotices(id);
    }

    @PostMapping("editNotice")
    public ResponseEntity<?> noticePost(@RequestBody CreateNoticeDTO dto) {
        return noticeService.noticePost(dto);
    }

    @PostMapping("deactivateNotice")
    public ResponseEntity<?> deactivateNotice(@RequestBody NoticeActivationDTO dto) {
        return noticeService.deactivateNotice(dto);
    }

    @PostMapping("activateNotice")
    public ResponseEntity<?> activateNotice(@RequestBody NoticeActivationDTO dto) {
        return noticeService.activateNotice(dto);
    }

    @PostMapping("addCourse")
    public ResponseEntity<?> addCourse(@RequestBody CreateCourseDTO dto) {
        Boolean mod = appUserService.checkForModerator(dto.getUserId());
        if (!mod) {
            return new ResponseEntity<ErrorMsg>(new ErrorMsg("kolegije može dodavati jedino moderator!"),
                HttpStatus.METHOD_NOT_ALLOWED);
        }
        return courseService.addCourse(dto);
    }

    @PostMapping("addMajorToCourse")
    public ResponseEntity<?> addMajorToCourse(@RequestBody MajorToCourseDTO dto) {
        Boolean mod = appUserService.checkForModerator(dto.getUserId());
        if (!mod) {
            return new ResponseEntity<ErrorMsg>(new ErrorMsg("smjerove na kolegije može dodavati jedino moderator!"),
                HttpStatus.METHOD_NOT_ALLOWED);
        }
        return courseService.addMajorToCourse(dto);
    }

    @PostMapping("createQuery")
    public ResponseEntity<?> createQuery(@RequestBody CreateQueryDTO dto) {
        return queryService.createQuery(dto);
    }

    @PostMapping("rejectQuery")
    public ResponseEntity<?> rejectQuery(@RequestBody RejectQueryDTO dto) {
        return queryService.rejectQuery(dto);
    }

    @PostMapping("getNoticeQueries")
    public ResponseEntity<?> getNoticeQueries(@RequestBody GetNoticeQueriesDTO dto) {
        return queryService.getNoticeQueries(dto);
    }

    @PostMapping("getCurrentUserQueries")
    public ResponseEntity<?> getCurrentUserQueries(@RequestBody GetUserQueriesDTO dto) {
        return queryService.getCurrentUserQueries(dto);
    }

    @PostMapping("gradeUser")
    public ResponseEntity<?> gradeUser(@RequestBody GradeUserDTO dto) {
        ResponseEntity response = appUserService.gradeUser(dto);

        if (response.getStatusCode() == HttpStatus.OK) {
            queryService.acceptQuery(dto.getQueryId());
        }

        return response;
    }

}
