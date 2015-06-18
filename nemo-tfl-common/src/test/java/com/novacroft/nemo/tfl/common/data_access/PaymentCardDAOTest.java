package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.test_support.CustomerRowMapper;
import com.novacroft.nemo.test_support.PaymentCardRowMapper;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.util.Date;

import static com.novacroft.nemo.test_support.PaymentCardTestUtil.*;
import static org.junit.Assert.*;

/**
 * PaymentCardDAO unit tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/nemo-tfl-common-dao-test.xml"})
@TransactionConfiguration(defaultRollback = false)
@Transactional
@Profile("nemo-tfl-common-dao-test")
public class PaymentCardDAOTest {
    private static final String TEST_USER_ID = "test-user";
    private static final String TEST_CUSTOMER_LAST_NAME = "PaymentCardDAOTest";
    private static final String DELETE_TEST_CUSTOMER_SQL =
            "delete from customer where lastname = ':lastName'".replace(":lastName", TEST_CUSTOMER_LAST_NAME);
    private static final String INSERT_TEST_CUSTOMER_SQL =
            "insert into customer (id, createduserid, createddatetime, lastname, deceased, anonymised, readonly) " +
                    "values (customer_seq.nextval, 'test', sysdate, ':lastName', 0, 0, 0)"
                            .replace(":lastName", TEST_CUSTOMER_LAST_NAME);
    private static final String SELECT_TEST_CUSTOMER_SQL =
            "select * from customer where lastname = ':lastName'".replace(":lastName", TEST_CUSTOMER_LAST_NAME);
    private static final String SELECT_COUNT_TEST_PAYMENT_CARD_SQL = "select count(*) from paymentcard where id = ?";
    private static final String DELETE_TEST_PAYMENT_CARDS_SQL = ("delete from paymentcard pc " +
            "where exists " +
            "(select null from customer c where c.lastname = ':lastName' and c.id = pc.customerid)")
            .replace(":lastName", TEST_CUSTOMER_LAST_NAME);
    private static final String INSERT_TEST_PAYMENT_CARD_SQL = "insert into paymentcard " +
            "(id, createduserid, createddatetime, customerid, obfuscatedprimaryaccountnumber, token) values " +
            "(paymentcard_seq.nextval, 'test', sysdate, :customerId, ':obfuscatedPrimaryAccountNumber', ':token')";
    private static final String SELECT_TEST_PAYMENT_CARD_SQL = "select * from paymentcard " +
            "where customerid = :customerId and obfuscatedprimaryaccountnumber = ':obfuscatedPrimaryAccountNumber'";

    @Autowired
    private DriverManagerDataSource dataSource;
    @Autowired
    private PaymentCardDAO paymentCardDAO;
    private Customer testCustomer;
    private PaymentCard testPaymentCard;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("spring.profiles.active", "nemo-tfl-common-dao-test");
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("java:comp/env/spring.profiles.active", "nemo-tfl-common-dao-test");
        try {
            builder.activate();
        } catch (NamingException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Before
    public void setUp() {
        tearDown();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        jdbcTemplate.update(INSERT_TEST_CUSTOMER_SQL);
        this.testCustomer = jdbcTemplate.queryForObject(SELECT_TEST_CUSTOMER_SQL, new CustomerRowMapper());
        jdbcTemplate.update(INSERT_TEST_PAYMENT_CARD_SQL.replace(":customerId", String.valueOf(this.testCustomer.getId()))
                .replace(":obfuscatedPrimaryAccountNumber", TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_2)
                .replace(":token", TEST_TOKEN_2));
        this.testPaymentCard = jdbcTemplate.queryForObject(
                SELECT_TEST_PAYMENT_CARD_SQL.replace(":customerId", String.valueOf(this.testCustomer.getId()))
                        .replace(":obfuscatedPrimaryAccountNumber", TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_2),
                new PaymentCardRowMapper());
    }

    @After
    public void tearDown() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        jdbcTemplate.update(DELETE_TEST_PAYMENT_CARDS_SQL);
        jdbcTemplate.update(DELETE_TEST_CUSTOMER_SQL);
    }

    @Test
    public void shouldCreate() {
        PaymentCard testPaymentCard =
                new PaymentCard(this.testCustomer.getId(), TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1, TEST_TOKEN_1, TEST_USER_ID,
                        new Date());

        PaymentCard result = this.paymentCardDAO.createOrUpdate(testPaymentCard);
        this.paymentCardDAO.flush();

        assertNotNull(result.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer orderCount = jdbcTemplate.queryForObject(SELECT_COUNT_TEST_PAYMENT_CARD_SQL, Integer.class, result.getId());
        assertEquals((Integer) 1, orderCount);
    }

    @Test
    public void shouldUpdate() {
        PaymentCard paymentCard = this.paymentCardDAO.findById(this.testPaymentCard.getId());
        paymentCard.setObfuscatedPrimaryAccountNumber(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_3);
        this.paymentCardDAO.createOrUpdate(paymentCard);
        this.paymentCardDAO.flush();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        PaymentCard result = jdbcTemplate.queryForObject(
                SELECT_TEST_PAYMENT_CARD_SQL.replace(":customerId", String.valueOf(this.testPaymentCard.getCustomerId()))
                        .replace(":obfuscatedPrimaryAccountNumber", TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_3),
                new PaymentCardRowMapper());
        assertNotNull(result);
    }

    @Test
    public void shouldFindById() {
        PaymentCard result = this.paymentCardDAO.findById(this.testPaymentCard.getId());
        assertEquals(this.testPaymentCard.getObfuscatedPrimaryAccountNumber(), result.getObfuscatedPrimaryAccountNumber());
    }
}
