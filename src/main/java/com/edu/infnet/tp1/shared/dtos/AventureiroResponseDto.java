package com.edu.infnet.tp1.shared.dtos;

import com.edu.infnet.tp1.enums.Classes;

public record AventureiroResponseDto(
    Long id,
    String nome,
    Classes classe,
    int nivel,
    boolean ativo,
    CompanheiroResponseDto companheiro) {
}
