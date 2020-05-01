package com.gestion.Client;

import com.gestion.Client.Ville.Ville;
import com.gestion.Client.Ville.VilleDaoIMPL;
import com.gestion.Client.Ville.VilleModal;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GuiClient extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    //Données à gérer
    private List<Client> clientList = new ArrayList<>();
    private ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
    private List<Ville> villeList = new ArrayList<>();
    private VilleDaoIMPL villeDaoIMPL = new VilleDaoIMPL();

    //Initialisation des conteneurs
    private BorderPane root = new BorderPane();
    private Scene scene = null;
    private HBox titleHbox = new HBox(25);
    private VBox buttonsVbox = new VBox();
    private VBox dataVbox = new VBox();

    //Initialisation des contrôles
    private ComboBox<Ville> villeComboBox = new ComboBox<>();
    private GridPane inputGrid = new GridPane();
    private TableView<Client> dataTableClient = new TableView<>();

    //Initialisation des boutons
    private Button addButton = new Button("Ajouter Client");
    private Button deleteButton = new Button("Supprimer Client");
    private Button updateButton = new Button("Modifier Client");
    private Button clearButton = new Button("Nouveau");
    private Button addVilleButton = new Button("+");
    private List<Button> managementButtons = new ArrayList<>();

    //Initialisation des inputs
    private TextField idInput = new TextField();
    private TextField nomInput = new TextField();
    private TextField prenomInput = new TextField();
    private TextField searchInput = new TextField();
    private TextField emailInput = new TextField();
    private TextField telephoneInput = new TextField();
    private TextField adresseInput = new TextField();
    private List<TextField> managementInputs = new ArrayList<>();

    // Initialisation des labels
    private Label mainTitleLabel = new Label("Gestion Clients");
    private Label idLabel = new Label("ID : ");
    private Label nomLabel = new Label("Nom : ");
    private Label prenomLabel = new Label("Prénom : ");
    private Label emailLabel = new Label("Email : ");
    private Label telephoneLabel = new Label("Teléphone : ");
    private Label adresseLabel = new Label("Adresse : ");
    private Label villeLabel = new Label("Ville : ");
    private List<Label> clientLabels = new ArrayList<>();

    //Initialisation des données Clients et villes
    private ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    private ObservableList<Ville> villeObservableList = FXCollections.observableArrayList();

    private void initPane(){
        scene = new Scene(root, WIDTH, HEIGHT);
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
        nomLabel.setLabelFor(nomInput);
        prenomLabel.setLabelFor(prenomInput);
        clientLabels.add(idLabel);
        clientLabels.add(nomLabel);
        clientLabels.add(prenomLabel);
        clientLabels.add(emailLabel);
        clientLabels.add(telephoneLabel);
        clientLabels.add(adresseLabel);
        clientLabels.add(villeLabel);
        for(Label label: clientLabels){
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
        addVilleButton.setPrefSize(10,10);
    }
    private void initDataTable(){
        dataTableClient.setMaxWidth(400);
        TableColumn<Client, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Client, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<Client, String> prenomColumn = new TableColumn<>("Prénom");
        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Client, String> telephoneColumn = new TableColumn<>("Téléphone");
        TableColumn<Client, String> adresseColumn = new TableColumn<>("Adresse");
        TableColumn<Client, String> villeColumn = new TableColumn<>("Ville");
        dataTableClient.getColumns().addAll(idColumn, nomColumn, prenomColumn, emailColumn, telephoneColumn, adresseColumn, villeColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));
    }

    private void initInputs(){
        idInput.setDisable(true);
        idInput.setPromptText("ID");
        nomInput.setPromptText("Nom");
        prenomInput.setPromptText("Prenom");
        searchInput.setPromptText("Rechercher");
        emailInput.setPromptText("Email");
        telephoneInput.setPromptText("Telephone");
        adresseInput.setPromptText("Adresse");
        managementInputs.add(idInput);
        managementInputs.add(nomInput);
        managementInputs.add(prenomInput);
        managementInputs.add(emailInput);
        managementInputs.add(telephoneInput);
        managementInputs.add(adresseInput);
        for(TextField textField:managementInputs) textField.getStyleClass().add("custom-input");
    }

    private void initGrid(){
        inputGrid.setAlignment(Pos.TOP_LEFT);
        for(int i=0; i<clientLabels.size();i++){
            inputGrid.add(clientLabels.get(i),0,i);
        }
        for(int i=0; i<managementInputs.size(); i++){
            inputGrid.add(managementInputs.get(i),1,i);
        }
        inputGrid.add(villeComboBox,1,managementInputs.size());
        inputGrid.add(addVilleButton,2, managementInputs.size());
    }

    private void initVilleComboBox(){
        //.. From the database getting all cities using the DAO city (Ville) Class
        villeComboBox.setPromptText("Choisir une ville");
        villeList = villeDaoIMPL.getAll();
        villeObservableList.setAll(villeList);
        villeComboBox.setItems(villeObservableList);

    }

    private void drawElements(){
        titleHbox.setAlignment(Pos.TOP_LEFT);
        titleHbox.getChildren().add(MenuScroll.getMenuScroll());
        titleHbox.getChildren().add(mainTitleLabel);
        buttonsVbox.setAlignment(Pos.BASELINE_LEFT);
        for (Button button:managementButtons) buttonsVbox.getChildren().add(button);
        dataVbox.getChildren().add(searchInput);
        dataVbox.getChildren().add(dataTableClient);
    }

    private void handleEvents(){
        searchInput.setOnKeyPressed(event -> getSpecificClients(searchInput.getText()));
        dataTableClient.setRowFactory(event -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (! row.isEmpty() && mouseEvent.getButton()== MouseButton.PRIMARY
                        && mouseEvent.getClickCount() == 2) {

                    Client client = row.getItem();
                    for(TextField input:managementInputs) input.clear();
                    idInput.setText(Long.toString(client.getId()));
                    nomInput.setText(client.getNom());
                    prenomInput.setText(client.getPrenom());
                    emailInput.setText(client.getEmail());
                    telephoneInput.setText(client.getTelephone());
                    adresseInput.setText(client.getAdresse());
                    villeComboBox.setValue(client.getVille());
                }
            });
            return row ;
        });
        clearButton.setOnAction(clearEvent -> {
            for(TextField input:managementInputs) input.clear();
        });
        addButton.setOnAction(addEvent -> {
            for(TextField input:managementInputs){
                if(input.getText() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Champs non remplis");
                    alert.setContentText("Veuillez remplir tous les champs");
                    alert.showAndWait();
                    return;
                }
            }
            if(villeComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs non remplis");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }
            Client client = new Client( nomInput.getText(), prenomInput.getText(), emailInput.getText(), telephoneInput.getText(), adresseInput.getText(), villeComboBox.getValue());
            clientDAOIMPL.create(client);
            clientObservableList.add(client);
        });
        updateButton.setOnAction(updateEvent -> {
            clientDAOIMPL.update(new Client(Long.parseLong(idInput.getText()), nomInput.getText(), prenomInput.getText(), emailInput.getText(), telephoneInput.getText(), adresseInput.getText(), villeComboBox.getValue()));
            getAllClients();
        });

        deleteButton.setOnAction(deleteEvent -> {
            clientDAOIMPL.delete(Long.parseLong(idInput.getText()));
            getAllClients();

        });

        addVilleButton.setOnAction(addEvent -> {
            VilleModal villeModal = new VilleModal();
            Stage stage = (Stage) addVilleButton.getScene().getWindow();
            try{
                villeModal.setVilleObservableList(villeObservableList);
                villeModal.initModality(Modality.WINDOW_MODAL);
                villeModal.initOwner(stage);
                villeModal.initStage();
            }catch (Exception e){
                e.printStackTrace();
            }

        });

    }

    private void getAllClients(){
        //..From the database getting all clients using the DAO Client Class
        clientList = clientDAOIMPL.getAll();
        clientObservableList.setAll(clientList);
        dataTableClient.setItems(clientObservableList);
    }


    private void getSpecificClients(String key){
        clientObservableList.setAll(clientDAOIMPL.findallLocal(key, clientList));
        dataTableClient.setItems(clientObservableList);
    }



    @Override
    public void start(Stage stage) {
        stage.setTitle("Gestion Client");
        initPane();
        initButtons();
        initInputs();
        initLabel();
        initVilleComboBox();
        initGrid();
        initDataTable();
        drawElements();
        getAllClients();
        handleEvents();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
