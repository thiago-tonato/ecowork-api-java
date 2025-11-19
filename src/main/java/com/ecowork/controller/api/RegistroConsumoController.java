package com.ecowork.controller.api;

import com.ecowork.dto.registro.RegistroConsumoCreateDTO;
import com.ecowork.dto.registro.RegistroConsumoResponseDTO;
import com.ecowork.models.enums.TipoConsumo;
import com.ecowork.service.RegistroConsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumos")
@RequiredArgsConstructor
public class RegistroConsumoController {

    private final RegistroConsumoService registroService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<RegistroConsumoResponseDTO> criar(
            @Valid @RequestBody RegistroConsumoCreateDTO dto
    ) {
        RegistroConsumoResponseDTO registro = registroService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<RegistroConsumoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(registroService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<RegistroConsumoResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        return ResponseEntity.ok(registroService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<RegistroConsumoResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        return ResponseEntity.ok(registroService.listarPorUsuario(usuarioId, pagina, tamanho));
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyAuthority('COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<RegistroConsumoResponseDTO>> listarPorTipo(
            @PathVariable TipoConsumo tipo,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        return ResponseEntity.ok(registroService.listarPorTipo(tipo, pagina, tamanho));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        registroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}