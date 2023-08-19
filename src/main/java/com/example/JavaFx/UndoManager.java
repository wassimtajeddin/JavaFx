package com.example.JavaFx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static com.example.JavaFx.PaintViewController.*;

public class UndoManager {
    public static void undoLastAction() {
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
    public static void undoOneShapeForEachClick(){
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

    public static void undoRectangleAction(Rectangle removedRectangle) {
        removedRectangle.setFill(graphicsContext.getFill());
        removedRectangle.setStroke(graphicsContext.getStroke());
        removedRectangle.setStrokeWidth(graphicsContext.getLineWidth());
        redoHistory.push(new Rectangle(removedRectangle.getX(), removedRectangle.getY(), removedRectangle.getWidth(), removedRectangle.getHeight()));
    }

    public static void undoCircleAction(Circle removedCircle) {
        removedCircle.setStrokeWidth(graphicsContext.getLineWidth());
        removedCircle.setFill(graphicsContext.getFill());
        removedCircle.setStroke(graphicsContext.getStroke());
        redoHistory.push(new Circle(removedCircle.getCenterX(), removedCircle.getCenterY(), removedCircle.getRadius()));

    }
}
