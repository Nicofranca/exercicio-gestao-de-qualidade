package org.example.repository.falha;

import org.example.database.Conexao;
import org.example.model.Falha;

import java.sql.*;

import static java.lang.String.valueOf;

public class FalhaRepository {

    public Falha saveNewFalha(Falha falha) throws SQLException{
        String query = """
                INSERT INTO Falha(equipamentoId, dataHoraOcorrencia, descricao, criticidade, status, tempoParadaHoras) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, falha.getEquipamentoId());
            stmt.setTimestamp(2, Timestamp.valueOf(falha.getDataHoraOcorrencia()));
            stmt.setString(3, falha.getDescricao());
            stmt.setString(4, falha.getCriticidade());
            stmt.setString(5, falha.getStatus());
            stmt.setBigDecimal(6, falha.getTempoParadaHoras());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()){
                    falha.setId(rs.getLong(1));
                    falha.setEquipamentoId(rs.getLong("equipamentoId"));
                    falha.setDataHoraOcorrencia(rs.getTimestamp(valueOf(Timestamp.valueOf("dataHoraOcorrencia"))).toLocalDateTime());
                    falha.setDescricao((rs.getString("descricao")));
                    falha.setCriticidade(rs.getString("criticidade"));
                    falha.setStatus(rs.getString("status"));
                    falha.setTempoParadaHoras(rs.getBigDecimal("tempoParadaHoras"));

                    return falha;
                }
            }

        }

        return null;
    }




    /*private static final String SQL_CREATE_TABLE_FALHA =
            """
            CREATE TABLE IF NOT EXISTS Falha (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                equipamentoId BIGINT NOT NULL,
                dataHoraOcorrencia DATETIME NOT NULL,
                descricao TEXT NOT NULL,
                criticidade VARCHAR(50) NOT NULL,
                status VARCHAR(50) NOT NULL,
                tempoParadaHoras DECIMAL(10,2) DEFAULT 0.00,

                CONSTRAINT chk_criticidade_falha CHECK (criticidade IN ('BAIXA','MEDIA','ALTA','CRITICA')),
                CONSTRAINT chk_status_falha CHECK (status IN ('ABERTA','EM_ANDAMENTO','RESOLVIDA'))
            );
            """;*/
}
