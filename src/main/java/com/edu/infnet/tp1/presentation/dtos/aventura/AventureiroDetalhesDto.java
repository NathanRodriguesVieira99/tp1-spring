package com.edu.infnet.tp1.presentation.dtos.aventura;

import com.edu.infnet.tp1.domain.enums.Classes;
import com.edu.infnet.tp1.presentation.dtos.companheiro.CompanheiroResponseDto;

public record AventureiroDetalhesDto(
    Long id,
    String nome,
    Classes classe,
    Integer nivel,
    boolean ativo,
    CompanheiroResponseDto companheiro,
    int totalParticipacoes,
    String ultimaMissao) {
}
