package com.ecowork.dto.meta;

import com.ecowork.models.enums.StatusMeta;
import com.ecowork.models.enums.TipoConsumo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MetaResponseDTO {

    private Long id;
    private TipoConsumo tipo;
    private BigDecimal valorAlvo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusMeta status;
    private Long empresaId;
}