package com.edu.infnet.tp1.domain.models.organizacao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "audit", name = "organizacoes")
@Getter
@Setter
public class Organizacao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false, unique = true)
  private String nome;

  @Column(name = "ativo", nullable = false)
  private boolean ativo;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  // preenche com valores default
  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = OffsetDateTime.now();
    }
  }

  // 1 Organizacao para N usuários
  @OneToMany(mappedBy = "organizacao", fetch = FetchType.LAZY)
  private List<Usuario> usuarios = new ArrayList<>();
}
