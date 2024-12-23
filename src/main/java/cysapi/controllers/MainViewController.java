package cysapi.controllers;

import cysapi.controllers.farm.BarnViewController;
import cysapi.dataaccess.DataService;
import cysapi.models.animals.Animal;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import cysapi.services.SearchService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private VBox barnWindows;

    public String selectedBarnName;

    MainMenuController controller;

    @FXML
    Label cowCount;

    @FXML
    Label sheepCount;

    @FXML
    Label goatCount;

    @FXML
    PieChart healthPieChart;
    @FXML
    Label totalAnimalCount;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.updateStatus();
    }

    public void getMainController(MainMenuController controller) {
        this.controller = controller;
    }

    @FXML
    public void updateStatus() {
        barnWindows.getChildren().clear();
        Farm farm = Farm.getInstance();

        if (!farm.getBarns().isEmpty()) {
            for (Barn barn : farm.getBarns()) {
                System.out.println(barn.getName() + "ağılının hayvan sayısı: " + barn.getBarnSize());
                addBarnWindow(barn.getName(), (double) barn.getBarnSize() / barn.getCapacity());
            }
            ArrayList<String> genus = new ArrayList<>();
            genus.add("İnek");
            cowCount.setText("" + SearchService.searchWithGenus(Farm.getInstance().getAllAnimals(), genus).size());
            genus.clear();
            genus.add("Koyun");
            sheepCount.setText("" + SearchService.searchWithGenus(Farm.getInstance().getAllAnimals(), genus).size());
            genus.clear();
            genus.add("Keçi");
            goatCount.setText("" + SearchService.searchWithGenus(Farm.getInstance().getAllAnimals(), genus).size());

            ArrayList<Animal> animals = Farm.getInstance().getAllAnimals();
            ArrayList<String> healthStatus= new ArrayList<>();

            healthStatus.add("Sağlıklı");
            int saglikli = SearchService.searchHealthStatus(animals, healthStatus).size();
            healthStatus.clear();
            healthStatus.add("Enfeksiyon");

            int enfeksiyon = SearchService.searchHealthStatus(animals, healthStatus).size();
            healthStatus.clear();
            healthStatus.add("Gebe");

            int gebe = SearchService.searchHealthStatus(animals, healthStatus).size();
            healthStatus.clear();
            healthStatus.add("Yaralanma");

            int yaralanma = SearchService.searchHealthStatus(animals, healthStatus).size();
            healthStatus.clear();
            healthStatus.add("Düşük Süt Verimliliği");
            int dusuksut = SearchService.searchHealthStatus(animals, healthStatus).size();
            healthStatus.clear();
            healthStatus.add("Diğer");
            int diger = SearchService.searchHealthStatus(animals, healthStatus).size();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Sağlıklı", saglikli),
                    new PieChart.Data("Enfeksiyon", enfeksiyon),
                    new PieChart.Data("Gebe", gebe),
                    new PieChart.Data("Yaralanma", yaralanma),
                    new PieChart.Data("Düşük Süt Verimliliği", dusuksut),
                    new PieChart.Data("Diğer", diger)

            );

            healthPieChart.getData().addAll(pieChartData);
            healthPieChart.setStyle("-fx-font-size: 16");
            totalAnimalCount.setText(String.valueOf(Farm.getInstance().getAllAnimals().size()));


        }
        else {
            System.out.println("Yüklenecek Ağıl yok");
        }
   }

   @FXML
   private VBox mainViewScene;
    @FXML
    public void showBarn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/farm/BarnView.fxml"));
        Parent root = loader.load();
        BarnViewController barnViewController = loader.getController();
        barnViewController.getBarn(selectedBarnName);
        controller.contentPane.getChildren().clear();
        controller.contentPane.getChildren().add(root);
        barnViewController.load();

    }

    public HBox createBarnHBOX() {
        HBox barnsHBOX = new HBox();
        barnsHBOX.prefWidth(1450);
        barnsHBOX.prefHeight(250);
        barnsHBOX.maxHeight(250);
        barnsHBOX.setSpacing(79);
        return barnsHBOX;
    }

    public StackPane createBarnVBOX(String barnName, double doluluk) {
        VBox vBox = new VBox();

        // DOLULUK YÜZDESİ
        Label yuzdeLabel = new Label("%" + doluluk * 100 );
        yuzdeLabel.setFont(new Font("System", 20));
        yuzdeLabel.setAlignment(Pos.TOP_RIGHT);

        // AĞIL İSMİ
        final Label barnNameLabel = new Label(barnName);
        barnNameLabel.getStyleClass().add("card-title");


        barnNameLabel.setFont(new Font("ROBOTO", 70));
        barnNameLabel.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setMargin(barnNameLabel, new javafx.geometry.Insets(70, 0, 0,0));

        // DOLULUK ORAN BARI
        ProgressBar progressBar = new ProgressBar(doluluk);
        progressBar.setPrefHeight(30);
        progressBar.setPrefWidth(400);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.getStyleClass().add("progress-bar");
        // BUTTON
        Button barnViewButton = new Button();
        barnViewButton.setPrefWidth(429);
        barnViewButton.setPrefHeight(250);
        barnViewButton.setOnAction(actionEvent -> {
            try {
                Button actionButton = (Button) actionEvent.getSource();
                StackPane stack = (StackPane) actionButton.getParent();
                VBox vBox1 = (VBox) stack.getChildren().get(0);
                Label barnLabel = (Label) vBox1.getChildren().get(0);
                selectedBarnName = barnLabel.getText();
                showBarn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        barnViewButton.setCursor(Cursor.HAND);
        barnViewButton.setOpacity(0.0);
        barnViewButton.getStyleClass().add("barn");


        //
        StackPane stackPane = new StackPane();
        StackPane dolulukStackPane = new StackPane();

        VBox.setMargin(stackPane, new Insets(0, 20, 0, 20));
        VBox.setVgrow(dolulukStackPane, Priority.ALWAYS);
        VBox.setVgrow(stackPane, Priority.NEVER);


        dolulukStackPane.getChildren().addAll(progressBar, yuzdeLabel);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(barnNameLabel, dolulukStackPane);

        dolulukStackPane.setAlignment(Pos.BOTTOM_CENTER);

        vBox.setSpacing(0);
        vBox.setPrefWidth(429);
        vBox.setPrefHeight(250);
        vBox.setMaxWidth(429);
        vBox.getStyleClass().add("barn");
        vBox.setFillWidth(true);
        vBox.setPadding(new Insets(0, 0, 15, 0));



        stackPane.getChildren().addAll(vBox, barnViewButton);

        return stackPane;
    }

    public void addBarnWindow(String barnName, double doluluk) {
        StackPane vBox = createBarnVBOX(barnName, doluluk);
        if (barnWindows.getChildren().size() > 0) {

            HBox hBox = (HBox) barnWindows.getChildren().get(barnWindows.getChildren().size() - 1);

            if (hBox.getChildren().size() < 3) {
                hBox.getChildren().add(vBox);
            }
            else {
                HBox new_HBox = createBarnHBOX();
                new_HBox.getChildren().add(vBox);
                barnWindows.getChildren().add(new_HBox);
            }
        }
        else {
            System.out.println("Hbox eklenmeye çalışılıyor");
            HBox new_HBox = createBarnHBOX();
            new_HBox.getChildren().add(vBox);
            barnWindows.getChildren().add(new_HBox);
        }

    }

}
