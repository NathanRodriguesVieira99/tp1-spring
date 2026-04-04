package com.edu.infnet.tp1.domain.models.organizacao;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "audit", name = "roles")
@Getter
@Setter
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "descricao")
  private String descricao;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  // preenche com valores default
  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = OffsetDateTime.now();
    }
  }

  // 1 role para N usuários
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private Set<UsuarioRole> usuarioRoles = new HashSet<>();

  // N roles para N role_permissions
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(schema = "audit", name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions = new HashSet<>();

  // N organizacoes para 1 role
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "organizacao_id", nullable = false)
  private Organizacao organizacao;
}
