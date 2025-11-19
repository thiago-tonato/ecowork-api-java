package com.ecowork.service;

import com.ecowork.dto.usuario.*;
import com.ecowork.exception.BusinessException;
import com.ecowork.exception.NotFoundException;
import com.ecowork.mapper.UsuarioMapper;
import com.ecowork.models.Empresa;
import com.ecowork.models.Usuario;
import com.ecowork.repository.EmpresaRepository;
import com.ecowork.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO criar(UsuarioCreateDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail()))
            throw new BusinessException("Já existe um usuário com este e-mail.");

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        Usuario usuario = UsuarioMapper.toEntity(dto, empresa, passwordEncoder.encode(dto.getSenha()));
        usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioResponseDTO buscarPorIdComRegra(Long id, String emailLogado) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (usuario.getEmail().equals(emailLogado))
            return UsuarioMapper.toDTO(usuario);

        Usuario admin = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new NotFoundException("Usuário autenticado não encontrado."));

        switch (admin.getRole()) {
            case SYSTEM_ADMIN, COMPANY_ADMIN -> {
                return UsuarioMapper.toDTO(usuario);
            }
            default -> throw new BusinessException("Você não tem permissão para visualizar este usuário.");
        }
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto, String emailLogado) {

        Usuario usuario = buscarEntity(id);

        boolean ehOProprio = usuario.getEmail().equals(emailLogado);

        Usuario logado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new NotFoundException("Usuário autenticado não encontrado."));

        boolean ehAdmin = logado.getRole().name().contains("ADMIN");

        if (!ehOProprio && !ehAdmin)
            throw new BusinessException("Você não pode atualizar este usuário.");

        usuario.setNome(dto.getNome());
        usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = buscarEntity(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public UsuarioResponseDTO buscarPorEmailDTO(String email) {
        return UsuarioMapper.toDTO(
                usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado."))
        );
    }

    public List<UsuarioEmpresaListDTO> listarPorEmpresa(Long empresaId) {

        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        return empresa.getUsuarios().stream()
                .map(u -> UsuarioEmpresaListDTO.builder()
                        .id(u.getId())
                        .nome(u.getNome())
                        .email(u.getEmail())
                        .build()
                ).toList();
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }
}