package com.edu.infnet.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.services.RemoverCompanheiroService;

@RestController
@RequestMapping("/api/companheiros")
public class RemoverCompanheiroController {
  @Autowired
  RemoverCompanheiroService removerCompanheiroService;

  @DeleteMapping("/{id}")

  public void removerCompanheiro() {
  
  }
}
