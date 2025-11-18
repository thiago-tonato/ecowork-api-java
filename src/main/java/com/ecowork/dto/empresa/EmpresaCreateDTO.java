package com.ecowork.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmpresaCreateDTO {

    @NotBlank(message = "O nome da empresa é obrigatório.")
    private String nome;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos.")
    private String cnpj;

    @NotBlank(message = "O endereço é obrigatório.")
    private String endereco;
}