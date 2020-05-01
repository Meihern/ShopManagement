package com.gestion.Produit.Categorie;

import java.util.Objects;

public class Categorie {
    private static long next_id = CategorieDAOIMPL.getNextCategorieId();
    private long id;
    private String intitule;

    public Categorie(long id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public Categorie(String intitule) {
        this.intitule = intitule;
        this.id = next_id;
        next_id++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id &&
                intitule.equals(categorie.intitule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, intitule);
    }

    @Override
    public String toString() {
        return this.intitule;
    }
}
