
package dev.juanv.decconnectcode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SkillRequest {

    @NotBlank(message = "El nombre de la habilidad es obligatorio")
    @Size(max = 255, message = "La habilidad no puede superar 255 caracteres")
    private String name;

    public SkillRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
