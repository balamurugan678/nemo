package com.novacroft.nemo.tfl_database.test_data;

import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.*;
import static com.novacroft.nemo.tfl_database.test_data.SqlConstant.*;

/**
 * Create test data
 */
public class FixtureGenerator implements CustomTaskChange {

    private PreparedStatement gbCountrySelectStatement = null;
    private PreparedStatement addressSequenceStatement = null;
    private PreparedStatement addressInsertStatement = null;

    private PreparedStatement customerSequenceStatement = null;
    private PreparedStatement customerInsertStatement = null;

    private PreparedStatement cardSequenceStatement = null;
    private PreparedStatement cardInsertStatement = null;

    private PreparedStatement mockOysterCardInsertStatement = null;
    private PreparedStatement mockPrePayValueInsertStatement = null;
    private PreparedStatement mockPrePayTicketInsertStatement = null;
    private PreparedStatement mockPrePayValuePendingInsertStatement = null;
    private PreparedStatement mockPrePayTicketPendingInsertStatement = null;

    private PreparedStatement orderSequenceStatement = null;
    private PreparedStatement orderNumberStatement = null;
    private PreparedStatement orderInsertStatement = null;

    private PreparedStatement settlementSequenceStatement = null;
    private PreparedStatement settlementInsertStatement = null;

    private PreparedStatement contactSequenceStatement = null;
    private PreparedStatement contactInsertStatement = null;

    private AddressData addressData = new AddressData();
    private CustomerData customerData = new CustomerData();
    private CardData cardData = new CardData();
    private MockCubicData mockCubicData = new MockCubicData();
    private OrderData orderData = new OrderData();
    private SettlementData settlementData = new SettlementData();
    private ContactData contactData = new ContactData();

    @Override
    public void execute(Database database) throws CustomChangeException {

        try {
            initialiseStatements((JdbcConnection) database.getConnection());

            Integer gbCountryId = this.addressData.getGbCountryId(this.gbCountrySelectStatement);

            populateVolumeData(5000, gbCountryId);
            populateFixedData(gbCountryId);

        } catch (SQLException | DatabaseException e) {
            throw new CustomChangeException(e.getMessage(), e);
        } finally {
            try {
                closeStatements();
            } catch (SQLException e) {
                throw new CustomChangeException(e.getMessage(), e);
            }
        }
    }

    @Override
    public String getConfirmationMessage() {
        return null;
    }

    @Override
    public void setUp() throws SetupException {

    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {

    }

    @Override
    public ValidationErrors validate(Database database) {
        return null;
    }

    private void populateFixedData(Integer gbCountryId) throws SQLException {
        for (int i = 0; i < FIXED_TEST_USER_DATA.length; i++) {
            populate(FIXED_TEST_USER_DATA[i][USER_NAME_INDEX], FIXED_TEST_USER_DATA[i][TITLE_INDEX],
                    FIXED_TEST_USER_DATA[i][FIRST_NAME_INDEX], FIXED_TEST_USER_DATA[i][INITIALS_INDEX],
                    FIXED_TEST_USER_DATA[i][LAST_NAME_INDEX], FIXED_TEST_USER_DATA[i][HOUSE_NAME_NUMBER_INDEX],
                    FIXED_TEST_USER_DATA[i][STREET_INDEX], FIXED_TEST_USER_DATA[i][TOWN_INDEX],
                    FIXED_TEST_USER_DATA[i][POSTCODE_INDEX], gbCountryId, FIXED_TEST_USER_DATA[i][CARD_INDEX].split(","),
                    FIXED_TEST_USER_DATA[i][HOME_PHONE_INDEX], FIXED_TEST_USER_DATA[i][MOBILE_PHONE_INDEX]);
        }
    }

    private void populateVolumeData(Integer numberOfUsersToCreate, Integer gbCountryId) throws SQLException {
        RandomFixtureGenerator randomFixtureGenerator = new RandomFixtureGenerator();
        for (int i = 0; i < numberOfUsersToCreate; i++) {
            populate(randomFixtureGenerator.getRandomUserName(i), randomFixtureGenerator.getRandomTitle(),
                    randomFixtureGenerator.getRandomFirstName(), randomFixtureGenerator.getRandomInitials(),
                    randomFixtureGenerator.getRandomLastName(i), randomFixtureGenerator.getRandomHouseNameNumber(),
                    randomFixtureGenerator.getRandomStreet(), randomFixtureGenerator.getRandomTown(),
                    randomFixtureGenerator.getRandomPostcode(), gbCountryId,
                    randomFixtureGenerator.getRandomOysterCardNumbers(i), randomFixtureGenerator.getRandomPhoneNumber(),
                    randomFixtureGenerator.getRandomPhoneNumber());
        }
    }

    private void populate(String userName, String title, String firstName, String initials, String lastName,
                          String houseNameNumber, String street, String town, String postcode, Integer countryId,
                          String[] oysterCardNumbers, String homePhone, String mobilePhone) throws SQLException {
        Integer addressId = this.addressData
                .createAddress(this.addressSequenceStatement, this.addressInsertStatement, houseNameNumber, street, town,
                        postcode, countryId);

        Integer customerId = this.customerData
                .createCustomer(customerSequenceStatement, customerInsertStatement, userName, title, firstName, initials,
                        lastName, addressId);

        for (int i = 0; i < oysterCardNumbers.length; i++) {
            Integer cardId =
                    this.cardData.createCard(cardSequenceStatement, cardInsertStatement, oysterCardNumbers[i], customerId);

            this.mockCubicData.createOysterCard(mockOysterCardInsertStatement, mockPrePayValueInsertStatement,
                    mockPrePayTicketInsertStatement, mockPrePayValuePendingInsertStatement,
                    mockPrePayTicketPendingInsertStatement, oysterCardNumbers[i]);
        }

        Integer orderId = this.orderData
                .createOrder(orderSequenceStatement, orderNumberStatement, orderInsertStatement, customerId, null, null,
                        WEB_CREDIT_VALUE, OrderStatus.PAID.code());

        Integer settlementId = this.settlementData
                .createSettlement(settlementSequenceStatement, settlementInsertStatement, orderId,
                        SettlementStatus.COMPLETE.code(), null, WEB_CREDIT_VALUE, WEB_CREDIT_SETTLEMENT_METHOD);

        this.contactData
                .createContact(contactSequenceStatement, contactInsertStatement, customerId, "Phone", homePhone, "HomePhone");

        this.contactData.createContact(contactSequenceStatement, contactInsertStatement, customerId, "Phone", mobilePhone,
                "MobilePhone");
    }

    private void initialiseStatements(JdbcConnection connection) throws DatabaseException {
        this.gbCountrySelectStatement = connection.prepareStatement(GB_COUNTRY_SELECT_SQL);
        this.addressSequenceStatement = connection.prepareStatement(ADDRESS_SEQUENCE_SQL);
        this.addressInsertStatement = connection.prepareStatement(ADDRESS_INSERT_SQL);

        this.customerSequenceStatement = connection.prepareStatement(CUSTOMER_SEQUENCE_SQL);
        this.customerInsertStatement = connection.prepareStatement(CUSTOMER_INSERT_SQL);

        this.cardSequenceStatement = connection.prepareStatement(CARD_SEQUENCE_SQL);
        this.cardInsertStatement = connection.prepareStatement(CARD_INSERT_SQL);

        this.mockOysterCardInsertStatement = connection.prepareStatement(MOCK_OYSTER_CARD_INSERT_SQL);
        this.mockPrePayValueInsertStatement = connection.prepareStatement(MOCK_PRE_PAY_VALUE_INSERT_SQL);
        this.mockPrePayTicketInsertStatement = connection.prepareStatement(MOCK_PRE_PAY_TICKET_INSERT_SQL);
        this.mockPrePayValuePendingInsertStatement = connection.prepareStatement(MOCK_PRE_PAY_VALUE_PENDING_INSERT_SQL);
        this.mockPrePayTicketPendingInsertStatement = connection.prepareStatement(MOCK_PRE_PAY_TICKET_PENDING_INSERT_SQL);

        this.orderSequenceStatement = connection.prepareStatement(ORDER_SEQUENCE_SQL);
        this.orderNumberStatement = connection.prepareStatement(ORDER_NUMBER_SQL);
        this.orderInsertStatement = connection.prepareStatement(ORDER_INSERT_SQL);

        this.settlementSequenceStatement = connection.prepareStatement(SETTLEMENT_SEQUENCE_SQL);
        this.settlementInsertStatement = connection.prepareStatement(SETTLEMENT_INSERT_SQL);

        this.contactSequenceStatement = connection.prepareStatement(CONTACT_SEQUENCE_SQL);
        this.contactInsertStatement = connection.prepareStatement(CONTACT_INSERT_SQL);
    }

    private void closeStatements() throws SQLException {
        this.gbCountrySelectStatement.close();
        this.addressSequenceStatement.close();
        this.addressInsertStatement.close();

        this.customerSequenceStatement.close();
        this.customerInsertStatement.close();

        this.cardSequenceStatement.close();
        this.cardInsertStatement.close();

        this.mockOysterCardInsertStatement.close();
        this.mockPrePayValueInsertStatement.close();
        this.mockPrePayTicketInsertStatement.close();
        this.mockPrePayValuePendingInsertStatement.close();
        this.mockPrePayTicketPendingInsertStatement.close();

        this.orderSequenceStatement.close();
        this.orderNumberStatement.close();
        this.orderInsertStatement.close();

        this.settlementSequenceStatement.close();
        this.settlementInsertStatement.close();

        this.contactSequenceStatement.close();
        this.contactInsertStatement.close();
    }
}
