package hr.fer.progi.sinappsa.backend.service.dto.user;

import hr.fer.progi.sinappsa.backend.entity.AppUser;

public class AppUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;

    private String avatar;

    private boolean isModerator;

    public AppUserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.nickname = appUser.getNickname();
        this.avatar = appUser.getAvatar();
        this.email=appUser.getEmail();
        this.isModerator=appUser.isModerator();
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
