package com.ecowork.mapper;

import com.ecowork.dto.registro.RegistroConsumoCreateDTO;
import com.ecowork.dto.registro.RegistroConsumoResponseDTO;
import com.ecowork.models.MetaSustentavel;
import com.ecowork.models.RegistroConsumo;
import com.ecowork.models.Sensor;
import com.ecowork.models.Usuario;

import java.time.LocalDateTime;

public class RegistroConsumoMapper {

    public static RegistroConsumo toEntity(
            RegistroConsumoCreateDTO dto,
            Usuario usuario,
            MetaSustentavel meta,
            Sensor sensor
    ) {
        return RegistroConsumo.builder()
                .tipo(dto.getTipo())
                .valor(dto.getValor())
                .usuario(usuario)
                .meta(meta)
                .sensor(sensor)
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static RegistroConsumoResponseDTO toDTO(RegistroConsumo r) {
        return RegistroConsumoResponseDTO.builder()
                .id(r.getId())
                .tipo(r.getTipo())
                .valor(r.getValor())
                .dataRegistro(r.getDataRegistro())
                .usuarioId(r.getUsuario().getId())
                .metaId(r.getMeta() != null ? r.getMeta().getId() : null)
                .sensorId(r.getSensor() != null ? r.getSensor().getId() : null)
                .build();
    }
}