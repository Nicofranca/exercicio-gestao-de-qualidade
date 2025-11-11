package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Falha {
    /*CREATE TABLE IF NOT EXISTS Falha (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipamentoId BIGINT NOT NULL,
    dataHoraOcorrencia DATETIME NOT NULL,
    descricao TEXT NOT NULL,
    criticidade VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    tempoParadaHoras DECIMAL(10, 2) DEFAULT 0.00,

    -- Garante que criticidade e status tenham valores pré-definidos
    CONSTRAINT chk_criticidade_falha CHECK (criticidade IN ('BAIXA', 'MEDIA', 'ALTA', 'CRITICA')),
    CONSTRAINT chk_status_falha CHECK (status IN ('ABERTA', 'EM_ANDAMENTO', 'RESOLVIDA')),
*/
    private Long id;

    private Long equipamentoId;

    private LocalDateTime dataHoraOcorrencia;

    private String descricao;

    private String criticidade;

    private String status;

    private BigDecimal tempoParadaHoras;


}
