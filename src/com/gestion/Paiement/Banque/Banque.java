package com.gestion.Paiement.Banque;

public class Banque {
    private static long next_id = BanqueDAOIMPL.getBanqueNextId();
    private long id;
    private String intitule;

    public Banque(String intitule) {
        this.id = next_id;
        this.intitule = intitule;
        next_id++;
    }

    public Banque(long id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return this.intitule;
    }
}
