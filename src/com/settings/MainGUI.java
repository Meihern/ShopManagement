package com.settings;

import com.gestion.Client.GuiClient;
import com.gestion.Produit.GuiProduit;
import com.gestion.Produit.ProduitDAOIMPL;
import com.gestion.Vente.GuiVente;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends Application {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;

    ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();

    private BorderPane root = new BorderPane();
    private Scene scene = null;
    private Stage stage;
    //Initialisation des conteneurs
    private HBox mainTitleHbox = new HBox();
    private VBox buttonsMenuVbox = new VBox();
   // private VBox imageVBox = new VBox();
    //Initialisation background
    private Image image = new Image(new FileInputStream("images/background.png"));
    private BackgroundImage backgroundImage;

    //Initialisation des Labels
   // private Label mainTitleLabel = new Label("Gestion Magasin");

    //Initialisation des boutons
    private Button produitButton = new Button("Gestion Produits");
    private Button clientButton = new Button("Gestion Clients");
    private Button venteButton = new Button("Gestion Ventes");
    private List<Button> managementButtons = new ArrayList<>();
    //5B6A7D
    public MainGUI() throws FileNotFoundException {
    }


    private void initPane(){
        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./Css/Mycss.css");
        //mainTitleHbox.getStyleClass().add("custom-pane-top");
        root.setLeft(buttonsMenuVbox);
    }

    private void initButtons(){
        managementButtons.add(produitButton);
        managementButtons.add(clientButton);
        managementButtons.add(venteButton);
        for(Button button: managementButtons){
            button.getStyleClass().add("menuButton");
            button.setPrefSize(180, 50);
            /*.setTranslateX(50);
            button.setTranslateY(100);*/
        }
    }

    private void setBackgroundImage(){
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    }

    private void handleEvents(){
        produitButton.setOnAction( action -> {
            GuiProduit guiProduit = new GuiProduit();
            stage = (Stage) produitButton.getScene().getWindow();
            try {
                guiProduit.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientButton.setOnAction( action -> {
            GuiClient guiClient = new GuiClient();
            stage = (Stage) clientButton.getScene().getWindow();
            try {
                guiClient.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        venteButton.setOnAction( action -> {
            GuiVente guiVente = new GuiVente();
            stage = (Stage) venteButton.getScene().getWindow();
            try {
                guiVente.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void drawElements(){
        for(Button button:managementButtons){
            buttonsMenuVbox.getChildren().add(button);
        }
        root.setBackground(new Background(backgroundImage));
    }

    @Override
    public void start(Stage stage) throws Exception {
        initPane();
        initButtons();
        setBackgroundImage();
        drawElements();
        handleEvents();
        stage.setScene(scene);
        stage.setTitle("Gestion Magasin");
        stage.show();
    }
}
