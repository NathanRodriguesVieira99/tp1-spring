package com.edu.infnet.tp1.infrastructure.repositories.organizacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.infnet.tp1.domain.models.organizacao.Organizacao;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {

}
