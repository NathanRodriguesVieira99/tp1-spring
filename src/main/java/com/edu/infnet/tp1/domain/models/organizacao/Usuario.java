package com.edu.infnet.tp1.domain.models.organizacao;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "audit", name = "usuarios")
@Getter
@Setter
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "senha_hash", nullable = false)
  private String senhaHash;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "ultimo_login_em")
  private OffsetDateTime ultimoLoginEm;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  // preenche com valores default
  @PrePersist
  public void prePersist() {
    OffsetDateTime now = OffsetDateTime.now();
    if (this.createdAt == null) {
      this.createdAt = now;
    }
    if (this.updatedAt == null) {
      this.updatedAt = now;
    }
    if (this.senhaHash == null) {
      this.senhaHash = "";
    }
    if (this.status == null) {
      this.status = "ACTIVE";
    }
  }

  // atualiza os valores default
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = OffsetDateTime.now();
  }

  // N usuários para 1 organizacao
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "organizacao_id", nullable = false)
  private Organizacao organizacao;

  // Associação para a jointable user_roles
  @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
  private Set<UsuarioRole> usuarioRoles = new HashSet<>();

  @Transient
  public Set<Role> getRoles() {
    return usuarioRoles == null ? new HashSet<>()
        : usuarioRoles.stream().map(UsuarioRole::getRole).filter(r -> r != null).collect(Collectors.toSet());
  }
}
