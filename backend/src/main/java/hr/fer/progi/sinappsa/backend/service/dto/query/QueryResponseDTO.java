package hr.fer.progi.sinappsa.backend.service.dto.query;

import hr.fer.progi.sinappsa.backend.entity.Query;
import hr.fer.progi.sinappsa.backend.entity.enums.QueryStatus;

public class QueryResponseDTO {

    private Long id;

    private String message;

    private Long senderId;

    private String senderNickname;

    private String senderAvatar;

    private Long receiverId;

    private QueryStatus status;

    private boolean isAskingHelp;
    
    private Long noticeId;

    public QueryResponseDTO(Query query) {
        this.id = query.getId();
        this.message = query.getMessage();
        this.senderId = query.getSender().getId();
        this.senderNickname = query.getSender().getNickname();
        this.senderAvatar = query.getSender().getAvatar();
        this.receiverId = query.getReceiver().getId();
        this.status = query.getStatus();
        this.isAskingHelp = query.getNotice().getIsAskingHelp();
        this.noticeId=query.getNotice().getId();
    }
    
    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public QueryStatus getStatus() {
        return status;
    }

    public void setStatus(QueryStatus status) {
        this.status = status;
    }

    public boolean getIsAskingHelp() {
        return isAskingHelp;
    }

    public void setIsAskingHelp(boolean isAskingHelp) {
        this.isAskingHelp = isAskingHelp;
    }

}
