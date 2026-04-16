package com.edu.infnet.tp1.presentation.dtos.pagination;

import com.edu.infnet.tp1.domain.enums.Classes;
import com.edu.infnet.tp1.presentation.dtos.companheiro.CompanheiroResponseDto;

// DTO -> Para resposta das rotas GET com filtros e pagination (retorna um aventureiro basicamente)
public record PaginationResponseDto(
    Long id,
    String nome,
    Classes classe,
    Number nivel,
    Boolean ativo,
    CompanheiroResponseDto companheiro) {
}
