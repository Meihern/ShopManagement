package com.gestion.Paiement.Traite;

import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Paiement.Cheque.ChequeDAOIMPL;
import com.gestion.Paiement.PaiementDAOIMPL;
import com.gestion.Paiement.PaiementTraite;
import com.settings.AbstractDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraiteDAOIMPL extends AbstractDAO implements ITraiteDAO {

    @Override
    public Traite get(long id) {
        String sql = "select * form traite where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                PaiementDAOIMPL paiementDAOIMPL = new PaiementDAOIMPL();
                PaiementTraite paiementTraite = (PaiementTraite) paiementDAOIMPL.get(resultSet.getLong("paiement_id"));
                ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
                Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                return new Traite(resultSet.getLong("id"), resultSet.getDate("date_prevue"),
                        resultSet.getDate("date_effet"),
                        resultSet.getBoolean("paid"), cheque, paiementTraite );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Traite T) {
        String sql = "insert into traite(date_prevue, date_effet, montant, cheque_id, paiement_id) values(?,?,?,?,?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from traite where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Traite T) {
        String sql="update traite set date_prevue=?, date_effet=?, montant=?, paid=?, cheque_id=?, paiement_id=? where id="+T.getId()+"";
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Traite> getAll() {
        List<Traite> traiteList = new ArrayList<>();
        String sql="select * from traite";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                PaiementDAOIMPL paiementDAOIMPL = new PaiementDAOIMPL();
                PaiementTraite paiementTraite = (PaiementTraite) paiementDAOIMPL.get(resultSet.getLong("paiement_id"));
                ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
                Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                 traiteList.add(new Traite(resultSet.getLong("id"), resultSet.getDate("date_prevue"),
                        resultSet.getDate("date_effet"),
                        resultSet.getBoolean("paid"), cheque, paiementTraite ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return traiteList;
    }

    @Override
    public List<Traite> getAllByPaiementTraite(PaiementTraite paiementTraite) {
        List<Traite> traiteList = new ArrayList<>();
        String sql="select * from traite where paiement_id="+paiementTraite.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
                Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                traiteList.add(new Traite(resultSet.getLong("id"), resultSet.getDate("date_prevue"),
                        resultSet.getDate("date_effet"),
                        resultSet.getBoolean("paid"), cheque, paiementTraite ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return traiteList;
    }

    public static long getNextTraiteId(){
        return getNextId("traite");
    }

}
