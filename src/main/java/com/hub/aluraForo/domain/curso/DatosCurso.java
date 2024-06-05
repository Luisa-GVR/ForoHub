package com.hub.aluraForo.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DatosCurso(
        @NotBlank
        String id,
        @NotBlank
        String nombre,
        @NotBlank
        String categoria


) {
}
