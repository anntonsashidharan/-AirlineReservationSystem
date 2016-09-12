package com.ars.util.db;

import com.ars.system.APPStatics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by JJ on 4/13/16.
 */
public class DBConnection {
    private Connection connection;
    private static DBConnection dbConnection = new DBConnection();

    /**
     *
     */
    private DBConnection(){
        try{
            Class.forName(APPStatics.postgresDriver).newInstance();
            connection = DriverManager.getConnection(APPStatics.databaseURL, APPStatics.userName, APPStatics.password);
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * get instance of dbconnection in singlton pattern
     */
    public static DBConnection getInstance(){
        if(dbConnection==null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }


    public Connection getConnection() {
        return connection;
    }
}
