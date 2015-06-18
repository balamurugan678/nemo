package com.novacroft.nemo.tfl_database.test_data;

import com.novacroft.nemo.common.utils.MetaphoneUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public class CustomerData extends SequenceData {

    public Integer createCustomer(PreparedStatement customerSequenceStatement, PreparedStatement customerInsertStatement,
                                  String userName, String title, String firstName, String initials, String lastName,
                                  Integer addressId) throws SQLException {
        Integer customerId = generateIdFromSequence(customerSequenceStatement);

        customerInsertStatement.setInt(1, customerId);
        customerInsertStatement.setString(2, USER_ID);
        customerInsertStatement.setString(3, title);
        customerInsertStatement.setString(4, firstName);
        customerInsertStatement.setString(5, initials);
        customerInsertStatement.setString(6, lastName);
        customerInsertStatement.setInt(7, addressId);
        customerInsertStatement.setString(8, userName);
        customerInsertStatement.setString(9, getEmailAddress(lastName, firstName));
        customerInsertStatement.setString(10, getEmailAddress(lastName, firstName));
        customerInsertStatement.setString(11, MetaphoneUtil.encode(firstName));
        customerInsertStatement.setString(12, MetaphoneUtil.encode(lastName));

        customerInsertStatement.executeUpdate();

        return customerId;
    }

    private String getEmailAddress(String lastName, String firstName) {
        return String.format("%s.%s@example.com", firstName, lastName).toLowerCase();
    }
}
