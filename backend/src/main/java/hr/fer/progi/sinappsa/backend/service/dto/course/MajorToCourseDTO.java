package hr.fer.progi.sinappsa.backend.service.dto.course;

public class MajorToCourseDTO {

    private Long courseId;
    private Long majorId;
    private Long userId;

    public MajorToCourseDTO(Long courseId, Long majorId, Long userId) {
        this.courseId = courseId;
        this.majorId = majorId;
        this.userId = userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public Long getUserId() {
        return userId;
    }

}
