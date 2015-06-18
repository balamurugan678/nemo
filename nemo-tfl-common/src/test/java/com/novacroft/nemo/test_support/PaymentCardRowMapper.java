package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Map result set record to payment card entity
 */
public class PaymentCardRowMapper implements RowMapper<PaymentCard> {
    @Override
    public PaymentCard mapRow(ResultSet resultSet, int i) throws SQLException {
        return new PaymentCard(resultSet.getLong("ID"), resultSet.getString("CREATEDUSERID"),
                resultSet.getDate("CREATEDDATETIME"), resultSet.getString("MODIFIEDUSERID"),
                resultSet.getDate("MODIFIEDDATETIME"), resultSet.getLong("CUSTOMERID"), resultSet.getLong("ADDRESSID"),
                resultSet.getString("OBFUSCATEDPRIMARYACCOUNTNUMBER"), resultSet.getString("TOKEN"),
                resultSet.getString("STATUS"), resultSet.getString("NICKNAME"));
    }
}
