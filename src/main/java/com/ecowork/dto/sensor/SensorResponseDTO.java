package com.ecowork.dto.sensor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorResponseDTO {

    private Long id;
    private String tipoSensor;
    private String localizacao;
    private Long empresaId;
}