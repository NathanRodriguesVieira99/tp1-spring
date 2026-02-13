package com.edu.infnet.tp1.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edu.infnet.tp1.shared.errors.ErrorMessage;
import com.edu.infnet.tp1.shared.exceptions.AventureiroInvalidParamsException;
import com.edu.infnet.tp1.shared.exceptions.AventureiroNotFoundException;

// @ControllerAdvice -> Serve para padronizar as Exceptions para evitar TryCatch  nos controllers
// Se não houver exceptions customizadas no service, o controller vai lancar a padrão do Java
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(AventureiroInvalidParamsException.class)
  private ResponseEntity<ErrorMessage> aventureiroInvalidParamsHandler(AventureiroInvalidParamsException exception) {
    ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED,
        "O Aventureiro foi criado com paramtros inválidos.");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
  }

  @ExceptionHandler(AventureiroNotFoundException.class)
  private ResponseEntity<ErrorMessage> aventureiroNotFoundExceptionHandler(AventureiroNotFoundException exception) {
    ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, "O aventureiro não foi encontrado.");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
  }
}
