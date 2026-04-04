package com.edu.infnet.tp1.application.services.aventura;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.domain.models.aventura.Companheiro;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.CompanheiroRepository;
import com.edu.infnet.tp1.shared.exceptions.AventureiroNotFoundException;
import com.edu.infnet.tp1.shared.exceptions.CompanheiroInvalidParamsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefinirCompanheiroService {
  private final AventureiroRepository aventureiroRepository;
  private final CompanheiroRepository companheiroRepository;

  public Companheiro exec(Long id, Companheiro companheiro) {
    Aventureiro aventureiro = aventureiroRepository.findById(id).orElseThrow(() -> new AventureiroNotFoundException());

    if (companheiro.getNome() == null || companheiro.getNome().isBlank())
      throw new CompanheiroInvalidParamsException();

    if (companheiro.getLealdade() < 0 || companheiro.getLealdade() > 100)
      throw new CompanheiroInvalidParamsException();

    if (aventureiro.getCompanheiro() == null) {
      companheiro.setAventureiro(aventureiro);
      return companheiroRepository.save(companheiro);
    }

    return aventureiro.getCompanheiro();
  }
}
