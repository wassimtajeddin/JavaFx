package com.example.JavaFx;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import static com.example.JavaFx.DrawShape.drawCircle;
import static com.example.JavaFx.DrawShape.drawRectangle;
import static com.example.JavaFx.SaveManager.saveToFile;
import static com.example.JavaFx.UndoManager.undoLastAction;


public class PaintViewController {

    public static Canvas canvas;
    public Button circleButton;
    public Button rectangleButton;
    public Button saveButton;
    public Button undoButton;
    public static GraphicsContext graphicsContext;
    public static Stage stage;
    public ColorPicker colorPicker = new ColorPicker();
    public TextField sizeField = new TextField();


    public static Stack<Shape> undoHistory = new Stack<>();
    public static Stack<Shape> redoHistory = new Stack<>();


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();

    }



    public void onCanvasClicked() {



        graphicsContext.setFill(colorPicker.getValue());
        graphicsContext.setLineWidth(1);

        canvas.setOnMousePressed(e -> {
            if (rectangleButton.isFocused()) {
              drawRectangle(e);
            } else if (circleButton.isFocused()) {
                drawCircle(e);
            }
            clearRedoHistory();
            setLastUndoFill();
        });
    }


    private void clearRedoHistory() {
        redoHistory.clear();
    }

    private void setLastUndoFill() {
        Shape lastUndo = undoHistory.lastElement();
        lastUndo.setFill(graphicsContext.getFill());
    }



    public void onSaveAction() {
        saveButton.setOnAction(e -> saveToFile());
    }



    public void onUndoAction() {
        undoButton.setOnAction(e -> undoLastAction());
    }




}

