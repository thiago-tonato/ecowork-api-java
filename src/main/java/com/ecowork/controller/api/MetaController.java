package com.ecowork.controller.api;

import com.ecowork.dto.meta.MetaCreateDTO;
import com.ecowork.dto.meta.MetaResponseDTO;
import com.ecowork.models.enums.StatusMeta;
import com.ecowork.service.MetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService metaService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<MetaResponseDTO> criar(@Valid @RequestBody MetaCreateDTO dto) {
        MetaResponseDTO meta = metaService.criar(dto);
        return ResponseEntity.created(URI.create("/api/metas/" + meta.getId())).body(meta);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MetaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(metaService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<List<MetaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(metaService.listarTodas());
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<List<MetaResponseDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(metaService.listarPorEmpresa(empresaId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<List<MetaResponseDTO>> listarPorStatus(@PathVariable StatusMeta status) {
        return ResponseEntity.ok(metaService.listarPorStatus(status));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<MetaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MetaCreateDTO dto
    ) {
        return ResponseEntity.ok(metaService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<MetaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusMeta status
    ) {
        return ResponseEntity.ok(metaService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}