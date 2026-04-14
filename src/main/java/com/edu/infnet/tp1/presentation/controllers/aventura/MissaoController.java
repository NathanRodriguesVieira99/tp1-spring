package com.edu.infnet.tp1.presentation.controllers.aventura;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.ListarMissoesService;
import com.edu.infnet.tp1.application.services.aventura.RelatorioMissoesService;
import com.edu.infnet.tp1.domain.models.aventura.Missao;
import com.edu.infnet.tp1.presentation.dtos.RelatorioMissaoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/missoes")
@RequiredArgsConstructor
public class MissaoController {
  private final ListarMissoesService listarMissoesService;
  private final RelatorioMissoesService relatorioMissoesService;

  public class ListarMissoesController {
    @GetMapping
    public ResponseEntity<List<Missao>> listar(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(listarMissoesService.exec(page, size));
    }
  }

  @GetMapping("/relatorio")
  public ResponseEntity<List<RelatorioMissaoDto>> relatorio() {
    return ResponseEntity.ok(relatorioMissoesService.exec());
  }
}
