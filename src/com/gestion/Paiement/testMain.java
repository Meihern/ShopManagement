package com.gestion.Paiement;

import com.gestion.Client.Client;
import com.gestion.Client.ClientDAOIMPL;
import com.gestion.Paiement.Banque.Banque;
import com.gestion.Paiement.Banque.BanqueDAOIMPL;
import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Paiement.Cheque.ChequeDAOIMPL;
import com.gestion.Paiement.Espece.Espece;
import com.gestion.Paiement.Espece.EspeceDAOIMPL;
import com.gestion.Paiement.Traite.Traite;
import com.gestion.Vente.Vente;
import com.gestion.Vente.VenteDAOIMPL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class testMain {
    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());
        PaiementDAOIMPL paiementDAOIMPL = new PaiementDAOIMPL();
        System.out.println(paiementDAOIMPL.getAll());
        /*BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
        Banque banque = banqueDAOIMPL.find(1);
        ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
        ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
        Client client = clientDAOIMPL.find(10);*/
        VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
        Vente vente = venteDAOIMPL.get(10);
        EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
        Espece espece = new Espece(2,700);
        especeDAOIMPL.create(espece);
        PaiementTraite paiementTraite = new PaiementTraite(vente, date, false, 4, espece);
        paiementDAOIMPL.create(paiementTraite);
        System.out.println(paiementDAOIMPL.get(paiementTraite.getId()));
        BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
        Banque banque = banqueDAOIMPL.get(1);
        ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
        Client client = clientDAOIMPL.get(10);
        Cheque cheque = new Cheque("124586",banque,client,date,500);
        Traite traite = new Traite(date,date,true,cheque,paiementTraite);
        List<Traite> traites = new ArrayList<>();
        traites.add(traite);
        paiementTraite.setTraites(traites);
        System.out.println(paiementTraite);
        /*PaiementEspece paiementEspece = new PaiementEspece(vente, date, true, espece);
        paiementDAOIMPL.create(paiementEspece);
        System.out.println(paiementDAOIMPL.get(paiementEspece.getId()));
        /*Cheque cheque = new Cheque("12758145485", banque, client, date, 61000 );
        chequeDAOIMPL.create(cheque);
        PaiementCheque paiementCheque = new PaiementCheque(vente, date, true, cheque);
        System.out.println(paiementCheque);
        paiementDAOIMPL.create(paiementCheque);
        System.out.println(paiementDAOIMPL.find(paiementCheque.getId()));
        //System.out.println(paiementDAOIMPL.findall());*/
    }
}
