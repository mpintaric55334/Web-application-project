package hr.fer.progi.sinappsa.backend.service.dto.query;

public class CreateQueryDTO {

    private Long noticeId;

    private Long senderId;

    private String queryMsg;

    public CreateQueryDTO() {
    }

    public CreateQueryDTO(Long noticeId, Long senderId, String queryMsg) {
        this.noticeId = noticeId;
        this.senderId = senderId;
        this.queryMsg = queryMsg;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getQueryMsg() {
        return queryMsg;
    }

    public void setQueryMsg(String queryMsg) {
        this.queryMsg = queryMsg;
    }

}
