package hr.fer.progi.sinappsa.backend.service.dto.rankings;

import hr.fer.progi.sinappsa.backend.entity.AppUser;

public class RankingsDTO {

    private Long id;
    private Long rank;
    private String studentNickname;
    private Long numberOfAcceptedNotices;
    private Long rankingPoints;
    private Double averageGrade;
    private String avatar;

    public RankingsDTO(AppUser appUser, Long rank) {
        this.id = appUser.getId();
        this.rank = rank;
        this.studentNickname = appUser.getNickname();
        this.numberOfAcceptedNotices = appUser.getNumberOfAcceptedNotices();
        this.rankingPoints = appUser.getRankingPoints();
        this.averageGrade = appUser.getAverageGrade();
        this.avatar = appUser.getAvatar();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getStudentNickname() {
        return studentNickname;
    }

    public void setStudentNickname(String studentNickname) {
        this.studentNickname = studentNickname;
    }

    public Long getNumberOfAcceptedNotices() {
        return numberOfAcceptedNotices;
    }

    public void setNumberOfAcceptedNotices(Long numberOfAcceptedNotices) {
        this.numberOfAcceptedNotices = numberOfAcceptedNotices;
    }

    public Long getRankingPoints() {
        return rankingPoints;
    }

    public void setRankingPoints(Long rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
