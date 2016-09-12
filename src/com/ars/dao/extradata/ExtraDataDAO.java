package com.ars.dao.extradata;

import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by JJ on 7/18/16.
 */
public class ExtraDataDAO {
    public String getData(String name){
        String data=null;
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"EXTRA_DATA\" ed " +
                "WHERE ed.name = ? ";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                data = resultSet.getString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
