package views;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.jdatepicker.impl.*;

import controllers.GerenciadorAtividade;
import controllers.GerenciadorCursos;
import controllers.GerenciadorEstudante;
import models.Atividade;
import models.Curso;

public class CriarNovaAtividade extends JFrame {

    private String selectedFilePath;

    public CriarNovaAtividade(int ra, GerenciadorAtividade gerenciadorAtividade, GerenciadorEstudante gerenciadorEstudante) {
        // Set the title of the window
        setTitle("Criar Nova Atividade");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels
        JLabel nomeAtividadeLabel = new JLabel("Nome da Atividade:");
        JLabel descricaoLabel = new JLabel("Descrição:");
        JLabel dataLabel = new JLabel("Data:");
        JLabel totalHorasLabel = new JLabel("Total de Horas:");
        JLabel tipoLabel = new JLabel("Tipo:");
        JLabel fileLabel = new JLabel("Arquivo:");
        JLabel filePathLabel = new JLabel("Nenhum arquivo selecionado");

        // Create text fields
        JTextField nomeAtividadeField = new JTextField(20);
        JTextArea descricaoField = new JTextArea(5, 20);
        descricaoField.setLineWrap(true);
        descricaoField.setWrapStyleWord(true);
        JTextField totalHorasField = new JTextField(20);

        // Set fixed sizes for text fields
        nomeAtividadeField.setPreferredSize(new Dimension(200, 25));
        descricaoField.setPreferredSize(new Dimension(200, 75));
        totalHorasField.setPreferredSize(new Dimension(200, 25));

        // Create dropdown for Tipo
        JComboBox<Atividade.Tipo> tipoDropdown = new JComboBox<>(Atividade.Tipo.values());
        tipoDropdown.setPreferredSize(new Dimension(200, 25));

        // Create date picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setPreferredSize(new Dimension(200, 25));

        // Create file chooser button
        JButton fileButton = new JButton("Selecionar Arquivo");

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileButton.setEnabled(false); // Disable the button while the file chooser is open
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFilePath = fileChooser.getSelectedFile().getPath();
                filePathLabel.setText(fileChooser.getSelectedFile().getName());
            }
            fileButton.setEnabled(true); // Re-enable the button after the file chooser is closed
        });

        // Create button
        JButton enviarButton = new JButton("Enviar");

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(64, 64, 64, 64)); // Add padding around the contents
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomeAtividadeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(nomeAtividadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(descricaoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(new JScrollPane(descricaoField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST; // Align the Data label to the left
        panel.add(dataLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(totalHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(totalHorasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(tipoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(tipoDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(fileLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(filePathLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(fileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add(enviarButton, gbc);

        // Add the panel to the frame
        add(panel);

        // Pack the frame to fit the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);

        // Add action listener to the button
        enviarButton.addActionListener(e -> {
            String nomeAtividade = nomeAtividadeField.getText();
            String descricao = descricaoField.getText();
            Date selectedDate = (Date) datePicker.getModel().getValue();
            LocalDateTime data = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int totalHoras = Integer.parseInt(totalHorasField.getText());
            Atividade.Tipo tipo = (Atividade.Tipo) tipoDropdown.getSelectedItem();

            // Get the UUID of the curso of the user from the ra
            Curso curso = gerenciadorEstudante.buscarCursoPeloRa(ra);
            UUID idCurso = curso != null ? curso.getId() : null;

            // Call preencherDadosAtividade
            boolean success = gerenciadorAtividade.preencherDadosAtividade(nomeAtividade, data, descricao, ra,idCurso, tipo,  totalHoras, selectedFilePath);
            if (!success) {
                JOptionPane.showMessageDialog(CriarNovaAtividade.this, "Dados inválidos, tente novamente", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(CriarNovaAtividade.this, "Atividade criada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the window
            }
        });
    }

    public static void main(String[] args) {
        // For testing purposes
        GerenciadorCursos gerenciadorCursos = new GerenciadorCursos();
        GerenciadorEstudante gerenciadorEstudante = new GerenciadorEstudante(gerenciadorCursos);
        GerenciadorAtividade gerenciadorAtividade = new GerenciadorAtividade(gerenciadorCursos);
        new CriarNovaAtividade(1, gerenciadorAtividade, gerenciadorEstudante);
    }
}

// DateLabelFormatter class for formatting the date
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}