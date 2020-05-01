package com.settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    private static Connection singleConnection = null;

    private DataConnection(){
        String url="jdbc:mysql://localhost:3306/dataaccess_magasin";
        try{
            singleConnection = DriverManager.getConnection(url, "root", "");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getSingleConnection(){
        if(singleConnection == null) new DataConnection();
        return singleConnection;

    }

}
