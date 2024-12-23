package cysapi.controllers;

import cysapi.dataaccess.DataService;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import cysapi.services.AlertService;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class LoginMenuController {
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    public void initialize() {
        // Kullanıcı adı için karakter sınırı
        addCharacterLimit(userNameInput, 20); // Maksimum 20 karakter

        // Şifre için karakter sınırı
        addCharacterLimit(passwordInput, 15); // Maksimum 15 karakter
    }

    private void addCharacterLimit(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                textField.setText(oldValue); // Eski değeri geri yükler
            }
        });
    }

    @FXML
    public void loginClick() throws IOException{
//        if ((userNameInput.getText().equals("admin")) || true) {
//            if ((passwordInput.getText().equals("Ruhi1234")) || true) {
        if ((userNameInput.getText().equals("admin")) || true) {
            if ((passwordInput.getText().equals("Ruhi1234")) || true) {
                Stage stage = (Stage) userNameInput.getScene().getWindow();
                Stage mainStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
                Parent root = loader.load();
                mainStage.setScene(new Scene(root));

                mainStage.setTitle("Çiftlik Yönetim Sistemi - Anasayfa");
                mainStage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                mainStage.setWidth(screenBounds.getWidth());
                mainStage.setHeight(screenBounds.getHeight());
                System.out.println(screenBounds.getWidth() + "x" + screenBounds.getHeight());

                Farm farm = Farm.getInstance();

                DataService.readAllBarnFiles();

                ArrayList liste = farm.getBarns();
                Collections.reverse(liste);

                mainStage.centerOnScreen();
                mainStage.setMaximized(true);
                mainStage.setResizable(true);
                stage.close();
                mainStage.show();



            }else
            {
                AlertService.showAlert("Giriş Hatası","Şifre Yanlış!");
            }

        } else{
            AlertService.showAlert("Giriş Hatası", "Kullanıcı bulunumadı!");

        }
    }



}