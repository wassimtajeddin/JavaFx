package com.example.labb3wassimtajeddin;

import com.example.model.PaintModel;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Field;

public class PaintViewController {

    PaintModel paintModel = new PaintModel();
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
        double size = Double.parseDouble(sizeField.getText());
        graphicsContext.setFill(colorPicker.getValue());
        graphicsContext.fillOval(mouseEvent.getSceneX(),mouseEvent.getSceneY(),size,size);
       // graphicsContext.fillRect(mouseEvent.getSceneX(), mouseEvent.getY(), size,size);
    }

    public void onCircleAction(ActionEvent actionEvent) {

    }

    public void onRectangleAction(ActionEvent actionEvent) {

    }

    public void onSaveAction(ActionEvent actionEvent) {
        FileChooser fileChooser= new FileChooser();
        fileChooser.setInitialFileName("BildensBild");
        fileChooser.setTitle("Save as");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG","*.jpg"));
    File file = fileChooser.showOpenDialog(stage);
//   if (file!=null)
    }

    public void onUndoAction(ActionEvent actionEvent) {
    }

    public void onColorAction(ActionEvent actionEvent) {

    }

    public void onSizeAction(ActionEvent actionEvent) {
        }
}
