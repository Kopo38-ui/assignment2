package com.example.koporaphepheng;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class CanvasController {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private double lastX, lastY;

    public CanvasController() {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        canvas.setStyle("-fx-background-color: white;");
        enableDrawing();
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void setDrawColor(Color color) {
        gc.setStroke(color);
        gc.setFill(color); // Used for text
        gc.setLineWidth(2);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ScrollPane getCanvasScrollPane() {
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public void saveCanvas(File file) {
        if (file.getName().endsWith(".png")) {
            WritableImage snapshot = canvas.snapshot(null, null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "PNG", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unsupported file type.");
        }
    }

    public void enableDrawing() {
        canvas.setOnMousePressed(e -> {
            lastX = e.getX();
            lastY = e.getY();
            gc.beginPath();
            gc.moveTo(lastX, lastY);
            gc.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            gc.lineTo(x, y);
            gc.stroke();
        });

        canvas.setOnMouseReleased(e -> {
            lastX = e.getX();
            lastY = e.getY();
        });
    }

    public void addTextToCanvas(String text) {
        gc.fillText(text, lastX, lastY);
    }
}
