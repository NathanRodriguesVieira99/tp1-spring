package com.edu.infnet.tp1.presentation.controllers.aventura;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.AtualizarDadosAventureiroService;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.presentation.dtos.AtualizarAventureiroRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class AtualizarDadosAventureiroController {

  private final AtualizarDadosAventureiroService atualizarDadosAventureiroService;

  @PatchMapping("/{id}")
  public ResponseEntity<?> atualizarDadosAventureiro(
      @PathVariable Long id,
      @RequestBody AtualizarAventureiroRequestDto aventureiroAtualizado) {

    Aventureiro resultado = atualizarDadosAventureiroService.exec(id, aventureiroAtualizado);

    return ResponseEntity.ok().body(resultado);

  }
}
