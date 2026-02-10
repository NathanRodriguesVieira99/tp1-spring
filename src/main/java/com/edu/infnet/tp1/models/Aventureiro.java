package com.edu.infnet.tp1.models;

import java.util.UUID;

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
}
