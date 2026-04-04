package com.edu.infnet.tp1.application.repositories.aventura;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.infnet.tp1.domain.enums.NivelPerigo;
import com.edu.infnet.tp1.domain.enums.StatusMissao;
import com.edu.infnet.tp1.domain.models.aventura.Missao;

public interface MissaoRepository extends JpaRepository<Missao, Long> {

  @Query("SELECT m FROM Missao m WHERE " +
      "(:status IS NULL OR m.status = :status) AND " +
      "(:nivelPerigo IS NULL OR m.nivelPerigo = :nivelPerigo)")
  Page<Missao> findComFiltros(
      @Param("status") StatusMissao status,
      @Param("nivelPerigo") NivelPerigo nivelPerigo,
      Pageable pageable);
}
