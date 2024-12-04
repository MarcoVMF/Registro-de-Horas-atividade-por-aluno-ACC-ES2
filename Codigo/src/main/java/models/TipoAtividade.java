package models;

import javax.persistence.*;

@Entity
@Table(name = "tipo_atividades")
public class TipoAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private int maxHoras;
    private double horasAtuais;
    private double coeficienteHoras;

    public TipoAtividade() {
        // Default constructor for JPA
    }

    public TipoAtividade(String nome, int maxHoras, double coeficienteHoras) {
        this.nome = nome;
        this.maxHoras = maxHoras;
        this.horasAtuais = 0;
        this.coeficienteHoras = coeficienteHoras;
    }

    public double getCoeficienteHoras() {
        return coeficienteHoras;
    }

    public void setCoeficienteHoras(double coeficienteHoras) {
        this.coeficienteHoras = coeficienteHoras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTotalHoras() {
        return maxHoras;
    }

    public void setTotalHoras(int maxHoras) {
        this.maxHoras = maxHoras;
    }

    public double getHorasAtuais() {
        return horasAtuais;
    }

    public void setHorasAtuais(double horasAtuais) {
        this.horasAtuais = horasAtuais;
    }

    public void adicionarHoras(double horas) {
        this.horasAtuais += horas;
    }
}
