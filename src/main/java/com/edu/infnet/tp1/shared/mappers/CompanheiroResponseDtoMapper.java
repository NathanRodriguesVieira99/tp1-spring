package com.edu.infnet.tp1.shared.mappers;

import com.edu.infnet.tp1.domain.models.aventura.Companheiro;
import com.edu.infnet.tp1.presentation.dtos.CompanheiroResponseDto;

public class CompanheiroResponseDtoMapper {
  /*
   * Mapper do DTO para corrigir um erro que estava acontecendo em alguns
   * endpoints, o JSON retornava todo quebrado.
   * Pelo o que entendi o uso de ResponseEntity<?> + mal uso de alguns DTOs
   * causram isso.
   * Não conhecia esse erro, porém essa solucão foi a que encontrei via perguntas
   * pro copilot.
   * Basicamente são mappers dos DTOs, corrigi nos services para
   * usar esse método e no controller o tipo do retorno do método.
   */
  public static CompanheiroResponseDto toCompanheiroResponseDto(Companheiro companheiro) {
    if (companheiro == null) {
      return null;
    }
    return new CompanheiroResponseDto(
        companheiro.getId(),
        companheiro.getNome(),
        companheiro.getEspecie(),
        companheiro.getLealdade());
  }
}
