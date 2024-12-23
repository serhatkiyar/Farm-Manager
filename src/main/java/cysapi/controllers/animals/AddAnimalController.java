package cysapi.controllers.animals;

import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import cysapi.services.AlertService;
import cysapi.services.BarnService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import cysapi.models.animals.Animal;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AddAnimalController implements Initializable {
    @FXML
    ChoiceBox<String> genusChoiceBox;
    @FXML
    RadioButton erkekRadioButton;
    @FXML
    RadioButton disiRadioButton;
    @FXML
    TextField weightTextField;
    @FXML
    ChoiceBox<String> healthStatusChoiceBox;
    @FXML
    ChoiceBox<String> barnLocationChoiceBox;
    @FXML
    Button cancelButton;
    @FXML
    Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genusChoiceBox.getItems().addAll("İnek", "Koyun", "Keçi");
        genusChoiceBox.setStyle("-fx-font-size: 16");
        healthStatusChoiceBox.getItems().addAll("Sağlıklı", "Enfeksiyon", "Gebe", "Yaralanma", "Düşük Süt Verimliliği");
        healthStatusChoiceBox.setStyle("-fx-font-size: 16");

        Farm farm = Farm.getInstance();
        Set<String> uniqueBarnNames = farm.getBarns().stream()
                .map(Barn::getName)
                .collect(Collectors.toSet());

        barnLocationChoiceBox.getItems().addAll(uniqueBarnNames);
        barnLocationChoiceBox.setStyle("-fx-font-size: 16");

        setupWeightTextField();
    }
    private void setupWeightTextField()
    {
        weightTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.length() > 4)
            {
                weightTextField.setText(oldValue);
                return;
            }

            if (!newValue.matches("\\d*(\\.\\d{0,2})?"))
            {
                weightTextField.setText(oldValue);
            }
        });
    }

    @FXML
    public void addButtonClick() {
        Farm farm = Farm.getInstance();

        String genus = genusChoiceBox.getValue();
        String weightInput = weightTextField.getText();
        String healthStatus = healthStatusChoiceBox.getValue();
        String barnLocation = barnLocationChoiceBox.getValue();
        String gender = getSelectedGender();

        if (genus == null || genus.isEmpty())
        {
            AlertService.showAlert("Hata", "Tür seçimi yapmanız gereklidir!");
            return;
        }

        if (healthStatus == null || healthStatus.isEmpty())
        {
            AlertService.showAlert("Hata", "Sağlık durumu seçimi yapmanız gereklidir!");
            return;
        }

        if (barnLocation == null || barnLocation.isEmpty())
        {
            AlertService.showAlert("Hata", "Ahır lokasyonu seçimi yapmanız gereklidir!");
            return;
        }

        if (weightInput == null || weightInput.isEmpty())
        {
            AlertService.showAlert("Hata", "Ağırlık alanı boş bırakılamaz!");
            return;
        }
        else if (!weightInput.matches("\\d+(\\.\\d+)?"))
        {
            AlertService.showAlert("Hata", "Ağırlık geçerli bir sayı olmalıdır!");
            return;
        }

        double weight = Double.parseDouble(weightInput);
        if (weight <= 0)
        {
            AlertService.showAlert("Hata", "Ağırlık sıfırdan büyük olmalıdır!");
            return;
        }

        BarnService barnService = new BarnService();
        Animal newAnimal = new Animal(weight, gender, healthStatus, genus);
        newAnimal.setBarnLocation(farm.findBarn(barnLocation));

        if (barnService.addAnimalToBarn(newAnimal.getBarnLocation(), newAnimal))
        {
            AlertService.showAlert("Başarılı", "Hayvan başarıyla eklendi!");
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }
        else
        {
            AlertService.showAlert("Hata", "Hayvan eklenirken bir sorun oluştu!");
        }


    }


    private String getSelectedGender()
    {
        if (erkekRadioButton.isSelected())
        {
            return "Erkek";
        }
        else if (disiRadioButton.isSelected())
        {
            return "Dişi";
        }
        return null;
    }

    @FXML
    public void cancelButtonClick()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
