package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Curso;
import models.Estudante;
import models.TipoAtividade;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class GerenciadorEstudante {
    private List<Estudante> estudantes;
    private GerenciadorCursos gerenciadorCursos;

    public GerenciadorEstudante(GerenciadorCursos gerenciadorCursos) {
        this.gerenciadorCursos = gerenciadorCursos;
        this.estudantes = new ArrayList<>();
        loadEstudantesFromDatabase();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    private void loadEstudantesFromDatabase() {
        Session session = getSession();
        try {
            estudantes = session.createQuery("from Estudante", Estudante.class).list();
        } finally {
            session.close();
        }
    }

    public void saveEstudantesToDatabase() {
        Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (Estudante estudante : estudantes) {
                Estudante managedEstudante = session.find(Estudante.class, estudante.getRa());
                if (managedEstudante != null) {
                    session.merge(estudante);
                } else {
                    session.save(estudante);
                }
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
        return estudante.getSenha().equals(senha) ? estudante.getRa() : 0;
    }

    public boolean inserirDadosRegistro(int ra, String nome, String cpf, String email, String senha, String nomeCurso) {
        for (Estudante estudante : estudantes) {
            if (estudante.getRa() == ra || estudante.getCpf().equals(cpf) || estudante.getEmail().equals(email)) {
                return false;
            }
        }
        if (validarDadosRegistro(ra, nome, cpf, email, senha, nomeCurso)) {
            Estudante novoEstudante = new Estudante(ra, nome, cpf, email, senha, nomeCurso);
            estudantes.add(novoEstudante);
            return true;
        }
        return false;
    }

    public boolean validarDadosRegistro(int ra, String nome, String cpf, String email, String senha, String nomeCurso) {
        return validarRa(ra) &&
               validarNome(nome) &&
               validarCpf(cpf) &&
               validarEmailInstitucional(email) &&
               validarSenha(senha) &&
               gerenciadorCursos.validarNomeExistenteCurso(nomeCurso);
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

    public boolean atualizarHoras(int ra, double horas, TipoAtividade tipo) {
        for (Estudante estudante : estudantes) {
            if (estudante.getRa() == ra) {
                for (TipoAtividade tipoAtividade : estudante.getTipoAtividades()) {
                    if (tipoAtividade.getNome().equals(tipo.getNome())) {
                        double horasAtuais = tipoAtividade.getHorasAtuais();
                        double maxHoras = tipoAtividade.getTotalHoras();
                        double horasParaAdicionar = Math.min(horas, maxHoras - horasAtuais);
    
                        if (horasParaAdicionar > 0) {
                            tipoAtividade.adicionarHoras(horasParaAdicionar);
                            estudante.somarHoras(horasParaAdicionar);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String buscarNomePeloRa(int ra) {
        for (Estudante estudante : estudantes) {
            if (estudante.getRa() == ra) {
                return estudante.getNome();
            }
        }
        return null;
    }

    public Curso buscarCursoPeloRa(int ra) {
        for (Estudante estudante : estudantes) {
            if (estudante.getRa() == ra) {
                return gerenciadorCursos.buscarCurso(estudante.getNomeCurso());
            }
        }
        return null;
    }

    public Estudante buscarEstudantePeloRa(int ra) {
        for (Estudante estudante : estudantes) {
            if (estudante.getRa() == ra) {
                return estudante;
            }
        }
        return null;
    }

    public boolean addTipoAtividadePorCurso(String nomeCurso, String nomeTipo, int maxHoras, double coeficienteHoras) {
        for (Estudante estudante : estudantes) {
            if (estudante.getNomeCurso().equals(nomeCurso)) {
                return estudante.addTipoAtividade(nomeTipo, maxHoras, coeficienteHoras);
            }
        }
        return false;
    }

    public boolean atualizarTipoAtividadePorCurso(String nomeCurso, String nomeTipoAtividade, int maxHoras, double coeficiente) {
        boolean updated = false;
        for (Estudante estudante : estudantes) {
            if (estudante.getNomeCurso().equals(nomeCurso)) {
                for (TipoAtividade tipoAtividade : estudante.getTipoAtividades()) {
                    if (tipoAtividade.getNome().equals(nomeTipoAtividade)) {
                        tipoAtividade.setTotalHoras(maxHoras);
                        tipoAtividade.setCoeficienteHoras(coeficiente);
                        updated = true;
                    }
                }
            }
        }
        return updated;
    }

    public List<Estudante> buscarEstudantesPorCurso(String nomeCurso) {
        List<Estudante> estudantesPorCurso = new ArrayList<>();
        for (Estudante estudante : estudantes) {
            if (estudante.getNomeCurso().equals(nomeCurso)) {
                estudantesPorCurso.add(estudante);
            }
        }
        return estudantesPorCurso;
    }
}