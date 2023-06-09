package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PdfServiceImpl implements PdfService {
    private final Logger logger;
    private final EventAggregator eventAggregator;

    private final float fontSize;
    private final PDFont font;

    public PdfServiceImpl(Logger logger, EventAggregator eventAggregator) {
        this.logger = logger;
        this.eventAggregator = eventAggregator;
        fontSize = 14;
        font = PDType1Font.HELVETICA;
    }

    public void generatePDFWithImageAndData(String imagePath, Tour tour, List<TourLog> tourLogs, String outputFilePath) {
        try {
            PDDocument document = new PDDocument();

            addTourInformationPage(document, tour);
            addImagePage(imagePath, document);

            if (tourLogs != null && tourLogs.size() > 0) {
                for (TourLog tourLog : tourLogs)
                    addTourLogPage(document, tourLog);
            } else {
                logger.warn("There are no logs or they are null to write to the pdf file");
            }

            createDirectoryIfNotExists(outputFilePath);
            document.save(outputFilePath + "/" + tour.getId() + ".pdf");
            document.close();

            logger.info("PDF generated successfully at: " + outputFilePath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn("Could not create pdf, tour is null");
        }
    }

    private PDImageXObject createPDImageXObjectFromFile(String imagePath, PDDocument document) {
        try {
            return PDImageXObject.createFromFile(imagePath, document);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private void addImagePage(String imagePath, PDDocument document) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDImageXObject image = createPDImageXObjectFromFile(imagePath, document);

        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();
        float desiredWidth = 200;
        float scaleFactor = desiredWidth / imageWidth;
        float scaledHeight = imageHeight * scaleFactor;
        float x = (page.getMediaBox().getWidth() - desiredWidth) / 2;
        float y = (page.getMediaBox().getHeight() - scaledHeight) / 2;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.drawImage(image, x, y, desiredWidth, scaledHeight);

            if (image != null) {
                contentStream.drawImage(image, x, y, desiredWidth, scaledHeight);
            } else {
                logger.warn("Could not draw image to pdf");
            }
        }
    }

    private void addTourInformationPage(PDDocument document, Tour tour) throws IOException, IllegalArgumentException {
        if (tour != null) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                setFontAttributes(contentStream);

                String name = "Tour Name: " + tour.getName();
                showTextWithWrapping(contentStream, name);
                contentStream.newLine();
                String description = "Description: " + tour.getDescription();
                showTextWithWrapping(contentStream, description);
                contentStream.newLine();
                String from = "From: " + tour.getFrom();
                showTextWithWrapping(contentStream, from);
                contentStream.newLine();
                String to = "To: " + tour.getTo();
                showTextWithWrapping(contentStream, to);

                contentStream.endText();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void addTourLogPage(PDDocument document, TourLog tourLog) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            setFontAttributes(contentStream);

            showTextWithWrapping(contentStream, "Log Date/Time: " + tourLog.getLogDateTime().format(formatter));
            contentStream.newLine();
            showTextWithWrapping(contentStream, "Comment: " + tourLog.getComment());
            contentStream.newLine();
            showTextWithWrapping(contentStream, "Difficulty: " + tourLog.getDifficulty());
            contentStream.newLine();
            showTextWithWrapping(contentStream,"Total Time: " + tourLog.getTotalTime());
            contentStream.newLine();
            showTextWithWrapping(contentStream,"Rating: " + tourLog.getRating());

            contentStream.endText();
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Directory created: " + directory.getAbsolutePath());
            } else {
                logger.error("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }

    private void showTextWithWrapping(PDPageContentStream contentStream, String text) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        String[] words = text.split("\\s+");
        float currentWidth = 0;

        for (String word : words) {
            float wordWidth = font.getStringWidth(word) / 1000 * fontSize;

            if (currentWidth + wordWidth <= 500) {
                currentLine.append(word).append(" ");
                currentWidth += wordWidth + font.getSpaceWidth() / 1000 * fontSize;
            } else {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + " ");
                currentWidth = wordWidth + font.getSpaceWidth() / 1000 * fontSize;
            }
        }

        lines.add(currentLine.toString().trim());

        for (String line : lines) {
            contentStream.showText(line);
            contentStream.newLine();
        }
    }

    private void setFontAttributes(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(50, 750);
        contentStream.setLeading(25f);
    }
}