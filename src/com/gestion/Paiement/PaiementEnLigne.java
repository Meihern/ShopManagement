package com.gestion.Paiement;

import com.gestion.Vente.Vente;

import java.sql.Date;

public class PaiementEnLigne extends Paiement {

    public PaiementEnLigne(Vente vente, Date date, boolean paid) {
        super(vente, date, paid);
    }

    public PaiementEnLigne(long id, Vente vente, Date date, boolean paid) {
        super(id, vente, date, paid);
    }

    @Override
    public double getMontantPaye() {
        return 0;
    }
}
