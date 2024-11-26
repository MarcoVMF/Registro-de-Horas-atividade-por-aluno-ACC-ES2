package views;

import javax.swing.*;
import java.awt.*;

public class SumarioAtividadeCoordenador extends JFrame {

    public SumarioAtividadeCoordenador() {
        // Set the title of the window
        setTitle("Sumário da Atividade");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels for each item with HTML for spacing
        JLabel nomeAtividadeLabel = new JLabel("Nome da Atividade:");
        JLabel nomeAtividadeValue = new JLabel("Nome da Atividade", SwingConstants.RIGHT);
        
        JLabel dataRealizacaoLabel = new JLabel("Data de Realização:");
        JLabel dataRealizacaoValue = new JLabel("01/01/2023", SwingConstants.RIGHT);
        
        JLabel statusLabel = new JLabel("Status:");
        JLabel statusValue = new JLabel("Pendente", SwingConstants.RIGHT);
        
        JLabel descricaoLabel = new JLabel("Descrição:");
        JLabel descricaoValue = new JLabel("Descrição da Atividade", SwingConstants.RIGHT);
        
        JLabel totalHorasLabel = new JLabel("Total de Horas:");
        JLabel totalHorasValue = new JLabel("10", SwingConstants.RIGHT);
        
        JLabel raAlunoLabel = new JLabel("RA do Aluno:");
        JLabel raAlunoValue = new JLabel("123456", SwingConstants.RIGHT);
        
        JLabel cursoLabel = new JLabel("Curso:");
        JLabel cursoValue = new JLabel("Curso de Exemplo", SwingConstants.RIGHT);
        
        JLabel tipoLabel = new JLabel("Tipo:");
        JLabel tipoValue = new JLabel("Monitoria", SwingConstants.RIGHT);

        // Create label and button for Documento
        JLabel documentoLabel = new JLabel("Documento:");
        JButton abrirButton = new JButton("Abrir");

        // Create buttons for Aceitar and Negar
        JButton aceitarButton = new JButton("Aceitar");
        JButton negarButton = new JButton("Negar");

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the contents
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(aceitarButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(negarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeAtividadeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nomeAtividadeValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(dataRealizacaoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(dataRealizacaoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(statusValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(descricaoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(descricaoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(totalHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(totalHorasValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(raAlunoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(raAlunoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cursoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(cursoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(tipoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(tipoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(documentoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(abrirButton, gbc);

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
        // Create an instance of the SumarioAtividade class to display the window
        new SumarioAtividadeCoordenador();
    }
}