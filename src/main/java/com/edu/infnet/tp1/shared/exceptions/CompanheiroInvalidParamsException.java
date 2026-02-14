package com.edu.infnet.tp1.shared.exceptions;

public class CompanheiroInvalidParamsException extends RuntimeException {

  public CompanheiroInvalidParamsException() {
    super("Parametros de Companheiro inválidos");
  }

  public CompanheiroInvalidParamsException(String message) {
    super(message);
  }
}
