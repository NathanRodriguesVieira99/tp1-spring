package com.edu.infnet.tp1.controllers;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aventureiro")
public class EncerrarVinculoGuildaController {

  @PatchMapping("/{id}")
  public void encerrarVinculoGuilda() {

  }
}
