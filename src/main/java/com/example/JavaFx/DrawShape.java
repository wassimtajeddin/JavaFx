package com.example.JavaFx;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.example.JavaFx.PaintViewController.*;

public class DrawShape {

    public static void drawRectangle(MouseEvent event, TextField sizeField) {
         Rectangle rectangle = new Rectangle();


        double size = 25;
        if (!sizeField.getText().isEmpty())
            size = Double.parseDouble(sizeField.getText());
        rectangle.setWidth(Math.abs((event.getX() - rectangle.getX())));
        rectangle.setHeight(Math.abs((event.getY() - rectangle.getY())));

        graphicsContext.fillRect(event.getX(), event.getY(), size, size);

        undoHistory.push(new Rectangle(event.getX(), event.getY(), size, size));
    }

    public static void drawCircle(MouseEvent event , TextField sizeField) {
        Circle circle = new Circle();
        double size = 25;
        if (!sizeField.getText().isEmpty())
            size = Double.parseDouble(sizeField.getText());
        double centerX = Math.min(event.getX(), circle.getCenterX());
        double centerY = Math.min(event.getY(), circle.getCenterY());
        circle.setRadius((Math.abs(event.getX() - circle.getCenterX()) + Math.abs(event.getY() - circle.getCenterY())) / 2);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);

        graphicsContext.fillOval(event.getX(), event.getY(), size, size);

        undoHistory.push(new Circle(event.getX(), event.getY(), size));
    }



}
