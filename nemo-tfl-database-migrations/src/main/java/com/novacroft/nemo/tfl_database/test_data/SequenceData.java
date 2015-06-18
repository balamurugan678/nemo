package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SequenceData {
    protected Integer generateIdFromSequence(PreparedStatement sequenceStatement) throws SQLException {
        ResultSet sequenceResultSet = sequenceStatement.executeQuery();
        if (sequenceResultSet.next()) {
            return sequenceResultSet.getInt(1);
        }
        throw new SQLException("No data found.");
    }
}
