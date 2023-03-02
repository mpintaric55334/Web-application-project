package hr.fer.progi.sinappsa.backend.service.dto.notice;

import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;

public class CreateNoticeDTO {

    private String title;
    private String description;
    private Long courseId;
    private Long majorId;
    private NoticeCategory category;
    private Long userId;
    private Long noticeId;
    private boolean isAskingHelp;

    public CreateNoticeDTO() {
    }

    public CreateNoticeDTO(String title, String description, Long courseId, Long majorId, NoticeCategory category,
        Long userId, Long noticeId, boolean isAskingHelp) {
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.majorId = majorId;
        this.category = category;
        this.userId = userId;
        this.noticeId = noticeId;
        this.isAskingHelp = isAskingHelp;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public NoticeCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setCategory(NoticeCategory category) {
        this.category = category;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsAskingHelp() {
        return isAskingHelp;
    }

    public void setIsAskingHelp(boolean isAskingHelp) {
        this.isAskingHelp = isAskingHelp;
    }

}
