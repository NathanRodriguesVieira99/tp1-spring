package com.edu.infnet.tp1.application.services.aventura;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.ParticipacaoMissaoRepository;
import com.edu.infnet.tp1.presentation.dtos.RankingParticipacaoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingParticipacaoService {

  private final AventureiroRepository aventureiroRepository;
  private final ParticipacaoMissaoRepository participacaoRepository;

  public List<RankingParticipacaoDto> exec() {
    var todasParticipacoes = participacaoRepository.findAll();

    return aventureiroRepository.findAll().stream()
        .map(aventureiro -> {
          var participacoes = todasParticipacoes.stream()
              .filter(p -> p.getAventureiro().getId().equals(aventureiro.getId()))
              .toList();

          int total = participacoes.size();
          BigDecimal soma = participacoes.stream()
              .map(p -> p.getRecompensaOuro() != null ? p.getRecompensaOuro() : BigDecimal.ZERO)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
          int destaques = (int) participacoes.stream()
              .filter(p -> p.isDestaque())
              .count();

          return new RankingParticipacaoDto(
              aventureiro.getId(),
              aventureiro.getNome(),
              total,
              soma,
              destaques);
        })
        .sorted((a, b) -> {
          int compTotal = Integer.compare(b.totalParticipacoes(), a.totalParticipacoes());
          if (compTotal != 0)
            return compTotal;
          return b.somaRecompensas().compareTo(a.somaRecompensas());
        })
        .collect(Collectors.toList());
  }
}
