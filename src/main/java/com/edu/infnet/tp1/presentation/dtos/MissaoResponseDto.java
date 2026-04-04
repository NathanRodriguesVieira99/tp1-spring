package com.edu.infnet.tp1.presentation.dtos;

import java.time.OffsetDateTime;

import com.edu.infnet.tp1.domain.enums.NivelPerigo;
import com.edu.infnet.tp1.domain.enums.StatusMissao;

public record MissaoResponseDto(
    Long id,
    String titulo,
    StatusMissao status,
    NivelPerigo nivelPerigo,
    OffsetDateTime createdAt,
    OffsetDateTime dataInicio,
    OffsetDateTime dataTermino) {
}
