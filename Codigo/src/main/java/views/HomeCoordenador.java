package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import controllers.GerenciadorAtividade;
import controllers.GerenciadorCursos;
import controllers.GerenciadorCoordenador;
import controllers.GerenciadorEstudante;
import models.Atividade;
import models.Coordenador;

public class HomeCoordenador extends JFrame {

    private JList<String> itemList;
    private JScrollPane listScrollPane;
    private JPanel panel;
    private GerenciadorCursos gerenciadorCursos;
    private GerenciadorCoordenador gerenciadorCoordenador;

    public HomeCoordenador(String cpfCoordenador, GerenciadorAtividade gerenciadorAtividade, GerenciadorCursos gerenciadorCursos, GerenciadorCoordenador gerenciadorCoordenador, GerenciadorEstudante gerenciadorEstudante) {
        this.gerenciadorCursos = gerenciadorCursos;
        this.gerenciadorCoordenador = gerenciadorCoordenador;

        // Set the title of the window
        setTitle("Home Coordenador");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Search for the Coordenador by cpf
        Coordenador coordenador = gerenciadorCoordenador.buscarCoordenador(cpfCoordenador);
        String nomeCoordenador = coordenador != null ? coordenador.getNome() : "Coordenador não encontrado";

        // Create a label for the name
        JLabel nomeLabel = new JLabel(nomeCoordenador, SwingConstants.LEFT);

        // Create a button for Perfil
        JButton perfilButton = new JButton("Perfil");
        perfilButton.addActionListener(e -> {
            new PerfilCoordenador(coordenador);
        });

        // Create a button for AdicionarTipoCurso
        JButton adicionarTipoButton = new JButton("Adicionar Tipo");
        adicionarTipoButton.addActionListener(e -> {
            new AdicionarTipoCurso(coordenador, gerenciadorCoordenador);
        });

        // Create a button for AtualizarLimitesAcc
        JButton atualizarTiposButton = new JButton("Atualizar Tipos");
        atualizarTiposButton.addActionListener(e -> {
            new AtualizarLimitesAcc(coordenador, gerenciadorAtividade, gerenciadorEstudante, gerenciadorCoordenador);
        });

        // Create a toolbar with four options
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Disable the option to detach the toolbar

        // Create buttons
        JButton todasButton = new JButton("Todas");
        JButton pendentesButton = new JButton("Pendentes");
        JButton aprovadasButton = new JButton("Aprovadas");
        JButton negadasButton = new JButton("Negadas");

        // Add action listeners to the buttons
        todasButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarPorCurso(coordenador.getNomeCurso());
            updateActivityList(atividades);
        });

        pendentesButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarPorCursoeStatus(coordenador.getNomeCurso(), "Pendente");
            updateActivityList(atividades);
        });

        aprovadasButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarPorCursoeStatus(coordenador.getNomeCurso(), "Aprovada");
            updateActivityList(atividades);
        });

        negadasButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarPorCursoeStatus(coordenador.getNomeCurso(), "Negada");
            updateActivityList(atividades);
        });

        // Create a box to center the buttons
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(todasButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(pendentesButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(aprovadasButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(negadasButton);
        box.add(Box.createHorizontalGlue());

        // Add the box to the toolbar
        toolBar.add(box);

        // Fetch the list of all activities
        List<Atividade> atividades = gerenciadorAtividade.buscarPorCurso(coordenador.getNomeCurso());

        // Create an array of activity names
        String[] activityNames = atividades.stream().map(Atividade::getNomeAtividade).toArray(String[]::new);

        // Create a list of clickable items
        itemList = new JList<>(activityNames);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setLayoutOrientation(JList.VERTICAL);
        itemList.setVisibleRowCount(-1);

        // Add a mouse listener for double-click events
        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = itemList.locationToIndex(e.getPoint());
                    Atividade selectedAtividade = atividades.get(index);
                    new SumarioAtividadeCoordenador(selectedAtividade, gerenciadorCursos, gerenciadorCoordenador);
                }
            }
        });

        // Create a scroll pane for the list
        listScrollPane = new JScrollPane(itemList);
        listScrollPane.setPreferredSize(new Dimension(300, 400)); // Set the preferred size of the scroll pane

        // Create a panel and add components to it
        panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(64, 64, 64, 64)); // Add padding around the contents

        // Create a panel for the top section with the name label, perfil button, listaAtividades button, adicionarTipo button, atualizarTipos button, and sair button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(nomeLabel, BorderLayout.WEST);
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(perfilButton);
        topRightPanel.add(adicionarTipoButton);
        topRightPanel.add(atualizarTiposButton);
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> {
            new Login(gerenciadorEstudante, gerenciadorCoordenador, gerenciadorCursos, gerenciadorAtividade);
            dispose();
        });
        topRightPanel.add(sairButton);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        // Add the top panel to the top
        panel.add(topPanel, BorderLayout.NORTH);

        // Add the toolbar to the center
        panel.add(toolBar, BorderLayout.CENTER);

        // Add the scroll pane with the list to the bottom
        panel.add(listScrollPane, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);

        // Pack the frame to fit the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);
    }

    private void updateActivityList(List<Atividade> atividades) {
        String[] activityNames = atividades.stream().map(Atividade::getNomeAtividade).toArray(String[]::new);
        itemList.setListData(activityNames);

        // Add a mouse listener for double-click events
        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = itemList.locationToIndex(e.getPoint());
                    Atividade selectedAtividade = atividades.get(index);
                    new SumarioAtividadeCoordenador(selectedAtividade, gerenciadorCursos, gerenciadorCoordenador);
                }
            }
        });

        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        // For testing purposes
        GerenciadorCursos gerenciadorCursos = new GerenciadorCursos();
        GerenciadorAtividade gerenciadorAtividade = new GerenciadorAtividade(gerenciadorCursos);
        GerenciadorCoordenador gerenciadorCoordenador = new GerenciadorCoordenador(gerenciadorAtividade, gerenciadorCursos, null);
        GerenciadorEstudante gerenciadorEstudante = new GerenciadorEstudante(gerenciadorCursos);
        new HomeCoordenador("123.456.789-00", gerenciadorAtividade, gerenciadorCursos, gerenciadorCoordenador, gerenciadorEstudante);
    }
}