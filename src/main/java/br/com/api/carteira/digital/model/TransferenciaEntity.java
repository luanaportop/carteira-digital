package br.com.api.carteira.digital.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transferencias")
public class TransferenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_transferencia")
    private Long codTransferencia;

    @Column(name = "cod_carteira_origem")
    private Long codCarteiraOrigem;

    @Column(name = "cod_carteira_destino")
    private Long codCarteiraDestino;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "status")
    private String status;

    @Column(name = "chave_idempotencia")
    private String chaveIdempotencia;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
}
