package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.model.Falha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ListFormat;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRepository {

    public List<Falha> listFalhas() throws SQLException {
        String query = """
                SELECT id
                , equipamentoId
                , dataHoraOcorrencia
                , descricao
                , criticidade
                , status
                , tempoParadaHoras
                 FROM Falha;
                """;
        List<Falha> listaFalhas = new ArrayList<>();

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

                    listaFalhas.add(newFalha);

                }
            }
        }

        return listaFalhas;

    }
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