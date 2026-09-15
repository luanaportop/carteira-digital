package br.com.api.carteira.digital.dto;

import br.com.api.carteira.digital.util.enums.StatusCarteira;
import java.math.BigDecimal;

public record CarteiraResponseDTO(
        Long codCarteira,
        BigDecimal saldo,
        StatusCarteira status
) {
}
