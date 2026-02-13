package com.edu.infnet.tp1.shared.dtos;

import java.util.UUID;

import com.edu.infnet.tp1.enums.Classes;
import com.edu.infnet.tp1.models.Companheiro;

// DTO -> Para resposta das rotas GET com filtros e pagination (retorna um aventureiro basicamente)
public record PaginationResponseDto(
    UUID id,
    String nome,
    Classes classe,
    Number nivel,
    Boolean ativo,
    Companheiro companheiro
  ) {
}
