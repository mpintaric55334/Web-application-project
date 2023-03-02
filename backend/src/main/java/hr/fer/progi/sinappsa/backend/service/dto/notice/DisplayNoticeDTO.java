package hr.fer.progi.sinappsa.backend.service.dto.notice;

import hr.fer.progi.sinappsa.backend.entity.Notice;
import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;

import java.sql.Timestamp;

public class DisplayNoticeDTO {

    private Long id;
    private Long userId;
    private String userNickname;
    private String majorName;
    private String courseName;
    private NoticeCategory category;
    private String title;
    private String description;
    private String userAvatar;
    private boolean isActive;
    private boolean isAskingHelp;
    private Timestamp creationTime;

    public DisplayNoticeDTO() {

    }

    public DisplayNoticeDTO(Notice notice) {
        this.id = notice.getId();
        this.userId = notice.getAppUser().getId();
        this.userNickname = notice.getAppUser().getNickname();
        this.majorName = notice.getMajor().getMajorName();
        this.courseName = notice.getCourse().getCourseName();
        this.category = notice.getCategory();
        this.title = notice.getTitle();
        this.description = notice.getDescription();
        this.isActive = notice.getIsActive();
        this.isAskingHelp = notice.getIsAskingHelp();
        this.userAvatar = notice.getAppUser().getAvatar();
        this.creationTime = notice.getCreationTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public NoticeCategory getCategory() {
        return category;
    }

    public void setCategory(NoticeCategory category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getIsAskingHelp() {
        return isAskingHelp;
    }

    public void setIsAskingHelp(boolean isAskingHelp) {
        this.isAskingHelp = isAskingHelp;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

}
