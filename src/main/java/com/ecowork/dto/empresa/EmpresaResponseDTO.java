package com.ecowork.dto.empresa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpresaResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String endereco;
}