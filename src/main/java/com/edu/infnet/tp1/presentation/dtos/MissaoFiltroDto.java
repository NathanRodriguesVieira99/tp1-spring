package com.edu.infnet.tp1.presentation.dtos;

import java.time.OffsetDateTime;

public record MissaoFiltroDto(
    int page,
    int size,
    String status,
    String nivelPerigo,
    OffsetDateTime dataInicio,
    OffsetDateTime dataFim) {
}
