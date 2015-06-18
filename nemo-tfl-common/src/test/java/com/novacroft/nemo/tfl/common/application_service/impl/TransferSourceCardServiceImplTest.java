package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardEighteenPlusDiscounted;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardEighteenPlusDiscountedForDiscountEntitlement2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardEighteenPlusDiscountedForDiscountEntitlement3;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithPayAsYouGoItemIsZero;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithPayAsYouGoItemLessThenZero;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithPayAsyouItemMoreThenZero;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithPendingTravelCards;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTotalAmountLessThenLimit;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTotalAmountMoreThenLimit;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTotalPayAsYouGo;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTravelCardWithCurrentDate;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTravelCardWithMoreThenADayRemaining;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTravelCards;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWitDeposit;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithApprenticeDiscount;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithApprenticeDiscountExpired;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithApprenticeDiscountExpiredForDiscountEntitlement2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithApprenticeDiscountExpiredForDiscountEntitlement3;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithDiscountEntitlement;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithJobCenterPlusDiscountOnTravelCards;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWithNoDeposit;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getProdouctItemOfOnlyTravelCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getProdouctItemOfTypeBus;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getProdouctItemOfTypeBusForPrePayTicket;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoForAdultType;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoForNotAdultType;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO16;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONonHotlistedCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithAutoTopUpStateDisabled;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithAutoloadState;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithHotlistedCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithMoreThenOneDayRemaining;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithMoreThenOneDayRemainingForPendingTickets;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTravelCardWithNoDiscount;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithItems;
import static com.novacroft.nemo.test_support.OrderTestUtil.getProductItemDTOTypeOfPreviouslyTradedTicket;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTOOfTypeBus;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTOTravelCard;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class TransferSourceCardServiceImplTest {

    @Mock
    private CardDataService mockCardDataService;

    @Mock
    private CartService mockCartService;

    @Mock
    private GetCardService mockGetCardService;

    @Mock
    private ProductService mockProductService;

    @Mock
    private ProductDataService mockProductDataService;

    @Mock
    private CardService mockCardService;

    @Mock
    private CartDataService mockCartDataService;

    @Mock
    private TravelCardService mockTravelCardService;

    @Mock
    private OrderDataService mockOrderDataService;

    @Mock
    private HotlistService mockHotlistService;

    private TransferSourceCardServiceImpl transferSourceCardServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transferSourceCardServiceImpl = new TransferSourceCardServiceImpl();
        transferSourceCardServiceImpl.cardService = mockCardService;
        transferSourceCardServiceImpl.cartDataService = mockCartDataService;
        transferSourceCardServiceImpl.cartService = mockCartService;
        transferSourceCardServiceImpl.productDataService = mockProductDataService;
        transferSourceCardServiceImpl.productService = mockProductService;
        transferSourceCardServiceImpl.getCardService = mockGetCardService;
        transferSourceCardServiceImpl.travelCardService = mockTravelCardService;
        transferSourceCardServiceImpl.orderDataService = mockOrderDataService;
        transferSourceCardServiceImpl.hotlistService = mockHotlistService;
    }

    @Test
    public void testIsCardHotlisted() {

        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTOWithHotlistedCard());
        when(mockHotlistService.isCardHotlisted(anyString())).thenReturn(true);
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardHotlisted(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.HOTLISTED.textCode(), ruleBreaches.get(0));
    }

    @Test
    public void testIsCardNotHotlisted() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTONonHotlistedCard());
        when(mockHotlistService.isCardHotlisted(anyString())).thenReturn(false);
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardHotlisted(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIsPassengerTypeAdultCard() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoForAdultType());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkPassengerTypeAdult(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIsPassengerTypeNotAdultCard() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoForNotAdultType());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkPassengerTypeAdult(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.TRANSFERS_IS_PASSENGER_TYPE_ADULT.textCode(), ruleBreaches.get(0));
    }

    @Test
    public void testIscardJobCenterDiscountedOnTravelCards() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(10L);
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithJobCenterPlusDiscountOnTravelCards());
        assertTrue(transferSourceCardServiceImpl.hasJobCenterPlusDiscountsOnTravelCards(OYSTER_NUMBER_1));
    }

    @Test
    public void testIscardJobCenterNotDiscountedOnTravelCards() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(10L);
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTravelCardWithNoDiscount());
        assertFalse(transferSourceCardServiceImpl.hasJobCenterPlusDiscountsOnTravelCards(OYSTER_NUMBER_1));
    }

    @Test
    public void testIsApprenticeDiscounted() {

        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithApprenticeDiscount(),
                        getCardWithApprenticeDiscountExpiredForDiscountEntitlement2(), getCardWithApprenticeDiscountExpiredForDiscountEntitlement3());
        for (int i = 0; i < 3; i++) {
            List<String> ruleBreaches = new ArrayList<String>();
            transferSourceCardServiceImpl.checkCardApprenticeDiscounted(OYSTER_NUMBER_1, ruleBreaches);
            assertEquals(ContentCode.TRANSFERS_CARD_APPRENTICE_DISCOUNTED.textCode(), ruleBreaches.get(0));
        }
    }

    @Test
    public void testIsEighteenPlusDiscounted() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardEighteenPlusDiscounted(),
                        getCardEighteenPlusDiscountedForDiscountEntitlement2(), getCardEighteenPlusDiscountedForDiscountEntitlement3());
        for (int i = 0; i < 3; i++) {
            List<String> ruleBreaches = new ArrayList<String>();
            transferSourceCardServiceImpl.checkCardEighteenPlusDiscounted(OYSTER_NUMBER_1, ruleBreaches);
            assertEquals(ContentCode.TRANSFERS_CARD_EIGHTEEN_PLUS_DISCOUNTED.textCode(), ruleBreaches.get(0));
        }
    }

    @Test
    public void testIsApprenticeNotDiscounted() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTravelCardWithNoDiscount());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardApprenticeDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIscardJobCenterNotDiscounted() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTravelCardWithNoDiscount());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardJobCenterPlusDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIscardJobCenterDiscounted() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithJobCenterPlusDiscountOnTravelCards());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardJobCenterPlusDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.TRANSFERS_TRAVELCARD_JOB_CENTRE_PLUS_DISCOUNTED.textCode(), ruleBreaches.get(0));
    }

    @Test
    public void testHasProductTypeBeenPreviouslyTradedForFalseCondition() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(10L);
        when(mockOrderDataService.findById(anyLong())).thenReturn(getOrderDTOWithItems());
        when(mockTravelCardService.isTravelCard(any(ItemDTO.class))).thenReturn(Boolean.TRUE);
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.hasTravelCardBeenPreviouslyTraded(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testHasProductTypeBeenPreviouslyTradedForTrueCondition() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(10L);
        when(mockOrderDataService.findById(anyLong())).thenReturn(getProductItemDTOTypeOfPreviouslyTradedTicket());
        when(mockTravelCardService.isTravelCard(any(ItemDTO.class))).thenReturn(Boolean.TRUE);
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.hasTravelCardBeenPreviouslyTraded(OYSTER_NUMBER_1, ruleBreaches);
        assertFalse(ruleBreaches.isEmpty());
    }

    @Test
    public void testIsAutoTopUpEnabledInCard() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadState());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.isAutoTopEnabledInCard(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());

    }

    @Test
    public void testIsAutoTopUpDisabledInCard() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(
                        getTestCardInfoResponseV2DTOWithAutoTopUpStateDisabled());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.isAutoTopEnabledInCard(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.TRANSFERS_AUTO_TOP_UP_DISABLED.textCode(), ruleBreaches.get(0));

    }

    @Test
    public void testHasTravelCardLessThenADayRemaining() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTravelCardWithMoreThenADayRemaining());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkTravelCardAtleastOneDayRemaining(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }
    
    @Test
    public void testHasTravelCardLessThenADayRemainingWithCurrentDate() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTravelCardWithCurrentDate());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkTravelCardAtleastOneDayRemaining(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.TRANSFERS_TRAVELCARD_ATLEASTONEDAY_REMAINING.textCode(), ruleBreaches.get(0));;
    }

    @Test
    public void testHasTravelCardMoreThenADayRemaining() {

        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(
                        getTestCardInfoResponseV2DTOWithMoreThenOneDayRemaining());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkTravelCardAtleastOneDayRemaining(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testHasTravelCardMoreThenADayRemainingForPendingTickets() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(
                        getTestCardInfoResponseV2DTOWithMoreThenOneDayRemainingForPendingTickets());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkTravelCardAtleastOneDayRemaining(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testCheckPayAsYouGoAmountIsMoreThenFifty() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTotalAmountMoreThenLimit());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkIfPayAsYouGoAmountIsMoreThanFifty(OYSTER_NUMBER_1, ruleBreaches);
        assertFalse(ruleBreaches.isEmpty());
    }

    @Test
    public void testCheckPAYGAmountIsLessThenFifty() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTotalAmountLessThenLimit());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkIfPayAsYouGoAmountIsMoreThanFifty(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testHasProductItemOftypeBusOrTram() {
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOOfTypeBus());
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getProdouctItemOfTypeBus());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkProductItemOfTypeBusOrTram(OYSTER_NUMBER_1, ruleBreaches);
        assertFalse(ruleBreaches.isEmpty());
    }

    @Test
    public void testHasProductItemOftypeBusOrTramForPrePayTicket() {

        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getProdouctItemOfTypeBusForPrePayTicket());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOOfTypeBus());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkProductItemOfTypeBusOrTram(OYSTER_NUMBER_1, ruleBreaches);
        assertFalse(ruleBreaches.isEmpty());
    }

    @Test
    public void testHasProductItemNotOftypeBusOrTram() {

        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getProdouctItemOfOnlyTravelCard());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOTravelCard());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkProductItemOfTypeBusOrTram(OYSTER_NUMBER_1, ruleBreaches);
        assertFalse(!ruleBreaches.isEmpty());
    }

    @Test
    public void testIsCardDiscounted() {
        TransferSourceCardServiceImpl mockTransferSourceCardServiceImpl = mock(TransferSourceCardServiceImpl.class);
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithDiscountEntitlement());
        List<String> ruleBreaches = new ArrayList<String>();
        doCallRealMethod().when(mockTransferSourceCardServiceImpl).checkCardDiscounted(anyString(), anyList());
        when(mockTransferSourceCardServiceImpl.isCardDiscountFieldNotBlank(anyString())).thenReturn(true);
        when(mockTransferSourceCardServiceImpl.hasTravelCards(anyString())).thenReturn(true);
        mockTransferSourceCardServiceImpl.checkCardDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        verify(mockTransferSourceCardServiceImpl, atLeast(1)).checkCardApprenticeDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        verify(mockTransferSourceCardServiceImpl, atLeast(1)).checkCardEighteenPlusDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        verify(mockTransferSourceCardServiceImpl, atLeast(1)).checkCardJobCenterPlusDiscounted(OYSTER_NUMBER_1, ruleBreaches);
    }

    @Test
    public void testIsCardNotDiscounted() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTravelCards());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testHasCardDeposit() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWitDeposit());
        assertTrue(transferSourceCardServiceImpl.hasDepositOnCard(OYSTER_NUMBER_1));
    }

    @Test
    public void testHasCardNoDeposit() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithNoDeposit());
        assertFalse(transferSourceCardServiceImpl.hasDepositOnCard(OYSTER_NUMBER_1));
    }

    @Test
    public void testHasPayAsYouGoItemLessZeroOrNegativeBalance() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithPayAsYouGoItemLessThenZero());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkPayAsYouGoHasNegativeAmount(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.TRANSFERS_PAYASYOUGO_HAS_NEGATIVE_AMOUNT.textCode(), ruleBreaches.get(0));

    }

    @Test
    public void testPayAsYouGoIteMorethenZero() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithPayAsyouItemMoreThenZero());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkPayAsYouGoHasNegativeAmount(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIfPayAsYouGoAmountIsMoreThenFifty() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTotalAmountMoreThenLimit());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkIfPayAsYouGoAmountIsMoreThanFifty(OYSTER_NUMBER_1, ruleBreaches);
        assertFalse(ruleBreaches.isEmpty());
    }

    @Test
    public void testIfPAYGAmountIsLessThenFifty() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTotalAmountLessThenLimit());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkIfPayAsYouGoAmountIsMoreThanFifty(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIsDiscountEntitlementExpired() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithApprenticeDiscountExpired());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkCardApprenticeDiscounted(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());

    }

    @Test
    public void testIsDiscountEntitlementEmptyReturnFalse() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardWithApprenticeDiscountExpired());
        assertFalse(transferSourceCardServiceImpl.isDiscountEntitlementExpired(""));
    }

    @Test
    public void testHasTravelCards() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTravelCards(),
                        getCardReponseWithPendingTravelCards());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOTravelCard());
        for (int i = 0; i < 2; i++) {
            assertTrue(transferSourceCardServiceImpl.hasTravelCards(OYSTER_NUMBER_1));
        }
    }

    @Test
    public void testHasNoTravelCards() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getProdouctItemOfTypeBusForPrePayTicket());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOTravelCard());
        assertFalse(transferSourceCardServiceImpl.hasTravelCards(OYSTER_NUMBER_1));
    }

    @Test
    public void testIsSourceCardEligibleForHotlistedCard() {

        TransferSourceCardServiceImpl mockTransferSourceCardServiceImpl = mock(TransferSourceCardServiceImpl.class);
        when(mockTransferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTOWithHotlistedCard());
        when(mockTransferSourceCardServiceImpl.isSourceCardEligible(OYSTER_NUMBER_1)).thenCallRealMethod();
        List<String> ruleBreaches = new ArrayList<String>();
        ruleBreaches.add(ContentCode.HOTLISTED.textCode());
        when(mockTransferSourceCardServiceImpl.checkCardHotlisted(OYSTER_NUMBER_1, new ArrayList<String>())).thenReturn(ruleBreaches);
        when(mockTransferSourceCardServiceImpl.isSourceCardEligible(OYSTER_NUMBER_1)).thenCallRealMethod();
        assertTrue(!ruleBreaches.isEmpty());
    }

    @Test
    public void testIfCardHasItemsToTransfer() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithPayAsyouItemMoreThenZero());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOTravelCard());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkIfCardHasItemsToTransfer(OYSTER_NUMBER_1, ruleBreaches);
        assertTrue(ruleBreaches.isEmpty());
    }

    @Test
    public void testIfCardHasNoItemsToTransfer() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithPayAsYouGoItemIsZero());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOTravelCard());
        List<String> ruleBreaches = new ArrayList<String>();
        transferSourceCardServiceImpl.checkIfCardHasItemsToTransfer(OYSTER_NUMBER_1, ruleBreaches);
        assertEquals(ContentCode.TRANSFERS_CARD_HAS_NO_PRODUCTS_TO_TRANSFER.textCode(), ruleBreaches.get(0));
    }

    @Test
    public void testTotalPendingPrePayValueItems() {
        when(transferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTotalPayAsYouGo());
        assertEquals(new Integer(0), transferSourceCardServiceImpl.getPayAsYouGoAmount(OYSTER_NUMBER_1));
    }

    @Test
    public void testIsSourceCardEligible() {

        TransferSourceCardServiceImpl mockTransferSourceCardServiceImpl = mock(TransferSourceCardServiceImpl.class);
        when(mockTransferSourceCardServiceImpl.getCardInfoResponseFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO16());
        when(mockTransferSourceCardServiceImpl.isSourceCardEligible(OYSTER_NUMBER_1)).thenCallRealMethod();
        mockTransferSourceCardServiceImpl.isSourceCardEligible(OYSTER_NUMBER_1);
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkProductItemOfTypeBusOrTram(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkCardDiscounted(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkCardHotlisted(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkTravelCardAtleastOneDayRemaining(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkPassengerTypeAdult(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkIfPayAsYouGoAmountIsMoreThanFifty(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkIfCardHasItemsToTransfer(OYSTER_NUMBER_1, new ArrayList<String>());
        verify(mockTransferSourceCardServiceImpl, atLeastOnce()).checkPayAsYouGoHasNegativeAmount(OYSTER_NUMBER_1, new ArrayList<String>());
    }
}
