package com.example.JavaFx;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
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
    public ColorPicker colorPicker;
    public TextField sizeField;
    Stack<Shape> undoHistory = new Stack<>();
    Stack<Shape> redoHistory = new Stack<>();


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();

    }

    public void onCanvasClicked() {
        double size;
        graphicsContext.setFill(colorPicker.getValue());
        if (sizeField.getText().isEmpty())
            size = 25;
        else
            size = Double.parseDouble(sizeField.getText());
        graphicsContext.setLineWidth(1);


        Rectangle rectangle = new Rectangle();
        Circle circle = new Circle();



        canvas.setOnMousePressed(e->{

            if(rectangleButton.isFocused()) {
                rectangle.setWidth(Math.abs((e.getX() - rectangle.getX())));
                rectangle.setHeight(Math.abs((e.getY() - rectangle.getY())));


                graphicsContext.fillRect(e.getX(), e.getY(), size,size);

                undoHistory.push(new Rectangle(e.getX(), e.getY(),size, size));

            }
            else if(circleButton.isFocused()) {
                circle.setRadius((Math.abs(e.getX() - circle.getCenterX()) + Math.abs(e.getY() - circle.getCenterY())) / 2);

                if(circle.getCenterX() > e.getX()) {
                    circle.setCenterX(e.getX());
                }
                if(circle.getCenterY() > e.getY()) {
                    circle.setCenterY(e.getY());
                }

                graphicsContext.fillOval(e.getX(), e.getY(), size, size);

                undoHistory.push(new Circle(e.getX(), e.getY(), size));
            }
            redoHistory.clear();
            Shape lastUndo = undoHistory.lastElement();
            lastUndo.setFill(graphicsContext.getFill());

        });

        colorPicker.setOnAction(e->{
            graphicsContext.setFill(colorPicker.getValue());
        });}



    public void onSaveAction() {
        saveButton.setOnAction((e) -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");
            saveFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));

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

    public void onUndoAction() {

        undoButton.setOnAction(e->{
            if(!undoHistory.empty()){
                graphicsContext.clearRect(0, 0, 1080, 790);
                Shape removedShape = undoHistory.lastElement();

                if(removedShape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) removedShape;
                    tempRect.setFill(graphicsContext.getFill());
                    tempRect.setStroke(graphicsContext.getStroke());
                    tempRect.setStrokeWidth(graphicsContext.getLineWidth());
                    redoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                }
                else if(removedShape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) removedShape;
                    tempCirc.setStrokeWidth(graphicsContext.getLineWidth());
                    tempCirc.setFill(graphicsContext.getFill());
                    tempCirc.setStroke(graphicsContext.getStroke());
                    redoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                }
                Shape lastRedo = redoHistory.lastElement();
                lastRedo.setFill(removedShape.getFill());
                lastRedo.setStroke(removedShape.getStroke());
                lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
                undoHistory.pop();

                for(int i=0; i < undoHistory.size(); i++) {
                    Shape shape = undoHistory.elementAt(i);

                    if(shape.getClass() == Rectangle.class) {
                        Rectangle temp = (Rectangle) shape;
                        graphicsContext.setLineWidth(temp.getStrokeWidth());
                        graphicsContext.setStroke(temp.getStroke());
                        graphicsContext.setFill(temp.getFill());
                        graphicsContext.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                        graphicsContext.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                    }
                    else if(shape.getClass() == Circle.class) {
                        Circle temp = (Circle) shape;
                        graphicsContext.setLineWidth(temp.getStrokeWidth());
                        graphicsContext.setStroke(temp.getStroke());
                        graphicsContext.setFill(temp.getFill());
                        graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                        graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                    }

                }
            } else {
                System.out.println("there is no action to undo");
            }
        });






    }
}
