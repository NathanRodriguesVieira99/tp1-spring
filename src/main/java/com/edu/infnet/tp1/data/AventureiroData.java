
package com.edu.infnet.tp1.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.edu.infnet.tp1.enums.Classes;
import com.edu.infnet.tp1.models.Aventureiro;
import com.edu.infnet.tp1.models.Companheiro;
import com.edu.infnet.tp1.shared.dtos.AtualizarAventureiroRequestDto;
import com.edu.infnet.tp1.shared.dtos.PaginationQueryDto;

/**
 *
 * @author nathan_rodrigues
 */
@Repository
public class AventureiroData {
  private final ArrayList<Aventureiro> aventureiros;

  public AventureiroData() {
    this.aventureiros = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      Aventureiro aventureiro = new Aventureiro(
          UUID.randomUUID(),
          "Aventureiro" + (i + 1), // evita comecar em 0
          Classes.GUERREIRO,
          (int) (Math.random() * 100) + 1, // gera nivel ente 1 e 100
          true,
          null);

      aventureiros.add(aventureiro);
    }
  }

  public Aventureiro registrar(Aventureiro aventureiro) {
    aventureiros.add(aventureiro);
    return aventureiro;
  }

  // Tive que pesquisar como se mapeia em Java, não conhecia esse .stream() para
  // navegar em lists/arrays
  public Optional<Aventureiro> buscarAventureiroPorId(UUID id) {
    return aventureiros.stream().filter(aventureiro -> aventureiro.getId().equals(id)).findFirst();
  }

  public List<Aventureiro> listarAventureiros(PaginationQueryDto params) {
    // Filtros
    List<Aventureiro> aventureirosFiltrados = aventureiros.stream()
        .filter(av -> params.classe() == null || av.getClasse().name().equals(params.classe()))
        .filter(av -> params.ativo() == null || av.getAtivo().equals(params.ativo()))
        .filter(av -> params.nivelMinimo() == null || av.getNivel().doubleValue() >= params.nivelMinimo())
        .collect(Collectors.toList());

    // Pagination
    int paginaInicial = params.page() * params.size();
    int paginaFinal = paginaInicial + params.size();

    if (paginaInicial >= aventureirosFiltrados.size())
      return new ArrayList<>();

    return aventureirosFiltrados.subList(paginaInicial, Math.min(paginaFinal, aventureirosFiltrados.size()));

  }

  public int contarAventureiros(PaginationQueryDto params) {
    // Valida e conta o total de Aventureiros na List<Aventureiro>
    return (int) aventureiros.stream()
        .filter(av -> params.classe() == null || av.getClasse().name().equals(params.classe()))
        .filter(av -> params.ativo() == null || av.getAtivo().equals(params.ativo()))
        .filter(av -> params.nivelMinimo() == null || av.getNivel().doubleValue() >= params.nivelMinimo())
        .count();

  }

  public Optional<Aventureiro> atualizarAventureiro(UUID id, AtualizarAventureiroRequestDto aventureiroAtualizado) {
    // busca na lista de aventureiros pelo ID
    Optional<Aventureiro> aventureiro = this.buscarAventureiroPorId(id);

    // se encontrar o aventureiro
    if (aventureiro.isPresent()) {
      Aventureiro aventureiroEncontrado = aventureiro.get();

      // se o campo não estiver vazio -> atualiza nome
      if (aventureiroAtualizado.nome() != null && !aventureiroAtualizado.nome().isEmpty()) {
        aventureiroEncontrado.setNome(aventureiroAtualizado.nome());
      }

      // se o campo não estiver vazio -> atualiza classe
      if (aventureiroAtualizado.classe() != null) {
        aventureiroEncontrado.setClasse(aventureiroAtualizado.classe());
      }

      // se o campo não estiver vazio -> atualiza nivel
      if (aventureiroAtualizado.nivel() != null) {
        aventureiroEncontrado.setNivel(aventureiroAtualizado.nivel().intValue());
      }

      return Optional.of(aventureiroEncontrado);
    }

    // se não preencher nenhum requisito
    return Optional.empty();
  }

  public Companheiro definirCompanheiro(UUID id, Companheiro companheiro) {
    Optional<Aventureiro> aventureiroSemCompanheiro = buscarAventureiroPorId(id);

    if (aventureiroSemCompanheiro.isPresent()) {
      Aventureiro aventureiroEncontrado = aventureiroSemCompanheiro.get();

      if (aventureiroEncontrado.getCompanheiro() == null) {
        aventureiroEncontrado.setCompanheiro(companheiro);
        return companheiro;
      } else {
        return aventureiroEncontrado.getCompanheiro();
      }
    }
    return null;
  }
}
