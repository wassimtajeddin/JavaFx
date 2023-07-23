package com.example.JavaFx;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
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


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
    graphicsContext = canvas.getGraphicsContext2D();

}

    public void onCanvasClicked(MouseEvent mouseEvent) {
        double size;
        graphicsContext.setFill(colorPicker.getValue());
        if (sizeField.getText().isEmpty())
            size = 25;
        else
            size = Double.parseDouble(sizeField.getText());
        graphicsContext.setFill(colorPicker.getValue());
        if (circleButton.isFocused()) {
            graphicsContext.fillOval(mouseEvent.getX(),mouseEvent.getY(),size,size);
        }

        else if (rectangleButton.isFocused()) {
            graphicsContext.fillRect(mouseEvent.getX(), mouseEvent.getY(), size, size);
        }

        else {
            graphicsContext.fillRect(mouseEvent.getX(), mouseEvent.getY(), size, size);
        }
    }


    public void onSaveAction(ActionEvent actionEvent) {
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
        Stack<Shape> undoHistory = new Stack<>();
        Stack<Shape> redoHistory = new Stack<>();
        Rectangle rectangle = new Rectangle();
        Circle circle = new Circle();
        undoHistory.push(new Circle(circle.getCenterX(), circle.getCenterY(), circle.getRadius()));
        undoHistory.push(new Rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()));
        undoButton.setOnAction(e->{
            if(!undoHistory.empty()){
                graphicsContext.clearRect(0, 0, 1750, 1500);
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
