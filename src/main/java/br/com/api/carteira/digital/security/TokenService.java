package br.com.api.carteira.digital.security;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String gerarToken(
            UsuarioPrincipal usuario) {

        Instant agora = Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer("carteira-digital")
                        .subject(
                                usuario.getCodUsuario().toString()
                        )
                        .issuedAt(agora)
                        .expiresAt(
                                agora.plus(
                                        2,
                                        ChronoUnit.HOURS
                                )
                        )
                        .claim(
                                "email",
                                usuario.getEmail()
                        )
                        .claim(
                                "roles",
                                List.of(usuario.getRole().name())
                        )
                        .build();

        JwsHeader header =
                JwsHeader
                        .with(MacAlgorithm.HS256)
                        .build();

        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(
                                header,
                                claims
                        )
                )
                .getTokenValue();
    }

}
