package com.gestion.Paiement;

import com.gestion.Vente.Vente;

import java.sql.Date;

public abstract class Paiement {
    protected static long next_id = PaiementDAOIMPL.getPaiementNextId();
    protected long id;
    protected Vente vente;
    protected Date date;
    protected boolean paid;


    public Paiement(Vente vente, Date date, boolean paid) {
        this.id = next_id;
        this.vente = vente;
        this.date = date;
        this.paid = paid;
        next_id++;
    }

    public Paiement(long id, Vente vente, Date date, boolean paid) {
        this.id = id;
        this.vente = vente;
        this.date = date;
        this.paid = paid;
    }

    public Paiement() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public abstract double getMontantPaye();

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", vente=" + vente +
                ", date=" + date +
                ", paid=" + paid +
                '}';
    }
}
