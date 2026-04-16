package com.edu.infnet.tp1.presentation.dtos.aventura;

public record BuscaAventureiroPorNomeDto(
    String nome,
    int page,
    int size) {
}
