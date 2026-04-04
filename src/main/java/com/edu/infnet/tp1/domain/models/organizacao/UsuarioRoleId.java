package com.edu.infnet.tp1.domain.models.organizacao;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UsuarioRoleId implements Serializable {
  @Column(name = "usuario_id")
  private Long usuarioId;

  @Column(name = "role_id")
  private Long roleId;
}
