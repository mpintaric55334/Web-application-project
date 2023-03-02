package hr.fer.progi.sinappsa.backend.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String nickname;

    private String avatar;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(length = 64)
    private String verificationCode;

    private boolean isRegistered = false;//default=false

    private boolean isModerator = false;//default=false

    private Long rankingPoints = 0L;

    private Long numberOfAcceptedNotices = 0L;

    private Double averageGrade = 0D;

    @OneToMany(mappedBy = "appUser")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AppUserGrade> grades = new ArrayList<>();

    @OneToMany(mappedBy = "appUser")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Notice> notice = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Query> receivedQueries = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Query> sentQueries = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(String firstName, String email, String lastName, String nickname, String password, String avatar) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }

    public Long getRankingPoints() {
        return rankingPoints;
    }

    public void setRankingPoints(Long rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public Long getNumberOfAcceptedNotices() {
        return numberOfAcceptedNotices;
    }

    public void setNumberOfAcceptedNotices(Long numberOfAcceptedNotices) {
        this.numberOfAcceptedNotices = numberOfAcceptedNotices;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<AppUserGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<AppUserGrade> grades) {
        this.grades = grades;
    }

    public List<Notice> getNotice() {
        return notice;
    }

    public void setNotice(List<Notice> notice) {
        this.notice = notice;
    }

    public List<Query> getReceivedQueries() {
        return receivedQueries;
    }

    public void setReceivedQueries(List<Query> receivedQueries) {
        this.receivedQueries = receivedQueries;
    }

    public List<Query> getSentQueries() {
        return sentQueries;
    }

    public void setSentQueries(List<Query> sentQueries) {
        this.sentQueries = sentQueries;
    }

}
