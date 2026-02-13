package com.edu.infnet.tp1.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Companheiro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefinirCompanheiroService {
  private final AventureiroData aventureiroData;

  public Companheiro exec(UUID id, Companheiro companheiro) {
    return aventureiroData.definirCompanheiro(id, companheiro);
  }
}
