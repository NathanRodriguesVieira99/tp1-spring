package com.edu.infnet.tp1.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.AtualizarDadosAventureiroService;
import com.edu.infnet.tp1.shared.dtos.AtualizarAventureiroRequestDto;
import com.edu.infnet.tp1.shared.errors.ErrorMessage;

@RestController
@RequestMapping("/api/aventureiros")
public class AtualizarDadosAventureiroController {
  @Autowired
  AtualizarDadosAventureiroService atualizarDadosAventureiroService;

  @PatchMapping("/{id}")
  public ResponseEntity<?> atualizarDadosAventureiro(
      @PathVariable UUID id,
      @RequestBody AtualizarAventureiroRequestDto aventureiroAtualizado) {
    try {
      Aventureiro resultado = atualizarDadosAventureiroService.exec(id, aventureiroAtualizado);

      return ResponseEntity.ok().body(resultado);
    } catch (ResponseStatusException e) {
      ErrorMessage error = new ErrorMessage(
          "Solicitação inválida",
          e.getMessage());

      return ResponseEntity.status(e.getStatusCode()).body(error);
    }
  }
}
