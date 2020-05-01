package com.settings;

import com.gestion.Client.GuiClient;
import com.gestion.Produit.GuiProduit;
import com.gestion.Vente.GuiVente;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuScroll {
    private static MenuBar menuBar = null;
    private static Menu menu = new Menu("Gestion");
    private static MenuItem menuProduit = new MenuItem("Produits");
    private static MenuItem menuClient = new MenuItem("Clients");
    private static MenuItem menuVente = new MenuItem("Ventes");
    private static Stage stage;

    private static void initMenu(){
        menu.getItems().addAll(menuProduit, menuClient, menuVente);
        menuBar.getMenus().add(menu);
    }

    private static void handleEvents(){
        menuProduit.setOnAction( action -> {
            GuiProduit guiProduit = new GuiProduit();
            stage = (Stage) menuBar.getScene().getWindow();
            try {
                guiProduit.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        menuClient.setOnAction( action -> {
            GuiClient guiClient = new GuiClient();
            stage = (Stage) menuBar.getScene().getWindow();
            try {
                guiClient.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        menuVente.setOnAction( action -> {
            GuiVente guiVente = new GuiVente();
            stage = (Stage) menuBar.getScene().getWindow();
            try {
                guiVente.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private MenuScroll(){
        menuBar = new MenuBar();
        initMenu();
        handleEvents();
    }

    public static MenuBar getMenuScroll(){
        if(menuBar == null) new MenuScroll();
        return menuBar;
    }


}
