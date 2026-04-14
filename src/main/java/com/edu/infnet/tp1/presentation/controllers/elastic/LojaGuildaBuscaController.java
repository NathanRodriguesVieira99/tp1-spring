package com.edu.infnet.tp1.presentation.controllers.elastic;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaComFiltrosService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaCombinadaService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaFaixaDePrecoService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaFuzzyService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaMulticampoService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaPorDescricaoService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaPorFraseExataService;
import com.edu.infnet.tp1.application.services.elastic.LojaGuildaBuscaPorNomeService;
import com.edu.infnet.tp1.presentation.dtos.elastic.ProdutoRetornadoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos/busca")
@RequiredArgsConstructor
public class LojaGuildaBuscaController {
  private final LojaGuildaBuscaCombinadaService lojaGuildaBuscaCombinadaService;
  private final LojaGuildaBuscaComFiltrosService lojaGuildaBuscaComFiltrosService;
  private final LojaGuildaBuscaFaixaDePrecoService lojaGuildaBuscaFaixaDePrecoService;
  private final LojaGuildaBuscaFuzzyService lojaGuildaBuscaFuzzyService;
  private final LojaGuildaBuscaMulticampoService lojaGuildaBuscaMulticampoService;
  private final LojaGuildaBuscaPorDescricaoService lojaGuildaBuscaPorDescricaoService;
  private final LojaGuildaBuscaPorFraseExataService lojaGuildaBuscaPorFraseExataService;
  private final LojaGuildaBuscaPorNomeService lojaGuildaBuscaPorNomeService;

  @GetMapping("/avancada")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscaAvancada(
      @RequestParam String raridade,
      @RequestParam String categoria,
      @RequestParam BigDecimal min,
      @RequestParam BigDecimal max) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaCombinadaService.buscaCombinada(raridade, categoria, min, max));
  }

  @GetMapping("com-filtro")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarPorNome(
      @RequestParam String termo,
      @RequestParam String categoria) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaComFiltrosService.buscarComFiltros(termo, categoria));
  }

  @GetMapping("faixa-preco")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarFuzzy(
      @RequestParam BigDecimal min,
      @RequestParam BigDecimal max) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaFaixaDePrecoService.buscarFaixaPreco(min, max));
  }

  @GetMapping("fuzzy")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarFuzzy(@RequestParam String termo) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaFuzzyService.buscarFuzzy(termo));
  }

  @GetMapping("multicampos")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarMulticampo(@RequestParam String termo) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaMulticampoService.buscarMulticampo(termo));
  }

  @GetMapping("descricao")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarPorDescricao(@RequestParam String termo) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaPorDescricaoService.buscarPorDescricao(termo));
  }

  @GetMapping("frase")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarPorFraseExata(@RequestParam String termo) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaPorFraseExataService.buscarPorFraseExata(termo));
  }

  @GetMapping("nome")
  public ResponseEntity<List<ProdutoRetornadoDto>> buscarPorNome(@RequestParam String termo) throws IOException {
    return ResponseEntity.ok(lojaGuildaBuscaPorNomeService.buscarPorNome(termo));
  }

}
