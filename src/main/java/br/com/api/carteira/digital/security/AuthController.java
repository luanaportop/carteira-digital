package br.com.api.carteira.digital.security;

import br.com.api.carteira.digital.dto.LoginDTO;
import br.com.api.carteira.digital.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/teste")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("Token válido! Usuário autenticado.");
    }
}
