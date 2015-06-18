package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Map result set record to customer entity
 */
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Customer(resultSet.getLong("ID"), resultSet.getString("CREATEDUSERID"), resultSet.getDate("CREATEDDATETIME"),
                resultSet.getString("MODIFIEDUSERID"), resultSet.getDate("MODIFIEDDATETIME"), resultSet.getString("STATUS"),
                resultSet.getString("TITLE"),resultSet.getString("FIRSTNAME"), resultSet.getString("INITIALS"), 
                resultSet.getString("LASTNAME"),  resultSet.getInt("DECEASED"), resultSet.getInt("ANONYMISED"), resultSet.getInt("READONLY"),
                resultSet.getLong("ADDRESSID"), resultSet.getString("USERNAME"), resultSet.getString("EMAILADDRESS"), 
                resultSet.getString("SALT"), resultSet.getString("PASSWORD"), resultSet.getString("PHOTOCARDNUMBER"),
                resultSet.getString("UNFORMATTEDEMAILADDRESS"), resultSet.getInt("PASSWORDCHANGEREQUIRED"), resultSet.getInt("DEACTIVATED"),
                resultSet.getString("DEACTIVATIONREASON"), resultSet.getLong("EXTERNALUSERID"));
    }
}
