package com.novacroft.nemo.tfl_database.test_data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.USER_ID;

public class CardData extends SequenceData {

    public Integer createCard(PreparedStatement cardSequenceStatement, PreparedStatement cardInsertStatement, String cardNumber,
                              Integer customerId) throws SQLException {
        Integer cardId = generateIdFromSequence(cardSequenceStatement);

        cardInsertStatement.setInt(1, cardId);
        cardInsertStatement.setString(2, USER_ID);
        cardInsertStatement.setString(3, cardNumber);
        cardInsertStatement.setInt(4, customerId);

        cardInsertStatement.executeUpdate();

        return cardId;
    }
}
