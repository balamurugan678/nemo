package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItem1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.GoodwillPaymentItemDataService;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public class GoodwillPaymentServiceImplTest {

    private GoodwillPaymentItemDataService goodwillPaymentItemDataService;
    private GoodwillPaymentServiceImpl goodwillPaymentServiceImpl;
    private CartService mockCartService;
    private ApplicationContext applicationContext;

    @Before
    public void setup() {

        goodwillPaymentServiceImpl = new GoodwillPaymentServiceImpl();
        goodwillPaymentItemDataService = mock(GoodwillPaymentItemDataService.class);
        mockCartService = mock(CartService.class);
        applicationContext = mock(ApplicationContext.class);
        
        goodwillPaymentServiceImpl.goodwillPaymentItemDataService = goodwillPaymentItemDataService;
        goodwillPaymentServiceImpl.cartService = mockCartService;
        setField(goodwillPaymentServiceImpl, "applicationContext", applicationContext);

    }

    @Test
    public void testfindByCardId() {
        GoodwillPaymentItemDTO goodwillPaymentItem1 = getGoodwillPaymentItemDTOItem1();
        goodwillPaymentItem1.setNullable(true);
        when(goodwillPaymentItemDataService.findById(anyLong())).thenReturn(goodwillPaymentItem1);
        GoodwillPaymentItem findByCardId = goodwillPaymentServiceImpl.findByCardId(1L);

        assertEquals(goodwillPaymentItem1.getCardId(), findByCardId.getCardId());
    }

    @Test
    public void testfindByCartIdAndCardId() {
        when(goodwillPaymentItemDataService.findByCartIdAndCardId(anyLong(), anyLong())).thenReturn(getGoodwillPaymentItem1());
        GoodwillPaymentItem findByCartIdAndCardId = goodwillPaymentServiceImpl.findByCartIdAndCardId(1L, 1L);
        assertEquals(findByCartIdAndCardId.getCardId(), getGoodwillPaymentItem1().getCardId());
    }

    @Test
    public void testfindById() {
        when(goodwillPaymentItemDataService.findById(anyString())).thenReturn(getGoodwillPaymentItem1());
        GoodwillPaymentItem findById = goodwillPaymentServiceImpl.findById("1");
        assertEquals(getGoodwillPaymentItem1().getCardId(), findById.getCardId());
    }

    @Test
    public void clearGoodwillPaymentCartItem() {
        CartItemCmdImpl cartCmdImpl = getTestTravelCard1();
        goodwillPaymentServiceImpl.deleteGoodwillPayment(cartCmdImpl);
    }

    @Test(expected = NullPointerException.class)
    public void clearGoodwillPaymentCartItemThroeException() {

        GoodwillPaymentItemDTO cartCmdImpl = getGoodwillPaymentItemDTOItem1();
        doThrow(new NullPointerException()).when(goodwillPaymentItemDataService).delete((GoodwillPaymentItemDTO) anyObject());

        goodwillPaymentServiceImpl.deleteGoodwillPayment(cartCmdImpl);
    }

    @Test
    public void populateProductCartItemsSuccess() {

        List<GoodwillPaymentItemDTO> goodwillItems = new ArrayList<GoodwillPaymentItemDTO>();
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        goodwillItems.add(getGoodwillPaymentItemDTOItem1());

        when(goodwillPaymentItemDataService.findById(anyLong())).thenReturn(getGoodwillPaymentItemDTOItem1());
        goodwillPaymentServiceImpl.populateProductCartItems(goodwillItems, cartItems);

        assert (cartItems.size() > 0);
        assertEquals(cartItems.get(0).getId(), getGoodwillPaymentItemDTOItem1().getId());

    }
}
