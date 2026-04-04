package com.edu.infnet.tp1.application.services.aventura;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.application.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.presentation.dtos.AtualizarAventureiroRequestDto;
import com.edu.infnet.tp1.shared.exceptions.AventureiroNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarDadosAventureiroService {

  private final AventureiroRepository aventureiroRepository;

  public Aventureiro exec(Long id, AtualizarAventureiroRequestDto aventureiroAtualizado) {
    Aventureiro aventureiro = aventureiroRepository.findById(id)
        .orElseThrow(() -> new AventureiroNotFoundException());

    if (aventureiroAtualizado.nome() != null && !aventureiroAtualizado.nome().isEmpty()) {
      aventureiro.setNome(aventureiroAtualizado.nome());
    }

    if (aventureiroAtualizado.classe() != null) {
      aventureiro.setClasse(aventureiroAtualizado.classe());
    }

    if (aventureiroAtualizado.nivel() != null) {
      aventureiro.setNivel(aventureiroAtualizado.nivel().intValue());
    }

    return aventureiroRepository.save(aventureiro);

  }
}
