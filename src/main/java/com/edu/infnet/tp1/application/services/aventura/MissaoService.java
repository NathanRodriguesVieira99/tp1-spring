package com.edu.infnet.tp1.application.services.aventura;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Missao;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.MissaoRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.ParticipacaoMissaoRepository;
import com.edu.infnet.tp1.presentation.dtos.MissaoDetalhesDto;
import com.edu.infnet.tp1.presentation.dtos.ParticipanteDto;
import com.edu.infnet.tp1.presentation.dtos.RankingParticipacaoDto;
import com.edu.infnet.tp1.presentation.dtos.RelatorioMissaoDto;
import com.edu.infnet.tp1.shared.exceptions.MissaoNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissaoService {
  private final AventureiroRepository aventureiroRepository;
  private final ParticipacaoMissaoRepository participacaoMissaoRepository;
  private final MissaoRepository missaoRepository;

  public Missao buscarMissaoPorId(Long id) {
    return missaoRepository.findById(id).orElseThrow(MissaoNotFoundException::new);
  }

  public MissaoDetalhesDto exibirMissaoComDetalhes(Long missaoId) {
    Missao missao = missaoRepository.findById(missaoId).orElseThrow(MissaoNotFoundException::new);

    var participacoes = participacaoMissaoRepository.findByMissaoIdComAventureiro(missaoId);

    List<ParticipanteDto> participantes = participacoes.stream()
        .map(p -> new ParticipanteDto(
            p.getAventureiro().getId(),
            p.getAventureiro().getNome(),
            p.getPapel(),
            p.getRecompensaOuro(),
            p.isDestaque()))
        .collect(Collectors.toList());

    return new MissaoDetalhesDto(
        missao.getId(),
        missao.getTitulo(),
        missao.getStatus(),
        missao.getNivelPerigo(),
        missao.getDataInicio(),
        missao.getDataTermino(),
        participantes);
  }

  public List<Missao> listarMissoes(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return missaoRepository.findAll(pageable).getContent();
  }

  public List<RankingParticipacaoDto> exibirRankingMissoes() {
    var todasParticipacoes = participacaoMissaoRepository.findAll();

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

  public List<RelatorioMissaoDto> exibirRelatorioMissao() {
    var todasParticipacoes = participacaoMissaoRepository.findAll();

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
