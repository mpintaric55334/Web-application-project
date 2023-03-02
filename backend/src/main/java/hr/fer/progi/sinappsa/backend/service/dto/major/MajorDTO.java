package hr.fer.progi.sinappsa.backend.service.dto.major;

import hr.fer.progi.sinappsa.backend.entity.Major;

public class MajorDTO {

    private Long id;

    private String majorName;

    public MajorDTO(Major major) {
        this.id = major.getId();
        this.majorName = major.getMajorName();
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
