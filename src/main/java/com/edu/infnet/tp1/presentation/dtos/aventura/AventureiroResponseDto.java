package com.edu.infnet.tp1.presentation.dtos.aventura;

import com.edu.infnet.tp1.domain.enums.Classes;
import com.edu.infnet.tp1.presentation.dtos.companheiro.CompanheiroResponseDto;

public record AventureiroResponseDto(
    Long id,
    String nome,
    Classes classe,
    int nivel,
    boolean ativo,
    CompanheiroResponseDto companheiro) {
}
