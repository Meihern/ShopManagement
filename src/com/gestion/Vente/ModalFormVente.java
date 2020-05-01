package com.gestion.Vente;

import com.gestion.Client.Client;
import com.gestion.Client.ClientDAOIMPL;
import com.gestion.Produit.Produit;
import com.gestion.Produit.ProduitDAOIMPL;
import com.gestion.Vente.LigneCommande.LigneCommande;
import com.gestion.Vente.LigneCommande.LigneCommandeDAOIMPL;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


class ModalFormVente extends Stage {
    private static final int HEIGHT = 400;
    private static final int WIDTH = 900;

    private List<Produit> produitList = new ArrayList<>();
    private Set<LigneCommande> ligneCommandes = new HashSet<>();
    private ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
    private ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();
    private ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    private ObservableList<Produit> produitObservableList = FXCollections.observableArrayList();
    private ObservableList<LigneCommande> ligneCommandeObservableList = FXCollections.observableArrayList();
    private ObservableList<Vente> venteObservableList = FXCollections.observableArrayList();
    private LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
    private VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
    private Vente current_vente = null;

    //Initialisation de la scène
    private BorderPane root = new BorderPane();
    private Scene scene = null;

    //Initialisation des conteneurs
    private HBox buttonHbox = new HBox();
    private VBox inputVbox = new VBox();
    private VBox tableVbox = new VBox();


    //Initialisation des contrôles
    private GridPane formGrid = new GridPane();
    private ComboBox <Client> clientComboBox = new ComboBox();
    private TableView<Produit> dataTableProduit = new TableView<>();
    private TableView<LigneCommande> dataTableLigneCommande = new TableView<>();

    //Initialisation des labels
    private Label idLabel = new Label("ID : ");
    private Label clientLabel = new Label("Client : ");
    private Label dateLabel = new Label("Date : ");
    private List<Label> managementLabels = new ArrayList<>();

    //Initialisation des Inputs
    private TextField idInput = new TextField();
    private DatePicker datePicker = new DatePicker();
    private TextField searchInput = new TextField();


    //Initialisation des boutons
    private Button confirmButton = new Button("Confirmer");


    private void initPane(){
        root.setCenter(inputVbox);
        root.setBottom(buttonHbox);
        root.setRight(tableVbox);
        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./CSS/Mycss.css");

    }

    private void initLabel(){
        managementLabels.add(idLabel);
        managementLabels.add(clientLabel);
        managementLabels.add(dateLabel);
        for(Label label:managementLabels){
            label.getStyleClass().add("custom-product-label");
        }
    }

    private void initInput(){
        idInput.setPromptText("ID");
        idInput.setDisable(true);
        idInput.getStyleClass().add("custom-input");
        searchInput.getStyleClass().add("custom-input");
        searchInput.setPromptText("Rechercher");
        if(current_vente == null) {
            LocalDate date = LocalDate.now();
            datePicker.setValue(date);
        }
        else{
            idInput.setText(Long.toString(current_vente.getId()));
            datePicker.setValue(current_vente.getDate().toLocalDate());
        }
    }

    private void initButton(){
        confirmButton.getStyleClass().add("custom-button");
        buttonHbox.setPrefSize(100,50);
        buttonHbox.setPadding(new Insets(0,0,50,125));
    }

    private void initGrid(){
        for(int i=0; i<managementLabels.size(); i++){
            formGrid.add(managementLabels.get(i), 0, i);
        }
        formGrid.add(idInput, 1, 0);
        formGrid.add(clientComboBox, 1, 1);
        formGrid.add(datePicker, 1, 2);
    }

    private void initDataTableProduit(){

        // TableView Produit
        dataTableProduit.setPrefWidth((double)WIDTH/2);
        TableColumn<Produit, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Produit, String> designationColumn = new TableColumn<>("Désignation");
        TableColumn<Produit, Double> prixColumn = new TableColumn<>("Prix");
        TableColumn addButtonColumn = new TableColumn<>("");
        TableColumn deleteButtonColumn = new TableColumn<>("");
        dataTableProduit.getColumns().addAll(idColumn, designationColumn, prixColumn, addButtonColumn, deleteButtonColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        addButtonColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Produit, Boolean>, ObservableValue>)
                param -> new SimpleBooleanProperty(param.getValue() != null));
        deleteButtonColumn.setCellFactory((Callback<TableColumn.CellDataFeatures<Produit, Boolean>, ObservableValue>)
                param -> new SimpleBooleanProperty(param.getValue() != null));

        //Ajout du bouton dans la cellule du tableau
        addButtonColumn.setCellFactory((Callback<TableColumn<Produit, Boolean>, TableCell<Produit, Boolean>>)
                param -> new ButtonCell("Ajouter", true));
        deleteButtonColumn.setCellFactory((Callback<TableColumn<Produit, Boolean>, TableCell<Produit, Boolean>>)
                param -> new ButtonCell("Supprimer", false));

    }

    private void initDataTableLigneCommande(){

        // TableView LigneCommande
        TableColumn<LigneCommande, Long> idColumn = new TableColumn<>("ID");
        TableColumn<LigneCommande, Produit> produitColumn = new TableColumn<>("Produit");
        TableColumn<LigneCommande, Double> prixUnitaireColumn = new TableColumn<>("Prix Unitaire");
        TableColumn<LigneCommande, Integer> quantiteColumn = new TableColumn<>("Quantité");
        TableColumn<LigneCommande, Double> sous_totalColumn = new TableColumn<>("Sous total");
        dataTableLigneCommande.getColumns().addAll(idColumn, produitColumn, prixUnitaireColumn, quantiteColumn, sous_totalColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        produitColumn.setCellValueFactory(new PropertyValueFactory<>("produit"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        sous_totalColumn.setCellValueFactory(new PropertyValueFactory<>("sous_total"));
        prixUnitaireColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProduit().getPrix()));
        dataTableLigneCommande.setItems(ligneCommandeObservableList);

    }

    private void initClientCombobox(){
        List<Client> clientList = clientDAOIMPL.getAll();
        clientObservableList.setAll(clientList);
        clientComboBox.setPromptText("Choisir un Client");
        clientComboBox.setItems(clientObservableList);
        if(current_vente !=null) clientComboBox.setValue(current_vente.getClient());
    }

    private void handleEvents(){
        searchInput.setOnKeyPressed(event-> getSpecificProducts(searchInput.getText()));
        confirmButton.setOnAction( event -> {
            // Création des lignes de commande depuis les produits séléctionnés
            // Récupération des données du formulaire
            if (current_vente == null) {
                if(ligneCommandes.isEmpty()){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Aucune ligne de commande n'est ajoutée");
                    errorAlert.setHeaderText("Veuillez ajoutez des lignes de commandes");
                    errorAlert.show();
                    return;
                }
                Vente vente = new Vente(Date.valueOf(datePicker.getValue()), clientComboBox.getValue(), ligneCommandes);
                venteDAOIMPL.create(vente);
                for (LigneCommande ligneCommande : vente.getLigneCommandes()) {
                    ligneCommande.setVente(vente);
                    ligneCommandeDAOIMPL.create(ligneCommande);
                }
                venteObservableList.add(vente);
                this.close();
            }
            else{
                Vente vente = new Vente(Long.parseLong(idInput.getText()), Date.valueOf(datePicker.getValue()), clientComboBox.getValue(), ligneCommandes);
                venteDAOIMPL.update(vente);
                for(LigneCommande ligneCommande:ligneCommandes){
                    ligneCommande.setVente(vente);
                    ligneCommandeDAOIMPL.update(ligneCommande);
                }
                venteObservableList.remove(current_vente);
                venteObservableList.add(vente);
                this.close();
            }
        });
    }

    private void drawElements(){
        inputVbox.getChildren().add(formGrid);
        inputVbox.getChildren().add(dataTableLigneCommande);
        buttonHbox.getChildren().add(confirmButton);
        //tableVbox.getChildren().add(searchInput);
        tableVbox.getChildren().add(dataTableProduit);
    }

    private void getAllProducts(){
        //Initialisation des données à gérer
        produitList = produitDAOIMPL.getAll();
        produitObservableList.setAll(produitList);
        dataTableProduit.setItems(produitObservableList);
    }

    private void getSpecificProducts(String key){
        //.. From the DataBase
        produitObservableList.setAll(produitDAOIMPL.findallLocal(key, produitList));
        dataTableProduit.setItems(produitObservableList);
    }

     void setVenteObservableList(ObservableList<Vente> venteObservableList){
        this.venteObservableList = venteObservableList;
    }

     void setVente(Vente vente){
        this.current_vente = vente;
        for(LigneCommande ligneCommande:vente.getLigneCommandes()){
            LigneCommande new_ligneCommande = new LigneCommande(ligneCommande.getId(), ligneCommande.getProduit(), ligneCommande.getQuantite());
            ligneCommandes.add(new_ligneCommande);
        }
        ligneCommandeObservableList.setAll(this.ligneCommandes);
    }


    private class ButtonCell extends TableCell<Produit, Boolean>{
        private final Button cellButton = new Button();
        // Constructor
        ButtonCell(String buttonText, Boolean is_add_Button){
            this.cellButton.setText(buttonText);
            // If it's an add button the handle event adds the product in the commandList
            if(is_add_Button) {
                cellButton.setOnAction(event -> {
                    Produit produit = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    TextInputDialog quantiteInputDialog = new TextInputDialog();
                    quantiteInputDialog.setTitle("Quantité");
                    quantiteInputDialog.setHeaderText("Choisissez la quantité");
                    quantiteInputDialog.setContentText("Quantité : ");
                    Optional<String> valueQuantite = quantiteInputDialog.showAndWait();
                    valueQuantite.ifPresent(presentEvent -> {
                        try {

                            LigneCommande ligneCommande = new LigneCommande(produit, Integer.parseInt(String.valueOf(valueQuantite.get())));
                            ligneCommandes.add(ligneCommande);
                            ligneCommandeObservableList.setAll(ligneCommandes);
                        }catch (Exception e){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setContentText("Veuillez entrer une valeur valide");
                            alert.showAndWait();
                        }
                    });
                });
            }
            else{
                // If it's a delete Button the handle event removes the command from the commandList
                cellButton.setOnAction( event -> {
                    Produit produit = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    LigneCommande ligneCommande = new LigneCommande(produit, 1);
                    ligneCommandes.remove(ligneCommande);
                    ligneCommandeObservableList.remove(ligneCommande);
                    if(current_vente != null){
                        ligneCommandeDAOIMPL.deleteByVenteIdAndProduitId(produit.getId(), current_vente.getId());
                    }
                });
            }
        }

         // If the row is empty, does not show the button
        @Override
        protected void updateItem(Boolean t, boolean empty){
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }


    void initStage(){
        initPane();
        initInput();
        initLabel();
        initButton();
        initGrid();
        initClientCombobox();
        getAllProducts();
        initDataTableProduit();
        initDataTableLigneCommande();
        drawElements();
        handleEvents();
        // Initialises the window's title depending on whether it's opened via an update action or an add action
        if(current_vente == null) this.setTitle("Ajout Vente");
        else this.setTitle("Modification de la vente n° : "+current_vente.getId());

        this.setResizable(false);
        this.setScene(scene);
        this.show();
    }



}
