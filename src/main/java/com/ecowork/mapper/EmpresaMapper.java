package com.ecowork.mapper;

import com.ecowork.dto.empresa.EmpresaCreateDTO;
import com.ecowork.dto.empresa.EmpresaResponseDTO;
import com.ecowork.models.Empresa;

public class EmpresaMapper {

    public static Empresa toEntity(EmpresaCreateDTO dto) {
        return Empresa.builder()
                .nome(dto.getNome())
                .cnpj(dto.getCnpj())
                .endereco(dto.getEndereco())
                .build();
    }

    public static EmpresaResponseDTO toDTO(Empresa empresa) {
        return EmpresaResponseDTO.builder()
                .id(empresa.getId())
                .nome(empresa.getNome())
                .cnpj(empresa.getCnpj())
                .endereco(empresa.getEndereco())
                .build();
    }
}