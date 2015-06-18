package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import static com.novacroft.nemo.test_support.OrderTestUtil.getItemDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getItemDTO2;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.tfl.common.application_service.CollateOrdersService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-online-context-integration-test.xml"})
public class ViewOrderHistoryPageStepDefinition extends BaseOnlineStepDefinition {

    
    @Autowired
    protected OrderService orderService;
    
    @Autowired
    protected CollateOrdersService collateOrdersService;

    private OrderDataService mockOrderDataService;
    
    private OrderDTO orderDTO;
    private List<OrderDTO> orderDTOList;

    public ViewOrderHistoryPageStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
        orderDTO = getTestOrderDTO1();
        orderDTO.getOrderItems().add(getItemDTO1());
        orderDTO.getOrderItems().add(getItemDTO2());
        orderDTOList = new ArrayList<OrderDTO>();
        orderDTOList.add(orderDTO);
        
        this.mockOrderDataService = mock(OrderDataService.class);
        when(this.mockOrderDataService.findByCustomerId(any(Long.class))).thenReturn(orderDTOList);

        ReflectionTestUtils.setField(this.orderService, "orderDataService", this.mockOrderDataService);
    }

    @When("^I navigate to the view order history page$")
    public void iNavigateToTheViewOrderHistoryPage() throws Exception {
        securitySetUp();
        this.testContext
                .setResultActions(this.testContext.getMockMvc().perform(get("/ViewOrderHistory.htm")));
    }

    @Then("^the view order history page should be displayed$")
    public void theViewOrderHistoryPageShouldBeDisplayed() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(view().name(PageView.VIEW_ORDER_HISTORY))
                .andExpect(model().attributeExists("orderDays"));
     }
}
