package hr.fer.progi.sinappsa.backend.entity;

import hr.fer.progi.sinappsa.backend.entity.enums.QueryStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private AppUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private AppUser receiver;

    @ManyToOne
    @JoinColumn(name = "notice_id", referencedColumnName = "id")
    private Notice notice;

    private QueryStatus status;

    public Query() {

    }

    public Query(String message, AppUser sender, AppUser receiver, Notice notice, QueryStatus status) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.notice = notice;
        this.status = status;
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

    public AppUser getSender() {
        return sender;
    }

    public void setSender(AppUser sender) {
        this.sender = sender;
    }

    public AppUser getReceiver() {
        return receiver;
    }

    public void setReceiver(AppUser receiver) {
        this.receiver = receiver;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public QueryStatus getStatus() {
        return status;
    }

    public void setStatus(QueryStatus status) {
        this.status = status;
    }

}
