package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.RegistrarAventureiroService;
import com.edu.infnet.tp1.shared.errors.ErrorMessage;

@RestController
@RequestMapping("/api/aventureiros")
public class RegistrarAventureiroController {

  @Autowired
  RegistrarAventureiroService registrarAventureiroService;

  @PostMapping("/create")
  public ResponseEntity<?> registrarAventureiro(@RequestBody Aventureiro aventureiro) {
    try {
      Aventureiro novoAventureiro = registrarAventureiroService.exec(aventureiro);

      return ResponseEntity.status(HttpStatus.CREATED).body(novoAventureiro);
    } catch (IllegalArgumentException e) {
      ErrorMessage erro = new ErrorMessage(
          HttpStatus.UNAUTHORIZED.value(), // -> Status Code
          "Solicitação inválida", // -> Mensgagem
          e.getMessage()); // -> getMessage() -> detalhes do erro vindos do service

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }
  }

}
