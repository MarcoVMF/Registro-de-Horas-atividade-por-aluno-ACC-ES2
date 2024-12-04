package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;   

import controllers.GerenciadorEstudante;
import controllers.GerenciadorCoordenador;
import controllers.GerenciadorCursos;
import controllers.GerenciadorAtividade;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class Login extends JFrame {

    public Login(GerenciadorEstudante gerenciadorEstudante, GerenciadorCoordenador gerenciadorCoordenador, GerenciadorCursos gerenciadorCursos, GerenciadorAtividade gerenciadorAtividade) {
        // Set the title of the window
        setTitle("Login");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");
        JLabel roleLabel = new JLabel("Você é um?");

        // Create text fields
        JTextField emailField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField(20);

        // Create radio buttons
        JRadioButton coordenadorRadioButton = new JRadioButton("Coordenador");
        JRadioButton estudanteRadioButton = new JRadioButton("Estudante");

        // Group the radio buttons
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(coordenadorRadioButton);
        roleGroup.add(estudanteRadioButton);

        // Create buttons
        JButton loginButton = new JButton("Entrar");
        JButton registrarButton = new JButton("Registrar");

        // Add action listeners to the buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword());

                if (estudanteRadioButton.isSelected()) {
                    int estudanteId = gerenciadorEstudante.inserirDadosEstudante(email, senha);
                    if (estudanteId == 0) {
                        JOptionPane.showMessageDialog(Login.this, "Usuário e/ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Handle successful login for estudante
                        new HomeUsuario(estudanteId, gerenciadorEstudante, gerenciadorAtividade, gerenciadorCursos, gerenciadorCoordenador);
                        dispose(); // Close the login window
                    }
                } else if (coordenadorRadioButton.isSelected()) {
                    String cpfCoordenador = gerenciadorCoordenador.inserirDadosAutenticarCoordenador(email, senha);
                    if (cpfCoordenador == null) {
                        JOptionPane.showMessageDialog(Login.this, "Usuário e/ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Handle successful login for coordenador
                        new HomeCoordenador(cpfCoordenador, gerenciadorAtividade, gerenciadorCursos, gerenciadorCoordenador, gerenciadorEstudante);
                        dispose(); // Close the login window
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Selecione o seu tipo de usuário para prosseguir com o Login", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estudanteRadioButton.isSelected()) {
                    new RegistrarUsuario(gerenciadorEstudante, gerenciadorCursos);
                } else if (coordenadorRadioButton.isSelected()) {
                    new RegistrarCoordenador(gerenciadorCoordenador, gerenciadorCursos);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Selecione o tipo de usuário para prosseguir", "Erro", JOptionPane.ERROR_MESSAGE);
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
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(coordenadorRadioButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(estudanteRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(senhaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(senhaField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(loginButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
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
        // Initialize Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
    
        // Declare variables outside the try block
        GerenciadorCursos gerenciadorCursos = null;
        GerenciadorEstudante gerenciadorEstudante = null;
        GerenciadorAtividade gerenciadorAtividade = null;
        GerenciadorCoordenador gerenciadorCoordenador = null;
    
        try {
            transaction = session.beginTransaction();
    
            // Initialize the Gerenciador instances
            gerenciadorCursos = new GerenciadorCursos();
            gerenciadorEstudante = new GerenciadorEstudante(gerenciadorCursos);
            gerenciadorAtividade = new GerenciadorAtividade(gerenciadorCursos);
            gerenciadorCoordenador = new GerenciadorCoordenador(gerenciadorAtividade, gerenciadorCursos, gerenciadorEstudante);
    
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    
        // Ensure the Gerenciador instances are not null
        final GerenciadorCursos finalGerenciadorCursos = gerenciadorCursos;
        final GerenciadorEstudante finalGerenciadorEstudante = gerenciadorEstudante;
        final GerenciadorAtividade finalGerenciadorAtividade = gerenciadorAtividade;
        final GerenciadorCoordenador finalGerenciadorCoordenador = gerenciadorCoordenador;
    
        // Create an instance of the Login class to display the window
        new Login(finalGerenciadorEstudante, finalGerenciadorCoordenador, finalGerenciadorCursos, finalGerenciadorAtividade);
    
        // Save changes to the database when the application closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (finalGerenciadorCursos != null) {
                finalGerenciadorCursos.saveCursosToDatabase();
            }
            if (finalGerenciadorEstudante != null) {
                finalGerenciadorEstudante.saveEstudantesToDatabase();
            }
            if (finalGerenciadorAtividade != null) {
                finalGerenciadorAtividade.saveAtividadesToDatabase();
            }
            if (finalGerenciadorCoordenador != null) {
                finalGerenciadorCoordenador.saveCoordenadoresToDatabase();
            }
            HibernateUtil.shutdown();
        }));
    }
}