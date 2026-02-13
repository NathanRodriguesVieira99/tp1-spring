package com.edu.infnet.tp1.shared.exceptions;

public class InvalidQueryParamException extends RuntimeException {
  public InvalidQueryParamException() {
    super("Query Params inválidos!");
  }

  public InvalidQueryParamException(String message) {
    super(message);
  }
}
