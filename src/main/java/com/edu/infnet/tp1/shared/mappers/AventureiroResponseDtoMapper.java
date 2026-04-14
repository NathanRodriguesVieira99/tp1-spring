package com.edu.infnet.tp1.shared.mappers;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.presentation.dtos.AventureiroResponseDto;
import com.edu.infnet.tp1.presentation.dtos.CompanheiroResponseDto;

public class AventureiroResponseDtoMapper {
  public static AventureiroResponseDto toAventureiroResponseDto(Aventureiro aventureiro) {
    CompanheiroResponseDto companheiroDto = CompanheiroResponseDtoMapper
        .toCompanheiroResponseDto(aventureiro.getCompanheiro());

    return new AventureiroResponseDto(
        aventureiro.getId(),
        aventureiro.getNome(),
        aventureiro.getClasse(),
        aventureiro.getNivel(),
        aventureiro.isAtivo(),
        companheiroDto);
  }

}
