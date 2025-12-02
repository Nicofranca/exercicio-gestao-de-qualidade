package org.example.repository.acaoCorretiva;

import org.example.database.Conexao;
import org.example.model.AcaoCorretiva;

import java.sql.*;

public class AcaoCorretivaRepository {
    public AcaoCorretiva registrarConclusaoDeAcao(AcaoCorretiva acaoCorretiva) throws SQLException{
        String query = """
                INSERT INTO AcaoCorretiva(falhaId, dataHoraInicio, dataHoraFim, responsavel, descricaoAcao) VALUES(?,?,?,?,?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, acaoCorretiva.getFalhaId());
            stmt.setTimestamp(2, Timestamp.valueOf(acaoCorretiva.getDataHoraInicio()));
            stmt.setTimestamp(3, Timestamp.valueOf(acaoCorretiva.getDataHoraFim()));
            stmt.setString(4, acaoCorretiva.getResponsavel());
            stmt.setString(5, acaoCorretiva.getDescricaoArea());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()){
                    acaoCorretiva.setId(rs.getLong(1));
                }
            }
        }
        return acaoCorretiva;
    }

    public AcaoCorretiva findById(Long id) throws SQLException{
        String query = """
                SELECT id, falhaId, dataHoraInicio, dataHoraFim, responsavel, descricaoAcao
                FROM AcaoCorretiva
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    AcaoCorretiva acaoCorretiva = new AcaoCorretiva();
                    acaoCorretiva.setFalhaId(rs.getLong("falhaId"));
                    acaoCorretiva.setDataHoraInicio(rs.getTimestamp("dataHoraInicio").toLocalDateTime());
                    acaoCorretiva.setDataHoraFim(rs.getTimestamp("dataHoraFim").toLocalDateTime());
                    acaoCorretiva.setResponsavel(rs.getString("responsavel"));
                    acaoCorretiva.setDescricaoArea(rs.getString("descricaoAcao"));

                    return acaoCorretiva;
                }
            }
        }

        return null;

    }
}

/*
CREATE TABLE IF NOT EXISTS AcaoCorretiva (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                falhaId BIGINT NOT NULL,
                dataHoraInicio DATETIME NOT NULL,
                dataHoraFim DATETIME NOT NULL,
                responsavel VARCHAR(255) NOT NULL,
                descricaoAcao TEXT NOT NULL,

                CONSTRAINT fk_acao_falha FOREIGN KEY (falhaId)
                REFERENCES Falha(id)
                ON DELETE RESTRICT
            );
 */
