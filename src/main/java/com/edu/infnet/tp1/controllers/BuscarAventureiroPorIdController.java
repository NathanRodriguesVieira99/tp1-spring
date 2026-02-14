package com.edu.infnet.tp1.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.BuscarAventureiroPorIdService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class BuscarAventureiroPorIdController {

  private final BuscarAventureiroPorIdService buscarAventureirosPorIdService;

  @GetMapping("/{id}")
  public ResponseEntity<?> listarAventureiros(@PathVariable UUID id) {

    Aventureiro aventureiro = buscarAventureirosPorIdService.exec(id);

    return ResponseEntity.status(HttpStatus.OK).body(aventureiro);

  }
}
