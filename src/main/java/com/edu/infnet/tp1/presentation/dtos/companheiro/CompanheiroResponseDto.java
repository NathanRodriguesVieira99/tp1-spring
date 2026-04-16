package com.edu.infnet.tp1.presentation.dtos.companheiro;

import com.edu.infnet.tp1.domain.enums.Especies;

public record CompanheiroResponseDto(
    Long id,
    String nome,
    Especies especie,
    int lealdade) {
}
