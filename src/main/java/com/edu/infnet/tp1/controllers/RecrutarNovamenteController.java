package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;

import com.edu.infnet.tp1.services.RecrutarNovamenteService;

public class RecrutarNovamenteController {
  @Autowired
  RecrutarNovamenteService recrutarNovamenteService;

  @PatchMapping("/{id}")
  public void recrutarNovamente() {
  }

}
