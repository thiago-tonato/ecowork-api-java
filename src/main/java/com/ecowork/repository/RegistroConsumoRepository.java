package com.ecowork.repository;

import com.ecowork.models.RegistroConsumo;
import com.ecowork.models.Usuario;
import com.ecowork.models.enums.TipoConsumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroConsumoRepository extends JpaRepository<RegistroConsumo, Long> {

    List<RegistroConsumo> findByUsuario(Usuario usuario);

    List<RegistroConsumo> findByTipo(TipoConsumo tipo);

    Page<RegistroConsumo> findByUsuario(Usuario usuario, Pageable pageable);

    Page<RegistroConsumo> findByTipo(TipoConsumo tipo, Pageable pageable);

}