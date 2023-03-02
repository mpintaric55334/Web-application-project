package hr.fer.progi.sinappsa.backend.service.dto.user;

public class ChangeUserAttributesDTO {

    String nickname;
    String password;
    String avatar;

    public ChangeUserAttributesDTO(String nickname, String password, String avatar) {
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
    }

}
