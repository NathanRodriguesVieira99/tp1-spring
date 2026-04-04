package com.edu.infnet.tp1.presentation.dtos;

import com.edu.infnet.tp1.domain.enums.Classes;
import com.edu.infnet.tp1.domain.models.aventura.Companheiro;

// DTO -> Para resposta das rotas GET com filtros e pagination (retorna um aventureiro basicamente)
public record PaginationResponseDto(
    Long id,
    String nome,
    Classes classe,
    Number nivel,
    Boolean ativo,
    Companheiro companheiro) {
}
