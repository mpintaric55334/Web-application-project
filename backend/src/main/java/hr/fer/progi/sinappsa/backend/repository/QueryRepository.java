package hr.fer.progi.sinappsa.backend.repository;

import hr.fer.progi.sinappsa.backend.entity.Query;
import hr.fer.progi.sinappsa.backend.entity.enums.QueryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {

    @Transactional
    @Modifying
    void deleteById(Long id);

    List<Query> findAllByNoticeIdAndStatusEquals(Long Id, QueryStatus status);

    List<Query> findAllBySenderIdAndStatusEquals(Long userId, QueryStatus status);

}
