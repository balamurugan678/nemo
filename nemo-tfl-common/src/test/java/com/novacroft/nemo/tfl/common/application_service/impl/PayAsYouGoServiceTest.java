package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithThreeProductItems;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartWithPayAsYouGoItem;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.getTestJobCentrePlusDiscountDTO3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.GetCardTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.JobCentrePlusInvestigationDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for New Pay As You Go Service
 */
public class PayAsYouGoServiceTest {

    private PayAsYouGoServiceImpl service;
    private CartDTO cartDTO;
    private CartItemCmdImpl cartItemCmd;
    private CartService mockCartService;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private CartAdministrationService mockNewCartAdministrationService;
    private AutoTopUpConfigurationService mockAutoTopUpConfigurationService;
    private SystemParameterService mockSystemParameterService;
    private JobCentrePlusInvestigationDataService mockJobCentrePlusInvestigationDataService;
    private CardUpdateService mockCardUpdateService;
    private AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService;

    @Before
    public void setUp() {
        service = new PayAsYouGoServiceImpl();
        cartItemCmd = new CartItemCmdImpl();
        cartDTO = new CartDTO();
        mockCartService = mock(CartService.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);
        mockAutoTopUpConfigurationService = mock(AutoTopUpConfigurationService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockJobCentrePlusInvestigationDataService = mock(JobCentrePlusInvestigationDataService.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        
        service.cartAdminService = mockNewCartAdministrationService;
        service.cartService = mockCartService;
        service.getCardService = mockGetCardService;
        service.autoTopUpConfigurationService = mockAutoTopUpConfigurationService;
        service.systemParameterService = mockSystemParameterService;
        service.cardDataService = mockCardDataService;
        service.jobCentrePlusInvestigationDataService = mockJobCentrePlusInvestigationDataService;
        service.cardUpdateService = mockCardUpdateService;
        service.adHocLoadSettlementDataService = mockAdHocLoadSettlementDataService;
    }

    @Test
    public void addCartItemForExistingCardShouldAddToCart() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyString())).thenReturn(getTestJobCentrePlusDiscountDTO3());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setAutoTopUpAmt(10);
        cartDTO = service.addCartItemForExistingCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }
    
    @Test
    public void addCartItemForExistingCardShouldRemoveFromCart() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class))).thenReturn(getNewCartWithPayAsYouGoItem());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyString())).thenReturn(getTestJobCentrePlusDiscountDTO3());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setAutoTopUpAmt(0);
        cartDTO = service.addCartItemForExistingCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void addCartItemForExistingCardWithNullCartItemCmdShouldShowAssertionException() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class))).thenReturn(null);
        cartDTO = service.addCartItemForExistingCard(cartDTO, null);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class));
    }

    @Test
    public void addCartItemForNewCardShouldAddToCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setAutoTopUpAmt(10);
        cartDTO = service.addCartItemForNewCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }
    
    @Test
    public void addCartItemForNewCardShouldRemoveFromCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartWithPayAsYouGoItem());
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setAutoTopUpAmt(0);
        cartDTO = service.addCartItemForNewCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void addCartItemForNewCardWithNullCartItemCmdShouldNotAddToCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class))).thenReturn(null);
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartDTO = service.addCartItemForNewCard(cartDTO, null);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(PayAsYouGoItemDTO.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
    }

    @Test
    public void addAutoTopUpCartItemForExistingCardShouldAddToCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setAutoTopUpAmt(10);
        cartDTO = service.addAutoTopUpCartItem(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test
    public void removeNonApplicableAutoTopUpCartItemShouldNotCallRemoveItemForEmptyCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartDTO = service.removeNonApplicableAutoTopUpCartItem(cartDTO);
        verify(mockCartService, never()).deleteItem(any(CartDTO.class), anyLong());
    }

    @Test
    public void removeNonApplicableAutoTopUpCartItemShouldCallRemoveItemForCartWithAutoTopUpItem() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(AutoTopUpConfigurationItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithThreeProductItems());
        AutoTopUpConfigurationItemDTO autoTopUpItem = new AutoTopUpConfigurationItemDTO();
        cartDTO.addCartItem(autoTopUpItem);
        cartDTO = service.removeNonApplicableAutoTopUpCartItem(cartDTO);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(cartDTO.getCartItems().size(), 3);
    }
    
    @Test
    public void shouldUpdatePrePayValueToCubic() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString()))
                        .thenReturn(new Integer(1));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO2());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
    }


    @Test
    public void shouldPersistRequestedAdHocLoadSettlementRecord() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        ArgumentCaptor<AdHocLoadSettlementDTO> settlementArgument = ArgumentCaptor.forClass(AdHocLoadSettlementDTO.class);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(
                        new Integer(1));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO2());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
        verify(mockAdHocLoadSettlementDataService).createOrUpdate(settlementArgument.capture());
        assertEquals(SettlementStatus.REQUESTED.code(), settlementArgument.getValue().getStatus());
    }

    @Test
    public void shouldPersistUpdatedAdHocLoadSettlementRecord() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        ArgumentCaptor<AdHocLoadSettlementDTO> settlementArgument = ArgumentCaptor.forClass(AdHocLoadSettlementDTO.class);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(new Integer(1));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO2());
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO2());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
        verify(mockAdHocLoadSettlementDataService).createOrUpdate(settlementArgument.capture());
        assertEquals(SettlementStatus.REQUESTED.code(), settlementArgument.getValue().getStatus());
    }

    @Test
    public void shouldPersistFailedAdHocLoadSettlementRecord() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        ArgumentCaptor<AdHocLoadSettlementDTO> settlementArgument = ArgumentCaptor.forClass(AdHocLoadSettlementDTO.class);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenThrow(
                        new ApplicationServiceException("Error"));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO2());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertTrue(error);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
        verify(mockAdHocLoadSettlementDataService).createOrUpdate(settlementArgument.capture());
        assertEquals(SettlementStatus.FAILED.code(), settlementArgument.getValue().getStatus());
    }

    @Test
    public void shouldNotPersistAdHocLoadSettlementRecordWhenRequestSequenceNumberIsNull() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
        verify(mockAdHocLoadSettlementDataService, never()).createOrUpdate(any(AdHocLoadSettlementDTO.class));
    }

    @Test
    public void shouldUpdateAdHocLoadSettlementDTOProperties() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        CartDTO testCartDTO = cartCmd.getCartDTO();
        AdHocLoadSettlementDTO settlementDTO = SettlementTestUtil.getTestAdHocLoadSettlementDTO1WithId();
        Integer cubicRequestNumber = new Integer(1);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(cubicRequestNumber);
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(
                        settlementDTO);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(settlementDTO);

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        assertEquals(settlementDTO.getCardId().longValue(), CartUtil.getPayAsYouGoItem(testCartDTO.getOrder().getOrderItems()).getCardId().longValue());
        assertEquals(settlementDTO.getOrderId().longValue(), testCartDTO.getOrder().getId().longValue());
        assertEquals(settlementDTO.getSettlementDate().getTime(), testCartDTO.getPaymentCardSettlement().getSettlementDate().getTime());
        assertEquals(settlementDTO.getItem().getId().longValue(), CartUtil.getPayAsYouGoItem(testCartDTO.getOrder().getOrderItems()).getId().longValue());
        assertEquals(settlementDTO.getAmount().intValue(), CartUtil.getPayAsYouGoItem(testCartDTO.getOrder().getOrderItems()).getPrice().intValue());
        assertNull(settlementDTO.getExpiresOn());
        assertEquals(settlementDTO.getPickUpNationalLocationCode().longValue(), cartCmd.getStationId().longValue());
        assertEquals(settlementDTO.getStatus(), SettlementStatus.REQUESTED.code());
        assertEquals(settlementDTO.getRequestSequenceNumber().intValue(), cubicRequestNumber.intValue());
    }

    
    @Test
    public void shouldUpdateAdHocLoadSettlementWithNewDate() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdWithNoPaymentCardSettlementImpl();
        cartCmd.getCartDTO().setOrder(OrderTestUtil.getOrderDTOWithItems());
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);
        cartCmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(
                        new Integer(1));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithPrePayDetails());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1WithId());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
    }

    @Test
    public void shouldNotUpdatePrePayValueToCubicAsCubicCardHasNoBalance() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(
                        new Integer(1));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithNoPrePayDetails());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, never()).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
    }
    
    @Test
    public void shouldNotUpdatePrePayValueToCubicIfPayAsYouGoItemIsNull()
    {
        cartDTO.addCartItem(new AutoTopUpConfigurationItemDTO());
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdImpl();
        cartCmd.getCartDTO().setCardId(CardTestUtil.CARD_ID);

        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString())).thenReturn(
                        new Integer(1));
        when(mockGetCardService.getCard(anyString())).thenReturn(GetCardTestUtil.getTestSuccessCardInfoResponseV2DTOWithNoPrePayDetails());

        boolean error = service.updatePrePayValueToCubic(cartCmd);

        assertFalse(error);
        verify(mockCardUpdateService, never()).requestCardUpdatePrePayValue(anyString(), any(Long.class), anyInt(), anyInt(), anyString());
        verify(mockCardDataService).findById(anyLong());
        verify(mockAdHocLoadSettlementDataService, never()).createOrUpdate(any(AdHocLoadSettlementDTO.class));
    }

    @Test
    public void shouldUpdateSettledAutoTopUpCartItem() {
        cartDTO = CartTestUtil.getNewCartWithPayAsYouGoItem();
        AutoTopUpConfigurationItemDTO existingItemDTO = cartDTO.getAutoTopUpItem();
        existingItemDTO.setAutoTopUpAmount(2000);
        service.updateSettledAutoTopUpCartItem(cartDTO, OrderTestUtil.ORDER_ID, CartTestUtil.STATION_ID_1, existingItemDTO);
        verify(mockAutoTopUpConfigurationService, times(1)).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
    }

    @Test
    public void shouldNotUpdateSettledAutoTopUpCartItem() {
        cartDTO = CartTestUtil.getNewCartWithPayAsYouGoItem();
        AutoTopUpConfigurationItemDTO existingItemDTO = cartDTO.getAutoTopUpItem();
        existingItemDTO.setAutoTopUpAmount(0);
        service.updateSettledAutoTopUpCartItem(cartDTO, OrderTestUtil.ORDER_ID, CartTestUtil.STATION_ID_1, existingItemDTO);
        verify(mockAutoTopUpConfigurationService, never()).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
    }


}
