package com.edu.infnet.tp1.presentation.dtos.missao;

import java.math.BigDecimal;

import com.edu.infnet.tp1.domain.enums.NivelPerigo;
import com.edu.infnet.tp1.domain.enums.StatusMissao;

public record RelatorioMissaoDto(
    Long id,
    String titulo,
    StatusMissao status,
    NivelPerigo nivelPerigo,
    int totalParticipantes,
    BigDecimal totalRecompensas) {
}
