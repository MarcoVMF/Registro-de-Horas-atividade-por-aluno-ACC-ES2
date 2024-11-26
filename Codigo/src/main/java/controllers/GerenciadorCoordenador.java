package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Atividade;
import models.Coordenador;

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
    }

    public boolean inserirDadosCoordenador(String nome, String cpf, String email, String senha, UUID idCurso) {
        if (validarDadosCoordenador(nome, cpf, email, senha, idCurso)) {
            Coordenador novoCoordenador = new Coordenador(nome, email,senha, cpf,  idCurso);
            coordenadores.add(novoCoordenador);
            return true;
        }
        return false;
    }

    public boolean validarDadosAutenticarCoordenador(String email, String senha) {
        return validarEmailCoordenador(email) && validarSenhaCoordenador(senha);
    }

    public UUID inserirDadosAutenticarCoordenador(String email, String senha) {
        if (validarDadosAutenticarCoordenador(email, senha)) {
            return autenticarCoordenador(email, senha);
        }
        return null;
    }

    public UUID autenticarCoordenador(String email, String senha) {
        Coordenador coordenador = buscarPeloEmail(email);
        if (coordenador == null) {
            return null;
        }
        return coordenador.getSenha().equals(senha) ? coordenador.getId() : null;
    }

    private Coordenador buscarPeloEmail(String email) {
        for (Coordenador coordenador : coordenadores) {
            System.out.println(coordenador.getEmail());
            if (coordenador.getEmail().equals(email)) {
                return coordenador;
            }
        }
        return null;
    }

    public boolean validarDadosCoordenador(String nome, String cpf, String email, String senha, UUID idCurso) {
        return validarNomeCoordenador(nome) &&
               validarCpfCoordenador(cpf) &&
               validarEmailCoordenador(email) &&
               validarSenhaCoordenador(senha) &&
               gerenciadorCursos.validarIdCurso(idCurso);
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

        return totalLength >= 8;
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

    public boolean negarAtividade(UUID idAtividade) {
        return gerenciadorAtividade.atualizarStatusAtividade(idAtividade, "Negado");
    }

    public boolean aceitarAtividade(UUID idAtividade) {
        Atividade atividade = gerenciadorAtividade.buscarAtividade(idAtividade);
        if (atividade == null) {
            return false;
        }
        double horas = gerenciadorAtividade.calcularHoras(atividade.getTipo(), atividade.getTotalHoras());
        if(gerenciadorEstudante.atualizarHoras(atividade.getRaUsuario(), horas)) {
            return gerenciadorAtividade.atualizarStatusAtividade(idAtividade, "Aceito");
        }
        return false;
    }

    public void removeCoordenador(Coordenador coordenador) {
        this.coordenadores.remove(coordenador);
    }

    public List<Coordenador> getCoordenadores() {
        return coordenadores;
    }
}