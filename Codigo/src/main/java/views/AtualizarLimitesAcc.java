package views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import controllers.GerenciadorAtividade;
import controllers.GerenciadorCoordenador;
import controllers.GerenciadorCursos;
import controllers.GerenciadorEstudante;
import models.Atividade;
import models.Coordenador;
import models.Estudante;
import models.TipoAtividade;

public class AtualizarLimitesAcc extends JFrame {

    public AtualizarLimitesAcc(Coordenador coordenador, GerenciadorAtividade gerenciadorAtividade, GerenciadorEstudante gerenciadorEstudante, GerenciadorCoordenador gerenciadorCoordenador) {
        // Set the title of the window
        setTitle("Atualizar Limites de Atividades");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels and input fields
        JLabel maxHorasLabel = new JLabel("Máximo de Horas:");
        JTextField maxHorasField = new JTextField(20);

        JLabel coeficienteLabel = new JLabel("Coeficiente:");
        JTextField coeficienteField = new JTextField(20);

        JLabel currentMaxHorasLabel = new JLabel("Atual Máximo de Horas: ");
        JLabel currentCoeficienteLabel = new JLabel("Atual Coeficiente: ");

        // Fetch the Estudante object using the coordenador's course
        List<Estudante> estudantes = gerenciadorEstudante.buscarEstudantesPorCurso(coordenador.getNomeCurso());
        Estudante estudante = estudantes.isEmpty() ? null : estudantes.get(0);

        // Create dropdown for TipoAtividade
        List<TipoAtividade> tipoAtividades = estudante != null ? estudante.getTipoAtividades() : new ArrayList<>();
        JComboBox<TipoAtividade> tipoDropdown = new JComboBox<>(tipoAtividades.toArray(new TipoAtividade[0]));
        tipoDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoAtividade) {
                    setText(((TipoAtividade) value).getNome());
                }
                return c;
            }
        });

        // Add action listener to the dropdown to update labels
        tipoDropdown.addActionListener(e -> {
            TipoAtividade selectedTipo = (TipoAtividade) tipoDropdown.getSelectedItem();
            if (selectedTipo != null) {
                currentMaxHorasLabel.setText("Atual Máximo de Horas: " + selectedTipo.getTotalHoras());
                currentCoeficienteLabel.setText("Atual Coeficiente: " + selectedTipo.getCoeficienteHoras());
            }
        });

        // Set initial selection and update labels
        if (tipoDropdown.getItemCount() > 0) {
            tipoDropdown.setSelectedIndex(0);
            TipoAtividade selectedTipo = (TipoAtividade) tipoDropdown.getSelectedItem();
            if (selectedTipo != null) {
                currentMaxHorasLabel.setText("Atual Máximo de Horas: " + selectedTipo.getTotalHoras());
                currentCoeficienteLabel.setText("Atual Coeficiente: " + selectedTipo.getCoeficienteHoras());
            }
        }

        // Create update button
        JButton updateButton = new JButton("Atualizar");

        // Add action listener to the update button
        updateButton.addActionListener(e -> {
            String maxHorasText = maxHorasField.getText();
            String coeficienteText = coeficienteField.getText();

            if (maxHorasText.isEmpty() || coeficienteText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Os campos de máximo de horas e coeficiente não podem estar vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int maxHoras = Integer.parseInt(maxHorasText);
                double coeficiente = Double.parseDouble(coeficienteText);

                if (coeficiente < 0.1 || coeficiente > 1) {
                    JOptionPane.showMessageDialog(this, "O coeficiente deve estar entre 0.1 e 1.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check for pending activities
                List<Atividade> atividadesPendentes = gerenciadorAtividade.buscarPorCursoeStatus(coordenador.getNomeCurso(), "Pendente");
                if (!atividadesPendentes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Existem atividades pendentes. Por favor, verifique todas as atividades pendentes antes de continuar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Update TipoAtividade
                TipoAtividade selectedTipo = (TipoAtividade) tipoDropdown.getSelectedItem();
                if (selectedTipo != null) {
                    boolean maxHorasChanged = maxHoras != selectedTipo.getTotalHoras();
                    boolean coeficienteChanged = coeficiente != selectedTipo.getCoeficienteHoras();

                    if (!maxHorasChanged && !coeficienteChanged) {
                        JOptionPane.showMessageDialog(this, "Para atualizar, você deve alterar pelo menos um dos campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    boolean success = gerenciadorEstudante.atualizarTipoAtividadePorCurso(coordenador.getNomeCurso(), selectedTipo.getNome(), maxHoras, coeficiente);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Limite de horas e coeficiente atualizados com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Close the window after successful update
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar o limite de horas e coeficiente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Formato inválido para máximo de horas ou coeficiente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo de Atividade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(tipoDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(currentMaxHorasLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(maxHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(maxHorasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(currentCoeficienteLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(coeficienteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(coeficienteField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(updateButton, gbc);

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
        Coordenador coordenador = new Coordenador("Nome Coordenador", "email@unesp.br", "senha123", "12345678901", "Curso Exemplo");
        new AtualizarLimitesAcc(coordenador, gerenciadorAtividade, gerenciadorEstudante, gerenciadorCoordenador);
    }
}