package hr.fer.progi.sinappsa.backend.service.dto.query;

public class GetUserQueriesDTO {

    private Long appUserId;

    private Long userProfileId;

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

}
