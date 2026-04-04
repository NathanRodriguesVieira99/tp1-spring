package com.edu.infnet.tp1.domain.models.aventura;

import com.edu.infnet.tp1.domain.enums.Classes;
import com.edu.infnet.tp1.domain.models.organizacao.Organizacao;
import com.edu.infnet.tp1.domain.models.organizacao.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "aventura", name = "aventureiros")
@Getter
@Setter
public class Aventureiro extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "organizacao_id", nullable = false)
  private Organizacao organizacao;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_cadastro_id", nullable = false)
  private Usuario usuarioCadastro;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "classe", nullable = false)
  private Classes classe;

  @Column(name = "nivel", nullable = false)
  private Integer nivel;

  @Column(name = "ativo", nullable = false)
  private boolean ativo = true;

  @OneToOne(mappedBy = "aventureiro", orphanRemoval = true)
  private Companheiro companheiro;
}
