package cysapi.controllers.animals;

import cysapi.models.animals.Animal;
import cysapi.models.farm.Farm;
import cysapi.services.SearchService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnimalVaccinationController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button vaccinationButton;
    @FXML
    private TextField earringNumber;
    @FXML
    private ChoiceBox<String> vaccinationChoiceBox;
    @FXML
    private VBox animalCard;

    @FXML
    public void vaccinationButtonClick()
    {
        String earringNumberInput = earringNumber.getText();
        if (earringNumberInput == null || earringNumberInput.isEmpty())
        {
            showAlert("Hata","Küpe numarası girmeniz gerekmektedir!");
        }
        String vaccination = vaccinationButton.getText();
        if (vaccination == null || vaccination.isEmpty())
        {
            showAlert("Hata","Aşı seçimi yapmanız gerekmektedir!");
        }
        Farm farm = Farm.getInstance();
        Animal animal = SearchService.searchWithEarringNumber(farm.getAllAnimals(),earringNumberInput).getFirst();
        animal.addVaccination(vaccination);

    }

    @FXML
    public void cancelButtonClick()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message)
    {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        vaccinationChoiceBox.getItems().addAll("Şap", "Bruselloz", "Enterotoksemi", "Şarbon", "Tetanoz");
        vaccinationChoiceBox.setStyle("-fx-font-size: 16");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/animals/AnimalView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        animalCard.getChildren().add(root);

        earringNumber.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.length() > 10)
            {
                earringNumber.setText(oldValue);
            } else {
                earringNumber.setText(newValue.toUpperCase());
            }
        });
    }
}
