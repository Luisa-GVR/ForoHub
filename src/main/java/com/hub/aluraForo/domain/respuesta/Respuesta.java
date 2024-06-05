package com.hub.aluraForo.domain.respuesta;

import com.hub.aluraForo.domain.topico.Topico;
import com.hub.aluraForo.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mensaje;
    private LocalDateTime fechaCreacion;
    private boolean solucion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;

}
