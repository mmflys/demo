package com.github.mmflys.demo.controller;

import com.github.mmflys.demo.domain.Response;
import com.github.mmflys.demo.domain.UserLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/root")
    public ResponseEntity<Response> root() {
        Response response = Response.builder().message("Hello world!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserLogin userLogin) {
        Response response = Response.builder().message(userLogin.getUsername() + " succeed to login").build();
        return ResponseEntity.ok(response);
    }

}
