package com.hub.aluraForo.domain.perfil;

import jakarta.validation.constraints.NotBlank;

public record DatosPerfil(
        @NotBlank
        String nombre) {

}
