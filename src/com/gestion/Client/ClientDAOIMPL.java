package com.gestion.Client;

import com.gestion.Client.Ville.Ville;
import com.gestion.Client.Ville.VilleDaoIMPL;
import com.settings.AbstractDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOIMPL extends AbstractDAO implements IClientDAO {

    @Override
    public Client get(long id) {
        String sql = "select * from dataaccess_magasin.client where id ="+ id +"";
        try{
            statement = connection.prepareStatement(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try{
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                VilleDaoIMPL villeDaoIMPL = new VilleDaoIMPL();
                Ville ville = villeDaoIMPL.get(resultSet.getLong("ville_id"));
                return new Client(resultSet.getLong("id"), resultSet.getString("nom"), resultSet.getString("prenom"),
                        resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("adresse"),ville
                        );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Client C) {
        String sql="insert into dataaccess_magasin.client(nom,prenom,email,telephone,adresse,ville_id) values(?,?,?,?,?,?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, C.getNom());
            statement.setString(2, C.getPrenom());
            statement.setString(3, C.getEmail());
            statement.setString(4, C.getTelephone());
            statement.setString(5, C.getAdresse());
            statement.setLong(6, C.getVille().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from dataaccess_magasin.client where id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client C) {
        String sql = "update dataaccess_magasin.client set nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ?, ville_id = ? where id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, C.getNom());
            statement.setString(2, C.getPrenom());
            statement.setString(3, C.getEmail());
            statement.setString(4, C.getTelephone());
            statement.setString(5, C.getAdresse());
            statement.setLong(6, C.getVille().getId());
            statement.setLong(7, C.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        String sql="select * from dataaccess_magasin.client";
        try{
            statement = connection.prepareStatement(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            ResultSet resultSet = statement.executeQuery();
            VilleDaoIMPL villeDaoIMPL = new VilleDaoIMPL();
            while(resultSet.next()){
                Ville ville = villeDaoIMPL.get(resultSet.getLong("ville_id"));
                Client client = new Client(resultSet.getLong("id"),resultSet.getString("nom"),resultSet.getString("prenom"), resultSet.getString("email"),
                        resultSet.getString("telephone"),resultSet.getString("adresse"),ville);
                clientList.add(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return clientList;
    }

    @Override
    public List<Client> findall(String key) {
        List<Client> Clist = new ArrayList<>();
        String sql="select * from dataaccess_magasin.client where nom LIKE ? or prenom like ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            ResultSet resultSet = statement.executeQuery();
            VilleDaoIMPL villeDaoIMPL = new VilleDaoIMPL();
            while(resultSet.next()){
                Ville ville = villeDaoIMPL.get(resultSet.getLong("ville_id"));
                Client client = new Client(resultSet.getLong("id"),resultSet.getString("nom"),resultSet.getString("prenom"), resultSet.getString("email"),
                        resultSet.getString("telephone"),resultSet.getString("adresse"),ville);
                Clist.add(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Clist;
    }

    @Override
    public List<Client> findallLocal(String key, List<Client> clientList) {
        List<Client> Clist = new ArrayList<Client>();
        for (Client client:clientList) {
            if(client.getNom().toUpperCase().contains(key.toUpperCase()) || client.getPrenom().toUpperCase().contains(key.toUpperCase())) Clist.add(client);
        }
        return Clist;
    }

    @Override
    public List<Client> findallByVilleId(long id) {
        return null;
    }

    public static long getNextClientId(){
        return getNextId("client");
    }
}
