package com.edu.infnet.tp1.presentation.dtos.missao;

import java.math.BigDecimal;

public record RankingParticipacaoDto(
    Long aventureiroId,
    String nome,
    int totalParticipacoes,
    BigDecimal somaRecompensas,
    int totalDestaques) {
}
