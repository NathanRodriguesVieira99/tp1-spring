package com.edu.infnet.tp1.shared.exceptions;

public class MissaoNotFoundException extends RuntimeException {
  public MissaoNotFoundException() {
    super("Missão não encontrada");
  }

  public MissaoNotFoundException(String message) {
    super(message);
  }
}
