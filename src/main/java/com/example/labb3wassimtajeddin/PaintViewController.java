package com.example.labb3wassimtajeddin;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;


public class PaintViewController {

    public Canvas canvas;
    public Button circleButton;
    public Button rectangleButton;
    public Button saveButton;
    public Button undoButton;
    public GraphicsContext graphicsContext;
    public Stage stage;
    public ColorPicker colorPicker;
    public TextField sizeField;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
    graphicsContext = canvas.getGraphicsContext2D();

}

    public void onCanvasClicked(MouseEvent mouseEvent) {
        double size;
        if (sizeField.getText().isEmpty())
            size = 100;
        else
            size = Double.parseDouble(sizeField.getText());
        graphicsContext.setFill(colorPicker.getValue());
        if (circleButton.isFocused()) {
            graphicsContext.fillOval(mouseEvent.getX(),mouseEvent.getY(),size,size);
        }

        else if (rectangleButton.isFocused()) {
            graphicsContext.fillRect(mouseEvent.getX(), mouseEvent.getY(), size, size);
        }


    }


    public void onSaveAction(ActionEvent actionEvent) {
        saveButton.setOnAction((e) -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");
            saveFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.svg"));

            File file = saveFile.showSaveDialog(stage);
            System.out.println("is file null ? " + file);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error!");
                }
            }
        });
    }

        //        SVGSaver svgSaver = new SVGSaver();
//        svgSaver.start(stage);

//
//        FileChooser fileChooser= new FileChooser();
//        fileChooser.setInitialFileName("paint.svg");
//        fileChooser.setTitle("Save as SVG");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG File","*.svg"));
//    File file = fileChooser.showSaveDialog(stage);
//  if (file!=null){}


    public void onUndoAction(ActionEvent actionEvent) {
    }
}
