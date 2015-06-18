package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.domain.WebAccount;
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

import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrder1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.getTestWebAccount1;
import static org.junit.Assert.*;

/**
 * Unit tests for OrderDAO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/nemo-tfl-common-dao-test.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
@Profile("nemo-tfl-common-dao-test")
public class OrderDAOTest {

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
    protected OrderDAO orderDAO;

    @Autowired
    protected WebAccountDAO webAccountDAO;

    @Test
    public void shouldCreate() {
        Date now = new Date();
        String testUserName = "OrderDAOTest-shouldCreate-" + String.valueOf(now.getTime());

        WebAccount testWebAccount = this.webAccountDAO.createOrUpdate(getNewTestWebAccount(testUserName));

        Order order = this.orderDAO.createOrUpdate(getNewOrder(testWebAccount));
        this.orderDAO.flush();

        assertNotNull(order.getId());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer orderCount =
                jdbcTemplate.queryForObject("select count(*) from customerorder where id = ?", Integer.class, order.getId());
        assertEquals((Integer) 1, orderCount);
    }

    @Test
    public void shouldUpdate() {
        Date now = new Date();
        String testUserName = "OrderDAOTest-shouldUpdate-" + String.valueOf(now.getTime());

        WebAccount testWebAccount = this.webAccountDAO.createOrUpdate(getNewTestWebAccount(testUserName));

        Order order = this.orderDAO.createOrUpdate(getNewOrder(testWebAccount));

        assertNotNull(order.getId());

        Integer updatedAmount = order.getTotalAmount() + 99;
        order.setTotalAmount(updatedAmount);

        order = this.orderDAO.createOrUpdate(order);
        this.orderDAO.flush();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        Integer orderCount = jdbcTemplate
                .queryForObject("select count(*) from customerorder where id = ? and totalamount = ?", Integer.class,
                        order.getId(), updatedAmount);
        assertEquals((Integer) 1, orderCount);
    }

    @Test
    public void shouldFindById() {
        Date now = new Date();
        String testUserName = "OrderDAOTest-shouldFindById-" + String.valueOf(now.getTime());

        WebAccount testWebAccount = webAccountDAO.createOrUpdate(getNewTestWebAccount(testUserName));

        Order order = this.orderDAO.createOrUpdate(getNewOrder(testWebAccount));
        this.orderDAO.flush();

        assertNotNull(order.getId());

        Order foundOrder = this.orderDAO.findById(order.getId());

        assertEquals(order.getId(), foundOrder.getId());

    }

    @Test
    @Transactional
    public void shouldDelete() {
        Date now = new Date();
        String testUserName = "OrderDAOTest-shouldDelete-" + String.valueOf(now.getTime());

        WebAccount testWebAccount = webAccountDAO.createOrUpdate(getNewTestWebAccount(testUserName));

        Order order = orderDAO.createOrUpdate(getNewOrder(testWebAccount));

        assertNotNull(order.getId());

        orderDAO.delete(order);
        orderDAO.flush();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Integer orderCount =
                jdbcTemplate.queryForObject("select count(*) from customerorder where id = ?", Integer.class, order.getId());
        assertEquals((Integer) 0, orderCount);
    }

    private WebAccount getNewTestWebAccount(String username) {
        WebAccount webAccount = getTestWebAccount1();
        webAccount.setId(null);
        webAccount.setUsername(username);
        webAccount.setCustomerId(null);
        return webAccount;
    }

    private Order getNewOrder(WebAccount webAccount) {
        Order order = getTestOrder1();
        order.setId(null);
        order.setWebAccountId(webAccount.getId());
        order.setOrderNumber(orderDAO.getNextOrderNumber());
        return order;
    }
}
