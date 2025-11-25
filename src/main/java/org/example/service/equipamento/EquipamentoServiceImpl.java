package org.example.service.equipamento;

import org.example.model.Equipamento;
import org.example.repository.equipamento.EquipamentoRepository;

import java.sql.SQLException;

public class EquipamentoServiceImpl implements EquipamentoService{

    EquipamentoRepository equipamentoRepository = new EquipamentoRepository();

    @Override
    public Equipamento criarEquipamento(Equipamento equipamento) throws SQLException {

        Equipamento novoEquipamento = null;

        try {
            novoEquipamento = equipamentoRepository.save(equipamento);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return novoEquipamento;
    }

    @Override
    public Equipamento buscarEquipamentoPorId(Long id) throws SQLException {
        return null;
    }
}
