package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.BuscarAventureiroPorIdService;
import com.edu.infnet.tp1.shared.errors.ErrorMessage;

@RestController
@RequestMapping("/api/aventureiros")
public class BuscarAventureiroPorIdController {

  @Autowired
  BuscarAventureiroPorIdService buscarAventureirosPorIdService;

  @GetMapping("/{id}")
  public ResponseEntity<?> listarAventureiros(@PathVariable UUID id) {
    try {
      Aventureiro aventureiro = buscarAventureirosPorIdService.exec(id);

      return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    } catch (ResponseStatusException e) {
      ErrorMessage error = new ErrorMessage(
          HttpStatus.NOT_FOUND.value(),
          "Solicitação inválida",
          e.getMessage());

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
  }
}
