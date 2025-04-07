package com.example.koporaphepheng;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import java.io.File;

public class HelloApplication extends Application {

    private CanvasController canvasController;
    private MediaController mediaController;

    @Override
    public void start(Stage primaryStage) {
        // Initialize controllers
        canvasController = new CanvasController();
        mediaController = new MediaController(canvasController);

        // Buttons
        Button stopButton = new Button("Stop");
        Button clearButton = new Button("Clear");
        Button saveButton = new Button("Save");
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button cancelButton = new Button("Cancel");
        Button addImageButton = new Button("Add Image");
        Button addVideoButton = new Button("Add Video");
        Button addAudioButton = new Button("Add Audio");

        // Color Picker for drawing
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(e -> canvasController.setDrawColor(colorPicker.getValue()));

        // Action Handlers
        clearButton.setOnAction(e -> canvasController.clearCanvas());
        stopButton.setOnAction(e -> mediaController.stopMedia());
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            // Adding filters for image files and media files separately
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
            FileChooser.ExtensionFilter mediaFilter = new FileChooser.ExtensionFilter("Media Files", "*.mp4", "*.mp3", "*.wav", "*.avi");

            // Apply both filters
            fileChooser.getExtensionFilters().add(imageFilter);
            fileChooser.getExtensionFilters().add(mediaFilter);

            // Show save dialog
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")) {
                    // If it's an image, call saveCanvas method
                    canvasController.saveCanvas(file);
                } else if (file.getName().endsWith(".mp4") || file.getName().endsWith(".mp3") || file.getName().endsWith(".wav") || file.getName().endsWith(".avi")) {
                    // If it's a media file (video/audio), call saveMedia method
                    mediaController.saveMedia(file);
                }
            }
        });
        playButton.setOnAction(e -> mediaController.playMedia());
        pauseButton.setOnAction(e -> mediaController.pauseMedia());
        cancelButton.setOnAction(e -> mediaController.cancelMedia());
        addImageButton.setOnAction(e -> mediaController.addImage(primaryStage));
        addVideoButton.setOnAction(e -> mediaController.addVideo(primaryStage));
        addAudioButton.setOnAction(e -> mediaController.addAudio(primaryStage));

        // Controls Box
        HBox controls = new HBox(10, colorPicker, stopButton, clearButton, saveButton, playButton, pauseButton, cancelButton, addImageButton, addVideoButton, addAudioButton);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        VBox mainLayout = new VBox(10, controls, canvasController.getCanvasScrollPane(), mediaController.getMediaStack());
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(10));

        // Load and apply the CSS file
        Scene scene = new Scene(mainLayout, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Interactive Digital Whiteboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
