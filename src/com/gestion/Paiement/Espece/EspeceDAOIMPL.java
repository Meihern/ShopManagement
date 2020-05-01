package com.gestion.Paiement.Espece;

import com.settings.AbstractDAO;
import com.settings.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspeceDAOIMPL extends AbstractDAO implements IDAO<Espece> {
    @Override
    public Espece get(long id) {
        String sql = "select * from espece where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Espece(resultSet.getLong("id"), resultSet.getDouble("montant"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Espece E) {
        String sql = "insert into espece(montant) values(?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, E.getMontant());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from espece where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Espece E) {
        String sql = "update espece set montant = ? where id="+E.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, E.getMontant());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Espece> getAll() {
        String sql = "select * from espece";
        List<Espece> especeList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                especeList.add(new Espece(resultSet.getLong("id"), resultSet.getDouble("montant")));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return especeList;
    }

    public static long getNextEspeceId(){
        return getNextId("espece");
    }

}
