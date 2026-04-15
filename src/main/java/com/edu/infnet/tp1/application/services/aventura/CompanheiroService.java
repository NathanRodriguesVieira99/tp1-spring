package com.edu.infnet.tp1.application.services.aventura;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.domain.models.aventura.Companheiro;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.CompanheiroRepository;
import com.edu.infnet.tp1.presentation.dtos.CompanheiroResponseDto;
import com.edu.infnet.tp1.shared.errors.exceptions.InvalidParamsException;
import com.edu.infnet.tp1.shared.errors.exceptions.ResourceNotFoundException;
import com.edu.infnet.tp1.shared.mappers.CompanheiroResponseDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanheiroService {
  private final AventureiroRepository aventureiroRepository;
  private final CompanheiroRepository companheiroRepository;

  public CompanheiroResponseDto criarCompanheiro(Long id, Companheiro companheiro) {
    Aventureiro aventureiro = aventureiroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());

    if (companheiro.getNome() == null || companheiro.getNome().isBlank())
      throw new InvalidParamsException();

    if (companheiro.getLealdade() < 0 || companheiro.getLealdade() > 100)
      throw new InvalidParamsException();

    if (aventureiro.getCompanheiro() == null) {
      companheiro.setAventureiro(aventureiro);
      Companheiro companheiroCriado = companheiroRepository.save(companheiro);
      return CompanheiroResponseDtoMapper.toCompanheiroResponseDto(companheiroCriado);
    }

    Companheiro companheiroRetornado = aventureiro.getCompanheiro();
    return CompanheiroResponseDtoMapper.toCompanheiroResponseDto(companheiroRetornado);
  }

  public Aventureiro removerCompanheiro(Long id) {
    Aventureiro aventureiro = aventureiroRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException());

    if (aventureiro.getCompanheiro() != null) {
      aventureiro.setCompanheiro(null);
      return aventureiroRepository.save(aventureiro);
    }

    return aventureiro;
  }
}
