package hr.fer.progi.sinappsa.backend.service.dto.profile;

public class EditProfileDTO {

    private String avatar;

    private Long id;

    private String newPassword;

    private String nickname;

    private String oldPassword;

    public EditProfileDTO() {
    }

    public EditProfileDTO(String avatar, Long id, String newPassword, String nickname, String oldPassword) {
        this.avatar = avatar;
        this.id = id;
        this.newPassword = newPassword;
        this.nickname = nickname;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public Long getId() {
        return id;
    }

}


