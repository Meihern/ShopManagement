package com.gestion.Produit.Categorie;

import com.settings.AbstractDAO;
import com.settings.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOIMPL extends AbstractDAO implements IDAO<Categorie> {
    @Override
    public Categorie get(long id) {
        String sql = "select * from dataaccess_magasin.categorie where id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try{
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            return new Categorie(resultSet.getLong("id"), resultSet.getString("nom"));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Categorie C) {
        String sql = "insert into dataaccess_magasin.categorie(nom) values(?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, C.getIntitule());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from dataaccess_magasin.categorie where id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Categorie C) {
        String sql = "update dataaccess_magasin.categorie set nom = ? where id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, C.getIntitule());
            statement.setLong(2, C.getId());
            statement.executeUpdate();
            System.out.println("Mise à jour effectué avec succès");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Categorie> getAll() {
        List<Categorie> categorieList = new ArrayList<>();
        String sql="select * from dataaccess_magasin.categorie";
        try{
            statement = connection.prepareStatement(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Categorie C = new Categorie(resultSet.getLong("id"),resultSet.getString("nom"));
                categorieList.add(C);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return categorieList;
    }

    public static long getNextCategorieId(){
        return getNextId("categorie");
    }

    }

