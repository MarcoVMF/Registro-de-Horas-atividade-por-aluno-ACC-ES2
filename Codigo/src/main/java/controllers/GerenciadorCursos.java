package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Curso;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class GerenciadorCursos {
    private List<Curso> cursos;

    public GerenciadorCursos() {
        this.cursos = new ArrayList<>();
        loadCursosFromDatabase();
    }

    private void loadCursosFromDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            cursos = session.createQuery("from Curso", Curso.class).list();
        } finally {
            session.close();
        }
    }

    public void saveCursosToDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (Curso curso : cursos) {
                session.saveOrUpdate(curso);
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

    public boolean inserirDadosCurso(String nome, int limiteHoras) {
        if (validarDadosCurso(nome, limiteHoras)) {
            Curso novoCurso = new Curso(nome, limiteHoras);
            cursos.add(novoCurso);
            return true;
        }
        return false;
    }

    public boolean validarDadosCurso(String nome, int limiteHoras) {
        return validarNomeCurso(nome) && validarLimiteHoras(limiteHoras);
    }

    public boolean validarNomeCurso(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
    
        return nome.matches("^[\\p{L}\\s]{6,32}$");
    }

    public boolean validarLimiteHoras(int limiteHoras) {
        return limiteHoras > 10 && limiteHoras < 500;
    }

    public Curso buscarCurso(String nome) {
        for (Curso curso : cursos) {
            if (curso.getNome().equals(nome)) {
                return curso;
            }
        }
        return null;
    }

    public boolean editarCurso(String nome, Integer limiteHoras) {
        Curso curso = buscarCurso(nome);
        if (curso == null) {
            return false;
        }

        if (nome != null && !nome.trim().isEmpty()) {
            curso.setNome(nome);
        }

        if (limiteHoras != null && validarLimiteHoras(limiteHoras)) {
            curso.setLimiteHoras(limiteHoras);
        }

        return true;
    }

    public void deletarCurso(Curso curso) {
        this.cursos.remove(curso);
    }

    public boolean validarNomeExistenteCurso(String nome) {
        for (Curso curso : cursos) {
            if (curso.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    public List<Curso> listarCursos() {
        return cursos;
    }
}