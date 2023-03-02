package hr.fer.progi.sinappsa.backend.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String majorName;

    @OneToMany(mappedBy = "major")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "major")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Notice> notice;

    public Major() {

    }
    public Major(Long id, String majorName) {
        this.id = id;
        this.majorName = majorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

}
