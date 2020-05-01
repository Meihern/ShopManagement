package com.gestion.Paiement.Espece;

public class Espece {
    private static long next_id = EspeceDAOIMPL.getNextEspeceId();
    private long id;
    private double montant;


    public Espece(double montant){
        this.id = next_id;
        this.montant = montant;
    }

    public Espece(long id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Espece{" +
                "id=" + id +
                ", montant=" + montant +
                '}';
    }
}
