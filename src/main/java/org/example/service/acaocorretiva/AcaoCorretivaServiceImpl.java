package org.example.service.acaocorretiva;

import org.example.model.AcaoCorretiva;
import org.example.model.Equipamento;
import org.example.model.Falha;
import org.example.repository.acaoCorretiva.AcaoCorretivaRepository;
import org.example.repository.equipamento.EquipamentoRepository;
import org.example.repository.falha.FalhaRepository;

import java.sql.SQLException;

public class AcaoCorretivaServiceImpl implements AcaoCorretivaService{

    AcaoCorretivaRepository acaoCorretivaRepository = new AcaoCorretivaRepository();
    FalhaRepository falhaRepository = new FalhaRepository();
    EquipamentoRepository equipamentoRepository = new EquipamentoRepository();

    @Override
    public AcaoCorretiva registrarConclusaoDeAcao(AcaoCorretiva acao) throws SQLException {

        Falha newFalha = falhaRepository.findById(acao.getFalhaId());

        if (newFalha == null){
            throw new RuntimeException("Falha não encontrada!");
        }

        if (newFalha.getCriticidade().equalsIgnoreCase("CRITICA")){
            Equipamento equipamento = equipamentoRepository.findById(newFalha.getEquipamentoId());

            equipamentoRepository.atualizarStatusOperacionalFalha(newFalha.getEquipamentoId());
        }

        falhaRepository.atualizarStatus(acao.getFalhaId());

        AcaoCorretiva acaoCorretiva = acaoCorretivaRepository.registrarConclusaoDeAcao(acao);

        return acaoCorretiva;
    }
}
