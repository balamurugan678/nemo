package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;
import com.novacroft.nemo.tfl.common.command.impl.CancelAndSurrenderCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.BackdatedRefundReasonDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundEngineService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.BackdatedRefundReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class CancelAndSurrenderServiceImplTest {
    private CancelAndSurrenderServiceImpl cancelAndSurrenderService;
    private RefundEngineService mockRefundEngineService;
    private ProductDataService mockProductDataService;
    private BackdatedRefundReasonDataService mockBackdatedRefundReasonDataService;
    private RefundCalculationBasisService mockRefundCalculationBasisService;

    private CartItemCmdImpl mockCartItemCmd;
    private CartItemCmdImpl mockTradedTicketCartItemCmd;
    private ProductDTO mockProductDTO;
    private DateTime testDateTime;
    private Refund mockRefund;
    private CartCmdImpl mockCartCmd;
    private List<CartItemCmdImpl> testCartItemList;

    @Before
    public void setup() {
        cancelAndSurrenderService = mock(CancelAndSurrenderServiceImpl.class);
        mockRefundEngineService = mock(RefundEngineService.class);
        mockProductDataService = mock(ProductDataService.class);
        mockBackdatedRefundReasonDataService = mock(BackdatedRefundReasonDataService.class);
        this.mockRefundCalculationBasisService = mock(RefundCalculationBasisService.class);

        cancelAndSurrenderService.refundEngineService = mockRefundEngineService;
        cancelAndSurrenderService.productDataService = mockProductDataService;
        cancelAndSurrenderService.backdatedRefundReasonDataService = mockBackdatedRefundReasonDataService;
        cancelAndSurrenderService.dateFormat = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
        this.cancelAndSurrenderService.refundCalculationBasisService = this.mockRefundCalculationBasisService;

        this.mockCartItemCmd = mock(CartItemCmdImpl.class);
        this.mockTradedTicketCartItemCmd = mock(CartItemCmdImpl.class);
        this.mockProductDTO = mock(ProductDTO.class);
        this.testDateTime = new DateTime(DateTestUtil.getAug19());
        this.mockRefund = mock(Refund.class);
        this.mockCartCmd = mock(CartCmdImpl.class);

        this.testCartItemList = new ArrayList<CartItemCmdImpl>();
        this.testCartItemList.add(this.mockCartItemCmd);
    }

    @Test
    public void testProcessCancelOrSurrenderRefund() {
        CancelAndSurrenderCmd cmd = new CancelAndSurrenderCmdImpl();
        Refund refund = new Refund();
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        when(mockRefundEngineService.calculateRefund(any(DateTime.class), any(DateTime.class), anyLong(), any(DateTime.class),
                any(RefundCalculationBasis.class))).thenReturn(refund);
        when(cancelAndSurrenderService.findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class)))
                .thenReturn(product);
        when(cancelAndSurrenderService.processCancelOrSurrenderRefund(cmd)).thenCallRealMethod();

        cmd.setRefundTicketStartZone("1");
        cmd.setRefundTicketEndZone("1");
        cmd.setTicketStartDate(new DateTime("10012100"));
        cmd.setTicketEndDate(new DateTime("10012100"));
        cmd.setRefundDate(new DateTime("10012100"));

        cmd.setDeceasedCustomer(true);
        Refund processCancelOrSurrenderRefund = cancelAndSurrenderService.processCancelOrSurrenderRefund(cmd);
        assertEquals(processCancelOrSurrenderRefund, refund);
    }

    @Test
    public void testFindProduct() {
        DateTime dateTime = new DateTime("10012100");

        when(mockRefundEngineService.findTravelCardTypeByDuration(any(DateTime.class), any(DateTime.class)))
                .thenReturn(Durations.OTHER.getDurationType());

        doCallRealMethod().when(cancelAndSurrenderService)
                .findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class));
        ProductDTO findProduct = cancelAndSurrenderService.findProduct(1, 1, dateTime, dateTime);
        assertNotNull(findProduct.getProductName());
    }

    @Test
    public void shouldGetBackdatedRefundTypes() {
        BackdatedRefundReasonDTO mockBackdatedRefundReasonDTO = mock(BackdatedRefundReasonDTO.class);
        when(mockBackdatedRefundReasonDTO.getReasonId()).thenReturn(0L);
        when(mockBackdatedRefundReasonDTO.getDescription()).thenReturn("test-description");
        List<BackdatedRefundReasonDTO> testBackdatedRefundReasons = new ArrayList<BackdatedRefundReasonDTO>();
        testBackdatedRefundReasons.add(mockBackdatedRefundReasonDTO);

        when(cancelAndSurrenderService.getBackdatedRefundTypes()).thenCallRealMethod();
        when(mockBackdatedRefundReasonDataService.findAll()).thenReturn(testBackdatedRefundReasons);

        cancelAndSurrenderService.getBackdatedRefundTypes();

        verify(mockBackdatedRefundReasonDTO).getReasonId();
        verify(mockBackdatedRefundReasonDTO).getDescription();
        verify(cancelAndSurrenderService).getBackdatedRefundTypes();
        verify(mockBackdatedRefundReasonDataService).findAll();
    }

    @Test
    public void setTicketTypeTest() {
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        doCallRealMethod().when(cancelAndSurrenderService).setTicketType(cmd);
        cancelAndSurrenderService.setTicketType(cmd);
        assertEquals(cmd.getTicketType(), TicketType.TRAVEL_CARD.code());
    }

    @Test
    public void setTicketTypeBusPassTest() {
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        cmd.setTravelCardType("travelcard Bus Pass");

        doCallRealMethod().when(cancelAndSurrenderService).setTicketType(cmd);
        cancelAndSurrenderService.setTicketType(cmd);
        assertEquals(cmd.getTicketType(), TicketType.BUS_PASS.code());
    }

    @Test
    public void setTravelcardTypeByFormTravelCardTypeTest() {
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        cmd.setTravelCardType("Annual Bus Pass");

        doCallRealMethod().when(cancelAndSurrenderService).setTravelcardTypeByFormTravelCardType(cmd);

        cancelAndSurrenderService.setTravelcardTypeByFormTravelCardType(cmd);
        assertEquals(cmd.getTravelCardType(), Durations.ANNUAL.getDurationType());
    }

    @Test
    public void isTradedTicketItemShouldReturnTrue() {
        when(this.cancelAndSurrenderService.isTradedTicketItem(any(CartItemCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartItemCmd.getTradedTicket()).thenReturn(this.mockTradedTicketCartItemCmd);

        assertTrue(this.cancelAndSurrenderService.isTradedTicketItem(this.mockCartItemCmd));

        verify(this.mockCartItemCmd).getTradedTicket();
    }

    @Test
    public void isTradedTicketItemShouldReturnFalse() {
        when(this.cancelAndSurrenderService.isTradedTicketItem(any(CartItemCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartItemCmd.getTradedTicket()).thenReturn(null);

        assertFalse(this.cancelAndSurrenderService.isTradedTicketItem(this.mockCartItemCmd));

        verify(this.mockCartItemCmd).getTradedTicket();
    }

    @Test
    public void isNotAdministrationFeeCancelAndSurrenderRefundItemShouldReturnTrue() {
        when(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenCallRealMethod();
        when(this.cancelAndSurrenderService.isAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenReturn(Boolean.FALSE);
        assertTrue(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(this.mockCartItemCmd));
        verify(this.cancelAndSurrenderService).isAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class));
    }

    @Test
    public void isNotAdministrationFeeCancelAndSurrenderRefundItemShouldReturnFalse() {
        when(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenCallRealMethod();
        when(this.cancelAndSurrenderService.isAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenReturn(Boolean.TRUE);
        assertFalse(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(this.mockCartItemCmd));
        verify(this.cancelAndSurrenderService).isAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class));
    }

    @Test
    public void isAdministrationFeeCancelAndSurrenderRefundItemShouldReturnTrue() {
        when(this.cancelAndSurrenderService.isAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenCallRealMethod();
        when(this.mockCartItemCmd.getItem())
                .thenReturn(CancelAndSurrenderServiceImpl.ADMINISTRATION_FEE_CANCEL_AND_SURRENDER_REFUND_ITEM);
        assertTrue(this.cancelAndSurrenderService.isAdministrationFeeCancelAndSurrenderRefundItem(this.mockCartItemCmd));
        verify(this.mockCartItemCmd).getItem();
    }

    @Test
    public void isAdministrationFeeCancelAndSurrenderRefundItemShouldReturnFalse() {
        when(this.cancelAndSurrenderService.isAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenCallRealMethod();
        when(this.mockCartItemCmd.getItem()).thenReturn(StringUtil.EMPTY_STRING);
        assertFalse(this.cancelAndSurrenderService.isAdministrationFeeCancelAndSurrenderRefundItem(this.mockCartItemCmd));
        verify(this.mockCartItemCmd).getItem();
    }

    @Test
    public void isNotPayAsYouGoCreditItemShouldReturnTrue() {
        when(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenCallRealMethod();
        when(this.cancelAndSurrenderService.isPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.FALSE);
        assertTrue(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(this.mockCartItemCmd));
        verify(this.cancelAndSurrenderService).isPayAsYouGoCreditItem(any(CartItemCmdImpl.class));
    }

    @Test
    public void isNotPayAsYouGoCreditItemShouldReturnFalse() {
        when(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenCallRealMethod();
        when(this.cancelAndSurrenderService.isPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(this.mockCartItemCmd));
        verify(this.cancelAndSurrenderService).isPayAsYouGoCreditItem(any(CartItemCmdImpl.class));
    }

    @Test
    public void isPayAsYouGoCreditItemShouldReturnTrue() {
        when(this.cancelAndSurrenderService.isPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartItemCmd.getItem()).thenReturn(CancelAndSurrenderServiceImpl.PAY_AS_YOU_GO_CREDIT_ITEM);
        assertTrue(this.cancelAndSurrenderService.isPayAsYouGoCreditItem(this.mockCartItemCmd));
        verify(this.mockCartItemCmd).getItem();
    }

    @Test
    public void isPayAsYouGoCreditItemShouldReturnFalse() {
        when(this.cancelAndSurrenderService.isPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartItemCmd.getItem()).thenReturn(StringUtil.EMPTY_STRING);
        assertFalse(this.cancelAndSurrenderService.isPayAsYouGoCreditItem(this.mockCartItemCmd));
        verify(this.mockCartItemCmd).getItem();
    }

    @Test
    public void shouldProcessTradedTicket() {
        doCallRealMethod().when(this.cancelAndSurrenderService)
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));

        when(this.mockCartItemCmd.getTradedTicket()).thenReturn(this.mockTradedTicketCartItemCmd);
        when(this.mockTradedTicketCartItemCmd.getStartDate()).thenReturn(DateTestUtil.AUG_19);
        when(this.mockTradedTicketCartItemCmd.getEndDate()).thenReturn(DateTestUtil.AUG_19);
        when(this.cancelAndSurrenderService.findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class)))
                .thenReturn(this.mockProductDTO);
        when(this.mockRefundCalculationBasisService.getRefundCalculationBasisForTradedTickets(anyInt(), anyInt()))
                .thenReturn(StringUtil.EMPTY_STRING);
        when(this.mockRefundEngineService
                .calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), anyLong(), anyLong(),
                        any(DateTime.class), any(DateTime.class),any(RefundCalculationBasis.class))).thenReturn(this.mockRefund);
        when(this.mockCartItemCmd.getDateOfRefund()).thenReturn(new Date());
        this.cancelAndSurrenderService
                .processTradedTicket(this.mockCartItemCmd, this.mockProductDTO, this.testDateTime, this.testDateTime);

        verify(this.mockCartItemCmd, atLeastOnce()).getTradedTicket();
        verify(this.mockTradedTicketCartItemCmd).getStartDate();
        verify(this.mockTradedTicketCartItemCmd).getEndDate();
        verify(this.cancelAndSurrenderService).findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class));
        verify(this.mockRefundCalculationBasisService).getRefundCalculationBasisForTradedTickets(anyInt(), anyInt());
        verify(this.mockRefundEngineService)
                .calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), anyLong(), anyLong(),
                        any(DateTime.class),any(DateTime.class), any(RefundCalculationBasis.class));
    }

    @Test
    public void shouldProcessCancelOrSurrenderRefundForPayAsYouGoOrAdministrationFee() {
        when(this.cancelAndSurrenderService.processCancelOrSurrenderRefund(any(CartItemCmdImpl.class), any(Date.class)))
                .thenCallRealMethod();
        when(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.FALSE);
        when(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenReturn(Boolean.FALSE);
        when(this.cancelAndSurrenderService.findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class)))
                .thenReturn(this.mockProductDTO);
        when(this.cancelAndSurrenderService.isTradedTicketItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.FALSE);
        doNothing().when(this.cancelAndSurrenderService)
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));
        when(this.mockRefundEngineService.calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                any(RefundCalculationBasis.class), any(ProductDTO.class))).thenReturn(this.mockRefund);
        when(this.mockCartItemCmd.getPrice()).thenReturn(ItemTestUtil.PRICE_1);

        this.cancelAndSurrenderService.processCancelOrSurrenderRefund(this.mockCartItemCmd, DateTestUtil.getAug19());

        verify(this.cancelAndSurrenderService).isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class));
        verify(this.cancelAndSurrenderService, never())
                .isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class));
        verify(this.cancelAndSurrenderService, never())
                .findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class));
        verify(this.cancelAndSurrenderService, never())
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));
        verify(this.mockRefundEngineService, never())
                .calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                        any(RefundCalculationBasis.class), any(ProductDTO.class));
        verify(this.mockCartItemCmd).getPrice();
    }

    @Test
    public void shouldProcessCancelOrSurrenderRefundForNotPayAsYouGoAndNotAdministrationFee() {
        when(this.cancelAndSurrenderService.processCancelOrSurrenderRefund(any(CartItemCmdImpl.class), any(Date.class)))
                .thenCallRealMethod();
        when(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.TRUE);
        when(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenReturn(Boolean.TRUE);
        when(this.cancelAndSurrenderService.findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class)))
                .thenReturn(this.mockProductDTO);
        when(this.cancelAndSurrenderService.isTradedTicketItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.FALSE);
        doNothing().when(this.cancelAndSurrenderService)
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));
        when(this.mockRefundEngineService.calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                any(RefundCalculationBasis.class), any(ProductDTO.class))).thenReturn(this.mockRefund);

        this.cancelAndSurrenderService.processCancelOrSurrenderRefund(this.mockCartItemCmd, DateTestUtil.getAug19());

        verify(this.cancelAndSurrenderService).isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class));
        verify(this.cancelAndSurrenderService).isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class));
        verify(this.cancelAndSurrenderService).findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class));
        verify(this.cancelAndSurrenderService, never())
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));
        verify(this.mockRefundEngineService).calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                any(RefundCalculationBasis.class), any(ProductDTO.class));
        verify(this.mockCartItemCmd).getPrice();
    }

    @Test
    public void shouldProcessCancelOrSurrenderRefundForTradedTicket() {
        when(this.cancelAndSurrenderService.processCancelOrSurrenderRefund(any(CartItemCmdImpl.class), any(Date.class)))
                .thenCallRealMethod();
        when(this.cancelAndSurrenderService.isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.TRUE);
        when(this.cancelAndSurrenderService.isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class)))
                .thenReturn(Boolean.TRUE);
        when(this.cancelAndSurrenderService.findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class)))
                .thenReturn(this.mockProductDTO);
        when(this.cancelAndSurrenderService.isTradedTicketItem(any(CartItemCmdImpl.class))).thenReturn(Boolean.TRUE);
        doNothing().when(this.cancelAndSurrenderService)
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));
        when(this.mockRefundEngineService.calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                any(RefundCalculationBasis.class), any(ProductDTO.class))).thenReturn(this.mockRefund);

        this.cancelAndSurrenderService.processCancelOrSurrenderRefund(this.mockCartItemCmd, DateTestUtil.getAug19());

        verify(this.cancelAndSurrenderService).isNotPayAsYouGoCreditItem(any(CartItemCmdImpl.class));
        verify(this.cancelAndSurrenderService).isNotAdministrationFeeCancelAndSurrenderRefundItem(any(CartItemCmdImpl.class));
        verify(this.cancelAndSurrenderService).findProduct(anyInt(), anyInt(), any(DateTime.class), any(DateTime.class));
        verify(this.cancelAndSurrenderService)
                .processTradedTicket(any(CartItemCmdImpl.class), any(ProductDTO.class), any(DateTime.class),
                        any(DateTime.class));
        verify(this.mockRefundEngineService, never())
                .calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                        any(RefundCalculationBasis.class), any(ProductDTO.class));
        verify(this.mockCartItemCmd).getPrice();
    }

    @Test
    public void shouldProcessCancelOrSurrenderRefund() {
        when(this.cancelAndSurrenderService.processCancelOrSurrenderRefund(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.cancelAndSurrenderService.processCancelOrSurrenderRefund(any(CartItemCmdImpl.class), any(Date.class)))
                .thenReturn(this.mockCartItemCmd);
        when(this.mockCartCmd.getCartItemList()).thenReturn(this.testCartItemList);

        this.cancelAndSurrenderService.processCancelOrSurrenderRefund(this.mockCartCmd);

        verify(this.cancelAndSurrenderService).processCancelOrSurrenderRefund(any(CartItemCmdImpl.class), any(Date.class));
    }
}
