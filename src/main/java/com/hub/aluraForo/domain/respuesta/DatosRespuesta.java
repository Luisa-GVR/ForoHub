package com.hub.aluraForo.domain.respuesta;


import com.hub.aluraForo.domain.topico.Topico;
import com.hub.aluraForo.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosRespuesta(
        @NotBlank
        int id,
        String mensaje,
        Boolean solucion,
        LocalDateTime fechaCreacion,
        Usuario autor,
        Topico topico
) {
}
