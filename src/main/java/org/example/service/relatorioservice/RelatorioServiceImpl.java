package org.example.service.relatorioservice;

import org.example.dto.EquipamentoContagemFalhasDTO;
import org.example.dto.FalhaDetalhadaDTO;
import org.example.dto.RelatorioParadaDTO;
import org.example.model.Equipamento;
import org.example.model.Falha;
import org.example.repository.equipamento.EquipamentoRepository;
import org.example.repository.falha.FalhaRepository;
import org.example.repository.relatorio.RelatorioRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RelatorioServiceImpl implements RelatorioService{

    FalhaRepository falhaRepository = new FalhaRepository();
    EquipamentoRepository equipamentoRepository= new EquipamentoRepository();
    RelatorioRepository relatorioRepository = new RelatorioRepository();

    @Override
    public List<RelatorioParadaDTO> gerarRelatorioTempoParada() throws SQLException {

        List<RelatorioParadaDTO> listFalhas = relatorioRepository.listFalhas();

        return listFalhas;
    }

    @Override
    public List<Equipamento> buscarEquipamentosSemFalhasPorPeriodo(LocalDate dataInicio, LocalDate datafim) throws SQLException {

        if (dataInicio.isBefore(datafim) && datafim.isAfter(dataInicio)){

        }

        return List.of();
    }

    @Override
    public Optional<FalhaDetalhadaDTO> buscarDetalhesCompletosFalha(long falhaId) throws SQLException {

        Falha newFalha = falhaRepository.findById(falhaId);
        Equipamento equipamento = equipamentoRepository.findById(newFalha.getEquipamentoId());

        FalhaDetalhadaDTO falhaDetalhadaDTO = new FalhaDetalhadaDTO(newFalha, equipamento);

        return Optional.of(falhaDetalhadaDTO);
    }

    @Override
    public List<EquipamentoContagemFalhasDTO> gerarRelatorioManutencaoPreventiva(int contagemMinimaFalhas) throws SQLException {
        return List.of();
    }
}
