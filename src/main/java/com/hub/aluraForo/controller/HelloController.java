package com.hub.aluraForo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "bearer-key")
@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String helloWorld(Model model) {
        return "hello";  // This should resolve to hello.html
    }
}
