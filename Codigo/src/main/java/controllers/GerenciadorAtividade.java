package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Atividade;
import models.Atividade.Tipo;

public class GerenciadorAtividade {
    private List<Atividade> atividades;
    private GerenciadorCursos gerenciadorCursos;

    public GerenciadorAtividade(GerenciadorCursos gerenciadorCursos) {
        this.atividades = new ArrayList<>();
        this.gerenciadorCursos = gerenciadorCursos;
    }

    public boolean preencherDadosAtividade(String nomeAtividade,LocalDateTime data, String descricao,  int raUsuario, UUID idCurso, Tipo tipo, int totalHoras, String documento) {
        if (validarDadosAtividade(nomeAtividade, descricao, data, raUsuario, idCurso)) {
            Atividade novaAtividade = new Atividade(nomeAtividade, data, "Pendente", descricao, raUsuario, idCurso, tipo, totalHoras, documento);
            atividades.add(novaAtividade);
            return true;
        }
        return false;
    }

    public boolean validarDadosAtividade(String nomeAtividade, String descricao, LocalDateTime data, int raUsuario, UUID idCurso) {
        return validarNomeAtividade(nomeAtividade) &&
               validarDescricao(descricao) &&
               validarData(data) &&
               validarRa(raUsuario) &&
               gerenciadorCursos.validarIdCurso(idCurso);
    }

    public boolean validarNomeAtividade(String nomeAtividade) {
        if (nomeAtividade == null || nomeAtividade.trim().isEmpty()) {
            return false;
        }
    
        int length = nomeAtividade.trim().length();
        return length >= 6 && length <= 32;
    }

    public boolean validarDescricao(String descricao) {
        if (descricao == null) {
            return false;
        }
    
        int length = descricao.trim().length();
        return length >= 0 && length <= 128;
    }

    public boolean validarData(LocalDateTime data) {
        if (data == null) {
            return false;
        }
    
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tenYearsAgo = today.minusYears(10);
    
        return !data.isAfter(today) && !data.isBefore(tenYearsAgo);
    }

    public boolean validarRa(int ra) {
        return ra != 0;
    }

    public boolean anexarDocumento(UUID idAtividade, String documento) {
        for (Atividade atividade : atividades) {
            if (atividade.getId().equals(idAtividade)) {
                atividade.setDocumento(documento);
                return true;
            }
        }
        return false;
    }

    public boolean validarDocumento(String documento) {
        if (documento == null) {
            return false;
        }
        return documento.toLowerCase().endsWith(".pdf");
    }

    public Atividade buscarAtividade(UUID idAtividade) {
        for (Atividade atividade : atividades) {
            if (atividade.getId().equals(idAtividade)) {
                return atividade;
            }
        }
        return null;
    }

    public List<Atividade> buscarAtividades() {
        return atividades;
    }

    public boolean atualizarStatusAtividade(UUID idAtividade, String status) {
        for (Atividade atividade : atividades) {
            if (atividade.getId().equals(idAtividade)) {
                atividade.setStatus(status);
                return true;
            }
        }
        return false;
    }

    public List<Atividade> buscarAtividadesPorUsuario(int raUsuario) {
        List<Atividade> atividadesPorUsuario = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getRaUsuario() == raUsuario) {
                atividadesPorUsuario.add(atividade);
            }
        }
        return atividadesPorUsuario;
    }

    public List<Atividade> buscarAtividadesPorStatus(String status) {
        List<Atividade> atividadesPorStatus = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getStatus().equals(status)) {
                atividadesPorStatus.add(atividade);
            }
        }
        return atividadesPorStatus;
    }

    public boolean deletarAtividade(UUID idAtividade) {
        for (Atividade atividade : atividades) {
            if (atividade.getId().equals(idAtividade)) {
                atividades.remove(atividade);
                return true;
            }
        }
        return false;
    }

    public void removerAtividade(Atividade atividade) {
        this.atividades.remove(atividade);
    }
    
    public double calcularHoras(Atividade.Tipo tipo, int totalHoras) {
        double result = 0;
        switch (tipo) {
            case CURSO:
                result = totalHoras * 0.3;
                break;
            case SEMINARIO:
                result = totalHoras * 0.1;
                break;
            case MONITORIA:
                result = totalHoras * 0.5;
                break;
        }
        return result;
    }
}
