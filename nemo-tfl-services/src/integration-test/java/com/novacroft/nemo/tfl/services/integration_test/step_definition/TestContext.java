package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

@Component
public class TestContext {
    private MockMvc mockMvc;
    private ResultActions resultActions;
    private CustomerDTO customerDTO;
    private PrePaidTicket prePaidTicket;
    private PayAsYouGo payAsYouGo;
    private CardDTO cardDTO;
    private CartDTO cartDTO;
    private CheckoutResult checkOutResult;


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
    
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

	public PrePaidTicket getPrePaidTicket() {
		return prePaidTicket;
	}

	public void setPrePaidTicket(PrePaidTicket prePaidTicket) {
		this.prePaidTicket = prePaidTicket;
	}

	public PayAsYouGo getPayAsYouGo() {
		return payAsYouGo;
	}

	public void setPayAsYouGo(PayAsYouGo payAsYouGo) {
		this.payAsYouGo = payAsYouGo;
	}

    public CheckoutResult getCheckOutResult() {
        return checkOutResult;
    }

    public void setCheckOutResult(CheckoutResult checkOutResult) {
        this.checkOutResult = checkOutResult;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }
}
