package com.ecowork.dto.usuario;

import com.ecowork.models.enums.RoleUsuario;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private RoleUsuario role;
    private Integer pontosTotais;
    private Long empresaId;
}