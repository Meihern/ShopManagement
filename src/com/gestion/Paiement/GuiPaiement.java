package com.gestion.Paiement;

import com.gestion.Paiement.Banque.BanqueDAOIMPL;
import com.gestion.Paiement.Cheque.ChequeDAOIMPL;
import com.gestion.Paiement.Espece.EspeceDAOIMPL;
import com.gestion.Paiement.Traite.TraiteDAOIMPL;
import com.gestion.Produit.Produit;
import com.gestion.Vente.LigneCommande.LigneCommande;
import com.gestion.Vente.LigneCommande.LigneCommandeDAOIMPL;
import com.gestion.Vente.Vente;
import com.gestion.Vente.VenteDAOIMPL;
import com.settings.MenuScroll;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiPaiement extends Application {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;


    private VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
    private LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
    private PaiementDAOIMPL paiementDAOIMPL = new PaiementDAOIMPL();
    private EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
    private ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
    private BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
    private TraiteDAOIMPL traiteDAOIMPL = new TraiteDAOIMPL();

    private Vente vente;
    private Paiement current_Paiement = null;
    static double totalPaye = 0;
    static double restePaye = 0;
    //Initialisatin des conteneurs
    private BorderPane root = new BorderPane();
    private Scene scene = null;
    private HBox titleHbox = new HBox(25);
    private HBox totalHbox = new HBox();
    private VBox dataLigneCommandeVbox = new VBox();
    private VBox dataTablePaiementVBox = new VBox();
    private VBox buttonsVBox = new VBox();

    //Initialisation des contrôles
    private GridPane totalGridPane = new GridPane();
    private TableView<LigneCommande> dataTableLigneCommande = new TableView<>();
    private TableView<Paiement> dataTablePaiement = new TableView<>();

    //Initialisation des boutons
    private Button addButton = new Button("Ajouter Paiement");
    private Button deleteButton = new Button("Supprimer Paiement");
    private List<Button> managementButtons = new ArrayList<>();
    //Initialisation des Labels

    private Label titleVenteLabel = new Label();
    private Label clientLabel = new Label("Client : ");
    private Label totalLabel = new Label("Total : ");
    static Label totalPayeLabel = new Label("Total Payé : ");
    static Label restePayeLabel = new Label("Reste à Payer : ");

    //Initialisation des données
    private ObservableList<LigneCommande> observableListLignesCommande = FXCollections.observableArrayList();
    private ObservableList<Paiement> observableListPaiements = FXCollections.observableArrayList();


    public GuiPaiement(Vente vente){
        this.vente = vente;
    }

    private void initPane(){
        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./Css/Mycss.css");
        titleHbox.getStyleClass().add("custom-pane-top");
        buttonsVBox.getStyleClass().add("custom-pane-left");
        root.setTop(titleHbox);
        root.setLeft(buttonsVBox);
        root.setCenter(dataLigneCommandeVbox);
        root.setRight(dataTablePaiementVBox);
        //root.setBottom(totalHbox);
    }

    private void initGridPane(){
        totalGridPane.add(totalPayeLabel, 0, 0);
        totalGridPane.add(restePayeLabel, 2, 0);
    }

    private void initLabel(){
        titleVenteLabel.setText("Vente n° : "+vente.getId()+" du "+vente.getDate());
        clientLabel.setText(clientLabel.getText()+vente.getClient());
        totalLabel.setText(totalLabel.getText()+vente.getTotal());
        totalPayeLabel.setText("Total Payé : "+totalPaye);
        restePayeLabel.setText("Reste à Payer : "+restePaye);
        restePayeLabel.setTextFill(Color.RED);
        restePayeLabel.setPadding(new Insets(0,0,0,50));
    }

    private void initButtons(){
        managementButtons.add(addButton);
        managementButtons.add(deleteButton);
        for(Button button:managementButtons){
            button.getStyleClass().add("custom-button");
        }
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

    private void initPaiementDataTable(){
        dataTablePaiement.setPrefSize(350,350);
        TableColumn<Paiement, Date> dateColumn = new TableColumn<>("Date");
        TableColumn<Paiement, Double> montantColumn = new TableColumn<>("Montant");
        TableColumn<Paiement, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Paiement, String> numChequeColumn = new TableColumn<>("N° Chèque");
        TableColumn<Paiement, String> etatColumn = new TableColumn<>("Etat");
        dataTablePaiement.getColumns().addAll(dateColumn, montantColumn, typeColumn, numChequeColumn, etatColumn);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        montantColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVente().getTotal()));
        typeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getClass().getSimpleName().substring("Paiement".length())));
        numChequeColumn.setCellValueFactory(param -> {
            if(param.getValue() instanceof PaiementCheque){
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return ((PaiementCheque) param.getValue()).getCheque().getNumCheque();
                    }
                };
            }
            else if(param.getValue() instanceof PaiementTraite && ((PaiementTraite) param.getValue()).getAvanceCheque() != null){
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return ((PaiementTraite) param.getValue()).getAvanceCheque().getNumCheque();
                    }
                };
            }
            else{
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return "---";
                    }
                };
            }
        });
        etatColumn.setCellValueFactory(param -> {
            if(param.getValue().isPaid()) return new ObservableValueBase<>() {
                @Override
                public String getValue() {
                    return "Payé";
                }
            };
            else return new ObservableValueBase<>() {
                @Override
                public String getValue() {
                    return "Non Payé";
                }
            };
        });
    }

    private void handleEvents(){

        dataTablePaiement.setRowFactory( event -> {
            TableRow<Paiement> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(!row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){
                    current_Paiement = row.getItem();
                }
            });
            return row;
        });

        addButton.setOnAction( addEvent -> {
            if( totalPaye == vente.getTotal()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cette Vente est déjà payée");
                alert.setTitle("Erreur");
                alert.setHeaderText("Impossible d'Ajouter Un Paiement");
                alert.show();
            }
            else{
                ModalFormPaiement formPaiement = new ModalFormPaiement();
                Stage stage = (Stage) addButton.getScene().getWindow();
                try{
                    formPaiement.setPaiementObservableList(observableListPaiements);
                    formPaiement.setVente(vente);
                    formPaiement.initModality(Modality.WINDOW_MODAL);
                    formPaiement.initOwner(stage);
                    formPaiement.initStage();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        deleteButton.setOnAction(deleteEvent -> {
            paiementDAOIMPL.delete(current_Paiement.getId());
            observableListPaiements.remove(current_Paiement);
            totalPaye -= current_Paiement.getMontantPaye();
            restePaye = vente.getTotal() - totalPaye;
            totalPayeLabel.setText("Total Payé : "+totalPaye);
            restePayeLabel.setText("Reste à Payer : "+restePaye);
            current_Paiement = null;
            getPaiements();
        });
    }

    private void drawElements(){
        titleHbox.setAlignment(Pos.TOP_LEFT);
        titleHbox.getChildren().add(MenuScroll.getMenuScroll());
        buttonsVBox.setAlignment(Pos.BASELINE_LEFT);
        dataLigneCommandeVbox.setAlignment(Pos.TOP_LEFT);
        dataTablePaiementVBox.setAlignment(Pos.TOP_RIGHT);
        titleHbox.getChildren().add(titleVenteLabel);
        titleHbox.getChildren().add(clientLabel);
        dataLigneCommandeVbox.getChildren().add(dataTableLigneCommande);
        dataLigneCommandeVbox.getChildren().add(totalLabel);
        dataTablePaiementVBox.getChildren().add(dataTablePaiement);
        dataTablePaiementVBox.getChildren().add(totalGridPane);
        //dataTablePaiementVBox.getChildren().add(totalPayeLabel);
        for(Button button:managementButtons){
            buttonsVBox.getChildren().add(button);
        }
        //totalHbox.getChildren().add(totalLabel);
    }

    private void getLignesCommandes(){
        Set<LigneCommande> ligneCommandeList = ligneCommandeDAOIMPL.findAllByVente(vente);
        observableListLignesCommande.setAll(ligneCommandeList);
        dataTableLigneCommande.setItems(observableListLignesCommande);
    }

    private void getPaiements(){
        List<Paiement> paiementList = paiementDAOIMPL.getAllByVente(vente);
        getTotalPaye(paiementList);
        observableListPaiements.setAll(paiementList);
        dataTablePaiement.setItems(observableListPaiements);
    }

    private void getTotalPaye(List<Paiement> paiementList){
        double total = 0;
        for(Paiement paiement:paiementList){
           // System.out.println("Montant payé du Paiement : "+paiement.getMontantPaye());
            System.out.println(paiement);
            total += paiement.getMontantPaye();
        }
        totalPaye = total;
        restePaye = vente.getTotal() - totalPaye;
    }

    @Override
    public void start(Stage stage) throws Exception {
            initPane();
            initButtons();
            getLignesCommandes();
            getPaiements();
            initLabel();
            initLigneCommandeDataTable();
            initPaiementDataTable();
            initGridPane();
            drawElements();
            handleEvents();
            stage.setTitle("Gestion Paiement de Vente ° :"+vente.getId());
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
