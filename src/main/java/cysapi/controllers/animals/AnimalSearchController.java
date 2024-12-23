package cysapi.controllers.animals;

import cysapi.controllers.MainMenuController;
import cysapi.dataaccess.DataService;
import cysapi.models.animals.Animal;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AnimalSearchController implements Initializable {

    Stage stage;
    MainMenuController controller;

    @FXML
    private TableView<Animal> animalsTableView;
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
    private TableColumn<Animal, String> barnLocationColumn;
    @FXML
    private TitledPane barnsTitledPane;
    @FXML
    private TitledPane healthsTitledPane;
    @FXML
    private CheckBox cowCheckBox, sheepCheckBox, goatCheckBox;
    @FXML
    private CheckBox maleCheckBox, femaleCheckBox;
    @FXML
    private TextField minWeightTextField, maxWeightTextField;
    private boolean isDataLoaded = false;


    @FXML
    public void initialize(URL location, ResourceBundle resources)
    {
        setupTableColumns();
//        loadAnimalData();
        populateBarns();
        populateHealthStatuses();

        minWeightTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.length() > 4 || !newValue.matches("\\d*"))
            {
                minWeightTextField.setText(oldValue);
            }
        });

        maxWeightTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.length() > 4 || !newValue.matches("\\d*"))
            {
                maxWeightTextField.setText(oldValue);
            }
        });
    }

    private void setupTableColumns()
    {
        earringNumberColumn.setCellValueFactory(new PropertyValueFactory<>("earringNumber"));
        genusColumn.setCellValueFactory(new PropertyValueFactory<>("genus"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        healthStatusColumn.setCellValueFactory(new PropertyValueFactory<>("healthStatus"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        barnLocationColumn.setCellValueFactory(new PropertyValueFactory<>("barnLocation"));
    }

    private void loadAnimalData()
    {
        if (!isDataLoaded)
        {
            try
            {
                DataService.readAllBarnFiles();
                isDataLoaded = true;
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        updateTableData();
    }

    private void updateTableData()
    {
        ArrayList<Animal> animals = Farm.getInstance().getAllAnimals();
        ObservableList<Animal> animalsList = FXCollections.observableArrayList(animals);
        animalsTableView.getItems().clear();
        animalsTableView.setItems(animalsList);
    }

    private void populateBarns()
    {
        ScrollPane barnsScroll = (ScrollPane) barnsTitledPane.getContent();
        VBox barnVbox = (VBox) barnsScroll.getContent();

        for (Barn barn : Farm.getInstance().getBarns())
        {
            CheckBox barnCheckBox = new CheckBox(barn.getName());
            barnCheckBox.getStyleClass().add("check-box");
            barnVbox.getChildren().add(barnCheckBox);
        }
    }
    private ArrayList<Barn> getSelectedBarns()
    {
        ArrayList<Barn> selectedBarns = new ArrayList<>();
        ScrollPane barnsScroll = (ScrollPane) barnsTitledPane.getContent();
        VBox barnVbox = (VBox) barnsScroll.getContent();

        for (javafx.scene.Node node : barnVbox.getChildren())
        {
            if (node instanceof CheckBox)
            {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected())
                {
                    String barnName = checkBox.getText();
                    Barn barn = Farm.getInstance().findBarn(barnName);
                    if (barn != null)
                    {
                        selectedBarns.add(barn);
                    }
                }
            }
        }
        return selectedBarns;
    }

    private void populateHealthStatuses()
    {
        ScrollPane healthsScroll = (ScrollPane) healthsTitledPane.getContent();
        VBox healthVbox = (VBox) healthsScroll.getContent();
        healthVbox.getChildren().clear();

        CheckBox selectAllCheckBox = new CheckBox("Tümünü Seç");
        selectAllCheckBox.setOnAction(event -> selectHealthsAllSelected());
        healthVbox.getChildren().add(selectAllCheckBox);

        String[] healthStatuses = {"Sağlıklı", "Gebe", "Enfeksiyon", "Yaralanma", "Düşük Süt Verimliliği", "Diğer"};
        for (String status : healthStatuses)
        {
            CheckBox healthCheckBox = new CheckBox(status);
            healthVbox.getChildren().add(healthCheckBox);
        }
    }

    @FXML
    public void selectBarnsAllSelected()
    {
        ScrollPane barnsScrollPane = (ScrollPane) barnsTitledPane.getContent();
        VBox barnCheckBoxes = (VBox) barnsScrollPane.getContent();
        CheckBox selectAll = (CheckBox) barnCheckBoxes.getChildren().get(0);
        boolean isSelected = selectAll.isSelected();

        for (int i = 1; i < barnCheckBoxes.getChildren().size(); i++)
        {
            CheckBox barnCheckBox = (CheckBox) barnCheckBoxes.getChildren().get(i);
            barnCheckBox.setSelected(isSelected);
            barnCheckBox.setDisable(isSelected);
        }
    }
    private ArrayList<String> getSelectedHealthStatuses()
    {
        ArrayList<String> selectedHealthStatuses = new ArrayList<>();
        ScrollPane healthsScroll = (ScrollPane) healthsTitledPane.getContent();
        VBox healthVbox = (VBox) healthsScroll.getContent();

        for (javafx.scene.Node node : healthVbox.getChildren())
        {
            if (node instanceof CheckBox)
            {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected())
                {
                    selectedHealthStatuses.add(checkBox.getText());
                }
            }
        }
        return selectedHealthStatuses;
    }

    @FXML
    public void selectHealthsAllSelected()
    {
        ScrollPane healthsScrollPane = (ScrollPane) healthsTitledPane.getContent();
        VBox healthCheckBoxes = (VBox) healthsScrollPane.getContent();

        if (healthCheckBoxes.getChildren().isEmpty())
        {
            return;
        }

        CheckBox selectAllCheckBox = (CheckBox) healthCheckBoxes.getChildren().get(0);
        boolean isSelected = selectAllCheckBox.isSelected();

        for (int i = 1; i < healthCheckBoxes.getChildren().size(); i++)
        {
            if (healthCheckBoxes.getChildren().get(i) instanceof CheckBox)
            {
                CheckBox healthCheckBox = (CheckBox) healthCheckBoxes.getChildren().get(i);
                healthCheckBox.setSelected(isSelected);
                healthCheckBox.setDisable(isSelected);
                healthCheckBox.setStyle(isSelected ? "-fx-opacity: 0.5;" : "-fx-opacity: 1;");
            }
        }
    }

    @FXML
    public void applyFilters()
    {
        ArrayList<Animal> allAnimals = Farm.getInstance().getAllAnimals();
        ArrayList<Animal> filteredAnimals = new ArrayList<>(allAnimals);

        if (cowCheckBox.isSelected() || sheepCheckBox.isSelected() || goatCheckBox.isSelected())
        {
            filteredAnimals.removeIf(animal -> !(
                    (cowCheckBox.isSelected() && animal.getGenus().equalsIgnoreCase("İnek")) ||
                            (sheepCheckBox.isSelected() && animal.getGenus().equalsIgnoreCase("Koyun")) ||
                            (goatCheckBox.isSelected() && animal.getGenus().equalsIgnoreCase("Keçi"))
            ));
        }

        if (maleCheckBox.isSelected() || femaleCheckBox.isSelected())
        {
            filteredAnimals.removeIf(animal -> !(
                    (maleCheckBox.isSelected() && animal.getGender().equalsIgnoreCase("Erkek")) ||
                            (femaleCheckBox.isSelected() && animal.getGender().equalsIgnoreCase("Dişi"))
            ));
        }

        ArrayList<String> selectedHealthStatuses = getSelectedHealthStatuses();
        if (!selectedHealthStatuses.isEmpty())
        {
            filteredAnimals.removeIf(animal -> !selectedHealthStatuses.contains(animal.getHealthStatus()));
        }

        ArrayList<Barn> selectedBarns = getSelectedBarns();
        if (!selectedBarns.isEmpty())
        {
            filteredAnimals.removeIf(animal -> !selectedBarns.contains(animal.getBarnLocation()));
        }

        try
        {
            String minWeightText = minWeightTextField.getText();
            String maxWeightText = maxWeightTextField.getText();
            double minWeight = minWeightText.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(minWeightText);
            double maxWeight = maxWeightText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxWeightText);
            filteredAnimals.removeIf(animal -> animal.getWeight() < minWeight || animal.getWeight() > maxWeight);
        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Geçersiz ağırlık değeri girildi!", ButtonType.OK);
            alert.showAndWait();
        }

        if (!filteredAnimals.isEmpty())
        {
            ObservableList<Animal> filteredList = FXCollections.observableArrayList(filteredAnimals);
            animalsTableView.getItems().clear();
            animalsTableView.setItems(filteredList);
        }
        else
        {
            animalsTableView.getItems().clear();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seçilen filtrelere uygun hayvan bulunamadı.", ButtonType.OK);
            clearFilters();
            alert.showAndWait();
        }
    }

    @FXML
    public void clearFilters()
    {
        cowCheckBox.setSelected(false);
        sheepCheckBox.setSelected(false);
        goatCheckBox.setSelected(false);
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);
        minWeightTextField.clear();
        maxWeightTextField.clear();

        ScrollPane barnsScroll = (ScrollPane) barnsTitledPane.getContent();
        VBox barnVbox = (VBox) barnsScroll.getContent();
        for (javafx.scene.Node node : barnVbox.getChildren())
        {
            if (node instanceof CheckBox)
            {
                ((CheckBox) node).setSelected(false);
            }
        }

        ScrollPane healthsScroll = (ScrollPane) healthsTitledPane.getContent();
        VBox healthVbox = (VBox) healthsScroll.getContent();
        for (javafx.scene.Node node : healthVbox.getChildren())
        {
            if (node instanceof CheckBox)
            {
                ((CheckBox) node).setSelected(false);
            }
        }

        updateTableData();
    }

    public void getMain(MainMenuController controller)
    {
        this.controller = controller;
    }

    public void updateStatus()
    {
        updateTableData();
    }

    public void saveButtonClick()
    {
        applyFilters();
    }

    public void clearButtonClick()
    {
        clearFilters();
    }
}