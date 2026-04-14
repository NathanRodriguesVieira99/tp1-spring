package com.edu.infnet.tp1.presentation.controllers.elastic;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.elastic.LojaGuildaAgregacaoPorCategoriaService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaAgregacaoPorFaixaDePrecosService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaAgregacaoPorPrecoMedioService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaAgregacaoPorRaridadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos/agregacoes")
@RequiredArgsConstructor
public class LojaGuildaAgregacoesController {
  private final LojaGuildaAgregacaoPorCategoriaService lojaGuildaAgregacaoPorCategoriaService;
  private final LojaGuildaAgregacaoPorFaixaDePrecosService lojaGuildaAgregacaoPorFaixaDePrecosService;
  private final LojaGuildaAgregacaoPorPrecoMedioService lojaGuildaAgregacaoPorPrecoMedioService;
  private final LojaGuildaAgregacaoPorRaridadeService lojaGuildaAgregacaoPorRaridadeService;

  @GetMapping("/por-categoria")
  public ResponseEntity<Map<String, Long>> porCategoria() throws IOException {
    return ResponseEntity.ok(lojaGuildaAgregacaoPorCategoriaService.quantidadePorCategoria());
  }

  public class LojaGuildaAgregacaoPorFaixaDePrecosController {
    @GetMapping("/faixas-preco")
    public ResponseEntity<Map<String, Long>> faixasDePreco() throws IOException {
      return ResponseEntity.ok(lojaGuildaAgregacaoPorFaixaDePrecosService.buscarFaixasDePreco());
    }
  }

  @GetMapping("/preco-medio")
  public ResponseEntity<Double> precoMedio() throws IOException {
    return ResponseEntity.ok(lojaGuildaAgregacaoPorPrecoMedioService.buscarPrecoMedioProdutos());
  }

  @GetMapping("/por-raridade")
  public ResponseEntity<Map<String, Long>> porRaridade() throws IOException {
    return ResponseEntity.ok(lojaGuildaAgregacaoPorRaridadeService.quantidadePorRaridade());
  }
}
