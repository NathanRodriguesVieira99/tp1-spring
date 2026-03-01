package com.edu.infnet.tp1.shared.dtos;

import com.edu.infnet.tp1.enums.NivelPerigo;
import com.edu.infnet.tp1.enums.StatusMissao;
import java.math.BigDecimal;

public record RelatorioMissaoDto(
    Long id,
    String titulo,
    StatusMissao status,
    NivelPerigo nivelPerigo,
    int totalParticipantes,
    BigDecimal totalRecompensas) {
}
