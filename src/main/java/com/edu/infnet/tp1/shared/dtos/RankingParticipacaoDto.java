package com.edu.infnet.tp1.shared.dtos;

import java.math.BigDecimal;

public record RankingParticipacaoDto(
    Long aventureiroId,
    String nome,
    int totalParticipacoes,
    BigDecimal somaRecompensas,
    int totalDestaques) {
}
