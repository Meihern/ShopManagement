package com.gestion.Vente.LigneCommande;

import com.gestion.Vente.Vente;
import com.settings.IDAO;
import java.util.Set;

public interface ILigneCommandeDAO extends IDAO<LigneCommande> {
    Set<LigneCommande> findAllByVente(Vente vente);
    void deleteByVenteIdAndProduitId(long idProduit, long idVente);
}
