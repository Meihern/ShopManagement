package com.gestion.Paiement;

import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Paiement.Espece.Espece;
import com.gestion.Paiement.Traite.Traite;
import com.gestion.Vente.Vente;

import java.sql.Date;
import java.util.List;

public class PaiementTraite extends Paiement {
    private int maxTraites;
    private Cheque avanceCheque;
    private Espece avanceEspece;
    private List<Traite> traites;


    public PaiementTraite(Vente vente, Date date, boolean paid, int maxTraites) {
        super(vente, date, paid);
        this.maxTraites = maxTraites;
    }

    public PaiementTraite(long id, Vente vente, Date date, boolean paid, int maxTraites) {
        super(id, vente, date, paid);
        this.maxTraites = maxTraites;
    }

    public PaiementTraite(Vente vente, Date date, boolean paid, int maxTraites, Cheque avanceCheque) {
        super(vente, date, paid);
        this.maxTraites = maxTraites;
        this.avanceCheque = avanceCheque;
    }

    public PaiementTraite(long id, Vente vente, Date date, boolean paid, int maxTraites, Cheque avanceCheque) {
        super(id, vente, date, paid);
        this.maxTraites = maxTraites;
        this.avanceCheque = avanceCheque;
    }

    public PaiementTraite(Vente vente, Date date, boolean paid, int maxTraites, Espece avanceEspece) {
        super(vente, date, paid);
        this.maxTraites = maxTraites;
        this.avanceEspece = avanceEspece;
    }

    public PaiementTraite(long id, Vente vente, Date date, boolean paid, int maxTraites, Espece avanceEspece) {
        super(id, vente, date, paid);
        this.maxTraites = maxTraites;
        this.avanceEspece = avanceEspece;
    }

    public int getMaxTraites() {
        return maxTraites;
    }

    public void setMaxTraites(int maxTraites) {
        this.maxTraites = maxTraites;
    }

    public Cheque getAvanceCheque() {
        return avanceCheque;
    }

    public void setAvanceCheque(Cheque avanceCheque) {
        this.avanceCheque = avanceCheque;
    }

    public Espece getAvanceEspece() {
        return avanceEspece;
    }

    public void setAvanceEspece(Espece avanceEspece) {
        this.avanceEspece = avanceEspece;
    }

    public List<Traite> getTraites() {
        return traites;
    }

    public void setTraites(List<Traite> traites) {
        this.traites = traites;
    }

    @Override
    public double getMontantPaye() {
        double totalPaye = 0;
        if(avanceEspece != null) totalPaye += avanceEspece.getMontant();
        if(avanceCheque != null) totalPaye += avanceCheque.getMontant();

        for(Traite traite:traites){
            totalPaye += traite.getCheque().getMontant();
        }
        return totalPaye;
    }

    @Override
    public String toString() {
        return super.toString()+"PaiementTraite{" +
                "maxTraites=" + maxTraites +
                ", avanceCheque=" + avanceCheque +
                ", avanceEspece=" + avanceEspece +
                ", traites=" + traites +
                '}';
    }
}
