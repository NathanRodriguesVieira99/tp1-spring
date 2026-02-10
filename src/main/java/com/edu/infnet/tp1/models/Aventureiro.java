package com.edu.infnet.tp1.models;

import java.util.Optional;
import java.util.UUID;

import com.edu.infnet.tp1.interfaces.Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author nathan.vieira
 */

@Getter @Setter
@AllArgsConstructor

public class Aventureiro {
  private UUID id;
  private String nome;
  private Classes classe;
  private Number nivel;
  private Boolean ativo;
  private Optional<String>  companheiro; 
}
