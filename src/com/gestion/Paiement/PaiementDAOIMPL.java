package com.gestion.Paiement;

import com.gestion.Paiement.Cheque.Cheque;
import com.gestion.Paiement.Cheque.ChequeDAOIMPL;
import com.gestion.Paiement.Espece.Espece;
import com.gestion.Paiement.Espece.EspeceDAOIMPL;
import com.gestion.Vente.Vente;
import com.gestion.Vente.VenteDAOIMPL;
import com.settings.AbstractDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAOIMPL extends AbstractDAO implements IPaiementDAO {

    @Override
    public Paiement get(long id) {
        String sql = "select * from paiement where id="+id+"";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
            VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
            ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
            while(resultSet.next()){
                Vente vente = venteDAOIMPL.get(resultSet.getLong("vente_id"));
                switch (resultSet.getString("type")){
                    case "PaiementEspece":
                        Espece espece = especeDAOIMPL.get(resultSet.getLong("espece_id"));
                        return new PaiementEspece(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                espece);
                    case "PaiementTraite":
                        long espece_id = resultSet.getLong("espece_id");
                        if(!resultSet.wasNull()){
                            Espece avanceEspece = especeDAOIMPL.get(espece_id);
                            return new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceEspece);
                        }
                        long cheque_id = resultSet.getLong("cheque_id");
                        if(!resultSet.wasNull()){
                            Cheque avanceCheque = chequeDAOIMPL.get(cheque_id);
                            return new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceCheque);
                        }

                    case "PaiementCheque":
                        Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                        return new PaiementCheque(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"), cheque);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return null;
    }


    @Override
    public void create(Paiement P) {

        String sql ="insert into paiement(date, paid, vente_id, type, cheque_id, espece_id, maxTraites) values(?,?,?,?,?,?,?) ";
        try {
            statement = connection.prepareStatement(sql);
            statement.setDate(1, P.getDate());
            statement.setBoolean(2, P.isPaid());
            statement.setLong(3, P.getVente().getId());
            statement.setString(4, P.getClass().getSimpleName());
            EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
            ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
            switch (P.getClass().getSimpleName()){
                case "PaiementEspece":
                    assert P instanceof PaiementEspece;
                    Espece espece = (((PaiementEspece) P).getEspece());
                    especeDAOIMPL.create(espece);
                    statement.setNull(5, Types.INTEGER);
                    statement.setLong(6, espece.getId());
                    statement.setNull(7, Types.INTEGER);
                    statement.executeUpdate();
                    break;
                case "PaiementCheque":
                    assert P instanceof PaiementCheque;
                    Cheque cheque = (((PaiementCheque) P).getCheque());
                    chequeDAOIMPL.create(cheque);
                    statement.setLong(5, cheque.getId());
                    statement.setNull(6, Types.INTEGER);
                    statement.setNull(7, Types.INTEGER);
                    statement.executeUpdate();
                    break;
                case "PaiementTraite":
                    assert P instanceof PaiementTraite;
                    statement.setInt(7, ((PaiementTraite) P).getMaxTraites());
                    if(((PaiementTraite)P).getAvanceEspece() != null){
                        Espece  avanceEspece = (((PaiementTraite) P).getAvanceEspece());
                        especeDAOIMPL.create(avanceEspece);
                        statement.setLong(6, (((PaiementTraite)P).getAvanceEspece().getId()));
                        statement.setNull(5, Types.INTEGER);
                    }
                    if(((PaiementTraite)P).getAvanceCheque() != null){
                        Cheque avanceCheque = (((PaiementTraite) P).getAvanceCheque());
                        chequeDAOIMPL.create(avanceCheque);
                        statement.setLong(5, (((PaiementTraite)P).getAvanceCheque().getId()));
                        statement.setNull(6, Types.INTEGER);
                    }
                    statement.executeUpdate();
                    break;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {
        String sql = "delete from paiement where id="+id+"";
        try{
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Paiement P) {

    }

    @Override
    public List<Paiement> getAll() {
        List<Paiement> paiementList = new ArrayList<>();
        String sql = "select * from paiement";
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
            VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
            EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
            while(resultSet.next()){
                Vente vente = venteDAOIMPL.get(resultSet.getLong("vente_id"));
                switch(resultSet.getString("type")){
                    case "PaiementEspece":
                        Espece espece = especeDAOIMPL.get(resultSet.getLong("espece_id"));
                        paiementList.add(new PaiementEspece(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                espece));
                        break;
                    case "PaiementTraite":
                        long espece_id = resultSet.getLong("espece_id");
                        if(!resultSet.wasNull()){
                            Espece avanceEspece = especeDAOIMPL.get(espece_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceEspece));
                        }
                        long cheque_id = resultSet.getLong("cheque_id");
                        if(!resultSet.wasNull()){
                            Cheque avanceCheque = chequeDAOIMPL.get(cheque_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceCheque));
                        }
                        break;
                    case "PaiementCheque":
                        Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                        paiementList.add(new PaiementCheque(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"), cheque));
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paiementList;
    }

    @Override
    public List<Paiement> getAllByVente(Vente vente) {
        String sql = "select * from paiement where vente_id="+vente.getId()+"";
        List<Paiement> paiementList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
            ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
            while(resultSet.next()){
                switch(resultSet.getString("type")){
                    case "PaiementEspece":
                        System.out.println(resultSet.getLong("espece_id"));
                        Espece espece = especeDAOIMPL.get(resultSet.getLong("espece_id"));
                        System.out.println(espece);
                        paiementList.add(new PaiementEspece(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                espece));
                        break;
                    case "PaiementTraite":
                        long espece_id = resultSet.getLong("espece_id");
                        if(!resultSet.wasNull()){
                            Espece avanceEspece = especeDAOIMPL.get(espece_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceEspece));
                        }
                        long cheque_id = resultSet.getLong("cheque_id");
                        if(!resultSet.wasNull()){
                            Cheque avanceCheque = chequeDAOIMPL.get(cheque_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceCheque));
                        }
                        break;
                    case "PaiementCheque":
                        Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                        paiementList.add(new PaiementCheque(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"), cheque));
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paiementList;
    }

    @Override
    public List<Paiement> getAllByType(String type) {
        List<Paiement> paiementList = new ArrayList<>();
        String sql = "select * from paiement where type="+type+"";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
            ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
            while(resultSet.next()){
                VenteDAOIMPL venteDAOIMPL = new VenteDAOIMPL();
                Vente vente = venteDAOIMPL.get(resultSet.getLong("vente_id"));
                switch(resultSet.getString("type")){
                    case "PaiementEspece":
                        Espece espece = especeDAOIMPL.get(resultSet.getLong("espece_id"));
                        paiementList.add(new PaiementEspece(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                espece));
                        break;
                    case "PaiementTraite":
                        long espece_id = resultSet.getLong("espece_id");
                        if(!resultSet.wasNull()){
                            Espece avanceEspece = especeDAOIMPL.get(espece_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceEspece));
                        }
                        long cheque_id = resultSet.getLong("cheque_id");
                        if(!resultSet.wasNull()){
                            Cheque avanceCheque = chequeDAOIMPL.get(cheque_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceCheque));
                        }
                        break;
                    case "PaiementCheque":
                        Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                        paiementList.add(new PaiementCheque(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"), cheque));
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paiementList;

    }

    @Override
    public List<Paiement> getAllByVenteAndType(Vente vente, String type) {
        String sql = "select * from paiement where vente_id= ? and type = ?";
        List<Paiement> paiementList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(sql);
            statement.setLong(1, vente.getId());
            statement.setString(2, type);
            ResultSet resultSet = statement.executeQuery();
            EspeceDAOIMPL especeDAOIMPL = new EspeceDAOIMPL();
            ChequeDAOIMPL chequeDAOIMPL = new ChequeDAOIMPL();
            while(resultSet.next()){
                switch(resultSet.getString("type")){
                    case "PaiementEspece":
                        Espece espece = especeDAOIMPL.get(resultSet.getLong("espece_id"));
                        paiementList.add(new PaiementEspece(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                espece));
                        break;
                    case "PaiementTraite":
                        long espece_id = resultSet.getLong("espece_id");
                        if(!resultSet.wasNull()){
                            Espece avanceEspece = especeDAOIMPL.get(espece_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceEspece));
                        }
                        long cheque_id = resultSet.getLong("cheque_id");
                        if(!resultSet.wasNull()){
                            Cheque avanceCheque = chequeDAOIMPL.get(cheque_id);
                            paiementList.add(new PaiementTraite(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"),
                                    resultSet.getInt("maxTraites"), avanceCheque));
                        }
                    case "PaiementCheque":
                        Cheque cheque = chequeDAOIMPL.get(resultSet.getLong("cheque_id"));
                        paiementList.add(new PaiementCheque(resultSet.getLong("id"), vente, resultSet.getDate("date"), resultSet.getBoolean("paid"), cheque));
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paiementList;
    }

    public static long getPaiementNextId(){
        return getNextId("paiement");
    }

}
