package hr.fer.progi.sinappsa.backend.service.dto.user;

public class GradeUserDTO {

    private Long queryId;

    private Long appUserId;

    private Double grade;

    public GradeUserDTO() {
    }

    public GradeUserDTO(Long queryId, Long appUserId, Double grade) {
        this.queryId = queryId;
        this.appUserId = appUserId;
        this.grade = grade;
    }

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

}
