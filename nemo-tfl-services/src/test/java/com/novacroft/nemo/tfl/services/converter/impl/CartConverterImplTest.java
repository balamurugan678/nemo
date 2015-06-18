package com.novacroft.nemo.tfl.services.converter.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.converter.ItemConverter;
import com.novacroft.nemo.tfl.services.transfer.Cart;

public class CartConverterImplTest {

    private CartConverterImpl cartConverter;
    private ItemConverter mockItemConverter;
    private CustomerDataService mockCustomerDataService;
    private CardDataService mockCardDataService;
    
    @Before
    public void setUp() throws Exception {
        cartConverter = new CartConverterImpl();
        mockItemConverter = mock(ItemConverterImpl.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        cartConverter.itemConverter = mockItemConverter;
        cartConverter.customerDataService = mockCustomerDataService;
        cartConverter.cardDataService = mockCardDataService;
        when(mockItemConverter.convert(anyList())).thenCallRealMethod();
        when(mockCustomerDataService.findByCustomerId(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
    }

    @Test
    public void testConvertCartDTO() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        Cart cart = cartConverter.convert(cartDTO);
        assertNotNull(cart);
        assertEquals(cart.getCartItems().size(), cartDTO.getCartItems().size());
    }

    @Test
    public void testConvertListOfCartDTO() {
        List<CartDTO> cartDTOList = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartDTOList();
        List<Cart> cartList = cartConverter.convert(cartDTOList);
        assertNotNull(cartList);
        assertEquals(cartList.size(), cartDTOList.size());
    }

}
