package com.edu.infnet.tp1.shared.exceptions;

// Exception customizada extendendo a padrão do Java
public class AventureiroInvalidParamsException extends RuntimeException {

  public AventureiroInvalidParamsException() {
    super("Parametros de Aventureiro inválidos");
  }

  // Instancia a mensagem customizada
  public AventureiroInvalidParamsException(String message) {
    super(message);
  }
}
