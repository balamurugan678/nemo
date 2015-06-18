package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public class MockCubicData extends SequenceData {

    public void createOysterCard(PreparedStatement mockOysterCardStatement, PreparedStatement mockPrePayValueStatement,
                                 PreparedStatement mockPrePayTicketStatement, PreparedStatement mockPrePayValuePendingStatement,
                                 PreparedStatement mockPrePayTicketPendingStatement, String cardNumber) throws SQLException {

        mockOysterCardStatement.setString(1, USER_ID);
        mockOysterCardStatement.setString(2, cardNumber);
        mockOysterCardStatement.executeUpdate();

        mockPrePayValueStatement.setString(1, USER_ID);
        mockPrePayValueStatement.setString(2, cardNumber);
        mockPrePayValueStatement.executeUpdate();

        mockPrePayTicketStatement.setString(1, USER_ID);
        mockPrePayTicketStatement.setString(2, cardNumber);
        mockPrePayTicketStatement.executeUpdate();

        mockPrePayValuePendingStatement.setString(1, USER_ID);
        mockPrePayValuePendingStatement.setString(2, cardNumber);
        mockPrePayValuePendingStatement.executeUpdate();

        mockPrePayTicketPendingStatement.setString(1, USER_ID);
        mockPrePayTicketPendingStatement.setString(2, cardNumber);
        mockPrePayTicketPendingStatement.executeUpdate();
    }
}
