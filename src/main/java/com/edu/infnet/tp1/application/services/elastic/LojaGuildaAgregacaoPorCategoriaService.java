package com.edu.infnet.tp1.application.services.elastic;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LojaGuildaAgregacaoPorCategoriaService {
  private final ElasticsearchClient client;

  public Map<String, Long> quantidadePorCategoria() throws IOException {
    SearchResponse<Void> response = client.search(
        s -> s.index("guilda_loja")
            .size(0)
            .aggregations("por_categoria", a -> a.terms(t -> t.field("categoria"))),
        Void.class);
    return response.aggregations()
        .get("por_categoria")
        .sterms()
        .buckets().array().stream()
        .collect(Collectors.toMap(
            b -> b.key().stringValue(),
            b -> b.docCount()));
  }
}
