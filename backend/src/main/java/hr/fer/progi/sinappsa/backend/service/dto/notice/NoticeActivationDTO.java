package hr.fer.progi.sinappsa.backend.service.dto.notice;

public class NoticeActivationDTO {

    private Long noticeId;

    private Long userId;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
