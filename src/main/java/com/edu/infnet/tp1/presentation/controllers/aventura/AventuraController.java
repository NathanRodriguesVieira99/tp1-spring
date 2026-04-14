package com.edu.infnet.tp1.presentation.controllers.aventura;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.infnet.tp1.application.services.aventura.AtualizarDadosAventureiroService;
import com.edu.infnet.tp1.application.services.aventura.BuscaAventureiroPorNomeService;
import com.edu.infnet.tp1.application.services.aventura.BuscaMissaoPorIdService;
import com.edu.infnet.tp1.application.services.aventura.BuscarAventureiroPorIdService;
import com.edu.infnet.tp1.application.services.aventura.EncerrarVinculoGuildaService;
import com.edu.infnet.tp1.application.services.aventura.ListarAventureirosService;
import com.edu.infnet.tp1.application.services.aventura.RankingParticipacaoService;
import com.edu.infnet.tp1.application.services.aventura.RecrutarNovamenteService;
import com.edu.infnet.tp1.application.services.aventura.RegistrarAventureiroService;
import com.edu.infnet.tp1.application.services.aventura.VisualizacaoAventureiroCompletaService;
import com.edu.infnet.tp1.domain.models.aventura.Aventureiro;
import com.edu.infnet.tp1.presentation.dtos.AtualizarAventureiroRequestDto;
import com.edu.infnet.tp1.presentation.dtos.AventureiroDetalhesDto;
import com.edu.infnet.tp1.presentation.dtos.MissaoDetalhesDto;
import com.edu.infnet.tp1.presentation.dtos.PaginationQueryDto;
import com.edu.infnet.tp1.presentation.dtos.PaginationResponseDto;
import com.edu.infnet.tp1.presentation.dtos.RankingParticipacaoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class AventuraController {
  private final AtualizarDadosAventureiroService atualizarDadosAventureiroService;
  private final BuscaAventureiroPorNomeService buscaAventureiroPorNomeService;
  private final BuscaMissaoPorIdService buscaMissaoPorIdService;
  private final BuscarAventureiroPorIdService buscarAventureirosPorIdService;
  private final EncerrarVinculoGuildaService encerrarVinculoGuildaService;
  private final ListarAventureirosService listarAventureirosService;
  private final RankingParticipacaoService rankingParticipacaoService;
  private final RecrutarNovamenteService recrutarNovamenteService;
  private final RegistrarAventureiroService registrarAventureiroService;
  private final VisualizacaoAventureiroCompletaService visualizacaoAventureiroCompletaService;

  public class AtualizarDadosAventureiroController {
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarDadosAventureiro(
        @PathVariable Long id,
        @RequestBody AtualizarAventureiroRequestDto aventureiroAtualizado) {
      Aventureiro resultado = atualizarDadosAventureiroService.exec(id, aventureiroAtualizado);
      return ResponseEntity.ok().body(resultado);
    }
  }

  public class BuscaAventureiroPorNomeController {
    @GetMapping("/buscar")
    public ResponseEntity<List<Aventureiro>> buscar(
        @RequestParam String nome,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(buscaAventureiroPorNomeService.exec(nome, page, size));
    }
  }

  public class BuscaMissaoPorIdController {
    @GetMapping("/{id}/detalhes")
    public ResponseEntity<MissaoDetalhesDto> detalhes(
        @PathVariable Long id) {
      return ResponseEntity.ok(buscaMissaoPorIdService.execComDetalhes(id));
    }
  }

  public class BuscarAventureiroPorIdController {
    @GetMapping("/{id}")
    public ResponseEntity<?> listarAventureiros(@PathVariable Long id) {
      Aventureiro aventureiro = buscarAventureirosPorIdService.exec(id);
      return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }
  }

  public class EncerrarVinculoGuildaController {
    @PatchMapping("/remove/{id}")
    public ResponseEntity<?> encerrarVinculoGuilda(@PathVariable Long id) {
      Aventureiro aventureiroAtivo = encerrarVinculoGuildaService.exec(id);
      return ResponseEntity.status(HttpStatus.OK).body(aventureiroAtivo);
    }
  }

  public class ListarAventureirosController {
    @GetMapping()
    public ResponseEntity<?> listarAventureiros(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String classe,
        @RequestParam(required = false) Boolean ativo,
        @RequestParam(required = false) Integer nivelMinimo) {
      PaginationQueryDto params = new PaginationQueryDto(page, size, classe, ativo, nivelMinimo);

      List<Aventureiro> resultado = listarAventureirosService.exec(params);

      List<PaginationResponseDto> result = resultado.stream()
          .map(aventureiro -> new PaginationResponseDto(
              aventureiro.getId(),
              aventureiro.getNome(),
              aventureiro.getClasse(),
              aventureiro.getNivel(),
              aventureiro.isAtivo(),
              aventureiro.getCompanheiro()))
          .toList();

      int totalAventureiros = listarAventureirosService.contarAventureiros(params);

      int totalPages = (int) Math.ceil(totalAventureiros / (double) size);

      HttpHeaders headers = new HttpHeaders();
      headers.add("X-Total-Count", String.valueOf(totalAventureiros));
      headers.add("X-Page", String.valueOf(page));
      headers.add("X-Size", String.valueOf(size));
      headers.add("X-Total-Pages", String.valueOf(totalPages));

      return ResponseEntity.ok().headers(headers).body(result);
    }
  }

  public class RankingParticipacaoController {
    @GetMapping("/ranking")
    public ResponseEntity<List<RankingParticipacaoDto>> ranking() {
      return ResponseEntity.ok(rankingParticipacaoService.exec());
    }
  }

  public class VisualizacaoAventureiroCompletaController {
    @GetMapping("/{id}/detalhes")
    public ResponseEntity<AventureiroDetalhesDto> detalhes(
        @PathVariable Long id) {
      var resultado = visualizacaoAventureiroCompletaService.exec(id);
      return ResponseEntity.ok(resultado);
    }
  }

  @PatchMapping("/recruit/{id}")
  public ResponseEntity<?> encerrarVinculoGuilda(@PathVariable Long id) {
    Aventureiro aventureiroInativo = recrutarNovamenteService.exec(id);
    return ResponseEntity.status(HttpStatus.OK).body(aventureiroInativo);
  }

  @PostMapping("/create")
  public ResponseEntity<?> registrarAventureiro(@RequestBody Aventureiro aventureiro) {
    Aventureiro novoAventureiro = registrarAventureiroService.exec(aventureiro);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoAventureiro);
  }

}
