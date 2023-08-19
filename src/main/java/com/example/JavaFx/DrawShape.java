package com.example.JavaFx;



import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import static com.example.JavaFx.PaintViewController.graphicsContext;
import static com.example.JavaFx.PaintViewController.undoHistory;

public class DrawShape {
    public static Rectangle rectangle = new Rectangle();
    public static Circle circle = new Circle();
    public static TextField sizeField = new TextField();
    public static double size;


    public static double size(){

        if (sizeField.getText().isEmpty())
            size = 25;
        else
            size = Double.parseDouble(sizeField.getText());
        return size;
    }

    public static void drawRectangle(MouseEvent event) {
        double size = size();
        rectangle.setWidth(Math.abs((event.getX() - rectangle.getX())));
        rectangle.setHeight(Math.abs((event.getY() - rectangle.getY())));

        graphicsContext.fillRect(event.getX(), event.getY(), size, size);

        undoHistory.push(new Rectangle(event.getX(), event.getY(), size, size));
    }

    public static void drawCircle(MouseEvent event) {
        double size = size();
        double centerX = Math.min(event.getX(), circle.getCenterX());
        double centerY = Math.min(event.getY(), circle.getCenterY());
        circle.setRadius((Math.abs(event.getX() - circle.getCenterX()) + Math.abs(event.getY() - circle.getCenterY())) / 2);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);

        graphicsContext.fillOval(event.getX(), event.getY(), size, size);

        undoHistory.push(new Circle(event.getX(), event.getY(), size));
    }

}
