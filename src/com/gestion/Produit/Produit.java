package com.gestion.Produit;

import com.gestion.Produit.Categorie.Categorie;

import java.util.Objects;

public class Produit {
    private static long next_id = ProduitDAOIMPL.getNextProductId();
    private long id;
    private String designation;
    private double prix;
    private Categorie categorie;

    public Produit(long id, String designation, double prix, Categorie categorie) {
        this.id = id;
        this.designation = designation;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Produit(String designation, double prix, Categorie categorie) {
        this.categorie = categorie;
        this.id = next_id;
        this.designation = designation;
        this.prix = prix;
        next_id++;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return id == produit.id &&
                Double.compare(produit.prix, prix) == 0 &&
                Objects.equals(designation, produit.designation) &&
                Objects.equals(categorie, produit.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, prix, categorie);
    }

    @Override
    public String toString() {
        /*return "Produit{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", prix=" + prix +
                '}';
    }*/
        return designation;
    }
}
