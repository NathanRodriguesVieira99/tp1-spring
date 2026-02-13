package com.edu.infnet.tp1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.data.AventureiroData;
import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.shared.dtos.PaginationQueryDto;
import com.edu.infnet.tp1.shared.exceptions.InvalidQueryParamException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarAventureirosService {

  private final AventureiroData aventureiroData;

  public List<Aventureiro> exec(PaginationQueryDto params) {
    if (params.page() < 0) {
      throw new InvalidQueryParamException();
    }

    if (params.size() < 1 || params.size() > 50)
      throw new InvalidQueryParamException();

    // if (params.classe() == null) {
    //   throw new InvalidQueryParamException();
    // }

    if (params.ativo() != true) {
      throw new InvalidQueryParamException();
    }

    if (params.nivelMinimo() <= 0) {
      throw new InvalidQueryParamException();
    }

    return aventureiroData.listarAventureiros(params);

  }

  public int contarAventureiros(PaginationQueryDto params) {
    return aventureiroData.contarAventureiros(params);
  }

}
