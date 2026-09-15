package br.com.api.carteira.digital.security;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidacaoErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro,
        Map<String, String> campos,
        String path
) {
}
