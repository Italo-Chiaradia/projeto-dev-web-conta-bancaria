/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conta.bancaria.repository;

import com.mycompany.conta.bancaria.model.Cliente;
import com.mycompany.conta.bancaria.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author italo
 */
public class ClienteRepository {

    /**
     * Salva um novo cliente no banco de dados.
     * Este método assume que o objeto Cliente já foi validado e contém todos os dados necessários.
     *
     * @param cliente O objeto Cliente a ser persistido.
     * @throws SQLException se ocorrer um erro de acesso ao banco de dados.
     */
    public void save(Cliente cliente) throws SQLException {
        
        final String sql = "insert into cliente ("
                + "nome, "
                + "cpf, "
                + "senha_hash,"
                + "conta_bancaria) values (?, ?, ?, ?)";

        
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getSenhaHash());
            stmt.setLong(4, cliente.getContaBancaria());

            stmt.executeUpdate();
        }
    }
    

    /**
     * Retorna um cliente completo pelo seu CPF.
     *
     * @param cpf O CPF do cliente a ser buscado.
     * @return O objeto Cliente preenchido se encontrado, caso contrário, retorna null.
     * @throws SQLException se ocorrer um erro de acesso ao banco de dados.
     */
    public Cliente findClienteByCpf(String cpf) throws SQLException {
        final String sql = "SELECT id, nome, cpf, senha_hash, saldo, saldo_investido, agencia, conta_bancaria " +
                           "FROM cliente WHERE cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                // Verifica se o banco de dados retornou alguma linha
                if (rs.next()) {
                    Cliente cliente = new Cliente();

                    // Popula o objeto Cliente com os dados do ResultSet
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setSenhaHash(rs.getString("senha_hash"));
                    cliente.setSaldo(rs.getBigDecimal("saldo"));
                    cliente.setSaldoInvestido(rs.getBigDecimal("saldo_investido"));
                    cliente.setAgencia(rs.getString("agencia"));
                    cliente.setContaBancaria(rs.getLong("conta_bancaria"));

                    return cliente;
                }
            }
        }
        return null;
    }
    
    /**
     * Retorna um Cliente pela conta bancária.
     *
     * @param contaBancaria O número da conta a ser verificado.
     * @return O objeto Cliente preenchido se encontrado, caso contrário, retorna null.
     * @throws SQLException se ocorrer um erro de acesso ao banco de dados.
     */
    public Cliente findClienteByContaBancaria(long contaBancaria) throws SQLException {
        final String sql = "SELECT id, nome, cpf, senha_hash, saldo, saldo_investido, agencia, conta_bancaria " +
                           "FROM cliente WHERE conta_bancaria = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, contaBancaria);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();

                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setSenhaHash(rs.getString("senha_hash"));
                    cliente.setSaldo(rs.getBigDecimal("saldo"));
                    cliente.setSaldoInvestido(rs.getBigDecimal("saldo_investido"));
                    cliente.setAgencia(rs.getString("agencia"));
                    cliente.setContaBancaria(rs.getLong("conta_bancaria"));

                    return cliente;
                }
            }
        }
        return null;
    }
    
    /**
     * Verifica se um cliente com o CPF especificado já existe no banco de dados.
     * 
     * Este método executa uma consulta para contar os registros que correspondem
     * ao CPF fornecido e determina a existência com base nesse total.
     *
     * @param cpf O CPF que será verificado na base de dados.
     * @return {@code true} se um cliente com o CPF fornecido for encontrado, {@code false} caso contrário.
     * @throws SQLException se ocorrer um erro de acesso ao banco de dados durante a execução da consulta.
     */
    public boolean existsByCpf(String cpf) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM CLIENTE WHERE CPF = (?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        
        }
        
        return false;
    }
}
