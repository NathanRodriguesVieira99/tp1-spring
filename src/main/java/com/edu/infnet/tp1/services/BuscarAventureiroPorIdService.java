package com.edu.infnet.tp1.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.shared.exceptions.AventureiroNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarAventureiroPorIdService {

  private final AventureiroData aventureiroData;

  public Aventureiro exec(UUID id) {
    return aventureiroData.buscarAventureiroPorId(id)
        .orElseThrow(() -> new AventureiroNotFoundException());
  }
}
