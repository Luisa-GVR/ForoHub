package com.hub.aluraForo.domain.topico;

import com.hub.aluraForo.domain.curso.Curso;
import com.hub.aluraForo.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosTopico(
        @NotBlank
        int id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        LocalDateTime fechaCreacion,
        @NotBlank
        String status,
        @NotBlank
        Usuario autor,
        @NotBlank
        Curso curso

) {
}
