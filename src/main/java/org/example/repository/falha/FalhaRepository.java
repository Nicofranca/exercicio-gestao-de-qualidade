package org.example.repository.falha;

import org.example.database.Conexao;
import org.example.model.Equipamento;
import org.example.model.Falha;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class FalhaRepository {

    public Falha saveNewFalha(Falha falha) throws SQLException{
        String query = """
                INSERT INTO Falha(equipamentoId, dataHoraOcorrencia, descricao, criticidade, status, tempoParadaHoras) VALUES (?, ?, ?, ?, ?, ?)
                """;

        Falha newFalha = new Falha();

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
                    newFalha.setId(rs.getLong(1));
                    newFalha.setEquipamentoId(falha.getEquipamentoId());
                    newFalha.setDataHoraOcorrencia(falha.getDataHoraOcorrencia());
                    newFalha.setDescricao(falha.getDescricao());
                    newFalha.setCriticidade(falha.getCriticidade());
                    newFalha.setStatus(falha.getStatus());
                    newFalha.setTempoParadaHoras(falha.getTempoParadaHoras());
                }
            }

        }

        return newFalha;
    }

    public List<Falha> listFalhas() throws SQLException{
        String query = """
                SELECT id, equipamentoId, dataHoraOcorrencia, descricao, criticidade, status, tempoParadaHoras FROM Falha WHERE status = 'ABERTA' and criticidade = 'CRITICA'
                """;
        List<Falha> listFalhas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Falha newFalha = new Falha();
                    newFalha.setId(rs.getLong("id"));
                    newFalha.setEquipamentoId(rs.getLong("equipamentoId"));
                    newFalha.setDataHoraOcorrencia(rs.getTimestamp("dataHoraOcorrencia").toLocalDateTime());
                    newFalha.setDescricao(rs.getString("descricao"));
                    newFalha.setCriticidade(rs.getString("criticidade"));
                    newFalha.setStatus(rs.getString("status"));
                    newFalha.setTempoParadaHoras(rs.getBigDecimal("tempoParadaHoras"));

                    listFalhas.add(newFalha);

                }
            }

        }

        return listFalhas;
    }

    public Falha findById(Long id) throws SQLException{
        String query = """
                    SELECT id, equipamentoId, dataHoraOcorrencia, descricao, criticidade, status, tempoParadaHoras FROM Falha WHERE id = ?;
                    """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    Falha falha = new Falha();
                    falha.setId(rs.getLong("id"));
                    falha.setEquipamentoId(rs.getLong("equipamentoId"));
                    falha.setDataHoraOcorrencia(rs.getTimestamp("dataHoraOcorrencia").toLocalDateTime());
                    falha.setDescricao(rs.getString("descricao"));
                    falha.setCriticidade(rs.getString("criticidade"));
                    falha.setStatus(rs.getString("status"));
                    falha.setTempoParadaHoras(rs.getBigDecimal("tempoParadaHoras"));

                    return falha;
                }
            }
        }

        return null;

    }

    public Falha atualizarStatus(long id) throws SQLException{
        String query = """
                UPDATE Falha SET status = ? WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    Falha newFalha = new Falha();
                    newFalha.setEquipamentoId(rs.getLong("equipamentoId"));
                    newFalha.setDataHoraOcorrencia(rs.getTimestamp("dataHoraOcorrencia").toLocalDateTime());
                    newFalha.setDescricao(rs.getString("descricao"));
                    newFalha.setCriticidade(rs.getString("criticidade"));
                    newFalha.setStatus(rs.getString("status"));
                    newFalha.setTempoParadaHoras(rs.getBigDecimal("tempoParadaHoras"));

                    return newFalha;
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
