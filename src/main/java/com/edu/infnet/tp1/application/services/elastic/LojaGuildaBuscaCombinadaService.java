package com.edu.infnet.tp1.application.services.elastic;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.elastic.LojaGuildaDocument;
import com.edu.infnet.tp1.presentation.dtos.elastic.ProdutoRetornadoDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LojaGuildaBuscaCombinadaService {
  private final ElasticsearchClient client;

  public List<ProdutoRetornadoDto> buscaCombinada(String raridade, String categoria, BigDecimal min, BigDecimal max)
      throws IOException {

    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.bool(b -> b
                .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
                .filter(f -> f.term(t -> t.field("raridade").value(raridade)))
                .filter(f -> f.range(r -> r.untyped(u -> u.field("preco")
                    .gte(JsonData.of(min))
                    .lte(JsonData.of(max))))))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  private List<ProdutoRetornadoDto> toDtoList(SearchResponse<LojaGuildaDocument> response) {
    return response.hits()
        .hits()
        .stream()
        .map(ProdutoRetornadoDto::toDTO)
        .toList();
  }
}
