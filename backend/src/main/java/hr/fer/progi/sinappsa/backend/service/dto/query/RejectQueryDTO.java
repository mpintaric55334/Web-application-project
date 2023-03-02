package hr.fer.progi.sinappsa.backend.service.dto.query;

public class RejectQueryDTO {

    private Long queryId;

    private Long appUserId;

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

}
