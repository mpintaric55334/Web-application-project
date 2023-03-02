package hr.fer.progi.sinappsa.backend.service.impl;

import hr.fer.progi.sinappsa.backend.entity.Course;
import hr.fer.progi.sinappsa.backend.entity.ErrorMsg;
import hr.fer.progi.sinappsa.backend.entity.Major;
import hr.fer.progi.sinappsa.backend.repository.CourseRepository;
import hr.fer.progi.sinappsa.backend.repository.MajorRepository;
import hr.fer.progi.sinappsa.backend.service.CourseService;
import hr.fer.progi.sinappsa.backend.service.dto.course.CreateCourseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.course.MajorToCourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CourseServiceJpa implements CourseService {

    private final CourseRepository courseRepo;

    private final MajorRepository majorRepository;

    @Autowired
    public CourseServiceJpa(CourseRepository courseRepo, MajorRepository majorRepository) {
        this.courseRepo = courseRepo;
        this.majorRepository = majorRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> addCourse(CreateCourseDTO dto) {
        Major major = majorRepository.findByMajorId(dto.getMajorId());
        try {
            Course myCourse = courseRepo.save(new Course(major, dto.getCourseName()));
            return new ResponseEntity<Course>(myCourse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<ErrorMsg>(new ErrorMsg("Kolegij se nije uspio kreirati sa danim podatcima!"),
                HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @Override
    public ResponseEntity<?> addMajorToCourse(MajorToCourseDTO dto) {
        Course myCourse = courseRepo.findById(dto.getCourseId()).get();
        Major major = majorRepository.findById(dto.getMajorId()).get();

        if (myCourse == null) {
            return new ResponseEntity<ErrorMsg>(new ErrorMsg("Ne postoji ovaj kolegij u bazi podataka!"),
                HttpStatus.NOT_ACCEPTABLE);
        }

        if (major == null) {
            return new ResponseEntity<ErrorMsg>(new ErrorMsg("Ne postoji ovaj major u bazi podataka!"),
                HttpStatus.NOT_ACCEPTABLE);
        }

        myCourse.setMajor(major);
        Course tempCourse = courseRepo.save(myCourse);

        if (tempCourse == null) {
            return new ResponseEntity<ErrorMsg>(new ErrorMsg("kolegij se ne može spremiti u bazu podataka!"),
                HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Course>(tempCourse, HttpStatus.ACCEPTED);
    }

}
