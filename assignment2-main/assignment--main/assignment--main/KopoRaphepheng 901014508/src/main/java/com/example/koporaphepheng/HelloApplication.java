package com.example.koporaphepheng;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import java.io.File;

public class HelloApplication extends Application {

    private CanvasController canvasController;
    private MediaController mediaController;

    @Override
    public void start(Stage primaryStage) {
        canvasController = new CanvasController();
        mediaController = new MediaController(canvasController);

        // UI Controls
        Button stopButton = new Button("Stop");
        Button clearButton = new Button("Clear");
        Button saveButton = new Button("Save");
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button cancelButton = new Button("Cancel");
        Button addImageButton = new Button("Add Image");
        Button addVideoButton = new Button("Add Video");
        Button addAudioButton = new Button("Add Audio");
        Button addTextButton = new Button("Add Text");

        // Color Picker for drawing/text color
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(e -> canvasController.setDrawColor(colorPicker.getValue()));

        // TextField for adding text
        TextField textInput = new TextField();
        textInput.setPromptText("Enter text");

        // Event Handlers
        clearButton.setOnAction(e -> canvasController.clearCanvas());
        stopButton.setOnAction(e -> mediaController.stopMedia());
        playButton.setOnAction(e -> mediaController.playMedia());
        pauseButton.setOnAction(e -> mediaController.pauseMedia());
        cancelButton.setOnAction(e -> mediaController.cancelMedia());
        addImageButton.setOnAction(e -> mediaController.addImage(primaryStage));
        addVideoButton.setOnAction(e -> mediaController.addVideo(primaryStage));
        addAudioButton.setOnAction(e -> mediaController.addAudio(primaryStage));

        addTextButton.setOnAction(e -> {
            String text = textInput.getText();
            if (!text.isEmpty()) {
                canvasController.addTextToCanvas(text);
                textInput.clear();
            }
        });

        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"),
                    new FileChooser.ExtensionFilter("Media Files", "*.mp4", "*.mp3", "*.wav", "*.avi")
            );

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                if (file.getName().matches(".*\\.(png|jpg|jpeg)")) {
                    canvasController.saveCanvas(file);
                } else {
                    mediaController.saveMedia(file);
                }
            }
        });

        HBox controls = new HBox(10, colorPicker, textInput, addTextButton, stopButton, clearButton, saveButton, playButton, pauseButton, cancelButton, addImageButton, addVideoButton, addAudioButton);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        VBox mainLayout = new VBox(10, controls, canvasController.getCanvasScrollPane(), mediaController.getMediaStack());
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 1000, 750);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Interactive Digital Whiteboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
