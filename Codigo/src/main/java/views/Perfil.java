package views;

import models.Estudante;
import models.TipoAtividade;
import models.Curso;
import controllers.GerenciadorCursos;

import javax.swing.*;
import java.awt.*;

public class Perfil extends JFrame {

    public Perfil(Estudante estudante, GerenciadorCursos gerenciadorCursos) {
        // Set the title of the window
        setTitle("Perfil do Estudante");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Search for the Curso by the name of the student's course
        Curso curso = gerenciadorCursos.buscarCurso(estudante.getNomeCurso());
        String cursoLimite = curso != null ? "Limite do Curso: " + curso.getLimiteHoras() + " horas" : "Curso não encontrado";

        // Create labels and fields for each attribute of Estudante
        JLabel cursoLimiteLabel = new JLabel(cursoLimite, SwingConstants.LEFT);

        JLabel raLabel = new JLabel("RA:");
        JLabel raValue = new JLabel(String.valueOf(estudante.getRa()), SwingConstants.RIGHT);

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel nomeValue = new JLabel(estudante.getNome(), SwingConstants.RIGHT);

        JLabel cpfLabel = new JLabel("CPF:");
        JLabel cpfValue = new JLabel(estudante.getCpf(), SwingConstants.RIGHT);

        JLabel emailLabel = new JLabel("Email:");
        JLabel emailValue = new JLabel(estudante.getEmail(), SwingConstants.RIGHT);

        JLabel nomeCursoLabel = new JLabel("Curso:");
        JLabel nomeCursoValue = new JLabel(estudante.getNomeCurso(), SwingConstants.RIGHT);

        JLabel totalHorasLabel = new JLabel("Total de Horas:");
        JLabel totalHorasValue = new JLabel(String.valueOf(estudante.getTotalHoras()), SwingConstants.RIGHT);

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the contents
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(cursoLimiteLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(raLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(raValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nomeValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cpfLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(cpfValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(emailValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeCursoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nomeCursoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(totalHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(totalHorasValue, gbc);

        // Add TipoAtividade in a hierarchical view
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        for (TipoAtividade tipoAtividade : estudante.getTipoAtividades()) {
            JPanel tipoPanel = new JPanel(new BorderLayout());
            tipoPanel.setBorder(BorderFactory.createTitledBorder(tipoAtividade.getNome()));
            JLabel horasLabel = new JLabel("Horas: " + tipoAtividade.getHorasAtuais() + " / " + tipoAtividade.getTotalHoras(), SwingConstants.RIGHT);
            tipoPanel.add(horasLabel, BorderLayout.CENTER);
            gbc.gridy++;
            panel.add(tipoPanel, gbc);
        }

        // Add the panel to the frame
        add(panel);

        // Pack the frame to fit the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // For testing purposes
        GerenciadorCursos gerenciadorCursos = new GerenciadorCursos();
        Estudante estudante = new Estudante(123456, "João Silva", "123.456.789-00", "joao.silva@example.com", "senha123", "Curso de Exemplo");
        new Perfil(estudante, gerenciadorCursos);
    }
}