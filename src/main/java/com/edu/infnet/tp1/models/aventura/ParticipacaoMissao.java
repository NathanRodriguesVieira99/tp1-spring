package com.edu.infnet.tp1.models.aventura;

import java.math.BigDecimal;

import com.edu.infnet.tp1.enums.PapelMissao;
import com.edu.infnet.tp1.enums.StatusMissao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "aventura", name = "participacoes", uniqueConstraints = @UniqueConstraint(columnNames = { "missao_id",
    "aventureiro_id" }))
@Getter
@Setter
public class ParticipacaoMissao extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "missao_id", nullable = false)
  private Missao missao;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "aventureiro_id", nullable = false)
  private Aventureiro aventureiro;

  @Column(name = "papel", nullable = false)
  private PapelMissao papel;

  @Column(name = "recompensa_ouro")
  private BigDecimal recompensaOuro;

  @Column(name = "destaque", nullable = false)
  private boolean destaque;

  @PrePersist
  public void validateParticipation() {
    if (this.aventureiro != null && !this.aventureiro.isAtivo()) {
      throw new IllegalStateException("Aventureiro inativo não pode participar de missões");
    }
    if (this.missao != null) {
      StatusMissao s = this.missao.getStatus();
      if (s == StatusMissao.CONCLUIDA || s == StatusMissao.CANCELADA) {
        throw new IllegalStateException("Missão não aceita novos participantes");
      }
      if (this.missao.getOrganizacao() != null && this.aventureiro != null &&
          !this.missao.getOrganizacao().getId().equals(this.aventureiro.getOrganizacao().getId())) {
        throw new IllegalStateException(
            "Organizações diferentes: missão e aventureiro devem pertencer à mesma organização");
      }
    }
  }
}
