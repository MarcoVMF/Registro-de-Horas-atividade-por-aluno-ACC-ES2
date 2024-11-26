package views;

import controllers.GerenciadorEstudante;
import controllers.GerenciadorAtividade;
import controllers.GerenciadorCursos;

import javax.swing.*;
import java.awt.*;

public class HomeUsuario extends JFrame {

    public HomeUsuario(int id, GerenciadorEstudante gerenciadorEstudante, GerenciadorAtividade gerenciadorAtividade, GerenciadorCursos gerenciadorCursos) {
        // Set the title of the window
        setTitle("Home Usuário");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get the name of the user using the id
        String nome = gerenciadorEstudante.buscarNomePeloRa(id);

        // Create a label for the name
        JLabel nomeLabel = new JLabel(nome, SwingConstants.CENTER);

        // Create buttons
        JButton criarButton = new JButton("Mandar nova Atividade");
        JButton verAtividadesButton = new JButton("Ver minhas atividades");
        JButton perfilButton = new JButton("Perfil");

        // Add action listener to the criarButton
        criarButton.addActionListener(e -> {
            new CriarNovaAtividade(id, gerenciadorAtividade, gerenciadorEstudante);
        });

        // Add action listener to the verAtividadesButton
        verAtividadesButton.addActionListener(e -> {
            new ListaAtividades(gerenciadorAtividade, gerenciadorCursos);
        });

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(64, 64, 64, 64)); // Add padding around the contents

        // Add the name label to the top
        panel.add(nomeLabel, BorderLayout.NORTH);

        // Create a panel for the buttons and add buttons to it
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(criarButton);
        buttonPanel.add(verAtividadesButton);
        buttonPanel.add(perfilButton);

        // Add the button panel to the center
        panel.add(buttonPanel, BorderLayout.CENTER);

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
        GerenciadorEstudante gerenciadorEstudante = new GerenciadorEstudante(gerenciadorCursos);
        GerenciadorAtividade gerenciadorAtividade = new GerenciadorAtividade(gerenciadorCursos);
        new HomeUsuario(1, gerenciadorEstudante, gerenciadorAtividade, gerenciadorCursos);
    }
}