package com.edu.infnet.tp1.application.services.aventura;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.application.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscaAventureiroPorNomeService {

  private final AventureiroRepository aventureiroRepository;

  public List<Aventureiro> exec(String nome, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return aventureiroRepository.findByNomeContaining(nome, pageable).getContent();
  }
}
