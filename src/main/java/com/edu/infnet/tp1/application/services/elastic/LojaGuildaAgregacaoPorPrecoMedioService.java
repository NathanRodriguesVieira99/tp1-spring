package com.edu.infnet.tp1.application.services.elastic;

import java.io.IOException;

import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LojaGuildaAgregacaoPorPrecoMedioService {
  private final ElasticsearchClient client;

  public Double buscarPrecoMedioProdutos() throws IOException {
    SearchResponse<Void> response = client.search(
        s -> s.index("guilda_loja")
            .size(0)
            .aggregations("preco_medio", a -> a.avg(t -> t.field("preco"))),
        Void.class);

    double valorArredondado = Math.round(response.aggregations()
        .get("preco_medio")
        .avg()
        .value() * 100.0) / 100.0;

    return valorArredondado;
  }
}
