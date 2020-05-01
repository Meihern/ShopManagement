package com.gestion.Paiement.Cheque;

import com.gestion.Client.Client;
import com.gestion.Client.ClientDAOIMPL;
import com.gestion.Paiement.Banque.Banque;
import com.gestion.Paiement.Banque.BanqueDAOIMPL;
import com.settings.AbstractDAO;
import com.settings.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChequeDAOIMPL extends AbstractDAO implements IDAO<Cheque> {
    @Override
    public Cheque get(long id) {
        String sql = "select * from cheque where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
                ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
                Banque banque = banqueDAOIMPL.get(resultSet.getLong("banque_id"));
                Client proprietaire = clientDAOIMPL.get(resultSet.getLong("proprietaire_id"));
                return new Cheque(resultSet.getLong("id"), resultSet.getString("numCheque"), banque, proprietaire,
                        resultSet.getDate("date"), resultSet.getDouble("montant"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Cheque Ch) {
        String sql = "insert into cheque(numCheque,banque_id,proprietaire_id,montant,date) values(?,?,?,?,?)";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1,Ch.getNumCheque());
            statement.setLong(2, Ch.getBanque().getId());
            statement.setLong(3, Ch.getProprietaire().getId());
            statement.setDouble(4, Ch.getMontant());
            statement.setDate(5, Ch.getDate());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from cheque where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Cheque Ch) {
        String sql = "update cheque set numCheque=?, banque_id=?, proprietaire_id=?, montant=?, date=? where id="+Ch.getId()+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, Ch.getNumCheque());
            statement.setLong(2, Ch.getBanque().getId());
            statement.setLong(3, Ch.getProprietaire().getId());
            statement.setDouble(4, Ch.getMontant());
            statement.setDate(5, Ch.getDate());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Cheque> getAll() {
        String sql = "select * from cheque";
        List<Cheque> chequeList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                BanqueDAOIMPL banqueDAOIMPL = new BanqueDAOIMPL();
                ClientDAOIMPL clientDAOIMPL = new ClientDAOIMPL();
                Banque banque = banqueDAOIMPL.get(resultSet.getLong("banque_id"));
                Client proprietaire = clientDAOIMPL.get(resultSet.getLong("client_id"));
                chequeList.add(new Cheque(resultSet.getLong("id"), resultSet.getString("numCheque"),
                        banque, proprietaire, resultSet.getDate("date"), resultSet.getDouble("montant")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return chequeList;
    }

    public static long getChequeNextId(){
        return getNextId("cheque");
    }

}
