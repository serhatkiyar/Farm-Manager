package cysapi.controllers.animals;

import cysapi.models.animals.Animal;
import cysapi.models.animals.Genus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimalViewController {

    @FXML
    private Label earringNumberLabel;
    @FXML
    private Label genusLabel;
    @FXML
    private Label barnLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label healthStatusLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private ImageView animalProfilePhoto;

    public void loadData(Animal animal) {
        earringNumberLabel.setText(": " + animal.getEarringNumber());
        genusLabel.setText(": " + animal.getGenus());
        barnLabel.setText(": " + animal.getBarnLocation().getName());
        genderLabel.setText(": " + animal.getGender());
        healthStatusLabel.setText(": " + animal.getHealthStatus());
        weightLabel.setText(": " + animal.getWeight());

        if (animal.getGenus().equals(Genus.COW.getDisplayName())) {
            animalProfilePhoto.setImage(new Image(getClass().getResource("/images/animal-status/animal-photos/inek.png").toExternalForm()));
        }
        else if (animal.getGenus().equals(Genus.SHEEP.getDisplayName())) {
            animalProfilePhoto.setImage(new Image(getClass().getResource("/images/animal-status/animal-photos/koyun.png").toExternalForm()));
        }
        else {
            animalProfilePhoto.setImage(new Image(getClass().getResource("/images/animal-status/animal-photos/keçi.png").toExternalForm()));
        }
    }


}
