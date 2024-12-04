package views;

import controllers.GerenciadorEstudante;
import controllers.GerenciadorAtividade;
import controllers.GerenciadorCoordenador;
import controllers.GerenciadorCursos;
import models.Estudante;

import javax.swing.*;
import java.awt.*;

public class HomeUsuario extends JFrame {

    public HomeUsuario(int ra, GerenciadorEstudante gerenciadorEstudante, GerenciadorAtividade gerenciadorAtividade, GerenciadorCursos gerenciadorCursos, GerenciadorCoordenador gerenciadorCoordenador) {
        // Set the title of the window
        setTitle("Home Usuário");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get the name of the user using the ra
        String nome = gerenciadorEstudante.buscarNomePeloRa(ra);

        // Create a label for the name
        JLabel nomeLabel = new JLabel(nome, SwingConstants.CENTER);

        // Create buttons
        JButton criarButton = new JButton("Mandar nova Atividade");
        JButton verAtividadesButton = new JButton("Ver minhas atividades");
        JButton perfilButton = new JButton("Perfil");

        // Add action listener to the criarButton
        criarButton.addActionListener(e -> {
            new CriarNovaAtividade(ra, gerenciadorAtividade, gerenciadorEstudante);
        });

        // Add action listener to the verAtividadesButton
        verAtividadesButton.addActionListener(e -> {
            new ListaAtividadesEstudante(ra, gerenciadorAtividade, gerenciadorCursos);
        });

        // Add action listener to the perfilButton
        perfilButton.addActionListener(e -> {
            Estudante estudante = gerenciadorEstudante.buscarEstudantePeloRa(ra);
            new Perfil(estudante, gerenciadorCursos);
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

        // Create a panel for the bottom section with the logout button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> {
            new Login(gerenciadorEstudante, gerenciadorCoordenador, gerenciadorCursos, gerenciadorAtividade);
            dispose();
        });
        bottomPanel.add(sairButton, BorderLayout.EAST);

        // Add the bottom panel to the bottom
        panel.add(bottomPanel, BorderLayout.SOUTH);

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
        GerenciadorCoordenador gerenciadorCoordenador = new GerenciadorCoordenador(gerenciadorAtividade, gerenciadorCursos, gerenciadorEstudante);
        new HomeUsuario(1, gerenciadorEstudante, gerenciadorAtividade, gerenciadorCursos, gerenciadorCoordenador);
    }
}