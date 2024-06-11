package com.hub.aluraForo.controller;

import com.hub.aluraForo.domain.categoria.Categoria;
import com.hub.aluraForo.domain.curso.Curso;
import com.hub.aluraForo.domain.curso.CursoRepository;
import com.hub.aluraForo.domain.topico.Topico;
import com.hub.aluraForo.domain.topico.TopicoRepository;
import com.hub.aluraForo.domain.usuario.Usuario;
import com.hub.aluraForo.domain.usuario.UsuarioRepository;
import com.hub.aluraForo.infra.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SecurityRequirement(name = "bearer-key")
@Controller
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    private String correoElectronicoGlobal;

    private String nombreGlobal;



    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    //Todos los topicos
    @GetMapping
    public String inicio(@RequestParam(name = "correoElectronico", required = true) String correoElectronico,
                         @RequestParam(name = "token", required = true) String token,
                         Model model){

        model.addAttribute("token", token);
        correoElectronicoGlobal = correoElectronico;

        String nombreUsuario = usuarioRepository.findNombreUsuarioByCorreo(correoElectronico);

        //Agregar global
        if (nombreGlobal == null){
            nombreGlobal = nombreUsuario;
        }

        List<Object[]> todosLosTopicos = topicoRepository.findAllTopicsWithDetails();

        model.addAttribute("topicos", todosLosTopicos);
        model.addAttribute("nombre", nombreGlobal);
        return "topicos";

    }


    @PostMapping("/nuevo")
    public String registrarTopico(@RequestParam(name = "titulo") String titulo,
                                  @RequestParam(name = "mensaje") String mensaje,
                                  @RequestParam(name = "nombreCurso") String nombreCurso,
                                  @RequestParam(name = "categoria") String categoria,
                                  @RequestParam(name = "correoElectronico") String correoElectronico,
                                  @RequestParam(name = "token") String token,
                                  Model model) {

        model.addAttribute("token", token);
        model.addAttribute("correoElectronico", correoElectronico);

        //Topico repetido
        Topico existingTopico = topicoRepository.findByTituloAndMensaje(titulo, mensaje);
        if (existingTopico != null) {
            return "redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico;
        }


        //Curso repetido
        Curso curso = cursoRepository.findByNombreAndCategoria(nombreCurso, Categoria.valueOf(categoria));

        if (curso == null) {
            curso = new Curso();
            curso.setNombre(nombreCurso);
            curso.setCategoria(Categoria.valueOf(categoria));

            System.out.println("nombre: " + nombreCurso + " categoria: " + Categoria.valueOf(categoria));
            cursoRepository.save(curso);
        }


        // Crear el tópico
        Topico topico = new Topico();
        topico.setTitulo(titulo);
        topico.setMensaje(mensaje);
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setStatus("active");
        topico.setCurso(curso);


        // Obtener el autor usando el correo electrónico
        Usuario autor = usuarioRepository.findUsuarioByCorreoElectronico(correoElectronicoGlobal);


        topico.setAutor(autor);

        topicoRepository.save(topico);

        List<Object[]> todosLosTopicos = topicoRepository.findAllTopicsWithDetails();


        // redirigir de vuelta a la página de tópicos
        model.addAttribute("topicos", todosLosTopicos);

        return "redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico;
    }


    //Aqui empiezan los exlusivos de {id}



    //Obtener un topico especifico
    @GetMapping("/{id}")
    public ModelAndView detalleTopico(@PathVariable Long id,
                                      @RequestParam(name = "token", required = true) String token

    ) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();

            // agregar topico al modelview
            ModelAndView modelAndView = new ModelAndView("topicoID");
            modelAndView.addObject("topico", topico);
            modelAndView.addObject("token", token);

            modelAndView.addObject("correoElectronico", correoElectronicoGlobal);

            return modelAndView;

        } else {
            return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronicoGlobal);
        }
    }

    //Borrar un topico especifico
    @DeleteMapping("/{id}")
    public ModelAndView eliminarTopico(@PathVariable Long id,
                                       @RequestParam(name = "token", required = true) String token,
                                       @RequestParam(name = "correoElectronico", required = true) String correoElectronico) {


        Optional<Topico> topicoOptional = topicoRepository.findById(id);


        if (topicoOptional.isPresent()) {

            Topico topico = topicoOptional.get();

            //usuario coincide
            Usuario autor = usuarioRepository.findUsuarioByCorreoElectronico(correoElectronico);
            if (topico.getAutor().equals(autor)) {

                topicoRepository.delete(topico);
                return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico);
            } else { //Usuario no coincide

                return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico)
                        .addObject("error", "No autorizado");
            }
        } else {
            return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico)
                    .addObject("error", "No encontrado");
        }
    }

    @PutMapping("/{id}")
    public ModelAndView editarTopico(@PathVariable Long id,
                                     @RequestParam(name = "token", required = true) String token,
                                     @RequestParam(name = "correoElectronico", required = true) String correoElectronico,
                                     @RequestParam(name = "mensaje", required = true) String mensaje,
                                     @RequestParam(name = "status", required = true) String status) {

        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();

            Usuario autor = usuarioRepository.findUsuarioByCorreoElectronico(correoElectronico);
            if (topico.getAutor().equals(autor)) {

                String originalMensaje = topico.getMensaje();
                if (!originalMensaje.equals(mensaje)) {
                    mensaje = mensaje + " (editado)"; //Agregar el "editado" al final si es que se edito el mensaje
                }


                topico.setMensaje(mensaje);
                topico.setStatus(status);
                topicoRepository.save(topico);
                return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico);
            } else {
                return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico)
                        .addObject("error", "No autorizado");
            }
        } else {
            return new ModelAndView("redirect:/topicos?token=" + token + "&correoElectronico=" + correoElectronico)
                    .addObject("error", "Topico no encontrado");
        }
    }



}
