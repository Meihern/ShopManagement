package com.gestion.Paiement.Banque;

import com.settings.AbstractDAO;
import com.settings.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanqueDAOIMPL extends AbstractDAO implements IDAO<Banque> {
    @Override
    public Banque get(long id) {
        String sql = "select * from banque where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Banque(resultSet.getLong("id"), resultSet.getString("intitule"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Banque B) {
        String sql = "insert into banque(intitule) values(?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, B.getIntitule());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from banque where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Banque B) {
        String sql = "update banque set intitule=? where id="+B.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, B.getIntitule());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Banque> getAll() {
        String sql = "select * from banque";
        List<Banque> banqueList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                banqueList.add(new Banque(resultSet.getLong("id"), resultSet.getString("intitule")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return banqueList;
    }

    public static long getBanqueNextId(){
        return getNextId("banque");
    }

}
