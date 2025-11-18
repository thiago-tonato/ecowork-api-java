package com.ecowork.dto.pontos;

import com.ecowork.models.enums.TipoPonto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PontuacaoResponseDTO {

    private Long id;
    private Integer quantidade;
    private LocalDateTime data;
    private TipoPonto tipo;
    private Long usuarioId;
}