package com.edu.infnet.tp1.application.services.aventura;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.ParticipacaoMissaoRepository;
import com.edu.infnet.tp1.presentation.dtos.aventura.AtualizarAventureiroRequestDto;
import com.edu.infnet.tp1.presentation.dtos.aventura.AventureiroDetalhesDto;
import com.edu.infnet.tp1.presentation.dtos.aventura.AventureiroResponseDto;
import com.edu.infnet.tp1.presentation.dtos.companheiro.CompanheiroResponseDto;
import com.edu.infnet.tp1.presentation.dtos.pagination.PaginationQueryDto;
import com.edu.infnet.tp1.presentation.dtos.pagination.PaginationResponseDto;
import com.edu.infnet.tp1.shared.errors.exceptions.InvalidParamsException;
import com.edu.infnet.tp1.shared.errors.exceptions.InvalidQueryParamsException;
import com.edu.infnet.tp1.shared.errors.exceptions.ResourceNotFoundException;
import com.edu.infnet.tp1.shared.mappers.AventureiroResponseDtoMapper;
import com.edu.infnet.tp1.shared.mappers.PaginationResponseDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AventuraService {
  private final AventureiroRepository aventureiroRepository;
  private final ParticipacaoMissaoRepository participacaoMissaoRepository;

  public List<AventureiroResponseDto> buscarAventureiroPorNome(String nome, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return aventureiroRepository.findByNomeContaining(nome, pageable).getContent().stream()
        .map(AventureiroResponseDtoMapper::toAventureiroResponseDto).toList();

  }

  public AventureiroResponseDto atualizarAventureiro(Long id, AtualizarAventureiroRequestDto aventureiroAtualizado) {
    Aventureiro aventureiro = aventureiroRepository.findById(id)
        .orElseThrow(() -> new InvalidParamsException());

    if (aventureiroAtualizado.nome() != null && !aventureiroAtualizado.nome().isEmpty())
      aventureiro.setNome(aventureiroAtualizado.nome());

    if (aventureiroAtualizado.classe() != null)
      aventureiro.setClasse(aventureiroAtualizado.classe());

    if (aventureiroAtualizado.nivel() != null)
      aventureiro.setNivel(aventureiroAtualizado.nivel().intValue());

    Aventureiro aventuereiroAtualizado = aventureiroRepository.save(aventureiro);
    return AventureiroResponseDtoMapper.toAventureiroResponseDto(aventuereiroAtualizado);
  }

  public Aventureiro buscarAventureiroPorId(Long id) {
    return aventureiroRepository.findById(id).orElseThrow(() -> new InvalidParamsException());
  }

  public AventureiroResponseDto buscarAventureiroResponsePorId(Long id) {
    Aventureiro aventureiro = buscarAventureiroPorId(id);
    return AventureiroResponseDtoMapper.toAventureiroResponseDto(aventureiro);
  }

  public List<PaginationResponseDto> listarAventureiros(PaginationQueryDto params) {
    if (params.page() < 0) {
      throw new InvalidQueryParamsException();
    }

    if (params.size() < 1 || params.size() > 50) {
      throw new InvalidQueryParamsException();
    }

    if (params.ativo() != null && !params.ativo()) {
      throw new InvalidQueryParamsException();
    }

    if (params.nivelMinimo() != null && params.nivelMinimo() <= 0) {
      throw new InvalidQueryParamsException();
    }

    Pageable pageable = PageRequest.of(params.page(), params.size());

    return aventureiroRepository.findComFiltros(params.classe(), params.ativo(), params.nivelMinimo(), pageable)
        .getContent()
        .stream()
        .map(PaginationResponseDtoMapper::toPaginationResponseDto)
        .toList();
  }

  public int contarAventureiros(PaginationQueryDto params) {
    return (int) aventureiroRepository.countComFiltros(params.classe(), params.ativo(), params.nivelMinimo());
  }

  public Aventureiro encerrarVinculoGuilda(Long id) {
    Aventureiro aventureiro = aventureiroRepository.findById(id)
        .orElseThrow(() -> new InvalidParamsException());

    if (aventureiro.isAtivo()) {
      aventureiro.setAtivo(false);
      return aventureiroRepository.save(aventureiro);
    }

    throw new InvalidParamsException();
  }

  public Aventureiro recrutarNovamente(Long id) {
    Aventureiro aventureiro = aventureiroRepository.findById(id).orElseThrow(() -> new InvalidParamsException());

    if (!aventureiro.isAtivo()) {
      aventureiro.setAtivo(true);
      return aventureiroRepository.save(aventureiro);
    }

    throw new InvalidParamsException();
  }

  public AventureiroResponseDto registrarAventureiro(Aventureiro aventureiro) {
    if (aventureiro.getNome() == null || aventureiro.getNome().isBlank())
      throw new InvalidParamsException();

    if (aventureiro.getClasse() == null)
      throw new InvalidParamsException();

    if (aventureiro.getNivel() == null || aventureiro.getNivel() <= 0)
      throw new InvalidParamsException();

    if (aventureiro.getOrganizacao() == null)
      throw new InvalidParamsException();

    if (aventureiro.getUsuarioCadastro() == null)
      throw new InvalidParamsException();

    aventureiro.setAtivo(true);
    aventureiro.setCompanheiro(null);

    Aventureiro aventureiroCriado = aventureiroRepository.save(aventureiro);

    return AventureiroResponseDtoMapper.toAventureiroResponseDto(aventureiroCriado);
  }

  public AventureiroDetalhesDto visualizarAventureiroCompleto(Long id) {
    Aventureiro aventureiro = aventureiroRepository.findByIdComCompanheiro(id)
        .orElseThrow(ResourceNotFoundException::new);

    int totalParticipacoes = (int) participacaoMissaoRepository.countByAventureiroId(id);

    var participacoes = participacaoMissaoRepository.findByAventureiroIdOrderByCreatedAtDesc(id);
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
