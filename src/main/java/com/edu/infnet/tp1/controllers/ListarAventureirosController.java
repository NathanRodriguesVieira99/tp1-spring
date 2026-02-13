package com.edu.infnet.tp1.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.services.ListarAventureirosService;
import com.edu.infnet.tp1.shared.dtos.PaginationQueryDto;
import com.edu.infnet.tp1.shared.dtos.PaginationResponseDto;

@RestController
@RequestMapping("/api/aventureiros")
public class ListarAventureirosController {

  @Autowired
  ListarAventureirosService listarAventureirosService;

  @GetMapping()
  public ResponseEntity<?> listarAventureiros(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String classe,
      @RequestParam(required = false) Boolean ativo,
      @RequestParam(required = false) Integer nivelMinimo) {

    PaginationQueryDto params = new PaginationQueryDto(page, size, classe, ativo, nivelMinimo);

    List<Aventureiro> resultado = listarAventureirosService.exec(params);

    List<PaginationResponseDto> res = resultado.stream()
        .map(aventureiros -> new PaginationResponseDto(
            aventureiros.getId(),
            aventureiros.getNome(),
            aventureiros.getClasse(),
            aventureiros.getNivel(),
            aventureiros.getAtivo(),
            aventureiros.getCompanheiro()))
        .collect(Collectors.toList());

    int totalAventureiros = listarAventureirosService.contarAventureiros(params);

    int totalPages = (int) Math.ceil(totalAventureiros / (double) size);

    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Total-Count", String.valueOf(totalAventureiros));
    headers.add("X-Page", String.valueOf(page));
    headers.add("X-Size", String.valueOf(size));
    headers.add("X-Total-Pages", String.valueOf(totalPages));

    return ResponseEntity.ok().headers(headers).body(res);

  }
}
