package com.edu.infnet.tp1.shared.dtos;

import com.edu.infnet.tp1.enums.Especies;

public record CompanheiroResponseDto(
    Long id,
    String nome,
    Especies especie,
    int lealdade) {
}
