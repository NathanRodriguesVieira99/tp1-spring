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
public class LojaGuildaBuscaMulticampoService {
  private final ElasticsearchClient client;

  public List<ProdutoRetornadoDto> buscarMulticampo(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.multiMatch(m -> m.fields("nome", "descricao", "categoria")
                .query(termo))),
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
