package com.ecowork.controller.api;

import com.ecowork.dto.sensor.SensorCreateDTO;
import com.ecowork.dto.sensor.SensorResponseDTO;
import com.ecowork.dto.sensor.SensorUpdateDTO;
import com.ecowork.service.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensores")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<SensorResponseDTO> criar(
            @Valid @RequestBody SensorCreateDTO dto
    ) {
        SensorResponseDTO sensor = sensorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<SensorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sensorService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<List<SensorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(sensorService.listarTodos());
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<List<SensorResponseDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(sensorService.listarPorEmpresaId(empresaId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<SensorResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SensorUpdateDTO dto
    ) {
        return ResponseEntity.ok(sensorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sensorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}