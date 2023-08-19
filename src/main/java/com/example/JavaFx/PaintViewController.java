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


public class PaintViewController {

    public Canvas canvas;
    public Button circleButton;
    public Button rectangleButton;
    public Button saveButton;
    public Button undoButton;
    public GraphicsContext graphicsContext;
    public Stage stage;
    public ColorPicker colorPicker = new ColorPicker();
    public TextField sizeField = new TextField();
    double size;
    Stack<Shape> undoHistory = new Stack<>();
    Stack<Shape> redoHistory = new Stack<>();


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();

    }
    public double size(){

        if (sizeField.getText().isEmpty())
            size = 25;
        else
            size = Double.parseDouble(sizeField.getText());
        return size;
    }
    Rectangle rectangle = new Rectangle();
    Circle circle = new Circle();

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

    private void drawRectangle(MouseEvent event) {
        double size = size();
        rectangle.setWidth(Math.abs((event.getX() - rectangle.getX())));
        rectangle.setHeight(Math.abs((event.getY() - rectangle.getY())));

        graphicsContext.fillRect(event.getX(), event.getY(), size, size);

        undoHistory.push(new Rectangle(event.getX(), event.getY(), size, size));
    }

    private void drawCircle(MouseEvent event) {
        double size = size();
        double centerX = Math.min(event.getX(), circle.getCenterX());
        double centerY = Math.min(event.getY(), circle.getCenterY());
        circle.setRadius((Math.abs(event.getX() - circle.getCenterX()) + Math.abs(event.getY() - circle.getCenterY())) / 2);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);

        graphicsContext.fillOval(event.getX(), event.getY(), size, size);

        undoHistory.push(new Circle(event.getX(), event.getY(), size));
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

    private void saveToFile() {
        FileChooser saveFile = new FileChooser();
        saveFile.setTitle("Save File");
        saveFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = saveFile.showSaveDialog(stage);

        if (file != null) {
            saveCanvasToFile(file);
        }
    }

    private void saveCanvasToFile(File file) {
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


    public void onUndoAction() {
        undoButton.setOnAction(e -> undoLastAction());
    }

    private void undoLastAction() {
        if (!undoHistory.empty()) {
            graphicsContext.clearRect(0, 0, 1750, 1500);
            Shape removedShape = undoHistory.lastElement();

            if (removedShape.getClass() == Rectangle.class) {
                undoRectangleAction((Rectangle) removedShape);
            } else if (removedShape.getClass() == Circle.class) {
                undoCircleAction((Circle) removedShape);
            }
            Shape lastRedo = redoHistory.lastElement();
            lastRedo.setFill(removedShape.getFill());
            lastRedo.setStroke(removedShape.getStroke());
            lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
            undoHistory.pop();
            undoOneShapeForEachClick();
        } else {
            System.out.println("There is no action to undo");
        }
    }
    private void undoOneShapeForEachClick(){
        for(int i=0; i < undoHistory.size(); i++) {
            Shape shape = undoHistory.elementAt(i);

            if(shape.getClass() == Rectangle.class) {
                Rectangle temp = (Rectangle) shape;
                graphicsContext.setFill(temp.getFill());
                graphicsContext.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
            }
            else if(shape.getClass() == Circle.class) {
                Circle temp = (Circle) shape;
                graphicsContext.setFill(temp.getFill());
                graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
            }

        }

    }

    private void undoRectangleAction(Rectangle removedRectangle) {
        removedRectangle.setFill(graphicsContext.getFill());
        removedRectangle.setStroke(graphicsContext.getStroke());
        removedRectangle.setStrokeWidth(graphicsContext.getLineWidth());
        redoHistory.push(new Rectangle(removedRectangle.getX(), removedRectangle.getY(), removedRectangle.getWidth(), removedRectangle.getHeight()));
    }

    private void undoCircleAction(Circle removedCircle) {
        removedCircle.setStrokeWidth(graphicsContext.getLineWidth());
        removedCircle.setFill(graphicsContext.getFill());
        removedCircle.setStroke(graphicsContext.getStroke());
        redoHistory.push(new Circle(removedCircle.getCenterX(), removedCircle.getCenterY(), removedCircle.getRadius()));

    }


}

