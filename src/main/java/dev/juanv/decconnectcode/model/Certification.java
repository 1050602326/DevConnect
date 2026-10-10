package dev.juanv.decconnectcode.model;

import jakarta.persistence.*;

@Entity
@Table(name = "certifications")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfessionalProfile profile;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String institution;

    public Certification() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}