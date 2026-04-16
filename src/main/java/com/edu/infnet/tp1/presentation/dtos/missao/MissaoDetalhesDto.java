package com.edu.infnet.tp1.presentation.dtos.missao;

import java.time.OffsetDateTime;
import java.util.List;

import com.edu.infnet.tp1.domain.enums.NivelPerigo;
import com.edu.infnet.tp1.domain.enums.StatusMissao;

public record MissaoDetalhesDto(
    Long id,
    String titulo,
    StatusMissao status,
    NivelPerigo nivelPerigo,
    OffsetDateTime dataInicio,
    OffsetDateTime dataTermino,
    List<ParticipanteDto> participantes) {
}
