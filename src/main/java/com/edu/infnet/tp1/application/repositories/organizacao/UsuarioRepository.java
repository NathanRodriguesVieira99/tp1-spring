package com.edu.infnet.tp1.application.repositories.organizacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.infnet.tp1.domain.models.organizacao.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  @EntityGraph(attributePaths = { "usuarioRoles",
      "usuarioRoles.role",
      "usuarioRoles.role.permissions" })
  @Query("select u from Usuario u where u.id = :id")
  Optional<Usuario> findByIdWithRolesAndPermissions(@Param("id") Long id);

}
