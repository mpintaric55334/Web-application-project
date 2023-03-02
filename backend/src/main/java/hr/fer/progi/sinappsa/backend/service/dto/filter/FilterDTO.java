package hr.fer.progi.sinappsa.backend.service.dto.filter;

import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;

public class FilterDTO {

    private String noticeTitle;

    private Long majorId;

    private Long courseId;

    private NoticeCategory category;

    public FilterDTO() {
    }

    public FilterDTO(String noticeTitle, Long majorId, Long courseId, NoticeCategory category) {
        this.noticeTitle = noticeTitle;
        this.majorId = majorId;
        this.courseId = courseId;
        this.category = category;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public NoticeCategory getCategory() {
        return category;
    }

    public void setCategory(NoticeCategory category) {
        this.category = category;
    }

}
