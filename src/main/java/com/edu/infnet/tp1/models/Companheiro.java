package com.edu.infnet.tp1.models;

import com.edu.infnet.tp1.interfaces.Especies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Companheiro {
  private String nome;
  private Especies especie;
  private Number lealdade;
}
