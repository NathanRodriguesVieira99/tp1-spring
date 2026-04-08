package com.edu.infnet.tp1.presentation.controllers.elastic;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.elastic.LojaGuildaAgregacaoPorPrecoMedioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos/agregacoes")
@RequiredArgsConstructor
public class LojaGuildaAgregacaoPorPrecoMedioController {
  private final LojaGuildaAgregacaoPorPrecoMedioService service;

  @GetMapping("/preco-medio")
  public ResponseEntity<Double> precoMedio() throws IOException {
    return ResponseEntity.ok(service.buscarPrecoMedioProdutos());
  }
}
