package com.settings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO {
    protected static Connection connection = DataConnection.getSingleConnection();
    protected PreparedStatement statement;

    protected static long getNextId(String table_name) {
        String sql = "SELECT `AUTO_INCREMENT` " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'dataaccess_magasin' " +
                "AND TABLE_NAME = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,table_name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) return resultSet.getLong("AUTO_INCREMENT");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
