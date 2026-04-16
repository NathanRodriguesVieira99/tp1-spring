package com.edu.infnet.tp1.shared.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edu.infnet.tp1.shared.errors.exceptions.InvalidParamsException;
import com.edu.infnet.tp1.shared.errors.exceptions.InvalidQueryParamsException;
import com.edu.infnet.tp1.shared.errors.exceptions.ResourceNotFoundException;

// @ControllerAdvice -> Serve para padronizar as Exceptions para evitar TryCatch  nos controllers
// Se não houver exceptions customizadas no service, o controller vai lancar a padrão do Java
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(InvalidParamsException.class)
  private ResponseEntity<ErrorMessage> aventureiroInvalidParamsHandler(InvalidParamsException exception) {
    ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
        "Parametros inválidos!");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  private ResponseEntity<ErrorMessage> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
    ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, "O recurso não foi encontrado!");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
  }

  @ExceptionHandler(InvalidQueryParamsException.class)
  private ResponseEntity<ErrorMessage> invalidQueryParamsException(InvalidQueryParamsException exception) {
    ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, "Query Params inválidos!");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

}
