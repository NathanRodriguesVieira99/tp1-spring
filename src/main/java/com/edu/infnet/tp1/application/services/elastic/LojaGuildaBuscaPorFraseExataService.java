package com.edu.infnet.tp1.application.services.elastic;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.elastic.LojaGuildaDocument;
import com.edu.infnet.tp1.presentation.dtos.elastic.ProdutoRetornadoDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LojaGuildaBuscaPorFraseExataService {
  private final ElasticsearchClient client;

  public List<ProdutoRetornadoDto> buscarPorFraseExata(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.matchPhrase(m -> m.field("descricao").query(termo))),
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
