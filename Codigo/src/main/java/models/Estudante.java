package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudantes")
public class Estudante {
    @Id
    private int ra;

    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String nomeCurso;
    private double totalHoras;

    @Version
    private int version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "estudante_id")
    private List<TipoAtividade> tipoAtividades = new ArrayList<>();

    // Default constructor for JPA
    public Estudante() {
    }

    public Estudante(int ra, String nome, String cpf, String email, String senha, String nomeCurso) {
        this.ra = ra;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nomeCurso = nomeCurso;
        this.totalHoras = 0;
        this.tipoAtividades = new ArrayList<>();

        this.tipoAtividades.add(new TipoAtividade("Cursos", 30, 0.3));
        this.tipoAtividades.add(new TipoAtividade("Seminarios", 10, 0.1));
        this.tipoAtividades.add(new TipoAtividade("Monitorias", 30, 0.3));
        this.tipoAtividades.add(new TipoAtividade("SemanaAcademica", 40, 0.4));
    }

    // Getters and setters...

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(double totalHoras) {
        this.totalHoras = totalHoras;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<TipoAtividade> getTipoAtividades() {
        return tipoAtividades;
    }

    public void setTipoAtividades(List<TipoAtividade> tipoAtividades) {
        this.tipoAtividades = tipoAtividades;
    }

    public boolean addTipoAtividade(String nome, int maxHoras, double coeficienteHoras) {
        for (TipoAtividade tipoAtividade : tipoAtividades) {
            if (tipoAtividade.getNome().equals(nome)) {
                return false;
            }
        }
        TipoAtividade novoTipoAtividade = new TipoAtividade(nome, maxHoras, coeficienteHoras);
        return tipoAtividades.add(novoTipoAtividade);
    }

    public void somarHoras(double horas) {
        this.totalHoras += horas;
    }
}