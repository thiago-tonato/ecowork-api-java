package com.ecowork.mapper;

import com.ecowork.dto.usuario.UsuarioCreateDTO;
import com.ecowork.dto.usuario.UsuarioResponseDTO;
import com.ecowork.models.Empresa;
import com.ecowork.models.Usuario;
import com.ecowork.models.enums.RoleUsuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCreateDTO dto, Empresa empresa, String senhaCriptografada) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(senhaCriptografada)
                .empresa(empresa)
                .role(RoleUsuario.EMPLOYEE)
                .pontosTotais(0)
                .build();
    }

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .empresaId(usuario.getEmpresa().getId())
                .pontosTotais(usuario.getPontosTotais())
                .role(usuario.getRole())
                .build();
    }
}