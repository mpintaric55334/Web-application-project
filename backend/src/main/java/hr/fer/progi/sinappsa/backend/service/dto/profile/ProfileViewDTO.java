package hr.fer.progi.sinappsa.backend.service.dto.profile;

public class ProfileViewDTO {

    private final String avatar;
    private final String firstName;
    private final String lastName;
    private final String nickname;
    private final String email;

    public ProfileViewDTO(String avatar, String firstName, String lastName, String nickname, String email) {
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
