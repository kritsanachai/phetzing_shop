package com.example.project.phetzing_shop.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.phetzing_shop.model.AccountCredentials;
import com.example.project.phetzing_shop.service.JwtService;


@RestController
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        try {
            UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.email(),
            credentials.password());
    Authentication auth = authenticationManager.authenticate(creds);
    // Generate token
    String jwts = jwtService.getToken(auth.getName());
    // Build response with the generated token
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
            "Bearer" + jwts).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                    "Authorization")
            .build();
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.ok(e);
        }
       
    }
}
