package com.ecowork.service;

import com.ecowork.dto.meta.MetaCreateDTO;
import com.ecowork.dto.meta.MetaResponseDTO;
import com.ecowork.exception.NotFoundException;
import com.ecowork.mapper.MetaMapper;
import com.ecowork.models.Empresa;
import com.ecowork.models.MetaSustentavel;
import com.ecowork.models.enums.StatusMeta;
import com.ecowork.repository.EmpresaRepository;
import com.ecowork.repository.MetaSustentavelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaSustentavelRepository metaRepository;
    private final EmpresaRepository empresaRepository;

    public MetaResponseDTO criar(MetaCreateDTO dto) {

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        MetaSustentavel meta = MetaMapper.toEntity(dto, empresa);
        metaRepository.save(meta);

        return MetaMapper.toDTO(meta);
    }

    public MetaResponseDTO buscarPorId(Long id) {
        return MetaMapper.toDTO(buscarEntity(id));
    }

    public List<MetaResponseDTO> listarTodas() {
        return metaRepository.findAll().stream()
                .map(MetaMapper::toDTO)
                .toList();
    }

    public List<MetaResponseDTO> listarPorEmpresa(Long empresaId) {

        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        return metaRepository.findByEmpresa(empresa).stream()
                .map(MetaMapper::toDTO)
                .toList();
    }

    public List<MetaResponseDTO> listarPorStatus(StatusMeta status) {
        return metaRepository.findByStatus(status).stream()
                .map(MetaMapper::toDTO)
                .toList();
    }

    public MetaResponseDTO atualizar(Long id, MetaCreateDTO dto) {

        MetaSustentavel meta = buscarEntity(id);

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        meta.setTipo(dto.getTipo());
        meta.setValorAlvo(dto.getValorAlvo());
        meta.setDataInicio(dto.getDataInicio());
        meta.setDataFim(dto.getDataFim());
        meta.setEmpresa(empresa);

        metaRepository.save(meta);
        return MetaMapper.toDTO(meta);
    }

    public MetaResponseDTO atualizarStatus(Long id, StatusMeta status) {

        MetaSustentavel meta = buscarEntity(id);
        meta.setStatus(status);

        metaRepository.save(meta);
        return MetaMapper.toDTO(meta);
    }

    public void deletar(Long id) {
        metaRepository.delete(buscarEntity(id));
    }

    private MetaSustentavel buscarEntity(Long id) {
        return metaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Meta não encontrada."));
    }
}