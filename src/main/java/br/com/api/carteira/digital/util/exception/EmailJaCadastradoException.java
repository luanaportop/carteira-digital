package br.com.api.carteira.digital.util.exception;

public class EmailJaCadastradoException extends RuntimeException{
    public EmailJaCadastradoException() {
        super("E-mail já cadastrado.");
    }
}
