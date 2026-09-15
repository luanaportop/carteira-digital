package br.com.api.carteira.digital.util.exception;

public class CarteiraNaoEncontradaException extends RuntimeException {
    public CarteiraNaoEncontradaException() {
        super("Carteira não encontrada");
    }
}
