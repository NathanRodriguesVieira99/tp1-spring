package com.edu.infnet.tp1.application.services.aventura;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.application.repositories.aventura.AventureiroRepository;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.presentation.dtos.PaginationQueryDto;
import com.edu.infnet.tp1.shared.exceptions.InvalidQueryParamException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarAventureirosService {

  private final AventureiroRepository aventureiroRepository;

  public List<Aventureiro> exec(PaginationQueryDto params) {
    if (params.page() < 0) {
      throw new InvalidQueryParamException();
    }

    if (params.size() < 1 || params.size() > 50)
      throw new InvalidQueryParamException();

    // if (params.classe() == null) {
    // throw new InvalidQueryParamException();
    // }

    if (params.ativo() != true) {
      throw new InvalidQueryParamException();
    }

    if (params.nivelMinimo() <= 0) {
      throw new InvalidQueryParamException();
    }

    Pageable pageable = PageRequest.of(params.page(), params.size());
    return aventureiroRepository.findAll(pageable).getContent();

  }

  public int contarAventureiros(PaginationQueryDto params) {
    return (int) aventureiroRepository.count();
  }

}
