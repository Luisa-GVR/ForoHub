package com.hub.aluraForo.domain.topico;

import com.hub.aluraForo.domain.curso.Curso;
import com.hub.aluraForo.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosTopico(
        @NotBlank
        int id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        Usuario autor,
        Curso curso

) {
}
