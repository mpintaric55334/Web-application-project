package hr.fer.progi.sinappsa.backend.repository;

import hr.fer.progi.sinappsa.backend.entity.Notice;
import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Transactional
    @Modifying
    void deleteNoticeByAppUser_Id(Long userId);

    void deleteById(Long id);

    Optional<Notice> findById(Long id);

    List<Notice> findByAppUser_Id(Long userId);

    List<Notice> findByOrderByCreationTimeDesc();

    List<Notice> findByTitleIsLikeIgnoreCase(String title);

    List<Notice> findByMajor_Id(Long majorId);

    List<Notice> findByCourse_Id(Long courseId);

    List<Notice> findByCategory(NoticeCategory category);

}
