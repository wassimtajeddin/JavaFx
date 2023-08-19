package com.example.JavaFx;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import static com.example.JavaFx.PaintViewController.canvas;
import static com.example.JavaFx.PaintViewController.stage;

public class SaveManager {

    public static void saveToFile() {
        FileChooser saveFile = new FileChooser();
        saveFile.setTitle("Save File");
        saveFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = saveFile.showSaveDialog(stage);

        if (file != null) {
            saveCanvasToFile(file);
        }
    }

    public static void saveCanvasToFile(File file) {
        try {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
            System.out.println("Success: " + file);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error!");
        }
    }

}
