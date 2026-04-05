package com.edu.infnet.tp1.application.services.painelTaticoMissao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.edu.infnet.tp1.domain.models.painelTaticoMissao.PainelTaticoMissao;
import com.edu.infnet.tp1.infrastructure.repositories.painelTaticoMissao.PainelTaticoMissaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PainelTaticoMissaoService {
  private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;

  /*
   * Estrátegia de CACHE => Utilizei o Redis para armazenar o resultado da
   * QUERY que estava lenta.
   * Agora levam em média pelos meus testes algo entre 10ms e 5ms para exibir os
   * dados. 
   * Apenas a primeira chamada está um pouco lenta, as
   * próximas ja ficam cacheadas por 1min.
   */

  @Cacheable(value = "topMissoes15Dias")
  public List<PainelTaticoMissao> getTopMissoesNosUltimos15Dias() {
    return painelTaticoMissaoRepository.buscarTop10NosUltimos15Dias();
  }
}
