package com.edu.infnet.tp1.presentation.dtos.aventura;

import com.edu.infnet.tp1.domain.enums.Classes;

public record AtualizarAventureiroRequestDto(String nome, Classes classe, Number nivel) {

}
