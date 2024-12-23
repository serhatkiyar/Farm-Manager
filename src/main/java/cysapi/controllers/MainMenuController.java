package cysapi.controllers;

import cysapi.controllers.animals.AnimalSearchController;
import cysapi.controllers.farm.AddBarnController;
import cysapi.controllers.farm.BarnViewController;
import cysapi.dataaccess.DataService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable{

    @FXML
    public Pane contentPane;
    @FXML
    private Button logoutButton;

    public Stage stage;

    @FXML
    ImageView profilePhoto;
    public Image profilePhotoImage = new Image(getClass().getResource("/images/logo.png").toExternalForm());

    @FXML
    Label username;

    String usernameText = "Serum Çakı";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            stage = (Stage) logoutButton.getScene().getWindow();
            try {
                mainMenuButtonClick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



    public FXMLLoader switchScene(String fxmlLocation) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
        Parent root = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(root);
        return loader;
    }

    @FXML
    public void searchAnimalButtonClick() throws IOException {
        FXMLLoader loader = switchScene("/fxml/animals/AnimalSearch.fxml");
        AnimalSearchController controller = loader.getController();
        stage.setTitle("Çiftlik Yönetim Sistemi - Hayvan Sorgu");
        controller.getMain(this);

        controller.updateStatus();
    }
    @FXML
    public void mainMenuButtonClick() throws IOException {
        FXMLLoader loader = switchScene("/fxml/MainView.fxml");
        MainViewController mainViewController = loader.getController();
        mainViewController.getMainController(this);
        stage.setTitle("Çiftlik Yönetim Sistemi - Anasayfa");
        username.setText(usernameText);
        profilePhoto.setImage(profilePhotoImage);
    }


    @FXML
    public void userSettingsButtonClick() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Çiftlik Yönetim Sistemi - Kullanıcı Ayarları");
        stage.setResizable(false);
        stage.centerOnScreen();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSettings.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/user-settings.png"));

        UserSettingsController userSettingsController = loader.getController();
        userSettingsController.getMainController(this);


    }
    @FXML
    public void addAnimalButtonClick() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Çiftlik Yönetim Sistemi - Hayvan Ekleme");
        stage.setResizable(false);
        stage.setWidth(600);
        stage.setHeight(700);
        stage.centerOnScreen();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/animals/AddAnimal.fxml"));
        Parent root = loader1.load();
        Scene addAnimalScene = new Scene(root);
        stage.setScene(addAnimalScene);
        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/add-animal.png"));
        stage.show();
    }
    @FXML
    public void removeAnimalButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/animals/RemoveAnimal.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Çiftlik Yönetim Sistemi - Hayvan Silme");
        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/remove-animal.png"));
        stage.show();
    }
    @FXML
    public void addBarnButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/farm/AddBarn.fxml"));
        Parent root = loader.load();
        AddBarnController controller = loader.getController();
        controller.getMainMenu(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Çiftlik Yönetim Sistemi - Ağıl Ekleme");
        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/add-barn.png"));
        stage.show();
    }
    @FXML
    public void animaltransferButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/animals/AnimalTransfer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Çiftlik Yönetim Sistemi - Hayvan Transferi");
        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/animal-transfer.png"));
        stage.show();
    }
    @FXML
    public void vaccinationButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/animals/AnimalVaccination.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Çiftlik Yönetim Sistemi - Hayvan Aşılama");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/vaccination.png"));
        stage.show();
    }
    @FXML
    public void logoutButtonClick() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış");
        alert.setHeaderText(null);
        alert.setContentText("Oturumunuzu sonlandırmak istediğinize emin misiniz?");


        Stage menuStage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Çiftlik Yönetim Sistemi - Anasayfa");
        stage.getIcons().add(new Image("file:src/main/resources/images/menu-bar-images/logo.png"));
        stage.setResizable(false);
        stage.centerOnScreen();

        menuStage.close();
        stage.show();
    }


}
