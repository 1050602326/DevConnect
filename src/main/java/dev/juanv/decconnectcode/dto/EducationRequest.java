
package dev.juanv.decconnectcode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EducationRequest {

    @NotBlank(message = "La institución es obligatoria")
    @Size(max = 255, message = "La institución no puede superar 255 caracteres")
    private String institution;

    @NotBlank(message = "El programa de estudio es obligatorio")
    @Size(max = 255, message = "El programa no puede superar 255 caracteres")
    private String program;

    public EducationRequest() {
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
