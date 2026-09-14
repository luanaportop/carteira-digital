package br.com.api.carteira.digital.util.exception;

public class CpfJaCadastradoException extends RuntimeException{
    public CpfJaCadastradoException(){
        super("CPF já cadastrado.");
    }
}
