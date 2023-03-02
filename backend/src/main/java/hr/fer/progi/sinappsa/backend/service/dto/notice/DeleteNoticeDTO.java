package hr.fer.progi.sinappsa.backend.service.dto.notice;

public class DeleteNoticeDTO {

    public Long userId;
    public Long noticeId;
    public String message;

    public DeleteNoticeDTO(Long userId, Long noticeId, String message) {
        this.userId = userId;
        this.noticeId = noticeId;
        this.message = message;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public String getMessage() {
        return message;
    }

}
