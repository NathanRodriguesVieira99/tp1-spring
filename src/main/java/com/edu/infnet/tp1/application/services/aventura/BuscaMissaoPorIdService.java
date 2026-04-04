package com.edu.infnet.tp1.application.services.aventura;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.application.repositories.aventura.MissaoRepository;
import com.edu.infnet.tp1.application.repositories.aventura.ParticipacaoMissaoRepository;
import com.edu.infnet.tp1.domain.models.aventura.Missao;
import com.edu.infnet.tp1.presentation.dtos.MissaoDetalhesDto;
import com.edu.infnet.tp1.presentation.dtos.ParticipanteDto;
import com.edu.infnet.tp1.shared.exceptions.MissaoNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscaMissaoPorIdService {

  private final MissaoRepository missaoRepository;
  private final ParticipacaoMissaoRepository participacaoRepository;

  public Missao exec(Long id) {
    return missaoRepository.findById(id)
        .orElseThrow(MissaoNotFoundException::new);
  }

  public MissaoDetalhesDto execComDetalhes(Long id) {
    Missao missao = missaoRepository.findById(id)
        .orElseThrow(MissaoNotFoundException::new);

    var participacoes = participacaoRepository.findByMissaoIdComAventureiro(id);

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
}
