package br.com.api.carteira.digital.dto;

public record UsuarioResponseDTO(
        Long codUsuario,
        String nome,
        String email,
        Long codCarteira
) {
}
