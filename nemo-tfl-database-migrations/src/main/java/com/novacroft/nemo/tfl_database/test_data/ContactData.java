package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public class ContactData extends SequenceData {

    public Integer createContact(PreparedStatement contactSequenceStatement, PreparedStatement contactInsertStatement,
                                 Integer customerId, String name, String value, String type) throws SQLException {
        Integer contactId = generateIdFromSequence(contactSequenceStatement);

        contactInsertStatement.setInt(1, contactId);
        contactInsertStatement.setString(2, USER_ID);
        contactInsertStatement.setInt(3, customerId);
        contactInsertStatement.setString(4, name);
        contactInsertStatement.setString(5, value);
        contactInsertStatement.setString(6, type);

        contactInsertStatement.executeUpdate();

        return contactId;
    }
}
