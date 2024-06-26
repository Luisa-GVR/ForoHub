package com.hub.aluraForo.domain.respuesta;

import com.hub.aluraForo.domain.curso.Curso;
import com.hub.aluraForo.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTopico(Topico topico);
}
