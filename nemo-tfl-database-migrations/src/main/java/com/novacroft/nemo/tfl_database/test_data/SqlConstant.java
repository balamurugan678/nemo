package com.novacroft.nemo.tfl_database.test_data;

public final class SqlConstant {

    protected static final String GB_COUNTRY_SELECT_SQL = "select id from country where code = 'GB'";
    protected static final String ADDRESS_SEQUENCE_SQL = "select address_seq.nextval from dual";
    protected static final String ADDRESS_INSERT_SQL = "insert into address " +
            "(id, createduserid, createddatetime, housenamenumber, street, town, postcode, externalid, country_id) values " +
            "(:id, :userId, sysdate, :houseNameNumber, :street, :town, :postcode, externalid_seq.nextval, :countryId)";

    protected static final String CUSTOMER_SEQUENCE_SQL = "select customer_seq.nextval from dual";
    // password is hard coded as 'nemo'
    protected static final String CUSTOMER_INSERT_SQL = "insert into customer " +
            "(id, createduserid, createddatetime, title, firstName, initials, lastName, deceased, anonymised, readonly, " +
            "addressid, username, emailaddress, salt, password, unformattedemailaddress, externalid, first_name_metaphone, " +
            "last_name_metaphone) " +
            "values " +
            "(:id, :userId, sysdate, :title, :firstName, :initials, :lastName, 0, 0, 0, :addressid, :username, :emailaddress," +
            " 'NYWlfc7v3-m4?^Z`ZtOa,ra|k^-.{Obt', 'qrYxU8CAzCRY8hJmS4m/sZLZNFAd4vkdiGyIl674lFI=', :unformattedemailaddress, " +
            "externalid_seq.nextval, :firstNameMetaphone, :lastNameMetaphone)";

    protected static final String CARD_SEQUENCE_SQL = "select card_seq.nextval from dual";
    protected static final String CARD_INSERT_SQL = "insert into card " +
            "(id, createduserid, createddatetime, cardnumber, customerid, externalid) " +
            "values " +
            "(:id, :userId, sysdate, :cardNumber, :customerId, externalid_seq.nextval)";

    protected static final String MOCK_OYSTER_CARD_INSERT_SQL = "insert into mock_oystercard " +
            "(id, createduserid, createddatetime, prestigeid, registered, autoloadstate, cardcapability, cardtype, " +
            "carddeposit, passengertype) values " +
            "(oystercard_seq.nextval, :userId, sysdate, :cardNumber, 0, 1, 1, 10, 0, 'Adult')";
    protected static final String MOCK_PRE_PAY_VALUE_INSERT_SQL = "insert into mock_oystercardprepayvalue " +
            "(id, createduserid, createddatetime, prestigeid, currency, balance) values " +
            "(oystercardprepayvalue_seq.nextval, :userid, sysdate, :cardnumber, 0, 220)";
    protected static final String MOCK_PRE_PAY_TICKET_INSERT_SQL = "insert into mock_oystercardprepayticket " +
            "(id, createduserid, createddatetime, prestigeid, slotnumber1, zone1, state1, slotnumber2, zone2, state2, " +
            "slotnumber3, zone3, state3) values " +
            "(oystercardprepayticket_seq.nextval, :userId, sysdate, :cardNumber, null, null, null, null, null, null, null, null, null)";
    protected static final String MOCK_PRE_PAY_VALUE_PENDING_INSERT_SQL = "insert into mock_oystercardppvpending " +
            "(id, createduserid, createddatetime, modifieduserid, modifieddatetime, prestigeid, requestsequencenumber, " +
            "realtimeflag, prepayvalue, currency, pickuplocation) values " +
            "(oystercardppvpending_seq.nextval, :userId, sysdate, null, null, :cardNumber, 0, null, 0, 0, 500)";
    protected static final String MOCK_PRE_PAY_TICKET_PENDING_INSERT_SQL = "insert into mock_oystercardpptpending " +
            "(id, createduserid, createddatetime, prestigeid) values (oystercardpptpending_seq.nextval, :userId, sysdate, " +
            ":cardNumber)";

    protected static final String ORDER_SEQUENCE_SQL = "select customerorder_seq.nextval from dual";
    protected static final String ORDER_NUMBER_SQL = "select order_number_seq.nextval from dual";
    protected static final String ORDER_INSERT_SQL = "insert into customerorder " +
            "(id, createduserid, createddatetime, customerid, ordernumber, orderdate, totalamount, status, " +
            "externalid) values " +
            "(:orderId, :userId, sysdate, :customerId, :orderNumber, :orderDate, :totalAmount, :status, " +
            "externalid_seq.nextval)";

    protected static final String SETTLEMENT_SEQUENCE_SQL = "select settlement_seq.nextval from dual";
    protected static final String SETTLEMENT_INSERT_SQL = "insert into settlement " +
            "(id, createduserid, createddatetime, customerorderid, status, settlementdate, amount, settlementmethod, " +
            "externalid) " +
            "values (:id, :userId, sysdate, :orderId, :status, :settlementDate, :amount, :settlementMethod, " +
            "externalid_seq.nextval)";

    protected static final String CONTACT_SEQUENCE_SQL = "select contact_seq.nextval from dual";
    protected static final String CONTACT_INSERT_SQL = "insert into contact " +
            "(id, createduserid, createddatetime, customerid, name, value, type, externalid) " +
            "values (:id, :userId, sysdate, :customerId, :name, :value, :type, externalid_seq.nextval)";

    private SqlConstant() {
    }
}
