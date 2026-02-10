package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.services.ListarAventureirosService;

@RestController
@RequestMapping("/api/aventureiros")
public class ListarAventureirosController {

  @Autowired
  ListarAventureirosService listarAventureirosService;

  @GetMapping()
  public void listarAventureiros() {
  }
}
