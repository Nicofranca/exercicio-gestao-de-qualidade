package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.model.Falha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

