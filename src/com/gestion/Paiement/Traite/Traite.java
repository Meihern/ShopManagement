package com.gestion.Paiement.Traite;

import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Paiement.PaiementTraite;

import java.sql.Date;

public class Traite {
    private static long next_id = TraiteDAOIMPL.getNextTraiteId();
    private long id;
    private Date date_prevue;
    private Date date_effet;
    private boolean paid;
    private Cheque cheque;
    private PaiementTraite paiementTraite;
    // private Espece espece;


    public Traite(Date date_prevue, Date date_effet, boolean paid, Cheque cheque, PaiementTraite paiementTraite) {
        this.id = next_id;
        this.date_prevue = date_prevue;
        this.date_effet = date_effet;
        this.paid = paid;
        this.cheque = cheque;
        this.paiementTraite = paiementTraite;
        next_id++;
    }

    public Traite(long id, Date date_prevue, Date date_effet, boolean paid, Cheque cheque, PaiementTraite paiementTraite) {
        this.id = id;
        this.date_prevue = date_prevue;
        this.date_effet = date_effet;
        this.paid = paid;
        this.cheque = cheque;
        this.paiementTraite = paiementTraite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate_prevue() {
        return date_prevue;
    }

    public void setDate_prevue(Date date_prevue) {
        this.date_prevue = date_prevue;
    }

    public Date getDate_effet() {
        return date_effet;
    }

    public void setDate_effet(Date date_effet) {
        this.date_effet = date_effet;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public PaiementTraite getPaiementTraite() {
        return paiementTraite;
    }

    public void setPaiementTraite(PaiementTraite paiementTraite) {
        this.paiementTraite = paiementTraite;
    }

    @Override
    public String toString() {
        return "Traite{" +
                "id=" + id +
                ", date_prevue=" + date_prevue +
                ", date_effet=" + date_effet +
                ", paid=" + paid +
                ", cheque=" + cheque +
                '}';
    }
}
