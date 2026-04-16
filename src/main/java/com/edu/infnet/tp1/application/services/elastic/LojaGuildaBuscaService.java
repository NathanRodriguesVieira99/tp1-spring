package com.edu.infnet.tp1.application.services.elastic;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.elastic.LojaGuildaDocument;
import com.edu.infnet.tp1.presentation.dtos.elastic.ProdutoRetornadoDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LojaGuildaBuscaService {
  private final ElasticsearchClient client;

  public List<ProdutoRetornadoDto> buscaCombinada(String raridade, String categoria, BigDecimal min, BigDecimal max)
      throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.bool(b -> b
                .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
                .filter(f -> f.term(t -> t.field("raridade").value(raridade)))
                .filter(f -> f.range(r -> r.number(n -> n.field("preco")
                    .gte(min.doubleValue())
                    .lte(max.doubleValue())))))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarComFiltros(String termo, String categoria) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.bool(b -> b.must(m -> m.match(ma -> ma.field("descricao")
                .query(termo)))
                .filter(f -> f.term(t -> t.field("categoria")
                    .value(categoria))))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarFaixaPreco(BigDecimal min, BigDecimal max) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.range(r -> r
                .number(n -> n
                    .field("preco")
                    .gte(min.doubleValue())
                    .lte(max.doubleValue())))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarFuzzy(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.match(m -> m.field("nome")
                .query(termo)
                .fuzziness("AUTO"))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarMulticampo(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.multiMatch(m -> m.fields("nome", "descricao", "categoria")
                .query(termo))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarPorDescricao(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.match(m -> m.field("descricao").query(termo))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarPorFraseExata(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.matchPhrase(m -> m.field("descricao").query(termo))),
        LojaGuildaDocument.class);

    return toDtoList(response);
  }

  public List<ProdutoRetornadoDto> buscarPorNome(String termo) throws IOException {
    SearchResponse<LojaGuildaDocument> response = client.search(
        s -> s.index("guilda_loja")
            .query(q -> q.match(m -> m.field("nome").query(termo))),
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
