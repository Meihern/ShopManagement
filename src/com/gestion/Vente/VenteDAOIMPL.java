package com.gestion.Vente;

import com.gestion.Client.Client;
import com.gestion.Client.ClientDAOIMPL;
import com.gestion.Vente.LigneCommande.LigneCommande;
import com.gestion.Vente.LigneCommande.LigneCommandeDAOIMPL;
import com.settings.AbstractDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VenteDAOIMPL extends AbstractDAO implements IVenteDAO{



    @Override
    public Vente get(long id) {
        String sql = "select * from vente where id ="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
                LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
                Client client = clientDAOIMPL.get(resultSet.getLong("client_id"));
                Vente vente = new Vente(resultSet.getLong("id"), resultSet.getDate("date"), client);
                Set<LigneCommande> ligneCommandes = ligneCommandeDAOIMPL.findAllByVente(vente);
                vente.setLigneCommandes(ligneCommandes);
                return vente;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Vente V) {
        String sql = "insert into Vente(date, total, client_id) values(?,?,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setDate(1, V.getDate());
            statement.setDouble(2, V.getTotal());
            statement.setLong(3, V.getClient().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from vente where id = "+id+"";
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Vente V){
        String sql = "delete from vente where id ="+V.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
            for(LigneCommande ligneCommande:V.getLigneCommandes()){
                ligneCommandeDAOIMPL.delete(ligneCommande.getId());
            }
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Vente V) {
        String sql = "update vente set date=?, total=?, client_id=? where id="+V.getId()+"";
        try {
            statement = connection.prepareStatement(sql);
            statement.setDate(1, V.getDate());
            statement.setDouble(2, V.getTotal());
            statement.setLong(3, V.getClient().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vente> getAll() {
        String sql = "select * from vente";
        List<Vente> ventes = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
            LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
            while(resultSet.next()){
                Client client = clientDAOIMPL.get(resultSet.getLong("client_id"));
                Vente vente = new Vente(resultSet.getLong("id"), resultSet.getDate("date"), client);
                Set<LigneCommande> ligneCommandes = ligneCommandeDAOIMPL.findAllByVente(vente);
                vente.setLigneCommandes(ligneCommandes);
                ventes.add(vente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventes;
    }

    @Override
    public List<Vente> findallByClientId(long id) {
        String sql = "select * from vente where client_id="+id+"";
        List<Vente> ventes = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
            LigneCommandeDAOIMPL ligneCommandeDAOIMPL = new LigneCommandeDAOIMPL();
            while(resultSet.next()){
                Client client = clientDAOIMPL.get(resultSet.getLong("client_id"));
                Vente vente = new Vente(resultSet.getLong("id"), resultSet.getDate("date"), client);
                Set<LigneCommande> ligneCommandes = ligneCommandeDAOIMPL.findAllByVente(vente);
                vente.setLigneCommandes(ligneCommandes);
                ventes.add(vente);
            }
            return ventes;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long getNextVenteId(){
        return getNextId("vente");
    }

}
