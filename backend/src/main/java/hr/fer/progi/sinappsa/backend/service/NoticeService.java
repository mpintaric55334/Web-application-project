package hr.fer.progi.sinappsa.backend.service;

import hr.fer.progi.sinappsa.backend.entity.Notice;
import hr.fer.progi.sinappsa.backend.service.dto.course.CourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.filter.FilterDTO;
import hr.fer.progi.sinappsa.backend.service.dto.major.MajorDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.CreateNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.DisplayNoticeDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.NoticeActivationDTO;
import hr.fer.progi.sinappsa.backend.service.dto.notice.NoticeId;
import hr.fer.progi.sinappsa.backend.service.dto.noticecategory.NoticeCategoryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.UserId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NoticeService {

    List<Notice> listAll();

    List<Notice> listAllActive();

    Long deleteNotice(Long noticeId);

    ResponseEntity<List<MajorDTO>> getMajors();

    ResponseEntity<List<NoticeCategoryDTO>> getCategories();

    ResponseEntity<List<CourseDTO>> getAllCourses();

    ResponseEntity<List<CourseDTO>> getSpecifiedCourses(Long majorId);

    ResponseEntity<?> noticePost(CreateNoticeDTO dto);

    ResponseEntity<List<DisplayNoticeDTO>> homeNotices(FilterDTO filter);

    ResponseEntity<?> userNotices(UserId Id);

    Boolean checkForOwner(Long userId, Long noticeId);

    ResponseEntity<?> deactivateNotice(NoticeActivationDTO dto);

    ResponseEntity<?> activateNotice(NoticeActivationDTO dto);

    ResponseEntity<?> getNotice(NoticeId noticeId);

}
