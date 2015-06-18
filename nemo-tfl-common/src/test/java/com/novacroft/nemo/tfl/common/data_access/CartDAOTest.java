package com.novacroft.nemo.tfl.common.data_access;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Ignore;
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

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.domain.Item;

/**
 * Unit tests for CartDAO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "/nemo-tfl-common-dao-test.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
@Profile("nemo-tfl-common-dao-test")
@Ignore
public class CartDAOTest {

    @BeforeClass
    public static void classSetUp() {
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

    @Autowired
    protected DriverManagerDataSource dataSource;

    @Autowired
    protected CartDAO cartDAO;

    @Autowired
    protected WebAccountDAO webAccountDAO;

    @Autowired
    protected CustomerDAO customerDAO;

    @Autowired
    protected CardDAO cardDAO;

    @Test
    public void shouldCreateUsingCard() {
        Card testCard = this.cardDAO.createOrUpdate(CartTestUtil.getNewTestCard());

        Cart cart = this.cartDAO.createOrUpdate(CartTestUtil.getNewCart(testCard));
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, cartCount);
    }

    @Test
    public void shouldCreateWithItemUsingCard() {
        Card testCard = this.cardDAO.createOrUpdate(CartTestUtil.getNewTestCard());

        Cart testCart = CartTestUtil.getNewCartWithItem(testCard);
        Cart cart = this.cartDAO.createOrUpdate(testCart);
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, cartCount);
        Integer itemCount = jdbcTemplate.queryForObject("select count(*) from item where cartid = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, itemCount);
    }

    @Test
    public void shouldFindByIdUsingCartCretedFromCard() {
        Card testCard = this.cardDAO.createOrUpdate(CartTestUtil.getNewTestCard());

        Cart cart = this.cartDAO.createOrUpdate(CartTestUtil.getNewCart(testCard));
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        Cart foundCart = this.cartDAO.findById(cart.getId());

        assertEquals(cart.getId(), foundCart.getId());

    }

    @Test
    public void shouldFindByIdWithItemUsingCartCreatedFromCard() {
        Card testCard = this.cardDAO.createOrUpdate(CartTestUtil.getNewTestCard());

        Cart testCart = CartTestUtil.getNewCartWithItem(testCard);
        Cart cart = this.cartDAO.createOrUpdate(testCart);
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        Cart foundCart = this.cartDAO.findById(cart.getId());

        assertEquals(cart.getId(), foundCart.getId());
        assertTrue(foundCart.getItems().size() > 0);
        for (Item item : foundCart.getItems()) {
            assertTrue(item instanceof AdministrationFeeItem);
            assertEquals(CartTestUtil.TEST_ADMINISTRATION_FEE_PRICE, item.getPrice());
        }
    }

    @Test
    public void shouldDeleteUsingCard() {
        Card testCard = this.cardDAO.createOrUpdate(CartTestUtil.getNewTestCard());

        Cart cart = cartDAO.createOrUpdate(CartTestUtil.getNewCart(testCard));

        assertNotNull(cart.getId());

        cartDAO.delete(cart);
        cartDAO.flush();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 0, cartCount);
    }

    @Test
    public void shouldCreateUsingCustomer() {
        Customer testCustomer = this.customerDAO.createOrUpdate(CartTestUtil.getNewTestCustomer());

        Cart cart = this.cartDAO.createOrUpdate(CartTestUtil.getNewCart(testCustomer));
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, cartCount);
    }

    @Test
    public void shouldCreateWithItemUsingCustomer() {
        Customer testCustomer = this.customerDAO.createOrUpdate(CartTestUtil.getNewTestCustomer());

        Cart testCart = CartTestUtil.getNewCartWithItem(testCustomer);
        Cart cart = this.cartDAO.createOrUpdate(testCart);
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, cartCount);
        Integer itemCount = jdbcTemplate.queryForObject("select count(*) from item where cartid = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, itemCount);
    }

    @Test
    public void shouldFindByIdUsingCartCretedFromCustomer() {
        Customer testCustomer = this.customerDAO.createOrUpdate(CartTestUtil.getNewTestCustomer());

        Cart cart = this.cartDAO.createOrUpdate(CartTestUtil.getNewCart(testCustomer));
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        Cart foundCart = this.cartDAO.findById(cart.getId());

        assertEquals(cart.getId(), foundCart.getId());

    }

    @Test
    public void shouldDeleteUsingCustomer() {
        Customer testCustomer = this.customerDAO.createOrUpdate(CartTestUtil.getNewTestCustomer());

        Cart cart = cartDAO.createOrUpdate(CartTestUtil.getNewCart(testCustomer));

        assertNotNull(cart.getId());

        cartDAO.delete(cart);
        cartDAO.flush();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 0, cartCount);
    }

    @Test
    public void shouldCreateUsingAnonymous() {
        Cart cart = this.cartDAO.createOrUpdate(CartTestUtil.getNewCart());
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 1, cartCount);
    }

    @Test
    public void shouldFindByIdUsingCartCretedFromAnonymous() {
        Cart cart = this.cartDAO.createOrUpdate(CartTestUtil.getNewCart());
        this.cartDAO.flush();

        assertNotNull(cart.getId());

        Cart foundCart = this.cartDAO.findById(cart.getId());

        assertEquals(cart.getId(), foundCart.getId());

    }

    @Test
    public void shouldDeleteUsingAnonymous() {
        Cart cart = cartDAO.createOrUpdate(CartTestUtil.getNewCart());

        assertNotNull(cart.getId());

        cartDAO.delete(cart);
        cartDAO.flush();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Integer cartCount = jdbcTemplate.queryForObject("select count(*) from cart where id = ?", Integer.class, cart.getId());
        assertEquals((Integer) 0, cartCount);
    }
}
