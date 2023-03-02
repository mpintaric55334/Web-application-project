package hr.fer.progi.sinappsa.backend.service.dto.user;

public class DeleteUserDTO {

    private String nickname;
    private String oldPassword;
    private Long id;

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
