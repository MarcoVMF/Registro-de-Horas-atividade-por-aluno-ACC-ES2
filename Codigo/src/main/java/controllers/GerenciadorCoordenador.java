package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Atividade;
import models.Coordenador;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class GerenciadorCoordenador {
    private List<Coordenador> coordenadores;
    private GerenciadorAtividade gerenciadorAtividade;
    private GerenciadorCursos gerenciadorCursos;
    private GerenciadorEstudante gerenciadorEstudante;

    public GerenciadorCoordenador(GerenciadorAtividade gerenciadorAtividade, GerenciadorCursos gerenciadorCursos, GerenciadorEstudante gerenciadorEstudante) {
        this.coordenadores = new ArrayList<>();
        this.gerenciadorAtividade = gerenciadorAtividade;
        this.gerenciadorCursos = gerenciadorCursos;
        this.gerenciadorEstudante = gerenciadorEstudante;
        loadCoordenadoresFromDatabase();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    private void loadCoordenadoresFromDatabase() {
        Session session = getSession();
        try {
            coordenadores = session.createQuery("from Coordenador", Coordenador.class).list();
        } finally {
            session.close();
        }
    }

    public void saveCoordenadoresToDatabase() {
        Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (Coordenador coordenador : coordenadores) {
                session.saveOrUpdate(coordenador);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public boolean inserirDadosCoordenador(String nome, String cpf, String email, String senha, String nomeCurso) {
        for (Coordenador coordenador : coordenadores) {
            if (coordenador.getCpf().equals(cpf) || coordenador.getEmail().equals(email)) {
                return false;
            }
        }
        if (validarDadosCoordenador(nome, cpf, email, senha, nomeCurso)) {
            Coordenador novoCoordenador = new Coordenador(nome, email, senha, cpf, nomeCurso);
            coordenadores.add(novoCoordenador);
            return true;
        }
        return false;
    }

    public boolean validarDadosAutenticarCoordenador(String email, String senha) {
        return validarEmailCoordenador(email) && validarSenhaCoordenador(senha);
    }

    public String inserirDadosAutenticarCoordenador(String email, String senha) {
        if (validarDadosAutenticarCoordenador(email, senha)) {
            return autenticarCoordenador(email, senha);
        }
        return null;
    }

    public String autenticarCoordenador(String email, String senha) {
        Coordenador coordenador = buscarPeloEmail(email);
        if (coordenador == null) {
            return null;
        }
        return coordenador.getSenha().equals(senha) && coordenador.getStatus().equals("Ativo") ? coordenador.getCpf() : null;
    }

    private Coordenador buscarPeloEmail(String email) {
        for (Coordenador coordenador : coordenadores) {
            if (coordenador.getEmail().equals(email)) {
                return coordenador;
            }
        }
        return null;
    }

    public boolean validarDadosCoordenador(String nome, String cpf, String email, String senha, String nomeCurso) {
        return validarNomeCoordenador(nome) &&
               validarCpfCoordenador(cpf) &&
               validarEmailCoordenador(email) &&
               validarSenhaCoordenador(senha) &&
               gerenciadorCursos.validarNomeExistenteCurso(nomeCurso);
    }

    public boolean validarNomeCoordenador(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        String[] words = nome.trim().split("\\s+");

        if (words.length < 2) {
            return false;
        }

        int totalLength = 0;
        for (String word : words) {
            if (!word.matches("[a-zA-Z]+")) {
                return false;
            }
            totalLength += word.length();
        }

        return totalLength >= 7;
    }

    public boolean validarCpfCoordenador(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }

        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfArray[i] * (10 - i);
        }
        int firstCheckDigit = 11 - (sum % 11);
        if (firstCheckDigit >= 10) {
            firstCheckDigit = 0;
        }
        if (firstCheckDigit != cpfArray[9]) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpfArray[i] * (11 - i);
        }
        int secondCheckDigit = 11 - (sum % 11);
        if (secondCheckDigit >= 10) {
            secondCheckDigit = 0;
        }
        return secondCheckDigit == cpfArray[10];
    }

    public boolean validarEmailCoordenador(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String localPart = parts[0];
        String domainPart = parts[1];
        return !localPart.isEmpty() && domainPart.equals("unesp.br");
    }

    public boolean validarSenhaCoordenador(String senha) {
        if (senha == null || senha.length() < 8 || senha.length() > 24) {
            return false;
        }

        boolean hasUpperCase = senha.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = senha.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = senha.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = senha.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    public boolean negarAtividade(String nomeAtividade, LocalDateTime data, int raUsuario, String nomeCurso) {
        return gerenciadorAtividade.atualizarStatusAtividade(nomeAtividade, data, raUsuario, nomeCurso, "Negada");
    }

    public boolean aceitarAtividade(String nomeAtividade, LocalDateTime data, int raUsuario, String nomeCurso) {
        Atividade atividade = gerenciadorAtividade.procurarAtividade(nomeAtividade, data, raUsuario, nomeCurso);
        if (atividade == null) {
            return false;
        }
        double horas = gerenciadorAtividade.calcularHoras(atividade.getTipoAtividade(), atividade.getTotalHoras());
        if (gerenciadorEstudante.atualizarHoras(atividade.getRaUsuario(), horas, atividade.getTipoAtividade())) {
            return gerenciadorAtividade.atualizarStatusAtividade(nomeAtividade, data, raUsuario, nomeCurso, "Aprovada");
        }
        return false;
    }

    public void removeCoordenador(Coordenador coordenador) {
        this.coordenadores.remove(coordenador);
    }

    public List<Coordenador> getCoordenadores() {
        return coordenadores;
    }

    public Coordenador buscarCoordenador(String cpf) {
        for (Coordenador coordenador : coordenadores) {
            if (coordenador.getCpf().equals(cpf)) {
                return coordenador;
            }
        }
        return null;
    }

    public boolean addTipoAtividade(String nome, int maxHoras, double coeficienteHoras, String nomeDoCurso) {
        return gerenciadorEstudante.addTipoAtividadePorCurso(nomeDoCurso, nome, maxHoras, coeficienteHoras);
    }
}