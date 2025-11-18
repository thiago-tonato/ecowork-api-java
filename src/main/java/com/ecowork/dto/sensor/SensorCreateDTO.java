package com.ecowork.dto.sensor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SensorCreateDTO {

    @NotBlank(message = "O tipo de sensor é obrigatório.")
    private String tipoSensor;

    @NotBlank(message = "A localização é obrigatória.")
    private String localizacao;

    private Long empresaId;
}