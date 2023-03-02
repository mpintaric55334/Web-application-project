package hr.fer.progi.sinappsa.backend.repository;

import hr.fer.progi.sinappsa.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c")
    List<Course> allCourses();

    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Course findByCourseId(@Param("id") Long id);

    List<Course> findAllByMajor_Id(Long majorId);

}
