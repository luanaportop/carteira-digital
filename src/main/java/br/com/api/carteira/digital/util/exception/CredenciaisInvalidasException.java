package br.com.api.carteira.digital.util.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {

        super("Email ou senha inválidos");
    }
}
