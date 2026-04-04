package com.edu.infnet.tp1.infrastructure.repositories.aventura;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.infnet.tp1.domain.enums.Classes;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;

public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

  @Query("SELECT a FROM Aventureiro a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
  Page<Aventureiro> findByNomeContaining(@Param("nome") String nome, Pageable pageable);

  @Query("SELECT a FROM Aventureiro a WHERE " +
      "(:classe IS NULL OR a.classe = :classe) AND " +
      "(:ativo IS NULL OR a.ativo = :ativo) AND " +
      "(:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)")
  Page<Aventureiro> findComFiltros(
      @Param("classe") Classes classe,
      @Param("ativo") Boolean ativo,
      @Param("nivelMinimo") Integer nivelMinimo,
      Pageable pageable);

  @Query("SELECT COUNT(a) FROM Aventureiro a WHERE " +
      "(:classe IS NULL OR a.classe = :classe) AND " +
      "(:ativo IS NULL OR a.ativo = :ativo) AND " +
      "(:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)")
  long countComFiltros(
      @Param("classe") Classes classe,
      @Param("ativo") Boolean ativo,
      @Param("nivelMinimo") Integer nivelMinimo);

  @Query("SELECT a FROM Aventureiro a LEFT JOIN FETCH a.companheiro WHERE a.id = :id")
  Optional<Aventureiro> findByIdComCompanheiro(@Param("id") Long id);
}
