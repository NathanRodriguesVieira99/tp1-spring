package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.services.RegistrarAventureiroService;

@RestController
@RequestMapping("/api/aventureiros")
public class RegistrarAventureiroController {

  @Autowired
  RegistrarAventureiroService registrarAventureiroService;

  @PostMapping
  public void registrarAventureiro() {
  }

}
