package com.ecowork.dto.ranking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankingUsuarioDTO {

    private Long usuarioId;
    private String nome;
    private Integer pontosTotais;
    private String empresa;
    private Integer posicao;
}