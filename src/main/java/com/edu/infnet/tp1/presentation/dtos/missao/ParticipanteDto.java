package com.edu.infnet.tp1.presentation.dtos.missao;

import java.math.BigDecimal;

import com.edu.infnet.tp1.domain.enums.PapelMissao;

public record ParticipanteDto(
    Long aventureiroId,
    String nomeAventureiro,
    PapelMissao papel,
    BigDecimal recompensaOuro,
    boolean destaque) {
}
