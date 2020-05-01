package com.gestion.Client.Ville;

import java.util.Objects;

public class Ville {
    private static long next_id = VilleDaoIMPL.getNextVilleId();
    private long id;
    private String nom;

    public Ville(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Ville(String nom) {
        this.nom = nom;
        this.id = next_id;
        next_id++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return id == ville.id &&
                nom.equals(ville.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
