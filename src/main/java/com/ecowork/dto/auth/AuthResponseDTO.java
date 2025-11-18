package com.ecowork.dto.auth;

import com.ecowork.models.enums.RoleUsuario;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

    private String token;
    private Long usuarioId;
    private String nome;
    private RoleUsuario role;
}