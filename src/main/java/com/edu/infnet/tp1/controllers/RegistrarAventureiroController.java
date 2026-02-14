package com.edu.infnet.tp1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.RegistrarAventureiroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class RegistrarAventureiroController {

  private final RegistrarAventureiroService registrarAventureiroService;

  @PostMapping("/create")
  public ResponseEntity<?> registrarAventureiro(@RequestBody Aventureiro aventureiro) {

    Aventureiro novoAventureiro = registrarAventureiroService.exec(aventureiro);

    return ResponseEntity.status(HttpStatus.CREATED).body(novoAventureiro);
  }
}
