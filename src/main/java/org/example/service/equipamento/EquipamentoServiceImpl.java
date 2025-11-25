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
            equipamento.setStatusOperacional("OPERACIONAL");

            novoEquipamento = equipamentoRepository.save(equipamento);

            if (novoEquipamento == null){
                throw new RuntimeException("O equipamento não deveria ser nulo");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return novoEquipamento;
    }

    @Override
    public Equipamento buscarEquipamentoPorId(Long id) throws SQLException {
        Equipamento equipamento = equipamentoRepository.findById(id);
        if (equipamento == null) {
            throw new RuntimeException("Equipamento não encontrado!");
        }
        return equipamento;
    }
}
