package br.com.api.carteira.digital.security;

import br.com.api.carteira.digital.dto.LoginDTO;
import br.com.api.carteira.digital.dto.LoginResponseDTO;
import br.com.api.carteira.digital.model.UsuarioEntity;
import br.com.api.carteira.digital.repository.UsuarioRepository;
import br.com.api.carteira.digital.util.exception.CredenciaisInvalidasException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(
            AuthenticationManager authenticationManager,
            TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO autenticar(
            LoginDTO loginDTO) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDTO.email(),
                                    loginDTO.senha()
                            )
                    );

            UsuarioPrincipal usuario =
                    (UsuarioPrincipal)
                            authentication.getPrincipal();

            String token =
                    tokenService.gerarToken(usuario);

            return new LoginResponseDTO(token);

        } catch (AuthenticationException exception) {

            throw new CredenciaisInvalidasException();
        }
    }
}
