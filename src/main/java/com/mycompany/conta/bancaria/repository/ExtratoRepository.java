package com.mycompany.conta.bancaria.repository;

import com.mycompany.conta.bancaria.model.Extrato;
import com.mycompany.conta.bancaria.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
} 