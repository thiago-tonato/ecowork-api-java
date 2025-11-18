package com.ecowork.dto.registro;

import com.ecowork.models.enums.TipoConsumo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegistroConsumoCreateDTO {

    @NotNull(message = "O tipo de consumo é obrigatório.")
    private TipoConsumo tipo;

    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    private Long usuarioId;
    private Long metaId;
    private Long sensorId;
}