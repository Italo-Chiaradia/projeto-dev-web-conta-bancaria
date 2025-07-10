/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conta.bancaria.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExtratoCompleto implements Serializable {

    private static final long serialVersionUID = 1L;

    // Campos da tabela EXTRATO original
    private Integer idExtrato;
    private Integer idCliente;
    private Date createdAt;

    private String tipo;
    private BigDecimal valor;
    private Integer idTransferencia;

    // Campos adicionais da VIEW (vindos dos JOINs)
    private String nomeCliente;
    private Integer idRemetente;
    private String nomeRemetente;
    private Integer idDestinatario;
    private String nomeDestinatario;
    
    public ExtratoCompleto() {
    }
    
    public ExtratoCompleto(Integer idExtrato, Integer idCliente, Date createdAt, String tipo, BigDecimal valor, Integer idTransferencia, String nomeCliente, Integer idRemetente, String nomeRemetente, Integer idDestinatario, String nomeDestinatario) {
        this.idExtrato = idExtrato;
        this.idCliente = idCliente;
        this.createdAt = createdAt;
        this.tipo = tipo;
        this.valor = valor;
        this.idTransferencia = idTransferencia;
        this.nomeCliente = nomeCliente;
        this.idRemetente = idRemetente;
        this.nomeRemetente = nomeRemetente;
        this.idDestinatario = idDestinatario;
        this.nomeDestinatario = nomeDestinatario;
    }

    


    public Integer getIdExtrato() {
        return idExtrato;
    }

    public void setIdExtrato(Integer idExtrato) {
        this.idExtrato = idExtrato;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Integer idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(Integer idRemetente) {
        this.idRemetente = idRemetente;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }
}
