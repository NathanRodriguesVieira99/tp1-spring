package com.edu.infnet.tp1.models.aventura;

import java.time.OffsetDateTime;

import com.edu.infnet.tp1.enums.NivelPerigo;
import com.edu.infnet.tp1.enums.StatusMissao;
import com.edu.infnet.tp1.models.organizacao.Organizacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "aventura", name = "missoes")
@Getter
@Setter
public class Missao extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "organizacao_id", nullable = false)
  private Organizacao organizacao;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "nivel_perigo", nullable = false)
  private NivelPerigo nivelPerigo;

  @Column(name = "status", nullable = false)
  private StatusMissao status;

  @Column(name = "data_inicio")
  private OffsetDateTime dataInicio;

  @Column(name = "data_termino")
  private OffsetDateTime dataTermino;
}
