package com.gestion.Vente;

import com.gestion.Client.Client;
import com.gestion.Paiement.GuiPaiement;
import com.gestion.Produit.Produit;
import com.gestion.Vente.LigneCommande.LigneCommande;
import com.gestion.Vente.LigneCommande.LigneCommandeDAOIMPL;
import com.settings.MenuScroll;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiVente extends Application {

    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    private VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
    private LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
    private Vente current_vente = null;

    //Initialisation des conteneurs
    private BorderPane root = new BorderPane();
    private Scene scene = null;
    private HBox titleHbox = new HBox(25);
    private VBox dataVenteVbox = new VBox();
    private VBox buttonsVbox = new VBox();
    private VBox dataCurrentVenteVbox = new VBox();

    //Initialisation des contrôles

    private TableView<Vente> dataTableVente = new TableView<>();
    private TableView<LigneCommande> dataTableLigneCommande = new TableView<>();

    //Initialisation des boutons
    private Button addButton = new Button("Ajouter Vente");
    private Button deleteButton = new Button("Supprimer Vente");
    private Button updateButton = new Button("Modifier Vente");
    private Button paiementButton = new Button("Afficher Paiements");
    private List<Button> managementButtons = new ArrayList<>();

    //Initialisation des Labels

    private Label mainTitleLabel = new Label("Gestion des Ventes");
    private Label detailVenteLabel = new Label("Aucune Vente n'est seléctionnée");
    private Label dateLabel = new Label("Date : ");
    private Label totalLabel = new Label();

    //Initialisation Alert
    Alert alert = new Alert(Alert.AlertType.ERROR);


    //Initialisation des données vente
    private ObservableList<Vente> observableListVentes = FXCollections.observableArrayList();
    private ObservableList<LigneCommande> observableListLignesCommande = FXCollections.observableArrayList();

    private void initPane(){
        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./Css/Mycss.css");
        titleHbox.getStyleClass().add("custom-pane-top");
        buttonsVbox.getStyleClass().add("custom-pane-left");
        root.setTop(titleHbox);
        root.setRight(dataVenteVbox);
        root.setLeft(buttonsVbox);
        root.setCenter(dataCurrentVenteVbox);
    }

    private void initLabel(){
        mainTitleLabel.getStyleClass().add("custom-label");
        mainTitleLabel.setLabelFor(titleHbox);
    }

    private void initButtons(){
        managementButtons.add(addButton);
        managementButtons.add(deleteButton);
        managementButtons.add(updateButton);
        managementButtons.add(paiementButton);
        for (Button button:managementButtons){
            button.getStyleClass().add("custom-button");
        }
    }

    private void initAlert(){
        alert.setTitle("Erreur");
        alert.setContentText("Vous devez séléctionnez une vente");
    }

    private void initVenteDataTable(){

        TableColumn<Vente, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Vente, Client> clientColumn = new TableColumn<>("Client");
        TableColumn<Vente, Date> dateColumn = new TableColumn<>("Date");
        TableColumn<Vente, Double> totalColumn = new TableColumn<>("Total");
        dataTableVente.getColumns().addAll(idColumn, clientColumn, dateColumn, totalColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void initLigneCommandeDataTable(){
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
    }


    private void drawElements(){
        titleHbox.setAlignment(Pos.TOP_LEFT);
        titleHbox.getChildren().add(MenuScroll.getMenuScroll());
        titleHbox.getChildren().add(mainTitleLabel);
        dataCurrentVenteVbox.setAlignment(Pos.TOP_CENTER);
        buttonsVbox.setAlignment(Pos.BASELINE_LEFT);
        dataCurrentVenteVbox.getChildren().add(detailVenteLabel);
        dataVenteVbox.getChildren().add(dataTableVente);
        dataCurrentVenteVbox.getChildren().add(dataTableLigneCommande);
        dataCurrentVenteVbox.getChildren().add(totalLabel);
        for (Button button:managementButtons) buttonsVbox.getChildren().add(button);
    }

    private void handleEvents(){
        dataTableVente.setRowFactory(event -> {
            TableRow<Vente> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(!row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){

                    current_vente = row.getItem();
                    detailVenteLabel.setText("Vente n°: "+current_vente.getId()+" Du : "+current_vente.getDate()+"\nClient : "+current_vente.getClient());
                    totalLabel.setText("Total : "+current_vente.getTotal());
                    getLignesCommandes(current_vente);
                }
            });

            return row;
        });

        addButton.setOnAction( addEvent -> {
            ModalFormVente formVente = new ModalFormVente();
            Stage stage = (Stage) addButton.getScene().getWindow();
            try {
                formVente.setVenteObservableList(observableListVentes);
                formVente.initModality(Modality.WINDOW_MODAL);
                formVente.initOwner(stage);
                formVente.initStage();
                getAllVentes();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        updateButton.setOnAction( updateEvent -> {
            ModalFormVente formVente = new ModalFormVente();
            Stage stage = (Stage) addButton.getScene().getWindow();
            if(current_vente == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucune vente n'est séléctionnée");
                alert.setContentText("Veuillez séléctionner une vente pour la modifier !");
                alert.showAndWait();
                return;
            }
            try {
                formVente.setVenteObservableList(observableListVentes);
                formVente.setVente(current_vente);
                formVente.initModality(Modality.WINDOW_MODAL);
                formVente.initOwner(stage);
                formVente.initStage();
                getAllVentes();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction( deleteEvent -> {
            if(current_vente != null){
                venteDAOIMPL.delete(current_vente.getId());
                getAllVentes();
            }
        });

        paiementButton.setOnAction( paiementEvent -> {
            if(current_vente == null){
                alert.show();
            }
            else{
                GuiPaiement guiPaiement = new GuiPaiement(current_vente);
                Stage stage = (Stage) paiementButton.getScene().getWindow();
                try{
                    guiPaiement.start(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } );
    }

    private void getAllVentes(){
        //.. From the database
        //Données à gérer
        List<Vente> venteList = venteDAOIMPL.getAll();
        observableListVentes.setAll(venteList);
        dataTableVente.setItems(observableListVentes);
    }

    private void getLignesCommandes(Vente vente){
        Set<LigneCommande> ligneCommandeList = ligneCommandeDAOIMPL.findAllByVente(vente);
        observableListLignesCommande.setAll(ligneCommandeList);
        dataTableLigneCommande.setItems(observableListLignesCommande);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initPane();
        initLabel();
        initButtons();
        initAlert();
        initLigneCommandeDataTable();
        getAllVentes();
        initVenteDataTable();
        drawElements();
        handleEvents();
        stage.setScene(scene);
        stage.setTitle("Gestion des Ventes");
        stage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
