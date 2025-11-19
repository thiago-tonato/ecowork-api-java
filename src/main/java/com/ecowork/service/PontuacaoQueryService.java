package com.ecowork.service;

import com.ecowork.dto.pontos.PontuacaoResponseDTO;
import com.ecowork.exception.NotFoundException;
import com.ecowork.mapper.PontuacaoMapper;
import com.ecowork.models.Pontuacao;
import com.ecowork.models.Usuario;
import com.ecowork.repository.PontuacaoRepository;
import com.ecowork.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PontuacaoQueryService {

    private final PontuacaoRepository repository;
    private final UsuarioService usuarioService;
    private final AuthUtils authUtils;

    public List<PontuacaoResponseDTO> historicoUsuarioLogado() {

        Usuario usuario = authUtils.getUsuarioLogado();

        return repository.findByUsuario(usuario)
                .stream()
                .map(PontuacaoMapper::toDTO)
                .toList();
    }

    public List<PontuacaoResponseDTO> historico(Long usuarioId) {

        Usuario usuario = usuarioService.buscarEntity(usuarioId);

        return repository.findByUsuario(usuario)
                .stream()
                .map(PontuacaoMapper::toDTO)
                .toList();
    }

    public PontuacaoResponseDTO buscar(Long id) {

        Pontuacao p = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pontuação não encontrada."));

        return PontuacaoMapper.toDTO(p);
    }
}