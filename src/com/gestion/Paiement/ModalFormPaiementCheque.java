package com.gestion.Paiement;

import com.gestion.Client.Client;
import com.gestion.Client.ClientDAOIMPL;
import com.gestion.Paiement.Banque.Banque;
import com.gestion.Paiement.Banque.BanqueDAOIMPL;
import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Vente.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.util.List;

public class ModalFormPaiementCheque extends Dialog<Cheque> {

    private Vente current_vente;
    private BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
    private ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();

    //Initialisation des Inputs
    private DatePicker datePicker = new DatePicker();
    private TextField idInput = new TextField();
    private TextField numChequeTextField = new TextField();
    private ComboBox<Banque> banqueComboBox = new ComboBox<>();
    private ComboBox<Client> clientComboBox = new ComboBox<>();

    ModalFormPaiementCheque(Vente vente){
        super();
        current_vente = vente;
        setTitle("Ajout Chèque");
        setHeaderText("Veuillez remplir les informations du chèque");
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        initBanqueCombobox();
        initClientComboBox();
        initInputs();
        getDialogPane().setContent(new VBox(8, idInput, numChequeTextField, datePicker, banqueComboBox, clientComboBox));
        setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Cheque(numChequeTextField.getText(), banqueComboBox.getValue(), clientComboBox.getValue(),
                        Date.valueOf(datePicker.getValue()), current_vente.getTotal());
            }
            return null;
        });

    }


    private void initBanqueCombobox(){
        List<Banque> banqueList = banqueDAOIMPL.getAll();
        ObservableList<Banque> banqueObservableList = FXCollections.observableArrayList();
        banqueObservableList.setAll(banqueList);
        banqueComboBox.setItems(banqueObservableList);
    }

    private void initClientComboBox(){
        List<Client> clientList = clientDAOIMPL.getAll();
        ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
        clientObservableList.setAll(clientList);
        clientComboBox.setItems(clientObservableList);
    }

    private void initInputs(){
        idInput.setPromptText("ID");
        idInput.setDisable(true);
        datePicker.setPromptText("Date d'effet chèque");
        numChequeTextField.setPromptText("Numéro de chèque");
        banqueComboBox.setPromptText("banque");
        clientComboBox.setPromptText("client");
    }





}
