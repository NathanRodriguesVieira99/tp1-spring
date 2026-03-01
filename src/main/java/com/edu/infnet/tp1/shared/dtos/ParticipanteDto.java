package com.edu.infnet.tp1.shared.dtos;

import java.math.BigDecimal;

import com.edu.infnet.tp1.enums.PapelMissao;

public record ParticipanteDto(
    Long aventureiroId,
    String nomeAventureiro,
    PapelMissao papel,
    BigDecimal recompensaOuro,
    boolean destaque) {
}
