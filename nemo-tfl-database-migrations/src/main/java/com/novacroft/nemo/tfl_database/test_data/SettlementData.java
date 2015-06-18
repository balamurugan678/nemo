package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public final class SettlementData extends SequenceData {

    public Integer createSettlement(PreparedStatement settlementSequenceStatement, PreparedStatement settlementInsertStatement,
                                    Integer orderId, String status, java.util.Date settlementDate, Integer amount,
                                    String settlementMethod) throws SQLException {
        Integer settlementId = generateIdFromSequence(settlementSequenceStatement);

        settlementInsertStatement.setInt(1, settlementId);
        settlementInsertStatement.setString(2, USER_ID);
        settlementInsertStatement.setInt(3, orderId);
        settlementInsertStatement.setString(4, status);
        settlementInsertStatement
                .setDate(5, new java.sql.Date(((null != settlementDate) ? settlementDate : new java.util.Date()).getTime()));
        settlementInsertStatement.setInt(6, amount);
        settlementInsertStatement.setString(7, settlementMethod);

        settlementInsertStatement.executeUpdate();

        return settlementId;
    }
}
