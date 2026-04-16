package com.edu.infnet.tp1.presentation.dtos.pagination;

import com.edu.infnet.tp1.domain.enums.Classes;

// DTO -> Para requisicao das rotas GET com filtros e pagination
public record PaginationQueryDto(
    int page,
    int size,
    Classes classe,
    Boolean ativo,
    Integer nivelMinimo) {
}
