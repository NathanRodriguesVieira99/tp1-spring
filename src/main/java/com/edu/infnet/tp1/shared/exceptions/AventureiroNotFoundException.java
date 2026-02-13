package com.edu.infnet.tp1.shared.exceptions;

public class AventureiroNotFoundException extends RuntimeException {
  public AventureiroNotFoundException() {
    super("Parametros inválidos");
  }

  public AventureiroNotFoundException(String message) {
    super(message);
  }
}
