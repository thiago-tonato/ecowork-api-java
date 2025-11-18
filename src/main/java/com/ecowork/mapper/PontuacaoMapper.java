package com.ecowork.mapper;

import com.ecowork.dto.pontos.PontuacaoResponseDTO;
import com.ecowork.models.Pontuacao;

public class PontuacaoMapper {

    public static PontuacaoResponseDTO toDTO(Pontuacao p) {
        return PontuacaoResponseDTO.builder()
                .id(p.getId())
                .quantidade(p.getQuantidade())
                .data(p.getData())
                .tipo(p.getTipo())
                .usuarioId(p.getUsuario().getId())
                .build();
    }
}