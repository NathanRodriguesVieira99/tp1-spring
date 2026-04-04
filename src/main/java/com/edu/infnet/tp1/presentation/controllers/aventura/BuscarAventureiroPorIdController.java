package com.edu.infnet.tp1.presentation.controllers.aventura;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.BuscarAventureiroPorIdService;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class BuscarAventureiroPorIdController {

  private final BuscarAventureiroPorIdService buscarAventureirosPorIdService;

  @GetMapping("/{id}")
  public ResponseEntity<?> listarAventureiros(@PathVariable Long id) {

    Aventureiro aventureiro = buscarAventureirosPorIdService.exec(id);

    return ResponseEntity.status(HttpStatus.OK).body(aventureiro);

  }
}
