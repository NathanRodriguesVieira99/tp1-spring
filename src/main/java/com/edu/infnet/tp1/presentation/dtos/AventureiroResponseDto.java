package com.edu.infnet.tp1.presentation.dtos;

import com.edu.infnet.tp1.domain.enums.Classes;

public record AventureiroResponseDto(
    Long id,
    String nome,
    Classes classe,
    int nivel,
    boolean ativo,
    CompanheiroResponseDto companheiro) {
}
