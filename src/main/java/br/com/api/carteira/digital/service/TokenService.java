package br.com.api.carteira.digital.service;

import br.com.api.carteira.digital.model.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuarioEntity usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Instant agora = Instant.now();
        Instant expiracao = agora.plus(2, ChronoUnit.HOURS);

        return JWT.create()
                .withIssuer("carteira-digital")
                .withSubject(usuario.getCodUsuario().toString())
                .withClaim("email", usuario.getEmail())
                .withIssuedAt(agora)
                .withExpiresAt(expiracao)
                .sign(algorithm);
    }
}
