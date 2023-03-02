package hr.fer.progi.sinappsa.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AppUserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double grade;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    public AppUserGrade() {

    }

    public AppUserGrade(Double grade, AppUser appUser) {
        this.grade = grade;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

}
