package com.edu.infnet.tp1.application.repositories.organizacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.infnet.tp1.domain.models.organizacao.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  @EntityGraph(attributePaths = { "permissions" })
  @Override
  Optional<Role> findById(Long id);
}
