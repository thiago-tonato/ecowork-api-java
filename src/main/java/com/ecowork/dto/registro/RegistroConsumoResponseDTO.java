package com.ecowork.dto.registro;

import com.ecowork.models.enums.TipoConsumo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RegistroConsumoResponseDTO {

    private Long id;
    private TipoConsumo tipo;
    private BigDecimal valor;
    private LocalDateTime dataRegistro;
    private Long usuarioId;
    private Long metaId;
    private Long sensorId;
}