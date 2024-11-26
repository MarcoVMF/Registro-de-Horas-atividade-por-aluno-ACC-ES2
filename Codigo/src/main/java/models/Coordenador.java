package models;

import java.util.UUID;

public class Coordenador {
    private UUID id;
    private String nome;
    private String email;
    private String senha;  
    private String cpf;
    private UUID idCurso;

    public Coordenador(String nome, String email, String senha, String cpf, UUID idCurso) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.idCurso = idCurso;
    }

    public UUID getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(UUID idCurso) {
        this.idCurso = idCurso;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Coordenador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

    public boolean aceitarAtividade(String atividadeId) {
        return true;
    }

    public boolean rejeitarAtividade(String atividadeId) {
        return true;
    }

    public boolean preencherNovoLimiteHoras(String atividadeId, float novoLimiteHoras, float taxaHoras) {
        return true;    
    }


}
