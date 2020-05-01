package com.gestion.Vente.LigneCommande;

import com.gestion.Produit.Produit;
import com.gestion.Produit.ProduitDAOIMPL;
import com.gestion.Vente.Vente;
import com.gestion.Vente.VenteDAOIMPL;
import com.settings.AbstractDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LigneCommandeDAOIMPL extends AbstractDAO implements ILigneCommandeDAO {



    @Override
    public LigneCommande get(long id) {
        String sql = "select * from ligne_commande where id = "+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();
                VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
                Produit produit = produitDAOIMPL.get(resultSet.getLong("produit_id"));
                Vente vente = venteDAOIMPL.get(resultSet.getLong("vente_id"));
                return new LigneCommande(resultSet.getLong("id"), produit, vente, resultSet.getInt("quantite"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(LigneCommande LC) {
        String sql = "insert into ligne_commande(quantite, sous_total, produit_id, vente_id) values(?,?,?,?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setInt(1,LC.getQuantite());
            statement.setDouble(2, LC.getSous_total());
            statement.setLong(3, LC.getProduit().getId());
            statement.setLong(4, LC.getVente().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from ligne_commande where id ="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(LigneCommande LC) {
        String sql = "update ligne_commande set quantite = ?, sous_total = ?, produit_id = ?, vente_id = ? where id = "+LC.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.setInt(1, LC.getQuantite());
            statement.setDouble(2, LC.getSous_total());
            statement.setLong(3, LC.getProduit().getId());
            statement.setLong(4, LC.getVente().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<LigneCommande> getAll() {
        String sql = "select * from ligne_commande";
        List<LigneCommande> ligneCommandes = new ArrayList<>();
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();
            VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
            while(resultSet.next()){
                Produit produit = produitDAOIMPL.get(resultSet.getLong("produit_id"));
                Vente vente = venteDAOIMPL.get(resultSet.getLong("vente_id"));
                ligneCommandes.add(new LigneCommande(resultSet.getLong("id"), produit, vente, resultSet.getInt("quantite") ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ligneCommandes;
    }

    @Override
    public Set<LigneCommande> findAllByVente(Vente vente) {
        String sql = "select * from ligne_commande where vente_id ="+vente.getId()+"";
        Set<LigneCommande> ligneCommandes = new HashSet<>();
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ProduitDAOIMPL produitDAOIMPL = new ProduitDAOIMPL();
            while (resultSet.next()){
                Produit produit = produitDAOIMPL.get(resultSet.getLong("produit_id"));
                ligneCommandes.add(new LigneCommande(resultSet.getLong("id"), produit, vente, resultSet.getInt("quantite")));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        return ligneCommandes;

    }

    @Override
    public void deleteByVenteIdAndProduitId(long idProduit, long idVente) {
        String sql = "delete from ligne_commande where vente_id="+idVente+" and produit_id="+idProduit+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static long getNextLigneCommandeId(){
        return getNextId("ligne_commande");
    }


}
