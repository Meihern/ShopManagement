package com.gestion.Vente;

import com.gestion.Client.Client;
import com.gestion.Vente.LigneCommande.LigneCommande;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

public class Vente {
    private static long next_id = VenteDAOIMPL.getNextVenteId();
    private long id;
    private Date date;
    private Client client;
    private Set<LigneCommande> ligneCommandes;
    private double total = 0;


    public Vente(long id,Date date, Client client, Set<LigneCommande> ligneCommandes) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.ligneCommandes = ligneCommandes;
        setTotal();
    }

    public Vente(Date date, Client client, Set<LigneCommande> ligneCommandes) {
        this.id = next_id;
        this.date = date;
        this.client = client;
        this.ligneCommandes = ligneCommandes;
        next_id++;
        setTotal();
    }

    public Vente(long id, Date date, Client client){
        this.id = id;
        this.date = date;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
        setTotal();
    }

    private void setTotal(){
        for(LigneCommande ligneCommande:ligneCommandes){
            total += ligneCommande.getSous_total();
        }
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Vente{" +
                "id=" + id +
                ", date=" + date +
                ", client=" + client +
                ", ligneCommandes=" + ligneCommandes +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vente vente = (Vente) o;
        return id == vente.id &&
                Double.compare(vente.total, total) == 0 &&
                date.equals(vente.date) &&
                client.equals(vente.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, client, total);
    }
}
