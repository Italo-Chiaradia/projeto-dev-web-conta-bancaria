package com.mycompany.conta.bancaria.repository;

import com.mycompany.conta.bancaria.model.Extrato;
import com.mycompany.conta.bancaria.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Date;

public class ExtratoRepository {
    /**
     * Registra uma nova transação no extrato.
     * @param extrato Objeto Extrato a ser registrado
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public void registrarTransacao(Extrato extrato) throws SQLException {
        final String sql = "INSERT INTO extrato (created_at, tipo, valor, id_cliente, id_transferencia) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(extrato.getCreatedAt().getTime()));
            stmt.setString(2, extrato.getTipo());
            stmt.setBigDecimal(3, extrato.getValor());
            stmt.setInt(4, extrato.getIdCliente());
            if (extrato.getIdTransferencia() != null) {
                stmt.setInt(5, extrato.getIdTransferencia());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        }
    }

    /**
     * Busca todas as transações do cliente em ordem cronológica decrescente.
     * @param idCliente ID do cliente
     * @return Lista de transações
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public List<Extrato> buscarTransacoesPorCliente(int idCliente) throws SQLException {
        List<Extrato> transacoes = new ArrayList<>();
        final String sql = "SELECT id, created_at, tipo, valor FROM extrato WHERE id_cliente = ? ORDER BY created_at DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Extrato extrato = new Extrato();
                    extrato.setId(rs.getInt("id"));
                    extrato.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));
                    extrato.setTipo(rs.getString("tipo"));
                    extrato.setValor(rs.getBigDecimal("valor"));
                    extrato.setIdCliente(idCliente);
                    transacoes.add(extrato);
                }
            }
        }
        return transacoes;
    }
} 