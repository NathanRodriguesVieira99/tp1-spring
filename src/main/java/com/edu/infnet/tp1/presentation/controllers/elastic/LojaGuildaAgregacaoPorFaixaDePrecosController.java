package com.edu.infnet.tp1.presentation.controllers.elastic;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.elastic.LojaGuildaAgregacaoPorFaixaDePrecosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos/agregacoes")
@RequiredArgsConstructor
public class LojaGuildaAgregacaoPorFaixaDePrecosController {
  private final LojaGuildaAgregacaoPorFaixaDePrecosService service;

  @GetMapping("/faixas-preco")
  public ResponseEntity<Map<String, Long>> faixasDePreco() throws IOException {
    return ResponseEntity.ok(service.buscarFaixasDePreco());
  }
}
