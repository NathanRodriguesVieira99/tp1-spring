package com.edu.infnet.tp1.presentation.controllers.aventura;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.RelatorioMissoesService;
import com.edu.infnet.tp1.presentation.dtos.RelatorioMissaoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/missoes")
@RequiredArgsConstructor
public class RelatorioMissoesController {

  private final RelatorioMissoesService service;

  @GetMapping("/relatorio")
  public ResponseEntity<List<RelatorioMissaoDto>> relatorio() {
    return ResponseEntity.ok(service.exec());
  }
}
