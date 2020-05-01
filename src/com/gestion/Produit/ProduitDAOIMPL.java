package com.gestion.Produit;

import com.gestion.Produit.Categorie.Categorie;
import com.gestion.Produit.Categorie.CategorieDAOIMPL;
import com.settings.AbstractDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAOIMPL extends AbstractDAO implements IProduitDAO {

    @Override
    public List<Produit> findall(String key) {
        List<Produit> Plist = new ArrayList<>();
        String sql="select * from produit where designation LIKE ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+key+"%");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                CategorieDAOIMPL categorieDAOIMPL =  new CategorieDAOIMPL();
                Categorie categorie = categorieDAOIMPL.get(resultSet.getLong("categorie_id"));
                Produit p = new Produit(resultSet.getLong("id"),resultSet.getString("designation"),resultSet.getDouble("prix"), categorie);
                Plist.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Plist;
    }

    @Override
    public List<Produit> findallLocal(String key, List<Produit> produitList) {
        List<Produit> Plist = new ArrayList<Produit>();
        for (Produit produit:produitList) {
            if(produit.getDesignation().toUpperCase().contains(key.toUpperCase())) Plist.add(produit);
        }
        return Plist;
    }

    @Override
    public List<Produit> findallByCategorieId(long id) {
        return null;
    }

    @Override
    public Produit get(long id) {
        String sql = "select * from produit where id ="+ id +"";
        try{
            statement = connection.prepareStatement(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try{
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CategorieDAOIMPL categorieDAOIMPL = new CategorieDAOIMPL();
                Categorie categorie = categorieDAOIMPL.get(resultSet.getLong("categorie_id"));
                return new Produit(resultSet.getLong("id"), resultSet.getString("designation"), resultSet.getDouble("prix"), categorie);
                }
            }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Produit P) {
        String sql = "insert into produit(designation, prix, categorie_id) values(?,?,?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, P.getDesignation());
            statement.setDouble(2, P.getPrix());
            statement.setLong(3, P.getCategorie().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from produit where id= ? ";
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
            System.out.println("Suppression effectué avec succès");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Suppression échouée");
    }

    public void update(Produit P) {
        String sql = "update produit set designation = ?,prix = ?, categorie_id = ? where id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, P.getDesignation());
            statement.setDouble(2, P.getPrix());
            statement.setLong(3,  P.getCategorie().getId());
            statement.setLong(4, P.getId());
            statement.executeUpdate();
            System.out.println("Mise à jour effectué avec succès");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> Plist = new ArrayList<>();
        String sql="select * from produit";
        try{
            statement = connection.prepareStatement(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            ResultSet resultSet = statement.executeQuery();
            CategorieDAOIMPL categorieDAOIMPL =  new CategorieDAOIMPL();
            while(resultSet.next()){
                Categorie categorie = categorieDAOIMPL.get(resultSet.getLong("categorie_id"));
                Produit p = new Produit(resultSet.getLong("id"),resultSet.getString("designation"),resultSet.getDouble("prix"), categorie);
                Plist.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Plist;
    }

    public static long getNextProductId(){
       return getNextId("produit");
    }

}
