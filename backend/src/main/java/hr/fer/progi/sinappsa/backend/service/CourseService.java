package hr.fer.progi.sinappsa.backend.service;

import hr.fer.progi.sinappsa.backend.service.dto.course.CreateCourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.course.MajorToCourseDTO;
import org.springframework.http.ResponseEntity;

public interface CourseService {

    ResponseEntity<?> addCourse(CreateCourseDTO dto);

    ResponseEntity<?> addMajorToCourse(MajorToCourseDTO dto);

}
