package br.com.api.carteira.digital.service;

import br.com.api.carteira.digital.dto.LoginDTO;
import br.com.api.carteira.digital.dto.LoginResponseDTO;
import br.com.api.carteira.digital.model.UsuarioEntity;
import br.com.api.carteira.digital.repository.UsuarioRepository;
import br.com.api.carteira.digital.util.exception.CredenciaisInvalidasException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    public LoginResponseDTO autenticar(LoginDTO loginDTO){
        UsuarioEntity usuario = usuarioRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new CredenciaisInvalidasException());

        if (!passwordEncoder.matches(loginDTO.senha(), usuario.getSenha())){
            throw new CredenciaisInvalidasException();
        }

        String token = tokenService.gerarToken(usuario);

        return new LoginResponseDTO(token);

    }
}
