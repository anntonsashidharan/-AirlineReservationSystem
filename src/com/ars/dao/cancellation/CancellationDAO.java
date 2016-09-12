package com.ars.dao.cancellation;

import com.ars.domain.cancellation.Cancellation;
import com.ars.system.APPStatics;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by JJ on 9/10/16.
 */
public class CancellationDAO {
    private Connection connection;

    public CancellationDAO(Connection connection) {
        this.connection = connection;
    }

    public Cancellation createCancellation(Cancellation cancellation) throws Exception{
        String sqlNextCancellationID = "SELECT nextval('" + APPStatics.schemaName + ".\"seqCancellationIDGenerator\"')";
        String sqlInsertIntoCancellation= "INSERT INTO " + APPStatics.schemaName + ".\"CANCELLATION\" " +
                "(cancellation_id,request_date,request_time,refund_amount,approved_date,approved_time,approved_by) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement statement1 = connection.prepareStatement(sqlNextCancellationID);
        ResultSet resultSet1 = statement1.executeQuery();
        resultSet1.next();
        int nextCancellation = resultSet1.getInt("nextval");


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoCancellation);
        String bookingID = "CANCL" + String.format("%06d", nextCancellation);
        cancellation.setCancellationID(bookingID);


        statement2.setString(1, cancellation.getCancellationID());
        java.util.Date now = new java.util.Date();
        cancellation.setCancellationRequestDate(now);
        cancellation.setCancellationRequestTime(now);
        statement2.setDate(2, new Date(now.getTime()));
        statement2.setTime(3, new Time(now.getTime()));
        statement2.setFloat(4,0f);
        //statement2.setDate(5, new Date(cancellation.getCancellationApprovedDate().getTime()));
        //statement2.setTime(6, new Time(cancellation.getCancellationApprovedTime().getTime()));
        statement2.setDate(5, null);
        statement2.setTime(6, null);
        statement2.setString(7, null);

        statement2.execute();


        return cancellation;
    }


    public Cancellation approveCancellation(Cancellation cancellation) throws Exception{
        String sqlInsertIntoCancellation= "UPDATE " + APPStatics.schemaName + ".\"CANCELLATION\" SET " +
                "refund_amount=?," +
                "approved_date=?," +
                "approved_time=?," +
                "approved_by=? " +
                "WHERE cancellation_id = ?";


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoCancellation);

        java.util.Date now = new java.util.Date();
        statement2.setFloat(1, cancellation.getApprovedRefund());
        statement2.setDate(2, new Date(now.getTime()));
        statement2.setTime(3, new Time(now.getTime()));
        statement2.setString(4, cancellation.getApprovedBy().getUserName());
        statement2.setString(5, cancellation.getCancellationID());

        statement2.execute();


        return cancellation;
    }


}
