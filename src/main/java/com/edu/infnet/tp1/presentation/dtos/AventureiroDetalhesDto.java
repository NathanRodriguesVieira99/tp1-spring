package com.edu.infnet.tp1.presentation.dtos;

import com.edu.infnet.tp1.domain.enums.Classes;

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
