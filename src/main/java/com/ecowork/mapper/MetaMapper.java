package com.ecowork.mapper;

import com.ecowork.dto.meta.MetaCreateDTO;
import com.ecowork.dto.meta.MetaResponseDTO;
import com.ecowork.models.Empresa;
import com.ecowork.models.MetaSustentavel;
import com.ecowork.models.enums.StatusMeta;

public class MetaMapper {

    public static MetaSustentavel toEntity(MetaCreateDTO dto, Empresa empresa) {
        return MetaSustentavel.builder()
                .tipo(dto.getTipo())
                .valorAlvo(dto.getValorAlvo())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .empresa(empresa)
                .status(StatusMeta.ATIVA)
                .build();
    }

    public static MetaResponseDTO toDTO(MetaSustentavel meta) {
        return MetaResponseDTO.builder()
                .id(meta.getId())
                .tipo(meta.getTipo())
                .valorAlvo(meta.getValorAlvo())
                .dataInicio(meta.getDataInicio())
                .dataFim(meta.getDataFim())
                .status(meta.getStatus())
                .empresaId(meta.getEmpresa().getId())
                .build();
    }
}