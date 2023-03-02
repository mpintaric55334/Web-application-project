package hr.fer.progi.sinappsa.backend.entity;

import hr.fer.progi.sinappsa.backend.entity.enums.NoticeCategory;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Notice {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Enumerated(EnumType.STRING)
    private NoticeCategory category;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;

    private boolean isActive;

    private boolean isAskingHelp;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    private Timestamp creationTime;

    @OneToMany(mappedBy = "notice")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Query> queries;

    public Notice() {
    }

    public Notice(String title, String description, Course course, NoticeCategory category, Major major,
        boolean isActive, boolean isAskingHelp, AppUser appUser) {
        this.title = title;
        this.description = description;
        this.course = course;
        this.category = category;
        this.major = major;
        this.isActive = isActive;
        this.isAskingHelp = isAskingHelp;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public NoticeCategory getCategory() {
        return category;
    }

    public void setCategory(NoticeCategory category) {
        this.category = category;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsAskingHelp() {
        return isAskingHelp;
    }

    public void setIsAskingHelp(boolean isAskingHelp) {
        this.isAskingHelp = isAskingHelp;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

}
