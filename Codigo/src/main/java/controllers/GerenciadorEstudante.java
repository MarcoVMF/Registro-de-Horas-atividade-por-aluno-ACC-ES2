package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Curso;
import models.Estudante;

public class GerenciadorEstudante {
    private List<Estudante> estudantes;
    private GerenciadorCursos gerenciadorCursos;

    public GerenciadorEstudante(GerenciadorCursos gerenciadorCursos) {
        this.gerenciadorCursos = gerenciadorCursos;
        this.estudantes = new ArrayList<>();
    }

    public int inserirDadosEstudante(String email, String senha) {
        if (validarDadosEstudante(email, senha)) {
            return autenticarEstudante(email, senha);
        }
        return 0;
    }

    public boolean validarDadosEstudante(String email, String senha) {
        return validarEmailInstitucional(email) && validarSenha(senha);
    }

    public int autenticarEstudante(String email, String senha) {
        Estudante estudante = buscarPeloEmail(email);
        if (estudante == null) {
            return 0;
        }
        return estudante.getSenha().equals(senha) ? estudante.getId() : null;
    }

    public boolean inserirDadosRegistro(int ra, String nome, String cpf, String email, String senha, UUID idCurso) {
        if (validarDadosRegistro(ra, nome, cpf, email, senha, idCurso)) {
            Estudante novoEstudante = new Estudante(ra, nome, cpf, email, senha, idCurso);
            estudantes.add(novoEstudante);
            return true;
        }
        return false;
    }

    public boolean validarDadosRegistro(int ra, String nome, String cpf, String email, String senha, UUID idCurso) {
        return validarRa(ra) &&
               validarNome(nome) &&
               validarCpf(cpf) &&
               validarEmailInstitucional(email) &&
               validarSenha(senha) &&
               gerenciadorCursos.validarIdCurso(idCurso);
    }

    public boolean validarEmailInstitucional(String email) {
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

    public boolean validarSenha(String senha) {
        if (senha == null || senha.length() < 8 || senha.length() > 24) {
            return false;
        }
    
        boolean hasUpperCase = senha.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = senha.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = senha.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = senha.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
    
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    public boolean validarCpf(String cpf) {
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

    public boolean validarRa(int ra) {
        return ra != 0;
    }

    public boolean validarNome(String nome) {
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

    public Estudante buscarPeloEmail(String email) {
        for (Estudante estudante : estudantes) {
            if (estudante.getEmail().equals(email)) {
                return estudante;
            }
        }
        return null;
    }

    public boolean atualizarHoras(int ra, double horas) {
        for (Estudante estudante : estudantes) {
            if (estudante.getId() == ra) {
                estudante.somarHoras(horas);
                return true;
            }
        }
        return false;
    }

    public String buscarNomePeloRa(int ra) {
        for (Estudante estudante : estudantes) {
            if (estudante.getId() == ra) {
                return estudante.getNome();
            }
        }
        return null;
    }

    public Curso buscarCursoPeloRa(int ra) {
        for (Estudante estudante : estudantes) {
            if (estudante.getId() == ra) {
                return gerenciadorCursos.buscarCurso(estudante.getIdCurso());
            }
        }
        return null;
    }
}
