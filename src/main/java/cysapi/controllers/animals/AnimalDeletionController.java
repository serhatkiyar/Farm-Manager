package cysapi.controllers.animals;

import cysapi.models.animals.Animal;
import cysapi.models.farm.Farm;
import cysapi.services.AlertService;
import cysapi.services.BarnService;
import cysapi.services.SearchService;
import cysapi.services.ValidationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AnimalDeletionController implements Initializable {
    @FXML
    TextField earringNumberTextField;
    @FXML
    Button removeButton;
    @FXML
    Button cancelButton;
    @FXML
    private VBox animalCard;

    private AnimalViewController animalViewController;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/animals/AnimalView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        animalCard.getChildren().add(root);
        animalViewController = loader.getController();


        earringNumberTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.length() > 10)
            {
                earringNumberTextField.setText(oldValue);
            } else {
                earringNumberTextField.setText(newValue.toUpperCase());
            }
        });
    }


    @FXML
    public void findAnimal() {
        if (!ValidationService.validateEarringNumber(earringNumberTextField.getText())) {
            earringNumberTextField.clear();
            return;
        }
        String earringNumber = earringNumberTextField.getText();
        List<Animal> animalList = SearchService.searchWithEarringNumber(Farm.getInstance().getAllAnimals(), earringNumber);
        Animal animal = animalList.getFirst();
        animalViewController.loadData(animal);
        System.out.println("Toplam Hayvan Sayısı: " + Farm.getInstance().getAllAnimals().size());
    }

    @FXML
    public void removeButtonClick()
    {
        String earringNumber = earringNumberTextField.getText().trim();

        if (!ValidationService.validateEarringNumber(earringNumber))
        {
            earringNumberTextField.clear();
            return;
        }
        Farm farm = Farm.getInstance();
        List<Animal> foundAnimals = SearchService.searchWithEarringNumber(farm.getAllAnimals(), earringNumber);

        if (foundAnimals.isEmpty())
        {
            AlertService.showAlert("Hata", "Girilen küpe numarasına sahip bir hayvan bulunamadı.");
            return;
        }

        Animal animal = foundAnimals.get(0);
        BarnService barnService = new BarnService();

        boolean removed = barnService.removeAnimalFromBarn(animal.getBarnLocation(), earringNumber);

        if (removed)
        {
            AlertService.showAlert("Başarılı", "Hayvan başarıyla silindi!");
            earringNumberTextField.clear(); // TextField'i temizle
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        else
        {
            AlertService.showAlert("Hata", "Hayvan silinirken bir hata oluştu.");
        }
    }

    @FXML
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}