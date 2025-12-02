package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.dto.EquipamentoContagemFalhasDTO;
import org.example.dto.FalhaDetalhadaDTO;
import org.example.dto.RelatorioParadaDTO;
import org.example.model.Falha;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RelatorioRepository {
    public List<RelatorioParadaDTO> listFalhas() throws SQLException {
        String query = """
                SELECT f.equipamentoId
                , f.tempoParadaHoras
                , e.nome
                 FROM Equipamento e 
                 JOIN Falha f
                 ON e.id = f.equipamentoId
                 ;
                """;
        List<RelatorioParadaDTO> listaFalhas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){

                    Long id = rs.getLong("equipamentoId");
                    String nome = rs.getString("nome");
                    double tempoParada =  rs.getDouble("tempoParadaHoras");

                    RelatorioParadaDTO newFalha = new RelatorioParadaDTO(id, nome, tempoParada);

                    listaFalhas.add(newFalha);

                }
            }
        }

        return listaFalhas;

    }

    public List<EquipamentoContagemFalhasDTO> contagemFalhas(long falhaId) throws SQLException{
        String query = """
                SELECT id, nome, numeroDeSerie, areaSetor, statusOperacional
                """;

        return null;
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