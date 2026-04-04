package com.edu.infnet.tp1.application.services.aventura;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.ParticipacaoMissaoRepository;
import com.edu.infnet.tp1.presentation.dtos.AventureiroDetalhesDto;
import com.edu.infnet.tp1.presentation.dtos.CompanheiroResponseDto;
import com.edu.infnet.tp1.shared.exceptions.AventureiroNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisualizacaoAventureiroCompletaService {

  private final AventureiroRepository aventureiroRepository;
  private final ParticipacaoMissaoRepository participacaoRepository;

  public AventureiroDetalhesDto exec(Long id) {
    Aventureiro aventureiro = aventureiroRepository.findByIdComCompanheiro(id)
        .orElseThrow(AventureiroNotFoundException::new);

    int totalParticipacoes = (int) participacaoRepository.countByAventureiroId(id);

    var participacoes = participacaoRepository.findByAventureiroIdOrderByCreatedAtDesc(id);
    String ultimaMissao = participacoes.isEmpty() ? null : participacoes.get(0).getMissao().getTitulo();

    CompanheiroResponseDto companheiroDto = null;
    if (aventureiro.getCompanheiro() != null) {
      companheiroDto = new CompanheiroResponseDto(
          aventureiro.getCompanheiro().getId(),
          aventureiro.getCompanheiro().getNome(),
          aventureiro.getCompanheiro().getEspecie(),
          aventureiro.getCompanheiro().getLealdade());
    }

    return new AventureiroDetalhesDto(
        aventureiro.getId(),
        aventureiro.getNome(),
        aventureiro.getClasse(),
        aventureiro.getNivel(),
        aventureiro.isAtivo(),
        companheiroDto,
        totalParticipacoes,
        ultimaMissao);
  }
}
