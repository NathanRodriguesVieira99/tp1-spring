package com.edu.infnet.tp1.shared.dtos;

// DTO -> Para requisicao das rotas GET com filtros e pagination
public record PaginationQueryDto(
    int page,
    int size,
    String classe,
    Boolean ativo,
    Integer nivelMinimo) {
}
