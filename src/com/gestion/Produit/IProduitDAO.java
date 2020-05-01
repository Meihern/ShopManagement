package com.gestion.Produit;

import com.settings.IDAO;

import java.util.List;

public interface IProduitDAO extends IDAO<Produit> {
    List<Produit> findall(String key);
    List<Produit> findallLocal(String key, List<Produit> produitList);
    List<Produit> findallByCategorieId(long id);
}
