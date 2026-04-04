package com.edu.infnet.tp1.application.repositories.aventura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.infnet.tp1.domain.models.aventura.ParticipacaoMissao;

public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {

  @Query("SELECT COUNT(p) FROM ParticipacaoMissao p WHERE p.aventureiro.id = :aventureiroId")
  long countByAventureiroId(@Param("aventureiroId") Long aventureiroId);

  @Query("SELECT p FROM ParticipacaoMissao p WHERE p.aventureiro.id = :aventureiroId ORDER BY p.createdAt DESC")
  List<ParticipacaoMissao> findByAventureiroIdOrderByCreatedAtDesc(@Param("aventureiroId") Long aventureiroId);

  @Query("SELECT p FROM ParticipacaoMissao p LEFT JOIN FETCH p.aventureiro WHERE p.missao.id = :missaoId")
  List<ParticipacaoMissao> findByMissaoIdComAventureiro(@Param("missaoId") Long missaoId);
}
