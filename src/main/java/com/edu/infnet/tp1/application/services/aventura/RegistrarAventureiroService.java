package com.edu.infnet.tp1.application.services.aventura;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.shared.exceptions.AventureiroInvalidParamsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrarAventureiroService {

  private final AventureiroRepository aventureiroRepository;

  public Aventureiro exec(Aventureiro aventureiro) {
    if (aventureiro.getNome() == null || aventureiro.getNome().isBlank())
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getClasse() == null)
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getNivel() == null || aventureiro.getNivel() <= 0)
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getOrganizacao() == null)
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getUsuarioCadastro() == null)
      throw new AventureiroInvalidParamsException();

    aventureiro.setAtivo(true);
    aventureiro.setCompanheiro(null);

    return aventureiroRepository.save(aventureiro);
  }
}
