package com.hub.aluraForo.domain.curso;

import com.hub.aluraForo.domain.categoria.Categoria;
import jakarta.validation.constraints.NotBlank;

public record DatosCurso(
        @NotBlank
        String id,
        @NotBlank
        String nombre,
        @NotBlank
        Categoria categoria
) {
}