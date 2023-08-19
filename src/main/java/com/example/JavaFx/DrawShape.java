package com.example.JavaFx;



import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.example.JavaFx.PaintViewController.*;

public class DrawShape {

    public static double size(){
        double size;

        if (sizeField.getText().isEmpty())
            size = 25;
        else
            size = Double.parseDouble(sizeField.getText());
        return size;
    }


    public static void drawRectangle(MouseEvent event) {
         Rectangle rectangle = new Rectangle();

        double size = size();
        rectangle.setWidth(Math.abs((event.getX() - rectangle.getX())));
        rectangle.setHeight(Math.abs((event.getY() - rectangle.getY())));

        graphicsContext.fillRect(event.getX(), event.getY(), size, size);

        undoHistory.push(new Rectangle(event.getX(), event.getY(), size, size));
    }

    public static void drawCircle(MouseEvent event) {
        Circle circle = new Circle();
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
