package cysapi.controllers.animals;

import cysapi.models.animals.Animal;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import cysapi.services.BarnService;
import cysapi.services.ValidationService;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AnimalTransferController implements Initializable{
    Farm farm = Farm.getInstance();

    @FXML
    private TextField earringNumberTextField;
    @FXML
    private ChoiceBox<String> targetBarnChoiceBox;
    @FXML
    private Button cancelButton;
    @FXML
    VBox animalCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*TODO: Barn ChoiceBox verileri yükleme.*/
        Farm farm = Farm.getInstance();
        List<String> barnNames = farm.getBarns().stream()
                .map(Barn::getName)
                .collect(Collectors.toList());
        targetBarnChoiceBox.getItems().addAll(barnNames);
        System.out.println("Mevcut Ağıllar: " + farm.getBarns());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/animals/AnimalView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        animalCard.getChildren().add(root);
    }



    @FXML
    public void transferButtonClick() {
        Animal transferAnimal = null;
        String earringNumber = earringNumberTextField.getText();
        String targetBarnName = targetBarnChoiceBox.getValue();

        Barn targetBarn = farm.findBarn(targetBarnName);

        if (!ValidationService.validateEarringNumber(earringNumber))
        {
            earringNumberTextField.clear();
            return;
        }
        else {
            for (Barn barn : farm.getBarns()) {
                Optional<Animal> foundAnimal = barn.getAnimals().stream()
                        .filter(animal -> animal.getEarringNumber().equals(earringNumber))
                        .findFirst();

                if (foundAnimal.isPresent()) {
                    transferAnimal = foundAnimal.get();
                    System.out.println("Bulunan Hayvan: " + foundAnimal.get());
                    break;
                }
            }

            if (transferAnimal == null) {
                System.out.println("Hayvan Bulunamadı!");
            }
            else {
                Barn sourceBarn = transferAnimal.getBarnLocation();
                BarnService.addAnimalToBarn(targetBarn, transferAnimal);
                BarnService.removeAnimalFromBarn(sourceBarn, transferAnimal.getEarringNumber());

            }
        }
    }

    @FXML
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
