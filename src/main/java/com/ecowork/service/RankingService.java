package com.ecowork.service;

import com.ecowork.dto.ranking.RankingUsuarioDTO;
import com.ecowork.exception.NotFoundException;
import com.ecowork.models.Usuario;
import com.ecowork.repository.UsuarioRepository;
import com.ecowork.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final AuthUtils authUtils;

    public List<RankingUsuarioDTO> rankingGlobal() {
        return usuarioRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Usuario::getPontosTotais).reversed())
                .map(this::toRankingDTO)
                .toList();
    }

    public List<RankingUsuarioDTO> rankingPorEmpresa(Long empresaId) {

        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getEmpresa().getId().equals(empresaId))
                .sorted(Comparator.comparingInt(Usuario::getPontosTotais).reversed())
                .map(this::toRankingDTO)
                .toList();
    }

    public RankingUsuarioDTO posicaoUsuarioLogado() {

        Usuario u = authUtils.getUsuarioLogado();
        return posicaoUsuario(u.getId());
    }

    public RankingUsuarioDTO posicaoUsuario(Long userId) {

        Usuario usuario = usuarioService.buscarEntity(userId);

        List<Usuario> ordenados = usuarioRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Usuario::getPontosTotais).reversed())
                .toList();

        int posicao = ordenados.indexOf(usuario) + 1;

        if (posicao == 0) throw new NotFoundException("Usuário não encontrado no ranking.");

        return RankingUsuarioDTO.builder()
                .usuarioId(usuario.getId())
                .empresa(usuario.getEmpresa().getNome())
                .nome(usuario.getNome())
                .pontosTotais(usuario.getPontosTotais())
                .posicao(posicao)
                .build();
    }

    private RankingUsuarioDTO toRankingDTO(Usuario u) {
        return RankingUsuarioDTO.builder()
                .usuarioId(u.getId())
                .nome(u.getNome())
                .empresa(u.getEmpresa().getNome())
                .pontosTotais(u.getPontosTotais())
                .build();
    }
}