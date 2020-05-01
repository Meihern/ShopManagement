package com.gestion.Produit;


import com.gestion.Produit.Categorie.Categorie;
import com.gestion.Produit.Categorie.CategorieDAOIMPL;
import com.gestion.Produit.Categorie.CategorieModal;
import com.settings.MenuScroll;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
public class GuiProduit extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    // Données à gérer
    private List<Produit> produitList = new ArrayList<>();
    private ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();
    private CategorieDAOIMPL categorieDAOIMPL = new CategorieDAOIMPL();
    private List<Categorie> categorieList = new ArrayList<>();

    // Initialisation des conteneurs
    private BorderPane root = new BorderPane();
    private Scene scene = null;
    private HBox titleHbox = new HBox(25);
    private VBox buttonsVbox = new VBox();
    private VBox dataVbox = new VBox();


    // Initialisation des contrôles
    private GridPane inputGrid = new GridPane();
    private ComboBox<Categorie> categorieComboBox = new ComboBox<>();
    private TableView<Produit> dataTableProduct = new TableView<>();

    // Initialisation des boutons
    private Button addButton = new Button("Ajouter Produit");
    private Button deleteButton = new Button("Supprimer Produit");
    private Button updateButton = new Button("Modifier Produit");
    private Button clearButton = new Button("Nouveau");
    private Button addCategorieButton = new Button("+");
    private List<Button> managementButtons = new ArrayList<>();

    // Initialisation des inputs
    private TextField idInput = new TextField();
    private TextField designationInput = new TextField();
    private TextField prixInput = new TextField();
    private TextField searchInput = new TextField();
    private List<TextField> managementInputs = new ArrayList<>();

    // Initialisation des labels
    private Label mainTitleLabel = new Label("Gestion Produits");
    private Label idLabel = new Label("ID : ");
    private Label designationLabel = new Label("Designation : ");
    private Label priceLabel = new Label("Prix : ");
    private Label categorieLabel = new Label("Catégorie");
    private List<Label> productLabels = new ArrayList<>();

    // Initialisation des données Produits
    private ObservableList<Produit> observableListProduits = FXCollections.observableArrayList();
    private ObservableList<Categorie> observableListCategories = FXCollections.observableArrayList();

    private void initPane(){
        scene =  new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./Css/Mycss.css");
        titleHbox.getStyleClass().add("custom-pane-top");
        buttonsVbox.getStyleClass().add("custom-pane-left");
        inputGrid.getStyleClass().add("custom-pane-center");
        inputGrid.setPadding(new Insets(30,0,0,50));
        inputGrid.setPrefHeight(400);
        inputGrid.setPrefWidth(600);
        root.setTop(titleHbox);
        root.setLeft(buttonsVbox);
        root.setCenter(inputGrid);
        root.setRight(dataVbox);

    }



    private void initLabel(){
        mainTitleLabel.getStyleClass().add("custom-label");
        mainTitleLabel.setLabelFor(titleHbox);
        idLabel.setLabelFor(idInput);
        designationLabel.setLabelFor(designationInput);
        priceLabel.setLabelFor(prixInput);
        productLabels.add(idLabel);
        productLabels.add(designationLabel);
        productLabels.add(priceLabel);
        productLabels.add(categorieLabel);
        for(Label label:productLabels){
            label.getStyleClass().add("custom-product-label");
        }
    }

    private void initButtons(){
        managementButtons.add(addButton);
        managementButtons.add(deleteButton);
        managementButtons.add(updateButton);
        managementButtons.add(clearButton);
        for(Button button:managementButtons){
            button.getStyleClass().add("custom-button");
        }
        addCategorieButton.setPrefSize(10,10);
    }


    private void initDataTable(){

        TableColumn<Produit, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Produit, Double> prixColumn = new TableColumn<>("Prix");
        TableColumn<Produit, String> designationColumn = new TableColumn<>("Designation");
        TableColumn<Produit, Categorie> categorieColumn = new TableColumn<>("Categorie");
        dataTableProduct.getColumns().addAll(idColumn, designationColumn, prixColumn, categorieColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
    }

    private void initInputs(){
        idInput.setDisable(true);
        idInput.setPromptText("ID");
        designationInput.setPromptText("Désignation");
        prixInput.setPromptText("Prix");
        searchInput.setPromptText("Rechercher");
        managementInputs.add(idInput);
        managementInputs.add(designationInput);
        managementInputs.add(prixInput);
        for(TextField textField:managementInputs) textField.getStyleClass().add("custom-input");
    }

    private void initGrid(){
        for(int i=0; i<productLabels.size();i++){
            inputGrid.add(productLabels.get(i),0,i);
        }
        for(int i=0; i<managementInputs.size(); i++){
            inputGrid.add(managementInputs.get(i),1,i);
        }
        inputGrid.add(categorieComboBox,1,managementInputs.size());
        inputGrid.add(addCategorieButton,2,managementInputs.size());
    }

    private void drawElements(){
        titleHbox.setAlignment(Pos.TOP_LEFT);
        titleHbox.getChildren().add(MenuScroll.getMenuScroll());
        titleHbox.getChildren().add(mainTitleLabel);
        buttonsVbox.setAlignment(Pos.BASELINE_LEFT);
        inputGrid.setAlignment(Pos.TOP_LEFT);
        for (Button button:managementButtons) buttonsVbox.getChildren().add(button);
        dataVbox.getChildren().add(searchInput);
        dataVbox.getChildren().add(dataTableProduct);

    }
    // Les méthodes d'accès aux données
    private void getAllProducts(){
        //.. From the DataBase
        produitList = produitDAOIMPL.getAll();
        observableListProduits.setAll(produitList);
        dataTableProduct.setItems(observableListProduits);
    }

    private void getSpecificProducts(String key){
        //.. From the DataBase
        observableListProduits.setAll(produitDAOIMPL.findallLocal(key,produitList));
        dataTableProduct.setItems(observableListProduits);
    }

    private void initCategorieComboBox(){
        categorieComboBox.setPromptText("Choisir une Catégorie");
        categorieList = categorieDAOIMPL.getAll();
        observableListCategories.setAll(categorieList);
        categorieComboBox.setItems(observableListCategories);
    }

    private void handleEvents() {
        searchInput.setOnKeyPressed(event -> getSpecificProducts(searchInput.getText()));
        dataTableProduct.setRowFactory(event -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (!row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY
                        && mouseEvent.getClickCount() == 2) {

                    Produit produit = row.getItem();
                    for (TextField input : managementInputs) input.clear();
                    idInput.setText(Long.toString(produit.getId()));
                    designationInput.setText(produit.getDesignation());
                    prixInput.setText(Double.toString(produit.getPrix()));
                    categorieComboBox.setValue(produit.getCategorie());
                }
            });
            return row;
        });
        clearButton.setOnAction(clearEvent -> {
            for (TextField input : managementInputs) input.clear();
        });
        addButton.setOnAction(addEvent -> {
            if(designationInput.getText() == null || prixInput.getText() == null || categorieComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs non remplis");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }
            produitDAOIMPL.create(new Produit(designationInput.getText(), Double.parseDouble(prixInput.getText()), categorieComboBox.getValue()));
            getAllProducts();
        });
        updateButton.setOnAction(updateEvent -> {
            if(designationInput.getText() == null || prixInput.getText() == null || categorieComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs non remplis");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }
            produitDAOIMPL.update(new Produit(Long.parseLong(idInput.getText()), designationInput.getText(), Double.parseDouble(prixInput.getText()), categorieComboBox.getValue()));
            getAllProducts();
        });

        deleteButton.setOnAction(deleteEvent -> {
            produitDAOIMPL.delete(Long.parseLong(idInput.getText()));
            getAllProducts();

        });
        addCategorieButton.setOnAction(addEvent -> {
            CategorieModal categorieModal = new CategorieModal();
            Stage stage = (Stage) addCategorieButton.getScene().getWindow();
            try {
                categorieModal.setCategorieObservableList(observableListCategories);
                categorieModal.initModality(Modality.WINDOW_MODAL);
                categorieModal.initOwner(stage);
                categorieModal.initStage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    @Override
    public void start(Stage stage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Shop Management");
        initCategorieComboBox();
        getAllProducts();
        initPane();
        initButtons();
        initInputs();
        initLabel();
        initDataTable();
        initGrid();
        drawElements();
        handleEvents();
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

