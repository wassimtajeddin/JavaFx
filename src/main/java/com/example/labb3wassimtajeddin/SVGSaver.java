package com.example.labb3wassimtajeddin;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SVGSaver {

    public void start(Stage primaryStage) {
        // Create a JavaFX scene with some content
        Group root = new Group();
        Scene scene = new Scene(root, 50, 50);
        scene.setFill(Color.WHITE);

        // ... Add your JavaFX content to the scene ...

        // Create a WritableImage
        WritableImage writableImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());

        // Render the scene onto the WritableImage
        scene.snapshot(writableImage);

        // Create an SVG string from the WritableImage
        String svgContent = createSvgContent(writableImage);

        // Use FileChooser to select the file name for saving
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as SVG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG Files", "*.svg"));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Save the SVG string to the selected file
            saveSvgToFile(svgContent, file);
            System.out.println("SVG file saved successfully.");
        }
    }

    private String createSvgContent(WritableImage image) {
        // Generate the SVG content from the WritableImage
        // Replace this code with your own implementation to convert the image to SVG format
        // Here, we are simply creating a dummy SVG string for demonstration purposes
        return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" +
                image.getWidth() + "\" height=\"" +
                image.getHeight() + "\">" +
                "<rect width=\"" + image.getWidth() + "\" height=\"" + image.getHeight() + "\" fill=\"red\"/>" +
                "</svg>";
    }

    private void saveSvgToFile(String svgContent, File file) {
        // Save the SVG content to the specified file
        try (Writer writer = new FileWriter(file)) {
            writer.write(svgContent);
        } catch (IOException e) {
            System.err.println("Failed to save SVG file: " + e.getMessage());
        }
    }

}

