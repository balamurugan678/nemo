package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

@Component
public class TestContext {
    private MockMvc mockMvc;
    private ResultActions resultActions;
    private UserDetails userDetails;
    private CustomerDTO customerDTO;
    private CardDTO cardDTO;
    private List<OrderDTO> orders;

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions getResultActions() {
        return resultActions;
    }

    public void setResultActions(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    
}
