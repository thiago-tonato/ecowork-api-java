package com.ecowork.mapper;

import com.ecowork.dto.sensor.SensorCreateDTO;
import com.ecowork.dto.sensor.SensorResponseDTO;
import com.ecowork.models.Empresa;
import com.ecowork.models.Sensor;

public class SensorMapper {

    public static Sensor toEntity(SensorCreateDTO dto, Empresa empresa) {
        return Sensor.builder()
                .tipoSensor(dto.getTipoSensor())
                .localizacao(dto.getLocalizacao())
                .empresa(empresa)
                .build();
    }

    public static SensorResponseDTO toDTO(Sensor s) {
        return SensorResponseDTO.builder()
                .id(s.getId())
                .tipoSensor(s.getTipoSensor())
                .localizacao(s.getLocalizacao())
                .empresaId(s.getEmpresa().getId())
                .build();
    }
}