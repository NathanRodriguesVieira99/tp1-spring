package com.edu.infnet.tp1.presentation.controllers.aventura;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.DefinirCompanheiroService;
import com.edu.infnet.tp1.domain.models.aventura.Companheiro;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companheiros")
@RequiredArgsConstructor
public class DefinirCompanheiroController {

  private final DefinirCompanheiroService definirCompanheiroService;

  @PostMapping("/create/{id}")
  public ResponseEntity<?> definirCompanheiro(@PathVariable Long id, @RequestBody Companheiro companheiro) {
    Companheiro companheiroCriado = definirCompanheiroService.exec(id, companheiro);

    return ResponseEntity.status(HttpStatus.CREATED).body(companheiroCriado);

  }
}
