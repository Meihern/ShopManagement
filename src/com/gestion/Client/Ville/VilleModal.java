package com.gestion.Client.Ville;

import com.gestion.Produit.Categorie.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VilleModal extends Stage {
    private static final int HEIGHT = 200;
    private static final int WIDTH = 500;

    //Initialisation des données à gérer
    private VilleDaoIMPL villeDaoIMPL = new VilleDaoIMPL();
    private ObservableList<Ville> villeObservableList = FXCollections.observableArrayList();

    //Initialisation de la scène
    private BorderPane root = new BorderPane();
    private Scene scene = null;

    //Initialisation des contrôleurs
    private HBox gridHbox = new HBox();
    private HBox buttonHbox = new HBox();
    private VBox tableVbox = new VBox();

    //Initialisation des contrôles
    private GridPane villeGrid = new GridPane();
    private TableView<Ville> dataTableVille = new TableView<>();

    //Initialisation des labels
    private Label idLabel = new Label("ID : ");
    private Label nomLabel = new Label("Nom : ");
    private List<Label> labelList = new ArrayList<>();

    //Initialisation des Inputs
    private TextField idInput = new TextField();
    private TextField nomInput = new TextField();
    private List<TextField> inputList = new ArrayList<>();

    //Initialisation des boutons
    private Button addButton = new Button("Ajouter");

    private void initPane(){
        root.setCenter(gridHbox);
        root.setBottom(buttonHbox);
        root.setRight(tableVbox);
        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./CSS/Mycss.css");

    }

    private void initLabel(){
        labelList.add(idLabel);
        labelList.add(nomLabel);
        for(Label label:labelList)
            label.getStyleClass().add("custom-product-label");
    }

    private void initInputs(){
        idInput.setPromptText("ID");
        nomInput.setPromptText("Nom Ville");
        idInput.setDisable(true);
        inputList.add(idInput);
        inputList.add(nomInput);
        for(TextField input:inputList)
            input.getStyleClass().add("custom-input");
    }

    private void initButton(){
        addButton.getStyleClass().add("custom-button");
        buttonHbox.setPrefSize(100,50);
        buttonHbox.setPadding(new Insets(0,0,50,125));
    }

    private void initGrid(){
        for(int i=0; i<labelList.size(); i++) {
            villeGrid.add(labelList.get(i), 0, i);
            villeGrid.add(inputList.get(i),1,i);
        }
    }

    private void initDataTable(){
        dataTableVille.setPrefWidth((double)WIDTH/3);
        TableColumn<Ville, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Ville, String> nomColumn = new TableColumn<>("Nom");
        dataTableVille.getColumns().addAll(idColumn, nomColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        dataTableVille.setItems(villeObservableList);
    }

    private void drawElements(){
        gridHbox.getChildren().add(villeGrid);
        buttonHbox.getChildren().add(addButton);
        tableVbox.getChildren().add(dataTableVille);
    }

    private void handleEvents(){
        addButton.setOnAction(addEvent -> {
            Ville ville = new Ville(nomInput.getText());
            villeDaoIMPL.create(ville);
            villeObservableList.add(ville);
            this.close();
        });
    }


    public void setVilleObservableList(ObservableList<Ville> villeObservableList) {
        this.villeObservableList = villeObservableList;
    }

    public void initStage(){
        initPane();
        initInputs();
        initLabel();
        initButton();
        initGrid();
        initDataTable();
        drawElements();
        handleEvents();
        this.setTitle("Ajout Ville");
        this.setResizable(false);
        this.setScene(scene);
        this.show();
    }

}
