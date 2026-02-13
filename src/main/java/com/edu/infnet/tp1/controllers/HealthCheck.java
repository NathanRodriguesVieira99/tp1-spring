package com.edu.infnet.tp1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthCheck {
  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity.ok().body("OK "+ java.time.LocalDateTime.now());
  }
}
