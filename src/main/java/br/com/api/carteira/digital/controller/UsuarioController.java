package br.com.api.carteira.digital.controller;

import br.com.api.carteira.digital.dto.UsuarioCadastroDTO;
import br.com.api.carteira.digital.dto.UsuarioResponseDTO;
import br.com.api.carteira.digital.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar (@Valid @RequestBody UsuarioCadastroDTO dto) {
        UsuarioResponseDTO resposta = usuarioService.cadastrar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

}
