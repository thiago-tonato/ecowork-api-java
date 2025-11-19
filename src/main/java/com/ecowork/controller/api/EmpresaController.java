package com.ecowork.controller.api;

import com.ecowork.dto.empresa.EmpresaCreateDTO;
import com.ecowork.dto.empresa.EmpresaResponseDTO;
import com.ecowork.dto.empresa.EmpresaUpdateDTO;
import com.ecowork.dto.usuario.UsuarioEmpresaListDTO;
import com.ecowork.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<EmpresaResponseDTO> criar(
            @Valid @RequestBody EmpresaCreateDTO dto
    ) {
        EmpresaResponseDTO empresa = empresaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodas() {
        List<EmpresaResponseDTO> lista = empresaService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<EmpresaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaUpdateDTO dto
    ) {
        EmpresaResponseDTO empresa = empresaService.atualizar(id, dto);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/usuarios")
    public ResponseEntity<List<UsuarioEmpresaListDTO>> listarUsuarios(@PathVariable Long id) {
        List<UsuarioEmpresaListDTO> usuarios = empresaService.listarUsuarios(id);
        return ResponseEntity.ok(usuarios);
    }
}