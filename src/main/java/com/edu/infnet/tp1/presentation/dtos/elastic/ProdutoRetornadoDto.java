package com.edu.infnet.tp1.presentation.dtos.elastic;

import java.math.BigDecimal;

import com.edu.infnet.tp1.domain.models.elastic.LojaGuildaDocument;

import co.elastic.clients.elasticsearch.core.search.Hit;

public record ProdutoRetornadoDto(
    String nome,
    String categoria,
    String descricao,
    BigDecimal preco,
    String raridade) {
  public static ProdutoRetornadoDto toDTO(Hit<LojaGuildaDocument> hit) {
    LojaGuildaDocument doc = hit.source();
    return new ProdutoRetornadoDto(
        doc.getNome(),
        doc.getCategoria(),
        doc.getDescricao(),
        doc.getPreco(),
        doc.getRaridade());
  }
}
