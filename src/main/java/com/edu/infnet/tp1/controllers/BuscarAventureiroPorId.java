package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.services.BuscarAventureiroPorIdService;

@RestController
@RequestMapping("/api/aventureiros")
public class BuscarAventureiroPorId {

  @Autowired
  BuscarAventureiroPorIdService buscarAventureirosPorIdService;

  @GetMapping("/{id}")
  public void listarAventureiros() {
  }
}
