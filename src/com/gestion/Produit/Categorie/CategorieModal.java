package com.gestion.Produit.Categorie;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CategorieModal extends Stage {

    private static final int HEIGHT = 200;
    private static final int WIDTH = 500;

    //Initialisation des données à gérer
    private CategorieDAOIMPL categorieDAOIMPL = new CategorieDAOIMPL();
    private ObservableList<Categorie> categorieObservableList = FXCollections.observableArrayList();

    //Initialisation de la scène
    private BorderPane root = new BorderPane();
    private Scene scene = null;

    //Initialisation des contrôleurs
    private HBox gridHbox = new HBox();
    private HBox buttonHbox = new HBox(25);
    private VBox tableVbox = new VBox();

    // Initialisation des contrôles
    private GridPane categorieGrid = new GridPane();
    private TableView<Categorie> dataTableCategorie = new TableView<>();

    //Initialisation des Labels
    private Label idLabel = new Label("ID : ");
    private Label intituleLabel = new Label("Intitule : ");
    private List<Label> labelList = new ArrayList<>();

    //Initialisation des inputs
    private TextField idInput = new TextField();
    private TextField intituleInput = new TextField();
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
        labelList.add(intituleLabel);
        for(Label label:labelList)
            label.getStyleClass().add("custom-product-label");
    }

    private void initInputs(){
        idInput.setPromptText("ID");
        intituleInput.setPromptText("Intitule");
        idInput.setDisable(true);
        inputList.add(idInput);
        inputList.add(intituleInput);
        for(TextField input:inputList)
            input.getStyleClass().add("custom-input");
    }

    private void initButton(){
        addButton.getStyleClass().add("custom-button");
        buttonHbox.setPrefSize(100,50);
        buttonHbox.setPadding(new Insets(0,0,50,125));
    }

    private void initGrid(){
        gridHbox.setPadding(new Insets(0,0,0,20));
        for(int i=0; i<labelList.size(); i++) {
            categorieGrid.add(labelList.get(i), 0, i);
            categorieGrid.add(inputList.get(i),1,i);
        }
    }

    private void initDataTable(){
        dataTableCategorie.setPrefWidth((double)WIDTH/3);
        TableColumn<Categorie, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Categorie, String> intituleColumn = new TableColumn<>("Intitule");
        dataTableCategorie.getColumns().addAll(idColumn, intituleColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        intituleColumn.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        dataTableCategorie.setItems(categorieObservableList);
    }

    private void drawElements(){
        gridHbox.getChildren().add(categorieGrid);
        buttonHbox.getChildren().add(addButton);
        tableVbox.getChildren().add(dataTableCategorie);

    }

    private void handleEvents(){
        addButton.setOnAction(addEvent -> {
            Categorie categorie = new Categorie(intituleInput.getText());
           categorieDAOIMPL.create(categorie);
           categorieObservableList.add(categorie);
           this.close();
        });
    }

    public void setCategorieObservableList(ObservableList<Categorie> categorieObservableList) {
        this.categorieObservableList = categorieObservableList;
    }


    public void initStage(){
        initPane();
        initInputs();
        initLabel();
        handleEvents();
        initGrid();
        initDataTable();
        initButton();
        drawElements();
        this.setTitle("Ajout Catégorie");
        this.setResizable(false);
        this.setScene(scene);
        this.show();
    }

}
