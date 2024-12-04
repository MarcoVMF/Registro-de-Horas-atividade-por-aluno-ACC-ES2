package views;

import models.Coordenador;

import javax.swing.*;
import java.awt.*;

public class PerfilCoordenador extends JFrame {

    public PerfilCoordenador(Coordenador coordenador) {
        // Set the title of the window
        setTitle("Perfil do Coordenador");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels and fields for each attribute of Coordenador
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel nomeValue = new JLabel(coordenador.getNome(), SwingConstants.RIGHT);

        JLabel emailLabel = new JLabel("Email:");
        JLabel emailValue = new JLabel(coordenador.getEmail(), SwingConstants.RIGHT);

        JLabel cpfLabel = new JLabel("CPF:");
        JLabel cpfValue = new JLabel(coordenador.getCpf(), SwingConstants.RIGHT);

        JLabel nomeCursoLabel = new JLabel("Curso:");
        JLabel nomeCursoValue = new JLabel(coordenador.getNomeCurso(), SwingConstants.RIGHT);

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
        gbc.gridwidth = 1;
        panel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nomeValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(emailValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cpfLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(cpfValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeCursoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nomeCursoValue, gbc);

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
        Coordenador coordenador = new Coordenador("Maria Silva", "maria.silva@example.com", "senha123", "123.456.789-00", "Curso de Exemplo");
        new PerfilCoordenador(coordenador);
    }
}