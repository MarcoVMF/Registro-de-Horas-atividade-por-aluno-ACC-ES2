package views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

public class PDFRender extends JFrame {

    public PDFRender(String pdfFilePath) {
        // Set the title of the window
        setTitle("PDF Render");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the PDF pages
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Read and render the PDF
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                // Render the page as an image
                Image image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIcon imageIcon = new ImageIcon(image);

                // Create a label to hold the image and add it to the panel
                JLabel label = new JLabel(imageIcon);
                panel.add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a scroll pane to hold the panel
        JScrollPane scrollPane = new JScrollPane(panel);

        // Add the scroll pane to the frame
        add(scrollPane);

        // Pack the frame to fit the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Check if a PDF file path is provided
        if (args.length == 0) {
            System.err.println("Usage: java PDFRender <PDF file path>");
            System.exit(1);
        }

        // Create an instance of the PDFRender class to display the PDF
        new PDFRender(args[0]);
    }
}