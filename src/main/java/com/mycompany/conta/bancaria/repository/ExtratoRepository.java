package com.mycompany.conta.bancaria.repository;

import com.mycompany.conta.bancaria.model.Extrato;
import com.mycompany.conta.bancaria.model.ExtratoCompleto;
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
    
    /**
     * Busca as últimas transações do cliente em ordem cronológica decrescente.
     * @param idCliente ID do cliente
     * @param qtdRegistros quantos registros no extrato serão resgatados
     * @return Lista de transações
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public List<ExtratoCompleto> buscarUltimasTransacoesPorCliente(int idCliente, int qtdRegistros) throws SQLException {
        List<ExtratoCompleto> transacoes = new ArrayList<>();
        final String sql = "SELECT "
                + "*"
                + "FROM V_EXTRATO_COMPLETO "
                + "WHERE id_cliente = ? "
                + "ORDER BY created_at DESC "
                + "FETCH FIRST ? ROWS ONLY";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor para o primeiro "?" (id_cliente)
            stmt.setInt(1, idCliente);
            // Define o valor para o segundo "?" (LIMIT)
            stmt.setInt(2, qtdRegistros);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ExtratoCompleto dto = new ExtratoCompleto();
                    dto.setIdExtrato(rs.getInt("ID_EXTRATO"));
                    dto.setIdCliente(rs.getInt("ID_CLIENTE"));
                    dto.setCreatedAt(new Date(rs.getTimestamp("CREATED_AT").getTime()));
                    dto.setTipo(rs.getString("TIPO"));
                    dto.setValor(rs.getBigDecimal("VALOR"));
                    dto.setIdTransferencia(rs.getInt("ID_TRANSFERENCIA"));
                    dto.setNomeCliente(rs.getString("NOME_CLIENTE"));
                    dto.setIdRemetente(rs.getInt("ID_REMETENTE"));
                    dto.setNomeRemetente(rs.getString("NOME_REMETENTE"));
                    dto.setIdDestinatario(rs.getInt("ID_DESTINATARIO"));
                    dto.setNomeDestinatario(rs.getString("NOME_DESTINATARIO"));
                    transacoes.add(dto);
                }
            }
        }
        return transacoes;
    }
}
