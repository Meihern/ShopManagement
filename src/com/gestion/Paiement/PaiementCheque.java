package com.gestion.Paiement;

import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Vente.Vente;

import java.sql.Date;

public class PaiementCheque extends Paiement {

    private Cheque cheque;

    public PaiementCheque(Vente vente, Date date, boolean paid, Cheque cheque) {
        super(vente, date, paid);
        this.cheque = cheque;
    }

    public PaiementCheque(long id, Vente vente, Date date, boolean paid, Cheque cheque) {
        super(id, vente, date, paid);
        this.cheque = cheque;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    @Override
    public double getMontantPaye() {
        return cheque.getMontant();
    }

    @Override
    public String toString() {
        return  super.toString()+"PaiementCheque{" +
                "cheque=" + cheque +
                '}';
    }
}
