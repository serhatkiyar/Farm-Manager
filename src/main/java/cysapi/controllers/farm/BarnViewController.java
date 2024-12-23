package cysapi.controllers.farm;

import cysapi.models.animals.Animal;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BarnViewController implements Initializable {
    @FXML
    private Label barnName;

    @FXML
    private TableColumn<Animal, String> earringNumberColumn;
    @FXML
    private TableColumn<Animal, String> genusColumn;
    @FXML
    private TableColumn<Animal, String> genderColumn;
    @FXML
    private TableColumn<Animal, String> healthStatusColumn;
    @FXML
    private TableColumn<Animal, Double> weightColumn;

    @FXML
    private TableView<Animal> barnTable;
    private Barn barn;


    public void getBarn(String barn) {
        this.barn = Farm.getInstance().findBarn(barn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (barn != null) {
            load();
        }
    }

    public void load() {
        barnName.setText(barn.getName());
        setupTableColumns();
        updateTableData();

    }

    private void setupTableColumns() {
            earringNumberColumn.setCellValueFactory(new PropertyValueFactory<>("earringNumber"));
            genusColumn.setCellValueFactory(new PropertyValueFactory<>("genus"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            healthStatusColumn.setCellValueFactory(new PropertyValueFactory<>("healthStatus"));
            weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
    }

    private void updateTableData()
    {
        ArrayList<Animal> animals = barn.getAnimals();
        ObservableList<Animal> animalsList = FXCollections.observableArrayList(animals);
        barnTable.getItems().clear();
        barnTable.setItems(animalsList);
    }
}
