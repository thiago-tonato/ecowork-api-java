package com.ecowork.models;

import com.ecowork.models.enums.RoleUsuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private RoleUsuario role;

    private Integer pontosTotais;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "usuario")
    private List<RegistroConsumo> registros;

    @OneToMany(mappedBy = "usuario")
    private List<Pontuacao> historicoPontos;
}