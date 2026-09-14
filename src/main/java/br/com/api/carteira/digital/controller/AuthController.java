package br.com.api.carteira.digital.controller;

import br.com.api.carteira.digital.dto.LoginDTO;
import br.com.api.carteira.digital.dto.LoginResponseDTO;
import br.com.api.carteira.digital.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authLogin(@Valid @RequestBody LoginDTO loginDTO){
        LoginResponseDTO resposta = authService.autenticar(loginDTO);
        return ResponseEntity.ok(resposta);
    }
}
