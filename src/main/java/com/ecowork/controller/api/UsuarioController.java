package com.ecowork.controller.api;

import com.ecowork.dto.usuario.*;
import com.ecowork.mapper.UsuarioMapper;
import com.ecowork.models.Usuario;
import com.ecowork.security.JwtService;
import com.ecowork.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> criar(
            @Valid @RequestBody UsuarioCreateDTO dto
    ) {
        UsuarioResponseDTO criado = usuarioService.criar(dto);
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + criado.getId()))
                .body(criado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable Long id,
            Principal principal
    ) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorIdComRegra(id, principal.getName());
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto,
            Principal principal
    ) {
        UsuarioResponseDTO atualizado = usuarioService.atualizar(id, dto, principal.getName());
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmailDTO(email));
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<List<UsuarioEmpresaListDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(usuarioService.listarPorEmpresa(empresaId));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> me(Principal principal) {
        return ResponseEntity.ok(usuarioService.buscarPorEmailDTO(principal.getName()));
    }
}