package com.example.koporaphepheng;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.File;

public class MediaController {

    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private StackPane mediaStack;
    private CanvasController canvasController;

    public MediaController(CanvasController canvasController) {
        this.canvasController = canvasController;
        mediaView = new MediaView();
        mediaStack = new StackPane();
        mediaStack.getChildren().add(mediaView);
        mediaStack.setPrefSize(600, 400);
        mediaStack.setStyle("-fx-background-color: #dcdcdc; -fx-border-radius: 10;");
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void playMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pauseMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void cancelMedia() {
        mediaStack.getChildren().clear();
        mediaStack.getChildren().add(mediaView);
    }

    public void addImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            canvasController.clearCanvas();
            canvasController.getGraphicsContext().drawImage(image, 0, 0, 800, 600);
        }
    }

    public void addVideo(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaStack.getChildren().clear();
            mediaStack.getChildren().add(mediaView);
            mediaPlayer.setAutoPlay(true);
            mediaView.setFitWidth(800);
            mediaView.setFitHeight(600);
            mediaView.setPreserveRatio(true);
        }
    }

    public void addAudio(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.m4a"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaStack.getChildren().clear();
            mediaStack.getChildren().add(new Label("Audio is playing..."));
        }
    }

    // Method to handle saving media files (image, audio, video).
    public void saveMedia(File file) {
        String fileName = file.getName().toLowerCase();

        // Save as image
        if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            // Save image by calling the CanvasController's saveCanvas method
            canvasController.saveCanvas(file);
        }
        // Save as audio or video (just copy for now)
        else if (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".m4a")) {
            // Audio saving logic (save the file as is)
            System.out.println("Audio file saved: " + file.getAbsolutePath());
        } else if (fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mov")) {
            // Video saving logic (save the file as is)
            System.out.println("Video file saved: " + file.getAbsolutePath());
        }
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public StackPane getMediaStack() {
        return mediaStack;
    }
}
