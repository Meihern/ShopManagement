package com.gestion.Client;

import com.gestion.Client.Ville.Ville;

import java.util.Objects;

public class Client {
    private static long next_id = ClientDAOIMPL.getNextClientId();
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private Ville ville;

    public Client(long id, String nom, String prenom, String email, String telephone, String adresse, Ville ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
        this.email = email;
    }

    public Client(String nom, String prenom, String email, String telephone, String adresse, Ville ville) {
        this.id = next_id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(nom, client.nom) &&
                Objects.equals(prenom, client.prenom) &&
                Objects.equals(email, client.email) &&
                Objects.equals(telephone, client.telephone) &&
                Objects.equals(adresse, client.adresse) &&
                Objects.equals(ville, client.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, email, telephone, adresse, ville);
    }

    @Override
    public String toString() {
        /*return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville=" + ville +
                '}';
    }*/
        return nom + " " + prenom;
    }
}
