package com.gestion.Client.Ville;

import com.settings.AbstractDAO;
import com.settings.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VilleDaoIMPL extends AbstractDAO implements IDAO<Ville> {
    @Override
    public Ville get(long id) {
        String sql = "select * from dataaccess_magasin.ville where id ="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Ville(resultSet.getLong("id"), resultSet.getString("nom"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Ville ville) {
        String sql = "insert into dataaccess_magasin.ville(nom) values(?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1,ville.getNom());
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {
        String sql = "delete from dataaccess_magasin.ville where id ="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Ville ville) {
        String sql = "update dataaccess_magasin.ville set nom = ? where id = "+ville.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, ville.getNom());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Ville> getAll() {
        List<Ville> villeList = new ArrayList<>();
        String sql = "select * from dataaccess_magasin.ville";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                villeList.add(new Ville(resultSet.getLong("id"), resultSet.getString("nom")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return villeList;
    }

    public static long getNextVilleId(){
        return getNextId("ville");
    }
}
