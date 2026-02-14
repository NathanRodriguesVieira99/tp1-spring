package com.edu.infnet.tp1.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.RemoverCompanheiroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companheiros")
@RequiredArgsConstructor
public class RemoverCompanheiroController {

 private final RemoverCompanheiroService removerCompanheiroService;

  @DeleteMapping("/delete/{id}")

  public ResponseEntity<Aventureiro> removerCompanheiro(@PathVariable UUID id) {
    Aventureiro aventureiroComCompanheiroEncontrado = removerCompanheiroService.exec(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(aventureiroComCompanheiroEncontrado);
  }
}
