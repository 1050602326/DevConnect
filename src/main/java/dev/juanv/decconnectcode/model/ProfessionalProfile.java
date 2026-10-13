package dev.juanv.decconnectcode.model;

import jakarta.persistence.*;

@Entity
@Table(name = "professional_profiles")
public class ProfessionalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "professional_id",
            nullable = false,
            unique = true
    )
    private Professional professional;

    @Column(nullable = false)
    private String headline;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 1000)
    private String experience;

    private String city;

    public ProfessionalProfile() {
    }

    public Long getId() {
        return id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}