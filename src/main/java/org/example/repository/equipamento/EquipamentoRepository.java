package org.example.repository.equipamento;

import org.example.database.Conexao;
import org.example.model.Equipamento;

import java.sql.*;

public class EquipamentoRepository {
    public Equipamento save(Equipamento equipamento) throws SQLException{
        String query = """
                INSERT INTO Equipamento (nome, numeroDeSerie, areaSetor, statusOperacional) VALUES (?, ?, ?, ?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, equipamento.getNome());
            stmt.setString(2, equipamento.getNumeroDeSerie());
            stmt.setString(3, equipamento.getAreaSetor());
            stmt.setString(4, equipamento.getStatusOperacional());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()){
                    equipamento.setId(rs.getLong(1));
                }
            }

        }

        return equipamento;
    }

    public Equipamento findById(Long id) throws SQLException{
        String query = """
                    SELECT id, nome, numeroDeSerie, areaSetor, statusOperacional FROM Equipamento WHERE id = ?;
                    """;

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    Equipamento equipamento = new Equipamento();
                    equipamento.setId(rs.getLong("id"));
                    equipamento.setNome(rs.getString("nome"));
                    equipamento.setNumeroDeSerie(rs.getString("numeroDeSerie"));
                    equipamento.setAreaSetor(rs.getString("areaSetor"));
                    equipamento.setStatusOperacional(rs.getString("statusOperacional"));

                    return equipamento;
                }
            }
        }

        return null;

    }


}
