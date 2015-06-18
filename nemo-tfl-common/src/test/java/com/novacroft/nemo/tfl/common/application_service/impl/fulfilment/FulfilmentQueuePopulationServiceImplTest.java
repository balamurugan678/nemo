package com.novacroft.nemo.tfl.common.application_service.impl.fulfilment;

import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTOWithoutCardNumber;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithAnnualTravelCard;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithAutoTopUpItem;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithAutoTopUpItemAndProductItem;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithNoAnnualTravelCard;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithNoAutoTopUpItem;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithNoPayAsYouGoItem;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithPayAsYouGoItemOnly;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOForFulfilmentPendingQueue;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOInPaidStatus;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithAnnualTravelCard;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithAutoTopUp;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithNoAutoTopUp;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithPayAsYouGoOnly;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.OrderDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

public class FulfilmentQueuePopulationServiceImplTest {

    private FulfilmentQueuePopulationServiceImpl service;
    private OrderDataService mockOrderDataService;
    private CardDataService mockCardDataService;

    @Before
    public void setUp() {
        service = new FulfilmentQueuePopulationServiceImpl();
        mockOrderDataService = mock(OrderDataServiceImpl.class);
        mockCardDataService = mock(CardDataService.class);

        service.orderDataService = mockOrderDataService;
        service.cardDataService = mockCardDataService;
    }

    @Test
    public void isFirstIssueShouldReturnTrueForCartWithNoCardId() {
        CartDTO cart = new CartDTO();
        assertTrue(service.isFirstIssue(cart));
    }

    @Test
    public void isFirstIssueShouldReturnTrueForCartWithCardIdAndNoCardNumber() {
        when(mockCardDataService.findById(any(Long.class))).thenReturn(getTestCardDTOWithoutCardNumber());
        CartDTO cart = new CartDTO();
        cart.setCardId(CARD_ID);
        assertTrue(service.isFirstIssue(cart));
        verify(mockCardDataService).findById(any(Long.class));
    }

    @Test
    public void isCardNumberEmptyShouldReturnFalse() {
        when(mockCardDataService.findById(any(Long.class))).thenReturn(getTestCardDTO1());
        CartDTO cart = new CartDTO();
        cart.setCardId(CARD_ID);
        assertFalse(service.isCardNumberEmpty(cart));
    }

    @Test
    public void isPayAsYouGoTheOnlyItemPresentInOrderShouldReturnTrue() {
        assertTrue(service.isPayAsYouGoTheOnlyItemPresentInOrder(getListOfItemsWithPayAsYouGoItemOnly()));
    }

    @Test
    public void isPayAsYouGoTheOnlyItemPresentInOrderShouldReturnFalse() {
        assertFalse(service.isPayAsYouGoTheOnlyItemPresentInOrder(getListOfItemsWithAutoTopUpItem()));
    }
    
    @Test
    public void isPayAsYouGoTheOnlyItemPresentInOrderShouldReturnFalseWithProductAndAutoTopItems() {
        assertFalse(service.isPayAsYouGoTheOnlyItemPresentInOrder(getListOfItemsWithAutoTopUpItemAndProductItem()));
    }

    @Test
    public void isPayAsYouGoTheOnlyItemPresentInOrderShouldReturnFalseWithNoPayAsYouGoItem() {
        assertFalse(service.isPayAsYouGoTheOnlyItemPresentInOrder(getListOfItemsWithNoPayAsYouGoItem()));
    }

    @Test
    public void isAutoTopUpPresentInOrderShouldReturnTrue() {
        assertTrue(service.isAutoTopUpPresentInOrder(getListOfItemsWithAutoTopUpItem()));
    }

    @Test
    public void isAutoTopUpPresentInOrderShouldReturnFalse() {
        assertFalse(service.isAutoTopUpPresentInOrder(getListOfItemsWithNoAutoTopUpItem()));
    }

    @Test
    public void isAnnualTravelCardPresentShouldReturnTrue() {
        assertTrue(service.isAnnualTravelCardPresentInOrder(getListOfItemsWithAnnualTravelCard()));
    }

    @Test
    public void isAnnualTravelCardPresentShouldReturnFalseForAMonthlyTravelCard() {
        assertFalse(service.isAnnualTravelCardPresentInOrder(getListOfItemsWithNoAnnualTravelCard()));
    }

    @Test
    public void updateOrderStatusShouldInvokeUpdateService() {
        service.updateOrderStatus(new OrderDTO(), OrderStatus.FULFILMENT_PENDING.code());
        verify(mockOrderDataService).createOrUpdate(any(OrderDTO.class));
    }

    @Test
    public void determineOrderStatusForFirstIssueAndAnnualCardShouldReturnGoldCardPendingStatus() {
        assertEquals(OrderStatus.GOLD_CARD_PENDING.code(),
                        service.determineOrderStatusForFirstIssueAndAnnualCard(false, false, getOrderDTOWithAnnualTravelCard()));
    }

    @Test
    public void determineOrderStatusWhenOrderIsAFirstIssueShouldReturnPayAsYouGoFulfilmentStatus() {
        assertEquals(OrderStatus.PAY_AS_YOU_GO_FULFILMENT_PENDING.code(),
                        service.determineOrderStatusForFirstIssueAndAnnualCard(true, false, getOrderDTOWithPayAsYouGoOnly()));
    }

    @Test
    public void determineOrderStatusWhenOrderIsAFirstIssueShouldReturnAutoTopUpPayAsYouGoFulFilmentStatus() {
        assertEquals(OrderStatus.AUTO_TOP_UP_PAYG_FULFILMENT_PENDING.code(),
                        service.determineOrderStatusForFirstIssueAndAnnualCard(true, false, getOrderDTOWithAutoTopUp()));
    }

    @Test
    public void determineOrderStatusWhenOrderIsAFirstIssueShouldReturnFulFilmentPendingStatus() {
        assertEquals(OrderStatus.FULFILMENT_PENDING.code(),
                        service.determineOrderStatusForFirstIssueAndAnnualCard(true, false, getOrderDTOForFulfilmentPendingQueue()));
    }

    @Test
    public void determineOrderStatusWhenOrderIsAFirstIssueShouldReturnAutoTopUpReplacementFulFilmentPendingStatus() {
        assertEquals(OrderStatus.AUTO_TOP_UP_REPLACEMENT_FULFILMENT_PENDING.code(),
                        service.determineOrderStatusForFirstIssueAndAnnualCard(true, true, getOrderDTOWithAutoTopUp()));
    }

    @Test
    public void determineOrderStatusWhenOrderIsAFirstIssueShouldReturnReplacementFulFilmentPendingStatus() {
        assertEquals(OrderStatus.REPLACEMENT.code(), service.determineOrderStatusForFirstIssueAndAnnualCard(true, true, getOrderDTOWithNoAutoTopUp()));
    }

    @Test
    public void determineOrderStatusWhenOrderIsAFirstIssueShouldReturnOrderStatus() {
        assertEquals(OrderStatus.PAID.code(), service.determineOrderStatusForFirstIssueAndAnnualCard(false, false, getOrderDTOInPaidStatus()));
    }

    @Test
    public void determineFulfilmentQueueAndUpdateOrderStatusShouldInvokeService() {
        service.determineFulfilmentQueueAndUpdateOrderStatus(true, true, getOrderDTOWithAnnualTravelCard());
        verify(mockOrderDataService).createOrUpdate(any(OrderDTO.class));
    }
}
