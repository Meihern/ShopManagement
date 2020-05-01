package com.gestion.Vente;

import com.gestion.Client.ClientDAOIMPL;
import com.gestion.Produit.ProduitDAOIMPL;
import com.gestion.Vente.LigneCommande.LigneCommande;
import com.gestion.Vente.LigneCommande.LigneCommandeDAOIMPL;
import java.sql.Date;
import java.util.*;

public class TestMain {

    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
        ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
        ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();
        LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
        Set<LigneCommande> ligneCommandes = new HashSet<>();
        Date date = new Date(utilDate.getTime());


        LigneCommande ligneCommande1 = new LigneCommande(produitDAOIMPL.get(7), 1);
        LigneCommande ligneCommande2 = new LigneCommande(produitDAOIMPL.get(6), 3);
        LigneCommande ligneCommande3 = new LigneCommande(produitDAOIMPL.get(10), 1);
        //LigneCommande _ligneCommande = new LigneCommande(produitDAOIMPL.find(10), 2);


        ligneCommandes.add(ligneCommande1);
        ligneCommandes.add(ligneCommande2);
        ligneCommandes.add(ligneCommande3);
        System.out.println(ligneCommande3);
       // ligneCommandes.add(_ligneCommande);
        //System.out.println(_ligneCommande.equals(ligneCommande3));
       // System.out.println(ligneCommande3);



        Vente vente = new Vente(date, clientDAOIMPL.get(9), ligneCommandes);
        venteDAOIMPL.create(vente);
        for(LigneCommande ligneCommande:ligneCommandes){
            ligneCommande.setVente(vente);
            ligneCommandeDAOIMPL.create(ligneCommande);
        }

    }





}
