package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.APPROVAL_ID;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.CARD_DEPOSIT;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO10;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmdItemWithDefaultItems;
import static com.novacroft.nemo.test_support.CartTestUtil.CART_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.LAST_NAME_3;
import static com.novacroft.nemo.test_support.CartTestUtil.TEST_REFUNDABLE_DEPOSIT_PRICE;
import static com.novacroft.nemo.test_support.CartTestUtil.TEST_TRAVEL_CARD_PRICE;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ADDRESS_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.REFUNDAMOUNT;
import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.getWorkflowItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil;
import com.novacroft.nemo.test_support.FailedCardRefundCartTestUtil;
import com.novacroft.nemo.test_support.GoodwillPaymentTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;


public class RefundToWorkflowConverterTest {
    private RefundToWorkflowConverter converter;
    private CardDataService mockCardService;
    private GetCardService mockGetCardService;
    private AdministrationFeeDataService mockAdministrationFeeDataService;
    private RefundService mockRefundService;
    private CustomerDataService mockCustomerService;
    private CartService mockCartService;
    private CartDTO mockCartDto;
    private RefundCalculationBasisService mockRefundCalculationService;
    private WorkFlowService mockWorkflowService;
    private AddressDataService mockAddressDataService;
    
    private CartCmdImpl cartCmdImpl;
    private CardDTO card;
    
    @Before
    public void init(){
        converter = new RefundToWorkflowConverter();
        
        mockCardService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockAdministrationFeeDataService = mock(AdministrationFeeDataService.class);
        mockRefundService = mock(RefundService.class);
        mockCustomerService = mock(CustomerDataService.class);
        mockCartService = mock(CartService.class);
        mockCartDto = mock(CartDTO.class);
        mockRefundCalculationService = mock(RefundCalculationBasisService.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockAddressDataService = mock(AddressDataService.class);
        converter.cardService = mockCardService;
        converter.getCardService = mockGetCardService;
        converter.refundService = mockRefundService;
        converter.administrationFeeDataService = mockAdministrationFeeDataService;
        converter.customerDataService = mockCustomerService;
        converter.cartService = mockCartService;
        converter.refundCalculationBasisService = mockRefundCalculationService;
        converter.workflowService = mockWorkflowService;
        converter.addressService = mockAddressDataService;

        cartCmdImpl = getTestCartCmdItemWithDefaultItems();
        cartCmdImpl.getCartDTO().setId(CART_ID_1);
        cartCmdImpl.setPaymentType(PaymentType.AD_HOC_LOAD.code());
        cartCmdImpl.setApprovalId(APPROVAL_ID);
        cartCmdImpl.setLastName(LAST_NAME_3);
        cartCmdImpl.setCartItemCmd(FailedCardRefundCartTestUtil.getTestCartItemCmdImplWithUnExpiredDate1());
        cartCmdImpl.getCartItemCmd().setPreviouslyExchanged(Boolean.TRUE);
        card = getTestCardDTO1();
        
        when(mockCartDto.getCartItems()).thenReturn(Arrays.asList(new ItemDTO()));
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO10());
        when(mockCartService.findById(anyLong())).thenReturn(mockCartDto);
        
        when(mockCartDto.getCartRefundTotal()).thenReturn(TEST_TRAVEL_CARD_PRICE);
        when(mockCartDto.getCardRefundableDepositAmount()).thenReturn(TEST_REFUNDABLE_DEPOSIT_PRICE);
    }
    
    @Test
    public void shouldConvertToWorkflow() {
        when(mockCartService.postProcessAndSortCartDTOAndRecalculateRefund(mockCartDto)).thenReturn(mockCartDto);
        when(mockCardService.findByCardNumber(anyString())).thenReturn(card);
        when(mockWorkflowService.isTravelCardOrBusPass(any(ItemDTO.class))).thenReturn(false);
        
        WorkflowItemDTO actualWorkflow = converter.convert(cartCmdImpl);
        
        verify(mockCartService).findById(CART_ID_1);
        verify(mockCardService).findByCardNumber(OYSTER_NUMBER_1);
        
        assertNotNull(actualWorkflow);
        RefundDetailDTO actualRefundDetail = actualWorkflow.getRefundDetails();
        assertNotNull(actualRefundDetail);
        assertEquals(CARD_ID_1, actualRefundDetail.getCardId());
    }
    
    @Test
    public void shouldConvertToCartCmdImpl() {
        when(mockCardService.findById(anyLong())).thenReturn(card);
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(mockCartDto);
        
        WorkflowItemDTO testWorkflowDTO = getWorkflowItem();
        testWorkflowDTO.getRefundDetails().setCardId(CARD_ID_1);
        testWorkflowDTO.getRefundDetails().setTotalRefundAmount(REFUNDAMOUNT);
        testWorkflowDTO.getRefundDetails().setRefundDate(new DateTime());
        CartCmdImpl actualResult = converter.convert(testWorkflowDTO);
        
        verify(mockCardService).findById(CARD_ID_1);
        verify(mockCartService).findByApprovalId(anyLong());
        assertEquals(CARD_ID_1, actualResult.getCardId());
    }
    
    @Test
    public void testGetDepositAmountIscalledFromCubic() {
        Integer actualResult = converter.getCardDepositAmount(OYSTER_NUMBER_1);
        
        verify(mockGetCardService).getCard(OYSTER_NUMBER_1);
        assertEquals(CARD_DEPOSIT, actualResult);
    }
    
    @Test
    public void getCardDepositAmountShouldReturnNull() {
        assertNull(converter.getCardDepositAmount(null));
    }

    @Test
    public void testhasChangedFromDefault() {
        when(mockWorkflowService.hasAdminFeeChangedFromDefault(any(CartCmdImpl.class))).thenReturn(Boolean.TRUE);
        when(mockWorkflowService.hasCalculationBasisChangedFromDefault(any(CartCmdImpl.class))).thenReturn(Boolean.TRUE);
        when(mockWorkflowService.hasPayAsYouGoChangedFromDefault(any(CartCmdImpl.class))).thenReturn(Boolean.TRUE);
        
        RefundDetailDTO actualRefundDetailDTO = new RefundDetailDTO();
        converter.hasChangedFromDefault(cartCmdImpl, actualRefundDetailDTO);
        
        verify(mockWorkflowService).hasAdminFeeChangedFromDefault(cartCmdImpl);
        verify(mockWorkflowService).hasCalculationBasisChangedFromDefault(cartCmdImpl);
        verify(mockWorkflowService).hasPayAsYouGoChangedFromDefault(cartCmdImpl);
        assertTrue(actualRefundDetailDTO.getAdminFeeChanged());
        assertTrue(actualRefundDetailDTO.getCalculationBasisChanged());
        assertTrue(actualRefundDetailDTO.getPayAsYouGoChanged());
    }
    
    @Test
    public void shouldFindAddressForCardHolder() {
        CustomerDTO mockReturnedCustomerDTO = getTestCustomerDTO1();
        AddressDTO mockReturnedAddressDTO = mock(AddressDTO.class);
        when(mockCustomerService.findByCardId(anyLong())).thenReturn(mockReturnedCustomerDTO);
        when(mockAddressDataService.findById(anyLong())).thenReturn(mockReturnedAddressDTO);
        
        AddressDTO actualResult = converter.findAddressForCardHolder(CUSTOMER_ID_1);
        
        verify(mockCustomerService).findByCardId(CUSTOMER_ID_1);
        verify(mockAddressDataService).findById(CUSTOMER_ADDRESS_ID_1);
        assertEquals(mockReturnedAddressDTO, actualResult);
    }
    
    @Test
    public void shouldCreateAddressFromCartCmd() {
        assertNotNull(RefundToWorkflowConverter.createAddressFromCartCmd(cartCmdImpl));
    }
    
    @Test
    public void isGoodwillPaymentShouldReturnTrue() {
        assertTrue(converter.isGoodwillPayment(new GoodwillPaymentItemDTO()));
    }
    
    @Test
    public void isGoodwillPaymentShouldReturnFalse() {
        assertFalse(converter.isGoodwillPayment(new PayAsYouGoItemDTO()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnFailedCard() {
        assertEquals(RefundScenarioEnum.FAILEDCARD, 
                        converter.findScenarioByCartType(CartType.FAILED_CARD_REFUND.code()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnDestroyedCard() {
        assertEquals(RefundScenarioEnum.DESTROYED, 
                        converter.findScenarioByCartType(CartType.DESTROYED_CARD_REFUND.code()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnLost() {
        assertEquals(RefundScenarioEnum.LOST, 
                        converter.findScenarioByCartType(CartType.LOST_REFUND.code()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnStolen() {
        assertEquals(RefundScenarioEnum.STOLEN, 
                        converter.findScenarioByCartType(CartType.STOLEN_REFUND.code()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnCancelled() {
        assertEquals(RefundScenarioEnum.CANCEL_AND_SURRENDER, 
                        converter.findScenarioByCartType(CartType.CANCEL_SURRENDER_REFUND.code()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnAnonymousGoodwill() {
        assertEquals(RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND, 
                        converter.findScenarioByCartType(CartType.ANONYMOUS_GOODWILL_REFUND.code()));
    }
    
    @Test
    public void findScenarioByCartTypeShouldReturnStandardGoodwill() {
        assertEquals(RefundScenarioEnum.STANDALONE_GOODWILL_REFUND, 
                        converter.findScenarioByCartType(CartType.STANDALONE_GOODWILL_REFUND.code()));
    }

    @Test
    public void shouldConvertItemDetailsTravelcard() {
        ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
        ProductItemDTO travelcard = ProductItemTestUtil.getTestTravelCardProductDTO_RefundExample1();
        travelcard.setRefund(new Refund());
        itemList.add(travelcard);
        when(mockCartDto.getCartItems()).thenReturn(itemList);
        when(mockWorkflowService.isTravelCardOrBusPass(any(ItemDTO.class))).thenReturn(Boolean.TRUE);
        converter.convertItemDetails(mockCartDto, new RefundDetailDTO());
    }

    @Test
    public void shouldConvertItemDetailsGoodwillItem() {
        ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
        itemList.add(GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1());
        when(mockCartDto.getCartItems()).thenReturn(itemList);
        when(mockWorkflowService.isItemDtoTypeOfGoodwillPaymentDTO(any(ItemDTO.class))).thenReturn(Boolean.TRUE);
        converter.convertItemDetails(mockCartDto, new RefundDetailDTO());
    }

    @Test
    public void shouldConvertItemDetailsAdministrationFeeItem() {
        ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
        itemList.add(AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice());
        when(mockCartDto.getCartItems()).thenReturn(itemList);
        when(mockWorkflowService.isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class))).thenReturn(Boolean.TRUE);
        converter.convertItemDetails(mockCartDto, new RefundDetailDTO());
    }

    @Test
    public void shouldConvertItemDetailsPayAsYouGoItem() {
        ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
        itemList.add(PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1());
        when(mockCartDto.getCartItems()).thenReturn(itemList);
        when(mockWorkflowService.isItemDtoTypeOfPayAsYouGoDTO(any(ItemDTO.class))).thenReturn(Boolean.TRUE);
        converter.convertItemDetails(mockCartDto, new RefundDetailDTO());
    }
    
    @Test
    public void totalRefundAmountShouldIncludeDepositForFailedCardRefund(){
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(mockCartDto);
        cartCmdImpl.setCartType(CartType.FAILED_CARD_REFUND.code());
        
        RefundDetailDTO refundDetail = new RefundDetailDTO();
        Refund refund = new Refund();
        converter.setTotalRefundAmount(cartCmdImpl, refundDetail, refund);
        
        Long expectedResult = TEST_TRAVEL_CARD_PRICE.longValue() + TEST_REFUNDABLE_DEPOSIT_PRICE.longValue();
        assertEquals(expectedResult, refundDetail.getTotalRefundAmount());
        assertEquals(expectedResult, refund.getRefundAmount());
    }
    
    @Test
    public void totalRefundAmountShouldIncludeDepositForDestroyedCardRefund(){
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(mockCartDto);
        cartCmdImpl.setCartType(CartType.DESTROYED_CARD_REFUND.code());
        
        RefundDetailDTO refundDetail = new RefundDetailDTO();
        Refund refund = new Refund();
        converter.setTotalRefundAmount(cartCmdImpl, refundDetail, refund);
        
        Long expectedResult = TEST_TRAVEL_CARD_PRICE.longValue() + TEST_REFUNDABLE_DEPOSIT_PRICE.longValue();
        assertEquals(expectedResult, refundDetail.getTotalRefundAmount());
        assertEquals(expectedResult, refund.getRefundAmount());
    }
    
    @Test
    public void totalRefundAmountShouldNotIncludeDeposit(){
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(mockCartDto);
        cartCmdImpl.setCartType(CartType.LOST_REFUND.code());
        
        RefundDetailDTO refundDetail = new RefundDetailDTO();
        Refund refund = new Refund();
        converter.setTotalRefundAmount(cartCmdImpl, refundDetail, refund);
        
        Long expectedResult = TEST_TRAVEL_CARD_PRICE.longValue();
        assertEquals(expectedResult, refundDetail.getTotalRefundAmount());
        assertEquals(expectedResult, refund.getRefundAmount());
    }
    
    @Test
    public void shouldConvertCustomerDetails() {
        RefundDetailDTO testRefundDetailDto = new RefundDetailDTO();
        CustomerDTO testCustomerDto = getTestCustomerDTO1();
        testCustomerDto.setUsername(USERNAME_1);
        when(mockCartService.findCustomerForCart(anyLong())).thenReturn(testCustomerDto);
        
        converter.convertCustomerDetails(getTestCartCmdItemWithDefaultItems(), testRefundDetailDto);
        
        assertEquals(testCustomerDto, testRefundDetailDto.getCustomer());
        assertEquals(CUSTOMER_ID_1, testRefundDetailDto.getCustomerId());
        assertEquals(USERNAME_1, testRefundDetailDto.getName());
    }
}
