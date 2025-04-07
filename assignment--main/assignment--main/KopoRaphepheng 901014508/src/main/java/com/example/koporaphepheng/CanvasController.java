package com.example.koporaphepheng;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelFormat;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class CanvasController {

    private Canvas canvas;
    private GraphicsContext gc;

    public CanvasController() {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        canvas.setStyle("-fx-background-color: white;");
        enableDrawing();
    }

    // Clears the canvas
    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    // Set the drawing color
    public void setDrawColor(Color color) {
        gc.setStroke(color);
        gc.setLineWidth(2);
    }

    // Return the canvas
    public Canvas getCanvas() {
        return canvas;
    }

    // Return the canvas inside a scrollable pane
    public ScrollPane getCanvasScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(canvas);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    // Return the graphics context to access drawing capabilities
    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    // Save the canvas drawing to a file (image).
    public void saveCanvas(File file) {
        if (file.getName().endsWith(".png")) {
            saveCanvasAsImage(file);
        } else {
            System.out.println("Unsupported file type.");
        }
    }

    // Method to save the canvas as an image (PNG format).
    private void saveCanvasAsImage(File file) {
        WritableImage snapshot = canvas.snapshot(null, null);  // Capture the canvas as an image

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "PNG", file);  // Save the snapshot as a PNG
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Enable drawing on the canvas
    public void enableDrawing() {
        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
    }
}
