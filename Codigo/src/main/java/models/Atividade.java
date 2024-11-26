package models;
import java.time.LocalDateTime;
import java.util.UUID;

public class Atividade {
    private UUID id;
    private String nomeAtividade;
    private LocalDateTime dataRealizacao;
    private String status;
    private String documento;
    private String descricao;
    private int totalHoras;
    private int raUsuario;
    private UUID idCurso;
    private Tipo tipo;

    public enum Tipo {
        CURSO,
        SEMINARIO,
        MONITORIA
    }

    public Atividade(String nomeAtividade, LocalDateTime dataRealizacao, String status, String descricao, int raUsuario, UUID idCurso, Tipo tipo, int totalHoras, String documento) {
        this.id = UUID.randomUUID();
        this.nomeAtividade = nomeAtividade;
        this.dataRealizacao = dataRealizacao;
        this.status = status;
        this.documento = documento;
        this.descricao = descricao;
        this.raUsuario = raUsuario;
        this.idCurso = idCurso;
        this.tipo = tipo;
        this.totalHoras = totalHoras;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public UUID getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(UUID idCurso) {
        this.idCurso = idCurso;
    }

    public int getRaUsuario() {
        return raUsuario;
    }

    public void setRaUsuario(int raUsuario) {
        this.raUsuario = raUsuario;
    }

    public UUID getId() {
        return id;
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

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getRaAluno() {
        return raUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getData() {
        return dataRealizacao;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "id=" + id +
                ", nomeAtividade='" + nomeAtividade + '\'' +
                ", dataRealizacao=" + dataRealizacao +
                ", status='" + status + '\'' +
                ", documento='" + documento + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
