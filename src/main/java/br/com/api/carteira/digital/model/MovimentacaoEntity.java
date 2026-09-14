package br.com.api.carteira.digital.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacoes")
public class MovimentacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_movimentacao")
    private Long codMovimentacao;

    @Column(name = "cod_carteira")
    private Long codCarteira;

    @Column(name = "cod_transferencia")
    private Long codTransferencia;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "saldo_anterior")
    private BigDecimal saldoAnterior;

    @Column(name = "saldo_posterior")
    private BigDecimal saldoPosterior;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
}
