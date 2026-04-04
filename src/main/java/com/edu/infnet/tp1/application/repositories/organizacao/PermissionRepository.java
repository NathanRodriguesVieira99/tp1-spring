package com.edu.infnet.tp1.application.repositories.organizacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.infnet.tp1.domain.models.organizacao.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
