package cysapi.services;

import javafx.scene.control.Alert;

public class AlertService {
    public static void showAlert(String title, String message)
    {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
