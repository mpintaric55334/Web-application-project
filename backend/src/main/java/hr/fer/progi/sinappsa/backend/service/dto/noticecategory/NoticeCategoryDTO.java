package hr.fer.progi.sinappsa.backend.service.dto.noticecategory;

import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;

public class NoticeCategoryDTO {

    private NoticeCategory noticeCategory;

    public NoticeCategoryDTO(NoticeCategory categories) {
        this.noticeCategory = categories;
    }

    public NoticeCategory getNoticeCategory() {
        return noticeCategory;
    }

    public void setNoticeCategory(NoticeCategory noticeCategory) {
        this.noticeCategory = noticeCategory;
    }

}
