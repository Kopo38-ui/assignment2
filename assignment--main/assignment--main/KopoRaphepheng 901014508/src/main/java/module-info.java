module com.example.koporaphepheng {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    opens com.example.koporaphepheng to javafx.fxml;
    exports com.example.koporaphepheng;
}