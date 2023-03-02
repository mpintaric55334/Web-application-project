package hr.fer.progi.sinappsa.backend.service.dto.query;

public class GetNoticeQueriesDTO {

    private Long noticeId;

    private Long appUserId;

    public GetNoticeQueriesDTO() {
    }

    public GetNoticeQueriesDTO(Long noticeId, Long appUserId) {
        this.noticeId = noticeId;
        this.appUserId = appUserId;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

}
