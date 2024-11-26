package views;

import javax.swing.*;
import java.awt.*;

public class HomeCoordenador extends JFrame {

    public HomeCoordenador() {
        // Set the title of the window
        setTitle("Home Coordenador");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label for the name
        JLabel nomeLabel = new JLabel("João Bobo");

        // Create a list of clickable items
        String[] items = {"Item 1", "Item 2", "Item 3"};
        JList<String> itemList = new JList<>(items);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setLayoutOrientation(JList.VERTICAL);
        itemList.setVisibleRowCount(-1);

        // Create a scroll pane for the list
        JScrollPane listScrollPane = new JScrollPane(itemList);
        listScrollPane.setPreferredSize(new Dimension(200, 300)); // Set the preferred size of the scroll pane

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24)); // Add padding around the contents

        // Add the name label to the top left
        panel.add(nomeLabel, BorderLayout.NORTH);

        // Add the scroll pane with the list to the center
        panel.add(listScrollPane, BorderLayout.CENTER);

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
        // Create an instance of the HomeCoordenador class to display the window
        new HomeCoordenador();
    }
}