package com.edu.infnet.tp1.shared.mappers;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.presentation.dtos.PaginationResponseDto;

public class PaginationResponseDtoMapper {
  public static PaginationResponseDto toPaginationResponseDto(Aventureiro aventureiro) {
    return new PaginationResponseDto(
        aventureiro.getId(),
        aventureiro.getNome(),
        aventureiro.getClasse(),
        aventureiro.getNivel(),
        aventureiro.isAtivo(),
        CompanheiroResponseDtoMapper.toCompanheiroResponseDto(aventureiro.getCompanheiro()));
  }
}
