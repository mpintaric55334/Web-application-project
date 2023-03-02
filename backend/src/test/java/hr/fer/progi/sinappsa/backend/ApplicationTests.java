package hr.fer.progi.sinappsa.backend;

import hr.fer.progi.sinappsa.backend.controller.LoginController;
import hr.fer.progi.sinappsa.backend.controller.SinapsaController;
import hr.fer.progi.sinappsa.backend.entity.AppUser;
import hr.fer.progi.sinappsa.backend.entity.Course;
import hr.fer.progi.sinappsa.backend.entity.Major;
import hr.fer.progi.sinappsa.backend.entity.Notice;
import hr.fer.progi.sinappsa.backend.entity.Query;
import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;
import hr.fer.progi.sinappsa.backend.entity.enums.QueryStatus;
import hr.fer.progi.sinappsa.backend.repository.AppUserGradeRepository;
import hr.fer.progi.sinappsa.backend.repository.AppUserRepository;
import hr.fer.progi.sinappsa.backend.repository.CourseRepository;
import hr.fer.progi.sinappsa.backend.repository.MajorRepository;
import hr.fer.progi.sinappsa.backend.repository.NoticeRepository;
import hr.fer.progi.sinappsa.backend.repository.QueryRepository;
import hr.fer.progi.sinappsa.backend.service.NoticeService;
import hr.fer.progi.sinappsa.backend.service.QueryService;
import hr.fer.progi.sinappsa.backend.service.dto.filter.FilterDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.CreateNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.profile.EditProfileDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetNoticeQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.QueryResponseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.GradeUserDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.UserId;
import hr.fer.progi.sinappsa.backend.service.impl.AppUserServiceJpa;
import hr.fer.progi.sinappsa.backend.service.impl.CourseServiceJpa;
import hr.fer.progi.sinappsa.backend.service.impl.NoticeServiceJpa;
import hr.fer.progi.sinappsa.backend.service.impl.QueryServiceJpa;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTests {

   @Autowired
    AppUserServiceJpa appUserServiceJpa;
    @MockBean
    JavaMailSender mailSender;
    @MockBean
    CourseServiceJpa courseServiceJpa;
    @Autowired
    NoticeServiceJpa noticeServiceJpa;
    @Autowired
    QueryServiceJpa queryServiceJpa;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AppUserGradeRepository appUserGradeRepository;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    QueryRepository queryRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    CourseRepository courseRepository;

    @BeforeAll
    void setUpAndTestCreation() throws MessagingException, UnsupportedEncodingException {
        AppUser user1 = new AppUser("Lovro", "lovro.krcelic24@gmail.com", "Krcelic", "Krci", "password", "avatar-1");
        user1.setVerificationCode("randomCode");
        user1.setRegistered(true);
        AppUser user2 =
            new AppUser("Matija", "matija.pintaric18@gmail.com", "Pintaric", "Keksa", "password", "avatar-2");
        user1.setVerificationCode("randomCode");
        user1.setRegistered(true);
        Major major = new Major(3L, "Racunarstvo");
        Course course = new Course(major, "PROGI");
        Notice notice = new Notice("Notice1", "testni notice 1", course, NoticeCategory.BLIC, major, true, true, user1);
        Query query = new Query("Testni query, nudi pomoc", user2, user1, notice, QueryStatus.IN_PROGRESS);

        appUserRepository.save(user1);
        appUserRepository.save(user2);

        majorRepository.save(major);

        courseRepository.save(course);

        noticeRepository.save(notice);

        queryRepository.save(query);
    }

    @Test
    @Order(1)
    void getNoticeQueries() {
        GetNoticeQueriesDTO getNoticeQueriesDTO = new GetNoticeQueriesDTO(5L,1L);
        ResponseEntity<?> response = queryServiceJpa.getNoticeQueries(getNoticeQueriesDTO);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    @Order(2)
    void gradeUser() {
        GradeUserDTO gradeUserDTO = new GradeUserDTO(6L, 1L, 4D);
        appUserServiceJpa.gradeUser(gradeUserDTO);

        AppUser gradedUser = appUserRepository.findByUserId(2L);

        Assertions.assertEquals(gradedUser.getAverageGrade(), 4);
    }

    @Test
    @Order(4)
    void editNotice() {
        CreateNoticeDTO createNoticeDTO = new CreateNoticeDTO("Notice1", "testni notice 1",4L ,3L, NoticeCategory.GRADIVO,  1L, 5L, true);
        noticeServiceJpa.noticePost(createNoticeDTO);

        Notice notice = noticeRepository.findById(5L).get();

        Assertions.assertEquals(notice.getCategory(), NoticeCategory.GRADIVO);
    }

   

    @Test
    @Order(6)
    void getHomeNotices() {
        FilterDTO filterDTO = new FilterDTO("Notice1", null, null, null);
        noticeServiceJpa.homeNotices(filterDTO);
        List<Notice> notices = noticeRepository.findAll();

        Assertions.assertFalse(notices.isEmpty());
    }

    @Test
    @Order(5)
    void getUserNotices() {
        UserId id = new UserId();
        id.setId(1L);
        noticeServiceJpa.userNotices(id);
        List<Notice> notices = noticeRepository.findAll();

        Assertions.assertFalse(notices.isEmpty());
    }

    @Test
    @Order(7)
    void deleteNotice() {
        noticeServiceJpa.deleteNotice(5L);
        List<Notice> notices = noticeRepository.findAll();

        Assertions.assertTrue(notices.isEmpty());
    }

}
