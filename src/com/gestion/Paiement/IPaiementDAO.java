package com.gestion.Paiement;

import com.gestion.Vente.Vente;
import com.settings.IDAO;

import java.util.List;

public interface IPaiementDAO extends IDAO<Paiement> {
    List<Paiement> getAllByVente(Vente vente);
    List<Paiement> getAllByType(String type);
    List<Paiement> getAllByVenteAndType(Vente vente, String type);
}
