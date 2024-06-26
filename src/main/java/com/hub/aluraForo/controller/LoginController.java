package com.hub.aluraForo.controller;

import com.hub.aluraForo.domain.usuario.DatosAutenticacionUsuario;
import com.hub.aluraForo.domain.usuario.Usuario;
import com.hub.aluraForo.infra.security.DatosJWTToken;
import com.hub.aluraForo.infra.security.TokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new DatosAutenticacionUsuario("", ""));
        return "login";
    }



    @PostMapping
    public String autenticarUsuario(@RequestParam String correoElectronico,
                                    @RequestParam String contrasena,
                                    RedirectAttributes redirectAttributes) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(correoElectronico, contrasena);

        try {
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

            //No olvidar pasar este siempre
            redirectAttributes.addAttribute("token", JWTtoken);
            redirectAttributes.addAttribute("correoElectronico", correoElectronico);


            return "redirect:/topicos";
        } catch (AuthenticationException e) {
            logger.error("Error de autenticación: " + e.getMessage());

            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/login";
        }
    }

}
