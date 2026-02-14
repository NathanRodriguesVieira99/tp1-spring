package com.edu.infnet.tp1.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Aventureiro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemoverCompanheiroService {
  private final AventureiroData aventureiroData;

  public Aventureiro exec(UUID id) {
    return aventureiroData.removerCompanheiro(id);
  }
}
