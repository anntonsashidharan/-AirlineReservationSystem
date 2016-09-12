package com.ars.util.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by JJ on 9/6/16.
 */
public class Transaction {
    public static Connection beginTransaction() throws Exception {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    public static void endTransaction(Connection connection) throws Exception {
        try {
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new Exception(e.getSQLState());
        } finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }
}
