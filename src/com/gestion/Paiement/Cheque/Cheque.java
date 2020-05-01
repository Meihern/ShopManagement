package com.gestion.Paiement.Cheque;

import com.gestion.Client.Client;
import com.gestion.Paiement.Banque.Banque;

import java.sql.Date;

public class Cheque {
    private static long next_id = ChequeDAOIMPL.getChequeNextId();
    private long id;
    private String numCheque;
    private Banque banque;
    private Client proprietaire;
    private Date date;
    private double montant;


    public Cheque(String numCheque, Banque banque, Client proprietaire, Date date, double montant) {
        this.id = next_id;
        this.numCheque = numCheque;
        this.banque = banque;
        this.proprietaire = proprietaire;
        this.date = date;
        this.montant = montant;
        next_id++;
    }

    public Cheque(long id, String numCheque, Banque banque, Client proprietaire, Date date, double montant) {
        this.id = id;
        this.numCheque = numCheque;
        this.banque = banque;
        this.proprietaire = proprietaire;
        this.date = date;
        this.montant = montant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public Client getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Client proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "id=" + id +
                ", numCheque='" + numCheque + '\'' +
                ", banque=" + banque +
                ", client=" + proprietaire +
                ", montant=" + montant +
                '}';
    }
}
