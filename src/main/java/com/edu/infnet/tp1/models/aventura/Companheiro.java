package com.edu.infnet.tp1.models.aventura;

import com.edu.infnet.tp1.enums.Especies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "aventura", name = "companheiros")
@Getter
@Setter
public class Companheiro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "aventureiro_id", nullable = false, unique = true)
  private Aventureiro aventureiro;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "especie", nullable = false)
  private Especies especie;

  @Column(name = "lealdade", nullable = false)
  private Integer lealdade;
}
