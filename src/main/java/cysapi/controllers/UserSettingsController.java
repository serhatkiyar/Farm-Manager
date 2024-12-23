package cysapi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSettingsController implements Initializable {
    @FXML
    ImageView profilePhotoImageView;
    @FXML
    TextField directoryPathTextField;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    @FXML
    ImageView profilePhoto;
    @FXML
    TextField nameTextField;

    private Image profilePhotoImage;

    MainMenuController mainMenuController;

    public void getMainController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
        profilePhoto.setImage(mainMenuController.profilePhotoImage);
        nameTextField.setText(mainMenuController.usernameText);
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void selectDirectory() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Resim Yükle");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.jpg", "*.png")
        );

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            directoryPathTextField.setText(selectedFile.getAbsolutePath());
            profilePhotoImage = new Image(selectedFile.toURI().toString());
            profilePhoto.setImage(profilePhotoImage);
        }
    }
    @FXML
    public void saveButtonClick() throws IOException {
        if (profilePhotoImage != null) {
            mainMenuController.profilePhotoImage = profilePhotoImage;
        }

        mainMenuController.usernameText = nameTextField.getText();
        mainMenuController.username.setText(nameTextField.getText());
        mainMenuController.mainMenuButtonClick();
        cancelButtonClick();
    }
    @FXML
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
