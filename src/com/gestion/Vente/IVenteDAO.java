package com.gestion.Vente;

import com.settings.IDAO;

import java.util.List;

public interface IVenteDAO extends IDAO<Vente> {
    List<Vente> findallByClientId(long id);
    void delete(Vente vente);
}
