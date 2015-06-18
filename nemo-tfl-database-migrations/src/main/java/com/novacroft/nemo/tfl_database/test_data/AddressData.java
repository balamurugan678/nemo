package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public final class AddressData extends SequenceData {

    public Integer getGbCountryId(PreparedStatement gbCountrySelectStatement) throws SQLException {
        ResultSet gbCountryIdResultSet = gbCountrySelectStatement.executeQuery();

        if (gbCountryIdResultSet.next()) {
            return gbCountryIdResultSet.getInt(1);
        }
        throw new SQLException("No data found.");
    }

    public Integer createAddress(PreparedStatement addressSequenceStatement, PreparedStatement addressInsertStatement,
                                 String houseNameNumber, String street, String town, String postcode, Integer countryId)
            throws SQLException {
        Integer addressId = generateIdFromSequence(addressSequenceStatement);

        addressInsertStatement.setInt(1, addressId);
        addressInsertStatement.setString(2, USER_ID);
        addressInsertStatement.setString(3, houseNameNumber);
        addressInsertStatement.setString(4, street);
        addressInsertStatement.setString(5, town);
        addressInsertStatement.setString(6, postcode);
        addressInsertStatement.setInt(7, countryId);

        addressInsertStatement.executeUpdate();

        return addressId;
    }
}
