package br.com.api.carteira.digital.controller;

import br.com.api.carteira.digital.dto.CarteiraResponseDTO;
import br.com.api.carteira.digital.service.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carteiras")
public class CarteiraController {
    @Autowired
    CarteiraService carteiraService;

    @GetMapping("/me")
    public ResponseEntity<CarteiraResponseDTO> buscarMinhaCarteira(@AuthenticationPrincipal Jwt jwt){
        Long codUsuario = Long.valueOf(jwt.getSubject());

        CarteiraResponseDTO carteira = carteiraService.buscarMinhaCarteira(codUsuario);

        return ResponseEntity.ok(carteira);
    }
}
