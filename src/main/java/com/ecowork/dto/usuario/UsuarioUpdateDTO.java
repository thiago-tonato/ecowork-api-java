package com.ecowork.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioUpdateDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
}