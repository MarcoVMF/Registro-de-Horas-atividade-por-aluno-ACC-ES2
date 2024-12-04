package models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atividades")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeAtividade;
    private LocalDateTime dataRealizacao;
    private String status;
    private String documento;
    private String descricao;
    private int totalHoras;
    private int raUsuario;
    private String nomeCurso;

    @ManyToOne
    @JoinColumn(name = "tipo_atividade_id")
    private TipoAtividade tipoAtividade;

    // Default constructor for JPA
    public Atividade() {
    }

    public Atividade(String nomeAtividade, LocalDateTime dataRealizacao, String status, String descricao, int raUsuario, String nomeCurso, TipoAtividade tipoAtividade, int totalHoras, String documento) {
        this.nomeAtividade = nomeAtividade;
        this.dataRealizacao = dataRealizacao;
        this.status = status;
        this.documento = documento;
        this.descricao = descricao;
        this.raUsuario = raUsuario;
        this.nomeCurso = nomeCurso;
        this.tipoAtividade = tipoAtividade;
        this.totalHoras = totalHoras;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    public TipoAtividade getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(TipoAtividade tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }
    
    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getRaUsuario() {
        return raUsuario;
    }

    public void setRaUsuario(int raUsuario) {
        this.raUsuario = raUsuario;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getRaAluno() {
        return raUsuario;
    }

    public LocalDateTime getData() {
        return dataRealizacao;
    }

    public String getNomeTipo() {
        return tipoAtividade.getNome();
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "raUsuario=" + raUsuario +
                ", nomeAtividade='" + nomeAtividade + '\'' +
                ", dataRealizacao=" + dataRealizacao +
                ", status='" + status + '\'' +
                ", documento='" + documento + '\'' +
                ", descricao='" + descricao + '\'' +
                ", totalHoras=" + totalHoras +
                ", nomeCurso='" + nomeCurso + '\'' +
                ", tipoAtividade=" + tipoAtividade +
                '}';
    }
}