package com.edu.infnet.tp1.presentation.controllers.aventura;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.RankingParticipacaoService;
import com.edu.infnet.tp1.presentation.dtos.RankingParticipacaoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class RankingParticipacaoController {

  private final RankingParticipacaoService service;

  @GetMapping("/ranking")
  public ResponseEntity<List<RankingParticipacaoDto>> ranking() {
    return ResponseEntity.ok(service.exec());
  }
}
