package com.edu.infnet.tp1.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Aventureiro;

@Service
public class RegistrarAventureiroService {
  @Autowired
  AventureiroData aventureiroData;

  public Aventureiro exec(Aventureiro aventureiro) {
    if (aventureiro.getId() == null)
      aventureiro.setId(UUID.randomUUID());

    if (aventureiro.getNome() == null || aventureiro.getNome().isBlank())
      throw new IllegalArgumentException("O nome do aventureiro não pode ser vazio!");

    if (aventureiro.getClasse() == null)
      throw new IllegalArgumentException("A classe não pode ser vazia!");

    if (aventureiro.getNivel() == null || aventureiro.getNivel().intValue() <= 0)
      throw new IllegalArgumentException("O nível não pode ser menor do que 1 ou vazio!");

    if (aventureiro.getAtivo() == null || aventureiro.getAtivo() == false)
      aventureiro.setAtivo(true);

    // Optional.empty() -> forca o campo 'companheiro' a ser vazio
    aventureiro.setCompanheiro(Optional.empty());

    return aventureiroData.registrar(aventureiro);
  }
}
