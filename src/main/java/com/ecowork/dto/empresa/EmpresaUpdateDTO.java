package com.ecowork.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaUpdateDTO {

    @NotBlank(message = "O nome da empresa é obrigatório.")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório.")
    private String endereco;
}