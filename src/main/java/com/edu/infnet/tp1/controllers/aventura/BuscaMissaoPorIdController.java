package com.edu.infnet.tp1.controllers.aventura;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.services.aventura.BuscaMissaoPorIdService;
import com.edu.infnet.tp1.shared.dtos.MissaoDetalhesDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/missoes")
@RequiredArgsConstructor
public class BuscaMissaoPorIdController {

  private final BuscaMissaoPorIdService service;

  @GetMapping("/{id}/detalhes")
  public ResponseEntity<MissaoDetalhesDto> detalhes(@PathVariable Long id) {
    return ResponseEntity.ok(service.execComDetalhes(id));
  }
}
