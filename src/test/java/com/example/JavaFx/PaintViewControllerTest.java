package com.example.JavaFx;

import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaintViewControllerTest {
    private PaintViewController controller;
    private final JFXPanel panel = new JFXPanel();

    @BeforeEach
    void setUp() throws Exception {
        controller = new PaintViewController();

    }


    @Test
void theNumberWhichIsWrittenInTextFiledShouldReturnShapeSize() {
        controller.sizeField.setText("100");
        double expected = Double.parseDouble(controller.sizeField.getText());
        double actual = 100;

        assertEquals(expected,actual);

    }
    @Test
void theChosenColorShouldBeTheColorOfShapes(){
        controller.colorPicker.setValue(Color.BLACK);
        Color expected = controller.colorPicker.getValue();
        Color actual = Color.BLACK;
        assertEquals(expected,actual);

}




}
