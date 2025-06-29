/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conta.bancaria.model;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author italo
 */

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String cpf;
    private String senhaHash;
    private BigDecimal saldo;
    private BigDecimal saldoInvestido;
    private String agencia;
    private Long contaBancaria;

    public Cliente() {
    }

    public Cliente(Integer id) {
        this.id = id;
    }

    public Cliente(Integer id, String nome, String cpf, String senhaHash, Long contaBancaria) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senhaHash = senhaHash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldoInvestido() {
        return saldoInvestido;
    }

    public void setSaldoInvestido(BigDecimal saldoInvestido) {
        this.saldoInvestido = saldoInvestido;
    }
    
        public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Long getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(Long contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

}
