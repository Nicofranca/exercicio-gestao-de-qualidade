package org.example.service.falha;

import org.example.model.Equipamento;
import org.example.model.Falha;
import org.example.repository.equipamento.EquipamentoRepository;
import org.example.repository.falha.FalhaRepository;

import java.sql.SQLException;
import java.util.List;

public class FalhaServiceImpl implements FalhaService{

    FalhaRepository falhaRepository = new FalhaRepository();
    EquipamentoRepository equipamentoRepository = new EquipamentoRepository();

    @Override
    public Falha registrarNovaFalha(Falha falha) throws SQLException {

        Equipamento equipamento = equipamentoRepository.findById(falha.getEquipamentoId());

            if (equipamento == null){
                throw new IllegalArgumentException("Equipamento não encontrado!");
            } else {

                falha.setStatus("ABERTA");

                Falha newFalha = falhaRepository.saveNewFalha(falha);

                if (falha.getCriticidade().equalsIgnoreCase("CRITICA")){
                    equipamentoRepository.atualizarStatusOperacional(equipamento.getId(), "EM_MANUTENCAO");
                }

                return newFalha;
            }

        }


    @Override
    public List<Falha> buscarFalhasCriticasAbertas() throws SQLException {

        List<Falha> listFalhas = falhaRepository.listFalhas();



        return listFalhas;
    }
}
