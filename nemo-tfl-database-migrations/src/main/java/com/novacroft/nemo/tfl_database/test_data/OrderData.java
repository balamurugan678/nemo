package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public final class OrderData extends SequenceData {

    public Integer createOrder(PreparedStatement orderSequenceStatement, PreparedStatement orderNumberStatement,
                               PreparedStatement orderInsertStatement, Integer customerId, Integer orderNumber,
                               java.util.Date orderDate, Integer totalAmount, String status) throws SQLException {
        Integer orderId = generateIdFromSequence(orderSequenceStatement);

        orderInsertStatement.setInt(1, orderId);
        orderInsertStatement.setString(2, USER_ID);
        orderInsertStatement.setInt(3, customerId);
        orderInsertStatement.setInt(4, (null != orderNumber) ? orderNumber : generateIdFromSequence(orderNumberStatement));
        orderInsertStatement.setDate(5, new java.sql.Date(((null != orderDate) ? orderDate : new java.util.Date()).getTime()));
        orderInsertStatement.setInt(6, totalAmount);
        orderInsertStatement.setString(7, status);

        orderInsertStatement.executeUpdate();

        return orderId;
    }
}
