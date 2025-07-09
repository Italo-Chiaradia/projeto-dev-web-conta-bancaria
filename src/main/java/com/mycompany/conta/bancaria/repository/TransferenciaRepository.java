package com.mycompany.conta.bancaria.repository;

import com.mycompany.conta.bancaria.model.Transferencias;
import com.mycompany.conta.bancaria.util.ConnectionFactory; // Importa sua classe de conexão
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TransferenciaRepository {

    /**
     * Salva uma nova transferência no banco de dados e retorna o objeto com o ID gerado.
     * @param transferencia O objeto Transferencias a ser salvo.
     * @return O mesmo objeto, agora com o ID preenchido pelo banco.
     * @throws SQLException se ocorrer um erro de banco.
     */
    public Transferencias registrarTransferencia(Transferencias transferencia) throws SQLException {
        String sql = "INSERT INTO TRANSFERENCIAS (ID_REMETENTE, ID_DESTINATARIO, VALOR, CREATED_AT) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, transferencia.getIdRemetente());
            pstmt.setInt(2, transferencia.getIdDestinatario());
            pstmt.setBigDecimal(3, transferencia.getValor());
            pstmt.setTimestamp(4, new Timestamp(transferencia.getCreatedAt().getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transferencia.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return transferencia;
    }
}