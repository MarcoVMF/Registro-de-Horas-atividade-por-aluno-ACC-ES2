package models;

import java.util.UUID;

public class Estudante {
    private int ra;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private UUID idCurso;
    private double totalHoras;

    public Estudante(int ra, String nome, String cpf, String email, String senha, UUID idCurso) {
        this.ra = ra;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.idCurso = idCurso;
        this.totalHoras = 0;
    }

    public double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(double totalHoras) {
        this.totalHoras = totalHoras;
    }

    public void somarHoras(double horas) {
        this.totalHoras += horas;
    }

    public UUID getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(UUID idCurso) {
        this.idCurso = idCurso;
    }

    public void setId(int ra) {
        this.ra = ra;
    }

    public int getId() {
        return ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
}