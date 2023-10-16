package com.example.JavaFx;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.util.Stack;
import static com.example.JavaFx.DrawShape.drawCircle;
import static com.example.JavaFx.DrawShape.drawRectangle;
import static com.example.JavaFx.SaveManager.saveToFile;
import static com.example.JavaFx.UndoManager.undoLastAction;


public class PaintViewController {

    public Canvas canvas;
    public Button circleButton;
    public Button rectangleButton;
    public Button saveButton;
    public Button undoButton;
    public static GraphicsContext graphicsContext;
    public static Stage stage;
    public ColorPicker colorPicker = new ColorPicker();
    public static Stack<Shape> undoHistory = new Stack<>();
    public static Stack<Shape> redoHistory = new Stack<>();
    public TextField sizeField = new TextField();

    public void setStage(Stage stage) {
        PaintViewController.stage = stage;
    }

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();

    }

    public void onCanvasClicked() {
        graphicsContext.setFill(colorPicker.getValue());
        graphicsContext.setLineWidth(1);

        canvas.setOnMousePressed(e -> {
            if (rectangleButton.isFocused()) {
                drawRectangle(e,sizeField);
            } else if (circleButton.isFocused()) {
                drawCircle(e,sizeField);
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
        saveButton.setOnAction(e -> saveToFile(canvas));
    }

    public void onUndoAction() {
        undoButton.setOnAction(e -> undoLastAction());
    }
}

