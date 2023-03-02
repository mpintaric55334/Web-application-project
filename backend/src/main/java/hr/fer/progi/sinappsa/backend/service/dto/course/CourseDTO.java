package hr.fer.progi.sinappsa.backend.service.dto.course;

import hr.fer.progi.sinappsa.backend.entity.Course;

public class CourseDTO {

    private Long id;
    private String courseName;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
