package com.edu.infnet.tp1.application.services.aventura;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.infrastructure.repositories.aventura.MissaoRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.ParticipacaoMissaoRepository;
import com.edu.infnet.tp1.presentation.dtos.RelatorioMissaoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatorioMissoesService {

  private final MissaoRepository missaoRepository;
  private final ParticipacaoMissaoRepository participacaoRepository;

  public List<RelatorioMissaoDto> exec() {
    var todasParticipacoes = participacaoRepository.findAll();

    return missaoRepository.findAll().stream()
        .map(missao -> {
          var participacoes = todasParticipacoes.stream()
              .filter(p -> p.getMissao().getId().equals(missao.getId()))
              .toList();

          int total = participacoes.size();
          BigDecimal soma = participacoes.stream()
              .map(p -> p.getRecompensaOuro() != null ? p.getRecompensaOuro() : BigDecimal.ZERO)
              .reduce(BigDecimal.ZERO, BigDecimal::add);

          return new RelatorioMissaoDto(
              missao.getId(),
              missao.getTitulo(),
              missao.getStatus(),
              missao.getNivelPerigo(),
              total,
              soma);
        })
        .toList();
  }
}
