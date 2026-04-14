package com.edu.infnet.tp1.presentation.controllers.aventura;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.CompanheiroService;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.domain.models.aventura.Companheiro;
import com.edu.infnet.tp1.presentation.dtos.CompanheiroResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companheiros")
@RequiredArgsConstructor
public class CompanheiroController {
  private final CompanheiroService companheiroService;

  @PostMapping("/create/{id}")
  public ResponseEntity<CompanheiroResponseDto> definirCompanheiro(@PathVariable Long id,
      @RequestBody Companheiro companheiro) {
    CompanheiroResponseDto companheiroCriado = companheiroService.criarCompanheiro(id, companheiro);
    return ResponseEntity.status(HttpStatus.CREATED).body(companheiroCriado);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Aventureiro> removerCompanheiro(@PathVariable Long id) {
    Aventureiro aventureiroComCompanheiroEncontrado = companheiroService.removerCompanheiro(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(aventureiroComCompanheiroEncontrado);
  }
}
