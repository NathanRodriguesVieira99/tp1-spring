package com.edu.infnet.tp1.application.services.aventura;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.aventura.Missao;
import com.edu.infnet.tp1.infrastructure.repositories.aventura.MissaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarMissoesService {

  private final MissaoRepository missaoRepository;

  public List<Missao> exec(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return missaoRepository.findAll(pageable).getContent();
  }
}
