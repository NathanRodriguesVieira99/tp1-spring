package com.edu.infnet.tp1.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.RecrutarNovamenteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros/guilda")
@RequiredArgsConstructor
public class RecrutarNovamenteController {

  private final RecrutarNovamenteService recrutarNovamenteService;

  @PatchMapping("/recruit/{id}")
  public ResponseEntity<?> encerrarVinculoGuilda(@PathVariable UUID id) {
    Aventureiro aventureiroInativo = recrutarNovamenteService.exec(id);

    return ResponseEntity.status(HttpStatus.OK).body(aventureiroInativo);
  }
}
