package com.example.aplicativopim.model;

import java.math.BigDecimal;
import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;

public class Pedido {
    private Long id;
    private BigDecimal valorTotal;
    private String status;
    private Date dataCompra; // Nova data de compra
    private Date dataCriacao; // Nova data de criação
    private String metodoPagamento; // Método de pagamento
    private String localPagamento; // Local do pagamento
    private BigDecimal valorPago; // Valor pago
    private Integer quantidadeParcelas; // Quantidade de parcelas
    private BigDecimal valorParcelas; // Valor das parcelas

    // Construtor atualizado
    public Pedido(Long id, BigDecimal valorTotal, String status, Date dataCompra,
                  Date dataCriacao, String metodoPagamento, String localPagamento,
                  BigDecimal valorPago, Integer quantidadeParcelas, BigDecimal valorParcelas) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.status = status;
        this.dataCompra = dataCompra;
        this.dataCriacao = dataCriacao;
        this.metodoPagamento = metodoPagamento;
        this.localPagamento = localPagamento;
        this.valorPago = valorPago;
        this.quantidadeParcelas = quantidadeParcelas;
        this.valorParcelas = valorParcelas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getLocalPagamento() {
        return localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public BigDecimal getValorParcelas() {
        return valorParcelas;
    }

    public void setValorParcelas(BigDecimal valorParcelas) {
        this.valorParcelas = valorParcelas;
    }
}