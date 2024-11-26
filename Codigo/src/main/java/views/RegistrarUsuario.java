package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import controllers.GerenciadorEstudante;
import controllers.GerenciadorCursos;
import models.Curso;

public class RegistrarUsuario extends JFrame {

    public RegistrarUsuario(GerenciadorEstudante gerenciadorEstudante, GerenciadorCursos gerenciadorCursos) {
        // Set the title of the window
        setTitle("Registrar Usuário");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels
        JLabel raLabel = new JLabel("RA:");
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cpfLabel = new JLabel("CPF:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");
        JLabel cursosLabel = new JLabel("Cursos:");

        // Create text fields
        JTextField raField = new JTextField(20);
        JTextField nomeField = new JTextField(20);
        JTextField cpfField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField(20);

        // Create dropdown for cursos
        List<Curso> cursos = gerenciadorCursos.listarCursos();
        JComboBox<Curso> cursosDropdown = new JComboBox<>(cursos.toArray(new Curso[0]));

        // Set custom renderer to display curso.nome
        cursosDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Curso) {
                    setText(((Curso) value).getNome());
                }
                return this;
            }
        });

        // Create button
        JButton registrarButton = new JButton("Registrar");

        // Add action listener to the button
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int ra = Integer.parseInt(raField.getText());
                    String nome = nomeField.getText();
                    String cpf = cpfField.getText();
                    String email = emailField.getText();
                    String senha = new String(senhaField.getPassword());
                    Curso selectedCurso = (Curso) cursosDropdown.getSelectedItem();
                    UUID idCurso = selectedCurso.getId();

                    boolean success = gerenciadorEstudante.inserirDadosRegistro(ra, nome, cpf, email, senha, idCurso);
                    if (!success) {
                        JOptionPane.showMessageDialog(RegistrarUsuario.this, "Erro ao registrar usuário", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(RegistrarUsuario.this, "Usuário registrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Close the registration window
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RegistrarUsuario.this, "Erro ao registrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24)); // Add padding around the contents
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(raLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(raField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(cpfLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(senhaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(senhaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(cursosLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(cursosDropdown, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(registrarButton, gbc);

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
        // Create instances of GerenciadorEstudante and GerenciadorCursos for testing
        GerenciadorCursos gerenciadorCursos = new GerenciadorCursos();
        GerenciadorEstudante gerenciadorEstudante = new GerenciadorEstudante(gerenciadorCursos);

        // Insert default courses for testing
        gerenciadorCursos.inserirDadosCurso("Ciencia da Computação", 90);
        gerenciadorCursos.inserirDadosCurso("Matematica", 160);
        gerenciadorCursos.inserirDadosCurso("Historia", 360);

        // Create an instance of the RegistrarUsuario class to display the window
        new RegistrarUsuario(gerenciadorEstudante, gerenciadorCursos);
    }
}