package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.GerenciadorCoordenador;
import models.Coordenador;

public class AdicionarTipoCurso extends JFrame {

    public AdicionarTipoCurso(Coordenador coordenador, GerenciadorCoordenador gerenciadorCoordenador) {
        // Set the title of the window
        setTitle("Adicionar Tipo de Curso");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels
        JLabel nomeTipoLabel = new JLabel("Nome do Tipo:");
        JLabel maxHorasLabel = new JLabel("Máximo de Horas:");
        JLabel coeficienteHorasLabel = new JLabel("Coeficiente de Horas:");

        // Create text fields
        JTextField nomeTipoField = new JTextField(20);
        JTextField maxHorasField = new JTextField(20);
        JTextField coeficienteHorasField = new JTextField(20);

        // Create a button to add the type
        JButton adicionarButton = new JButton("Adicionar");

        // Add action listener to the button
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCurso = coordenador.getNomeCurso();
                String nomeTipo = nomeTipoField.getText();
                int maxHoras = Integer.parseInt(maxHorasField.getText());
                double coeficienteHoras = Double.parseDouble(coeficienteHorasField.getText());

                // Call the method to add the type
                gerenciadorCoordenador.addTipoAtividade(nomeTipo, maxHoras, coeficienteHoras, nomeCurso);
                JOptionPane.showMessageDialog(AdicionarTipoCurso.this, "Tipo de curso adicionado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the window
            }
        });

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
        panel.add(nomeTipoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomeTipoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(maxHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(maxHorasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(coeficienteHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(coeficienteHorasField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(adicionarButton, gbc);

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
        GerenciadorCoordenador gerenciadorCoordenador = new GerenciadorCoordenador(null, null, null);
        new AdicionarTipoCurso(coordenador, gerenciadorCoordenador);
    }
}