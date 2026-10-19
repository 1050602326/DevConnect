
package dev.juanv.decconnectcode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CertificationRequest {

    @NotBlank(message = "El nombre de la certificación es obligatorio")
    @Size(max = 255, message = "La certificación no puede superar 255 caracteres")
    private String name;

    @NotBlank(message = "La institución certificadora es obligatoria")
    @Size(max = 255, message = "La institución certificadora no puede superar 255 caracteres")
    private String institution;

    public CertificationRequest() {
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
