package cysapi.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class cysmApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Çiftlik Yönetim Sistemi - Login");
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));

        FXMLLoader fxmlLoader = new FXMLLoader(cysmApp.class.getResource("/fxml/LoginMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}