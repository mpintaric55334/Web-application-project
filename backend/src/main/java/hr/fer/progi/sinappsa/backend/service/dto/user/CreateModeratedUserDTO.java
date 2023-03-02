package hr.fer.progi.sinappsa.backend.service.dto.user;

public class CreateModeratedUserDTO {

    public String nickname;
    public Integer rankingPoints;

    public CreateModeratedUserDTO() {
    }

    public CreateModeratedUserDTO(String nickname, Integer rankingPoints) {
        this.nickname = nickname;
        this.rankingPoints = rankingPoints;
    }

}
