package com.ecowork.models;

import com.ecowork.models.enums.TipoConsumo;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro_consumo")
public class RegistroConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoConsumo tipo;

    private BigDecimal valor;

    private LocalDateTime dataRegistro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "meta_id")
    private MetaSustentavel meta;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
}