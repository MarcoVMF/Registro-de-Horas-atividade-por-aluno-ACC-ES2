package models;

import java.util.UUID;

public class Curso {
    private UUID id;
    private String nome;
    private int limiteHoras;

    public Curso(String nome, int limiteHoras) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.limiteHoras = limiteHoras;
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

    public int getLimiteHoras() {
        return limiteHoras;
    }

    public void setLimiteHoras(int limiteHoras) {
        this.limiteHoras = limiteHoras;
    }
}
