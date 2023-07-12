package com.example.labb3wassimtajeddin;

import com.example.model.PaintModel;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        double size;
        if (sizeField.getText().isEmpty())
            size = 100;
        else
            size = Double.parseDouble(sizeField.getText());
        graphicsContext.setFill(colorPicker.getValue());
        if (circleButton.isFocused()) {
            graphicsContext.fillOval(mouseEvent.getSceneX(),mouseEvent.getSceneY(),size,size);
        }

        else if (rectangleButton.isFocused()) {
            graphicsContext.fillRect(mouseEvent.getSceneX(), mouseEvent.getY(), size, size);
        }


    }


    public void onSaveAction(ActionEvent actionEvent) {
        SVGSaver svgSaver = new SVGSaver();
        svgSaver.start(stage);

//
//        FileChooser fileChooser= new FileChooser();
//        fileChooser.setInitialFileName("paint.svg");
//        fileChooser.setTitle("Save as SVG");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG File","*.svg"));
//    File file = fileChooser.showSaveDialog(stage);
//  if (file!=null){}
    }

    public void onUndoAction(ActionEvent actionEvent) {
    }
}
