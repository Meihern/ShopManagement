package com.gestion.Paiement;

import com.gestion.Paiement.Espece.Espece;
import com.gestion.Vente.Vente;

import java.sql.Date;

public class PaiementEspece extends Paiement {

    private Espece espece;

    public PaiementEspece(){
        super();

    }

    public PaiementEspece(Vente vente, Date date, boolean paid, Espece espece) {
        super(vente, date, paid);
        this.espece = espece;
    }

    public PaiementEspece(long id, Vente vente, Date date, boolean paid, Espece espece) {
        super(id, vente, date, paid);
        this.espece = espece;
    }

    public Espece getEspece() {
        return espece;
    }

    @Override
    public double getMontantPaye(){
        return espece.getMontant();
    }

    public void setEspece(Espece espece) {
        this.espece = espece;
    }

    @Override
    public String toString() {

        return super.toString()+
                "PaiementEspece{" +
                "espece=" + espece +
                '}';
    }
}
