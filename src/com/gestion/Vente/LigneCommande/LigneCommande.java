package com.gestion.Vente.LigneCommande;

import com.gestion.Produit.Produit;
import com.gestion.Vente.Vente;

import java.util.Objects;

public class LigneCommande {
    private static long next_id = LigneCommandeDAOIMPL.getNextLigneCommandeId();
    private long id;
    private Produit produit;
    private Vente vente;
    private int quantite;
    private double sous_total = 0;


    public LigneCommande(long id, Produit produit, Vente vente, int quantite) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        this.vente = vente;
        setSous_total();
    }

    public LigneCommande(Produit produit, Vente vente, int quantite) {
        this.id = next_id;
        this.produit = produit;
        this.vente = vente;
        this.quantite = quantite;
        next_id++;
        setSous_total();
    }

    public LigneCommande(Produit produit, int quantite){
        this.id = next_id;
        this.produit = produit;
        this.quantite = quantite;
        next_id++;
        setSous_total();
    }

    public LigneCommande(long id, Produit produit, int quantite){
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        setSous_total();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
        setSous_total();
    }

    private void setSous_total(){
        sous_total = quantite * produit.getPrix();
    }

    public double getSous_total() {
        return sous_total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigneCommande that = (LigneCommande) o;

        if(produit.equals(that.produit) && Objects.equals(vente, that.vente)){
            ((LigneCommande)o).setQuantite(((LigneCommande)o).quantite + quantite);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, vente);
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "id=" + id +
                ", produit=" + produit +
                ", quantite=" + quantite +
                ", sous_total=" + sous_total +
                '}';
    }
}
