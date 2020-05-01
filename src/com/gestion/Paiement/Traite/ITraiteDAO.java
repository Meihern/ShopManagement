package com.gestion.Paiement.Traite;

import com.gestion.Paiement.PaiementTraite;
import com.settings.IDAO;

import java.util.List;

public interface ITraiteDAO extends IDAO<Traite> {
    List<Traite> getAllByPaiementTraite(PaiementTraite paiementTraite);
}
