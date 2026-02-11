package com.edu.infnet.tp1.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Aventureiro;

@Service
public class BuscarAventureiroPorIdService {
  @Autowired
  AventureiroData aventureiroData;

  public Aventureiro exec(UUID id) {
    return aventureiroData.buscarAventureiroPorId(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aventureiro não encontrado!"));
  }
}
