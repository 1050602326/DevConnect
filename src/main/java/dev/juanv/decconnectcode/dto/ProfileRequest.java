package dev.juanv.decconnectcode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileRequest {




    @NotBlank(message = "El titulo profecional es obligatoria")
    @Size(max = 255, message = "El titulo no puede superar mas de 255 caracteres")
    private String headline;

    @NotBlank(message = "La descripcion es obligatoria")
    private String description;


    @NotBlank(message = "La experiencia es obligatoria")
    private String experience;

    @Size(max = 255,message = "No puede pasar mas de 255 caracteres")
    private String city;

    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }

    public String getExperience() {
        return experience;
    }

    public String getCity() {
        return city;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
