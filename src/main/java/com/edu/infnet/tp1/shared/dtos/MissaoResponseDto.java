package com.edu.infnet.tp1.shared.dtos;

import com.edu.infnet.tp1.enums.NivelPerigo;
import com.edu.infnet.tp1.enums.StatusMissao;
import java.time.OffsetDateTime;

public record MissaoResponseDto(
    Long id,
    String titulo,
    StatusMissao status,
    NivelPerigo nivelPerigo,
    OffsetDateTime createdAt,
    OffsetDateTime dataInicio,
    OffsetDateTime dataTermino) {
}
