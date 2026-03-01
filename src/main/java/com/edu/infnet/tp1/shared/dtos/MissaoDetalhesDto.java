package com.edu.infnet.tp1.shared.dtos;

import java.time.OffsetDateTime;
import java.util.List;

import com.edu.infnet.tp1.enums.NivelPerigo;
import com.edu.infnet.tp1.enums.StatusMissao;

public record MissaoDetalhesDto(
    Long id,
    String titulo,
    StatusMissao status,
    NivelPerigo nivelPerigo,
    OffsetDateTime dataInicio,
    OffsetDateTime dataTermino,
    List<ParticipanteDto> participantes) {
}
