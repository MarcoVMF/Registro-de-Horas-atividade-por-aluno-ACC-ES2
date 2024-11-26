package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFReader {
    public BufferedImage readPDF(String filePath) {
        BufferedImage image = null;
        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            image = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
