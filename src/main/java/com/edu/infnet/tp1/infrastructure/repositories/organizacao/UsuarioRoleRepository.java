package com.edu.infnet.tp1.infrastructure.repositories.organizacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.infnet.tp1.domain.models.organizacao.UsuarioRole;
import com.edu.infnet.tp1.domain.models.organizacao.UsuarioRoleId;

public interface UsuarioRoleRepository extends JpaRepository<UsuarioRole, UsuarioRoleId> {

}
