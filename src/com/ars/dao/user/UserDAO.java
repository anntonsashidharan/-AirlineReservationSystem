package com.ars.dao.user;

import com.ars.domain.user.UserLogin;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;
import com.ars.util.security.Encrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by JJ on 4/30/16.
 */
public class UserDAO {
    /**
     * return null if user successfully created else returns error message
     * @param userName
     * @param password
     * @throws Exception
     */
    public String createInternetUser(String userName, String password) throws Exception {
        String sqlInsertIntoUser = "INSERT INTO " + APPStatics.schemaName + ".\"USER\" (user_name,password,email,email_confirmed,onetime_password) VALUES(?,?,?,?,?)";
        //String sqlInsertIntoInternetUser = "INSERT INTO " + APPStatics.schemaName + ".\"INTERNET_USER\" (email,user_name) VALUES(?,?)";
        String assignRole = "INSERT INTO " + APPStatics.schemaName + ".\"USER_ROLE\" (user_name,role_name) VALUES(?,?)";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction


            PreparedStatement statement1 = connection.prepareStatement(sqlInsertIntoUser);
            statement1.setString(1,userName);
            statement1.setString(2, Encrypt.hashFunction(password));
            statement1.setString(3,userName);
            statement1.setInt(4, 0); //not email confirmed
            int otp = (int)(Math.random()*1000000000);
            statement1.setString(5, +otp + ""); //a random number

            //PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoInternetUser);
            //statement2.setString(1,userName);
            //statement2.setString(2,userName);

            PreparedStatement statement3 = connection.prepareStatement(assignRole);
            statement3.setString(1,userName);
            statement3.setString(2,"internet_user");

            statement1.execute();
            //statement2.execute();
            statement3.execute();

            connection.commit();



        } catch (SQLException e) {
            e.printStackTrace();
            if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                return "User Already Exists";
            }else {
                return e.getSQLState();
            }

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public UserLogin getUserLogin(String userName, String password) throws Exception {
        UserLogin userLogin = null;
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"USER\" u, " + APPStatics.schemaName + ".\"USER_ROLE\" ur, "+ APPStatics.schemaName +".\"ROLE_PORTAL\" rp,  " + APPStatics.schemaName + ".\"PORTAL\" p  "+
                "WHERE u.user_name= ? AND password = ? " +
                "AND u.user_name = ur.user_name " +
                "AND ur.role_name = rp.role_name " +
                "AND rp.portal_name = p.portal_name";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,Encrypt.hashFunction(password));
            ResultSet resultSet = statement.executeQuery();
            HashSet<String> portals;
            HashSet<String> roles;

            if(resultSet.next()){
                userLogin = new UserLogin();
                userLogin.setUserName(userName);
                userLogin.setEmail(resultSet.getString("email"));
                userLogin.setFirstName(resultSet.getString("first_name"));
                userLogin.setLastName(resultSet.getString("last_name"));
                userLogin.setOtherName(resultSet.getString("other_name"));
                userLogin.setOneTimePassword(resultSet.getString("onetime_password"));
                userLogin.setMailConfirmed(resultSet.getInt("email_confirmed") == 1 ? true : false);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                userLogin.setDateOfBirth(resultSet.getDate("date_of_birth"));

                portals = new HashSet<String>();
                portals.add(resultSet.getString("portal_name"));

                roles = new HashSet<String>();
                roles.add(resultSet.getString("role_name"));

                while(resultSet.next()){
                    roles.add(resultSet.getString("role_name"));
                    portals.add(resultSet.getString("portal_name"));
                }
                userLogin.setPortals(new ArrayList<String>(portals));
                userLogin.setRoles(new ArrayList<String>(roles));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userLogin;
    }

    public UserLogin getUserLoginWithOneTimePW(String userName, String oneTimePassword) {
        UserLogin userLogin = null;
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"USER\" u, " + APPStatics.schemaName + ".\"USER_ROLE\" ur, "+ APPStatics.schemaName +".\"ROLE_PORTAL\" rp,  " + APPStatics.schemaName + ".\"PORTAL\" p  "+
                "WHERE u.user_name= ? AND onetime_password = ? " +
                "AND u.user_name = ur.user_name " +
                "AND ur.role_name = rp.role_name " +
                "AND rp.portal_name = p.portal_name";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,oneTimePassword);
            ResultSet resultSet = statement.executeQuery();
            HashSet<String> portals;
            HashSet<String> roles;

            if(resultSet.next()){
                userLogin = new UserLogin();
                userLogin.setUserName(userName);
                userLogin.setEmail(resultSet.getString("email"));
                userLogin.setFirstName(resultSet.getString("first_name"));
                userLogin.setLastName(resultSet.getString("last_name"));
                userLogin.setOtherName(resultSet.getString("other_name"));
                userLogin.setOneTimePassword(resultSet.getString("onetime_password"));
                userLogin.setMailConfirmed(resultSet.getInt("email_confirmed") == 1 ? true : false);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                userLogin.setDateOfBirth(resultSet.getDate("date_of_birth"));

                portals = new HashSet<String>();
                portals.add(resultSet.getString("portal_name"));

                roles = new HashSet<String>();
                roles.add(resultSet.getString("role_name"));

                while(resultSet.next()){
                    roles.add(resultSet.getString("role_name"));
                    portals.add(resultSet.getString("portal_name"));
                }
                userLogin.setPortals(new ArrayList<String>(portals));
                userLogin.setRoles(new ArrayList<String>(roles));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userLogin;
    }

    public void setEmailConfirmed(String userName, boolean emailConfirmed) throws Exception {
        String sqlUpdateUser = "UPDATE " + APPStatics.schemaName + ".\"USER\" SET email_confirmed=? " +
                "WHERE user_name = ?";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction


            PreparedStatement statement1 = connection.prepareStatement(sqlUpdateUser);
            statement1.setInt(1, emailConfirmed ? 1 : 0);
            statement1.setString(2,userName);
            statement1.execute();
            connection.commit();



        } catch (SQLException e) {
            throw new Exception(e.getSQLState());
        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }


    public String updateInternetUserByUserNameWithPassword(String userName,String password,String email,String firstName,String lastName,String otherName,Date dateOfBirth){
        String sqlUpdateUser = "UPDATE " + APPStatics.schemaName + ".\"USER\" SET password=?,email=?,first_name=?,last_name=?,other_name=?,date_of_birth=?" +
                "WHERE user_name = ?";
        String sqlUpdateInternetUser = "";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction


            PreparedStatement statement1 = connection.prepareStatement(sqlUpdateUser);
            statement1.setString(1,password);
            statement1.setString(2,email);
            statement1.setString(3,firstName);
            statement1.setString(4,lastName);
            statement1.setString(5,otherName);
            statement1.setDate(6, new java.sql.Date(dateOfBirth.getTime()));
            statement1.setString(7,userName);

            //PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoInternetUser);
            //statement2.setString(1,userName);
            //statement2.setString(2,userName);



            statement1.execute();
            //statement2.execute();

            connection.commit();



        } catch (SQLException e) {
            e.printStackTrace();
            //if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
            //    return "User Already Exists";
            //}else {
                return e.getSQLState();
            //}

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }


    public String updateInternetUserByUserNameWithoutPassword(String userName,String email,String firstName,String lastName,String otherName,Date dateOfBirth){
        String sqlUpdateUser = "UPDATE " + APPStatics.schemaName + ".\"USER\" SET email=?,first_name=?,last_name=?,other_name=?,date_of_birth=?" +
                "WHERE user_name = ?";
        String sqlUpdateInternetUser = "";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction


            PreparedStatement statement1 = connection.prepareStatement(sqlUpdateUser);
            statement1.setString(1,email);
            statement1.setString(2,firstName);
            statement1.setString(3,lastName);
            statement1.setString(4,otherName);
            statement1.setDate(5,new java.sql.Date(dateOfBirth.getTime()));
            statement1.setString(6,userName);

            //PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoInternetUser);
            //statement2.setString(1,userName);
            //statement2.setString(2,userName);

            statement1.execute();
            //statement2.execute();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            //if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
            //    return "User Already Exists";
            //}else {
            return e.getSQLState();
            //}

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    } 
}
