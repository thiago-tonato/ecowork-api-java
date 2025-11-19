package com.ecowork.dto.usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioEmpresaListDTO {

    private Long id;
    private String nome;
    private String email;
}