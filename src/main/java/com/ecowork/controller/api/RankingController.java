package com.ecowork.controller.api;

import com.ecowork.dto.ranking.RankingUsuarioDTO;
import com.ecowork.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/global")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RankingUsuarioDTO>> rankingGlobal() {
        return ResponseEntity.ok(rankingService.rankingGlobal());
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RankingUsuarioDTO>> rankingPorEmpresa(
            @PathVariable Long empresaId
    ) {
        return ResponseEntity.ok(rankingService.rankingPorEmpresa(empresaId));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingUsuarioDTO> minhaPosicao() {
        return ResponseEntity.ok(rankingService.posicaoUsuarioLogado());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingUsuarioDTO> posicaoUsuario(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(rankingService.posicaoUsuario(usuarioId));
    }
}