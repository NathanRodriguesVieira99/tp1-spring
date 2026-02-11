package com.edu.infnet.tp1.shared.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
  private Number status;
  private String mensagem;
  private String detalhes;
}
