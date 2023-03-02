package hr.fer.progi.sinappsa.backend.repository;

import hr.fer.progi.sinappsa.backend.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {

    @Query("SELECT m FROM Major m")
    List<Major> allMajors();

    @Query("SELECT m FROM Major m WHERE m.id = :id")
    Major findByMajorId(@Param("id") Long id);

}
