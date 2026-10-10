package dev.juanv.decconnectcode.model;

import jakarta.persistence.*;

@Entity
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfessionalProfile profile;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String program;

    public Education() {
    }

    public Long getId() {
        return id;
    }

    public ProfessionalProfile getProfile() {
        return profile;
    }

    public void setProfile(ProfessionalProfile profile) {
        this.profile = profile;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}