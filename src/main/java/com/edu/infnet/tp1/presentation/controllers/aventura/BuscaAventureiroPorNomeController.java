package com.edu.infnet.tp1.presentation.controllers.aventura;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.BuscaAventureiroPorNomeService;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class BuscaAventureiroPorNomeController {

  private final BuscaAventureiroPorNomeService buscaAventureiroPorNomeService;

  @GetMapping("/buscar")
  public ResponseEntity<List<Aventureiro>> buscar(
      @RequestParam String nome,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(buscaAventureiroPorNomeService.exec(nome, page, size));
  }
}
