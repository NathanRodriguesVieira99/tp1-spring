package com.edu.infnet.tp1.domain.models.organizacao;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "audit", name = "user_roles")
@Getter
@Setter
public class UsuarioRole {

  @EmbeddedId
  private UsuarioRoleId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("usuarioId")
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("roleId")
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @Column(name = "granted_at", nullable = false)
  private OffsetDateTime grantedAt;

  // preenche com valores default
  @PrePersist
  public void prePersist() {
    if (this.grantedAt == null) {
      this.grantedAt = OffsetDateTime.now();
    }
  }

}
