package com.ecowork.controller.api;

import com.ecowork.dto.pontos.PontuacaoResponseDTO;
import com.ecowork.service.PontuacaoQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos")
@RequiredArgsConstructor
public class PontuacaoController {

    private final PontuacaoQueryService queryService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PontuacaoResponseDTO>> minhasPontuacoes() {
        return ResponseEntity.ok(queryService.historicoUsuarioLogado());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<List<PontuacaoResponseDTO>> historicoPorUsuario(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(queryService.historico(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PontuacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(queryService.buscar(id));
    }
}