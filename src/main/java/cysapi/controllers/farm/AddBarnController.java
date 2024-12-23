package cysapi.controllers.farm;

import cysapi.controllers.MainMenuController;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import cysapi.services.AlertService;
import cysapi.services.FarmService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddBarnController {
    @FXML
    TextField barnName;
    @FXML
    TextField barnCapacity;
    @FXML
    Button addButton;
    @FXML
    Button cancelButton;

    MainMenuController mainMenuController;

    public void getMainMenu(MainMenuController controller) {
        mainMenuController = controller;
    }

    @FXML
    public void addButtonClick() throws IOException {
        String barnNameInput = barnName.getText();
        String barnCapacityInput = barnCapacity.getText();

        if (barnNameInput == null || barnNameInput.trim().isEmpty())
        {
            AlertService.showAlert("Hata", "Lütfen bir ahır adı giriniz!");
            return;
        }

        int barnCapacity;
        try
        {
            barnCapacity = Integer.parseInt(barnCapacityInput);
            if (barnCapacity <= 0)
            {
                AlertService.showAlert("Hata", "Ahır kapasitesi sıfırdan büyük olmalıdır!");
                return;
            }
        }
        catch (NumberFormatException e)
        {
            AlertService.showAlert("Hata", "Lütfen geçerli bir kapasite değeri giriniz!");
            return;
        }

        Barn barn = new Barn(barnNameInput, barnCapacity);
        FarmService.addBarnToFarm(Farm.getInstance(), barn);
        mainMenuController.mainMenuButtonClick();
        AlertService.showAlert("Başarılı", "Ahır başarıyla eklendi!");
        cancelButtonClick();
    }


    @FXML
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
