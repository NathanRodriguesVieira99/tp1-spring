package com.edu.infnet.tp1.shared.dtos;

import com.edu.infnet.tp1.enums.Classes;

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
