package hr.fer.progi.sinappsa.backend.service.dto.course;

public class CreateCourseDTO {

    private Long userId;
    private Long majorId;
    private String courseName;

    public CreateCourseDTO() {
    }

    public CreateCourseDTO(Long userId, Long majorId, String courseName) {
        this.userId = userId;
        this.majorId = majorId;
        this.courseName = courseName;
    }

    public CreateCourseDTO(Long userId, String courseName) {
        this.userId = userId;
        this.courseName = courseName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public String getCourseName() {
        return courseName;
    }

}
