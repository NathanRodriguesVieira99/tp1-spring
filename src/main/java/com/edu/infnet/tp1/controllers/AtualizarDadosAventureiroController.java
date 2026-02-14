package com.edu.infnet.tp1.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.AtualizarDadosAventureiroService;
import com.edu.infnet.tp1.shared.dtos.AtualizarAventureiroRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class AtualizarDadosAventureiroController {

  private final AtualizarDadosAventureiroService atualizarDadosAventureiroService;

  @PatchMapping("/{id}")
  public ResponseEntity<?> atualizarDadosAventureiro(
      @PathVariable UUID id,
      @RequestBody AtualizarAventureiroRequestDto aventureiroAtualizado) {

    Aventureiro resultado = atualizarDadosAventureiroService.exec(id, aventureiroAtualizado);

    return ResponseEntity.ok().body(resultado);

  }
}
