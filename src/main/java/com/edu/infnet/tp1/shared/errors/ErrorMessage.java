package com.edu.infnet.tp1.shared.errors;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável por padronizar o retorno das exceptions customizadas
 */
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
  private HttpStatus status;
  private String message;
}
