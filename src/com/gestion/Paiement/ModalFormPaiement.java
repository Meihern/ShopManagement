package com.gestion.Paiement;

import com.gestion.Paiement.Banque.BanqueDAOIMPL;
import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Paiement.Cheque.ChequeDAOIMPL;
import com.gestion.Paiement.Espece.Espece;
import com.gestion.Paiement.Espece.EspeceDAOIMPL;
import com.gestion.Paiement.Traite.TraiteDAOIMPL;
import com.gestion.Produit.GuiProduit;
import com.gestion.Vente.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModalFormPaiement extends Stage {
    private static final int HEIGHT = 400;
    private static final int WIDTH = 600;

    private Vente current_vente = null;
    private PaiementDAOIMPL paiementDAOIMPL = new PaiementDAOIMPL();
    private EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
    private ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
    private BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
    private TraiteDAOIMPL traiteDAOIMPL = new TraiteDAOIMPL();


    //Initialisation de la scène
    private BorderPane root = new BorderPane();
    private Scene scene = null;

    //Initialisation des conteneurs
    private VBox inputVbox = new VBox();
    private HBox buttonHbox = new HBox();

    //Initialisation des contrôles
    private GridPane formGrid = new GridPane();
    private ComboBox<String> typeComboBox = new ComboBox<>();

    //Initialisation des Labels
    private Label idLabel = new Label("ID : ");
    private Label dateLabel = new Label("Date : ");
    private Label montantLabel = new Label("Montant : ");
    private Label typeLabel = new Label("Type : ");
    private List<Label> managementLabels = new ArrayList<>();

    //Initialisation des Inputs
    private TextField idInput = new TextField();
    private TextField montantInput = new TextField();
    private DatePicker datePicker = new DatePicker();
    /*private TextField numCheque = new TextField();
    private */


    //Initialisation des boutons
    private Button confirmButton = new Button("Confirmer");

    //Initialisation des données
    ObservableList<Paiement> paiementObservableList = FXCollections.observableArrayList();
    String[] types = {"PaiementCheque", "PaiementEspece", "PaiementTraite"};

    private void initPane(){
        scene = new Scene(root, WIDTH, HEIGHT);
        root.setCenter(inputVbox);
        root.setBottom(buttonHbox);
        scene.getStylesheets().add("./CSS/Mycss.css");
    }

    private void initLabel(){
        managementLabels.add(idLabel);
        managementLabels.add(dateLabel);
        managementLabels.add(montantLabel);
        managementLabels.add(typeLabel);
        for(Label label:managementLabels){
            label.getStyleClass().add("custom-product-label");
        }
    }

    private void initInput(){
        idInput.setPromptText("ID");
        idInput.setDisable(true);
        idInput.getStyleClass().add("custom-input");
        montantInput.setText(Double.toString(current_vente.getTotal()));
        montantInput.setDisable(true);
        montantInput.getStyleClass().add("custom-input");
        datePicker.setValue(LocalDate.now());
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
        formGrid.add(idInput, 1,0);
        formGrid.add(datePicker, 1, 1);
        formGrid.add(montantInput, 1, 2);
        formGrid.add(typeComboBox,1,3);
    }

    private void initTypeComboBox(){

        ObservableList<String> typesObservableList = FXCollections.observableArrayList();
        typesObservableList.setAll(types);
        typeComboBox.setItems(typesObservableList);
    }

    private void handleEvents(){
        confirmButton.setOnAction( confrimEvent -> {
            if(datePicker.getValue() == null || typeComboBox.getValue() == null ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs non remplis");
                alert.setContentText("Veuillez remplir tous les champs");
            }
            else {
                switch (typeComboBox.getValue()) {
                    case "PaiementCheque":
                        ModalFormPaiementCheque modalFormPaiementCheque = new ModalFormPaiementCheque(current_vente);
                        Optional<Cheque> optionalCheque = modalFormPaiementCheque.showAndWait();
                        optionalCheque.ifPresent(presentEvent -> {
                            PaiementCheque paiementCheque = new PaiementCheque(current_vente, Date.valueOf(datePicker.getValue()), true, optionalCheque.get());
                            paiementDAOIMPL.create(paiementCheque);
                            GuiPaiement.totalPaye += optionalCheque.get().getMontant();
                            GuiPaiement.restePaye = current_vente.getTotal() - GuiPaiement.totalPaye;
                            GuiPaiement.totalPayeLabel.setText("Total Payé : "+GuiPaiement.totalPaye);
                            GuiPaiement.restePayeLabel.setText("Reste à Payer : "+GuiPaiement.restePaye);
                            paiementObservableList.add(paiementCheque);
                        });
                        break;
                    case "PaiementEspece":
                        Espece espece = new Espece(Double.parseDouble(montantInput.getText()));
                        PaiementEspece paiementEspece = new PaiementEspece(current_vente, Date.valueOf(datePicker.getValue()), true, espece);
                        paiementDAOIMPL.create(paiementEspece);
                        GuiPaiement.totalPaye += espece.getMontant();
                        GuiPaiement.restePaye = current_vente.getTotal() - GuiPaiement.totalPaye;
                        GuiPaiement.totalPayeLabel.setText("Total Payé : "+GuiPaiement.totalPaye);
                        GuiPaiement.restePayeLabel.setText("Reste à Payer : "+GuiPaiement.restePaye);
                        paiementObservableList.add(paiementEspece);
                        break;
                    case "PaiementTraite":
                        break;
                }
                close();
            }
        });
    }

    private void drawElements(){
        inputVbox.getChildren().add(formGrid);
        buttonHbox.getChildren().add(confirmButton);

    }

    void setPaiementObservableList(ObservableList<Paiement> paiementObservableList){
        this.paiementObservableList = paiementObservableList;
    }

    void setVente(Vente vente){
        this.current_vente = vente;
    }


    void initStage(){
        initPane();
        initTypeComboBox();
        initButton();
        initInput();
        initLabel();
        initGrid();
        drawElements();
        handleEvents();
        setTitle("Ajout Paiement");
        setResizable(false);
        setScene(scene);
        show();
    }

}
