package com.edu.infnet.tp1.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.shared.exceptions.AventureiroInvalidParamsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrarAventureiroService {

  private final AventureiroData aventureiroData;

  public Aventureiro exec(Aventureiro aventureiro) {
    if (aventureiro.getId() == null)
      aventureiro.setId(UUID.randomUUID());

    if (aventureiro.getNome() == null || aventureiro.getNome().isBlank())
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getClasse() == null)
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getNivel() == null || aventureiro.getNivel() <= 0)
      throw new AventureiroInvalidParamsException();

    if (aventureiro.getAtivo() == null || aventureiro.getAtivo() == false)
      aventureiro.setAtivo(true);

    // força o campo 'companheiro' a ser vazio
    aventureiro.setCompanheiro(null);

    return aventureiroData.registrar(aventureiro);
  }
}
