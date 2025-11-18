package com.ecowork.dto.meta;

import com.ecowork.models.enums.TipoConsumo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MetaCreateDTO {

    @NotNull(message = "O tipo de consumo é obrigatório.")
    private TipoConsumo tipo;

    @Positive(message = "O valor alvo deve ser maior que zero.")
    private BigDecimal valorAlvo;

    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória.")
    @Future(message = "A data de fim deve ser futura.")
    private LocalDate dataFim;

    private Long empresaId;
}