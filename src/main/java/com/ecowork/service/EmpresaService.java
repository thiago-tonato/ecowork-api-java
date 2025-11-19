package com.ecowork.service;

import com.ecowork.dto.empresa.EmpresaCreateDTO;
import com.ecowork.dto.empresa.EmpresaResponseDTO;
import com.ecowork.dto.empresa.EmpresaUpdateDTO;
import com.ecowork.dto.usuario.UsuarioEmpresaListDTO;
import com.ecowork.exception.NotFoundException;
import com.ecowork.mapper.EmpresaMapper;
import com.ecowork.models.Empresa;
import com.ecowork.models.Usuario;
import com.ecowork.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaResponseDTO criar(EmpresaCreateDTO dto) {
        Empresa empresa = EmpresaMapper.toEntity(dto);
        empresaRepository.save(empresa);
        return EmpresaMapper.toDTO(empresa);
    }

    public EmpresaResponseDTO buscarPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));
        return EmpresaMapper.toDTO(empresa);
    }

    public List<EmpresaResponseDTO> listarTodas() {
        return empresaRepository.findAll()
                .stream()
                .map(EmpresaMapper::toDTO)
                .toList();
    }

    public EmpresaResponseDTO atualizar(Long id, EmpresaUpdateDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        empresa.setNome(dto.getNome());
        empresa.setEndereco(dto.getEndereco());

        empresaRepository.save(empresa);
        return EmpresaMapper.toDTO(empresa);
    }

    public void deletar(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));
        empresaRepository.delete(empresa);
    }

    /**
     * Novo método: lista funcionários da empresa.
     */
    public List<UsuarioEmpresaListDTO> listarUsuarios(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        List<Usuario> usuarios = empresa.getUsuarios();

        return usuarios.stream()
                .map(u -> UsuarioEmpresaListDTO.builder()
                        .id(u.getId())
                        .nome(u.getNome())
                        .email(u.getEmail())
                        .build())
                .toList();
    }
}