package com.ecowork.service;

import com.ecowork.dto.registro.RegistroConsumoCreateDTO;
import com.ecowork.dto.registro.RegistroConsumoResponseDTO;
import com.ecowork.exception.NotFoundException;
import com.ecowork.mapper.RegistroConsumoMapper;
import com.ecowork.models.*;
import com.ecowork.models.enums.TipoConsumo;
import com.ecowork.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistroConsumoService {

    private final RegistroConsumoRepository registroRepository;
    private final UsuarioRepository usuarioRepository;
    private final MetaSustentavelRepository metaRepository;
    private final SensorRepository sensorRepository;
    private final PontuacaoService pontuacaoService;

    public RegistroConsumoResponseDTO criar(RegistroConsumoCreateDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        MetaSustentavel meta = null;
        if (dto.getMetaId() != null) {
            meta = metaRepository.findById(dto.getMetaId())
                    .orElseThrow(() -> new NotFoundException("Meta não encontrada."));
        }

        Sensor sensor = null;
        if (dto.getSensorId() != null) {
            sensor = sensorRepository.findById(dto.getSensorId())
                    .orElseThrow(() -> new NotFoundException("Sensor não encontrado."));
        }

        RegistroConsumo registro = RegistroConsumoMapper.toEntity(dto, usuario, meta, sensor);
        registroRepository.save(registro);

        int pontos = calcularPontos(registro);
        pontuacaoService.registrarPontos(usuario, pontos);

        usuarioRepository.save(usuario);

        return RegistroConsumoMapper.toDTO(registro);
    }

    private int calcularPontos(RegistroConsumo r) {
        return switch (r.getTipo()) {
            case ENERGIA -> r.getValor().multiply(java.math.BigDecimal.valueOf(10)).intValue();
            case PAPEL -> r.getValor().multiply(java.math.BigDecimal.valueOf(3)).intValue();
            case TRANSPORTE -> r.getValor().intValue();
        };
    }

    public RegistroConsumoResponseDTO buscarPorId(Long id) {
        RegistroConsumo r = registroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro não encontrado."));
        return RegistroConsumoMapper.toDTO(r);
    }

    public Page<RegistroConsumoResponseDTO> listarTodos(int pagina, int tamanho) {
        Page<RegistroConsumo> page = registroRepository.findAll(PageRequest.of(pagina, tamanho));
        return page.map(RegistroConsumoMapper::toDTO);
    }

    public Page<RegistroConsumoResponseDTO> listarPorUsuario(Long usuarioId, int pagina, int tamanho) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        Page<RegistroConsumo> page = registroRepository.findByUsuario(
                usuario,
                PageRequest.of(pagina, tamanho)
        );

        return page.map(RegistroConsumoMapper::toDTO);
    }

    public Page<RegistroConsumoResponseDTO> listarPorTipo(TipoConsumo tipo, int pagina, int tamanho) {
        Page<RegistroConsumo> page = registroRepository.findByTipo(
                tipo,
                PageRequest.of(pagina, tamanho)
        );

        return page.map(RegistroConsumoMapper::toDTO);
    }

    public void deletar(Long id) {
        RegistroConsumo r = registroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro não encontrado."));
        registroRepository.delete(r);
    }
}