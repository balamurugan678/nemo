package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.ItemTestUtil.ZERO;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestProductItemDTO1;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestProductItemDTO2;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestProductItemDTOForBackDateAndDeceased;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestProductItemDTOForDeceasedCustomer;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.NonOddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.OddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.ProductItemRefundCalculationService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.impl.RefundCalculationBasisServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.EmailReminder;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ProductItemActionTest {

	private ProductItemActionImpl service;
	private CartItemCmdImpl cartItemCmd;
	private TravelCardService mockTravelCardService;
	private BusPassService mockBusPassService;
	private ProductItemRefundCalculationService mockProductItemRefundCalculationService;
	private ProductService mockproductService;
	private OddPeriodTravelCardService mockOddPeriodTravelCardService;
	private NonOddPeriodTravelCardService mockNonOddPeriodTravelCardService;
	private SystemParameterService mockSystemParameterService;
	private RefundCalculationBasisServiceImpl mockRefundCalculationBasisService;

	private ProductItemDTO productItemDTO;

	@Before
	public void setUp() {
		service = new ProductItemActionImpl();
		cartItemCmd = new CartItemCmdImpl();

		mockTravelCardService = mock(TravelCardService.class);
		mockBusPassService = mock(BusPassService.class);
		mockproductService = mock(ProductService.class);
		mockProductItemRefundCalculationService = mock(ProductItemRefundCalculationService.class);
		mockOddPeriodTravelCardService = mock(OddPeriodTravelCardService.class);
		mockNonOddPeriodTravelCardService = mock(NonOddPeriodTravelCardService.class);
		mockSystemParameterService = mock(SystemParameterService.class);
		mockRefundCalculationBasisService = new RefundCalculationBasisServiceImpl();

		service.busPassService = mockBusPassService;
		service.travelCardService = mockTravelCardService;
		service.productService = mockproductService;
		service.productItemRefundCalculationService = mockProductItemRefundCalculationService;
		service.nonOddPeriodTravelCardService = mockNonOddPeriodTravelCardService;
		service.oddPeriodTravelCardService = mockOddPeriodTravelCardService;
		service.systemParameterService = mockSystemParameterService;
		service.refundCalculationBasisService = mockRefundCalculationBasisService;

		this.productItemDTO = mock(ProductItemDTO.class);
	}

	@Test
	public void createItemDTOForTravelCardTicketShouldReturnProductItemAndShouldCallNonOddPeriodTravelCardService() {
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(getTestProductItemDTO1());
		cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
		cartItemCmd.setTicketType(TicketType.TRAVEL_CARD.code());
		cartItemCmd.setTravelCardType("");
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockNonOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void createItemDTOForTravelCardTicketShouldReturnProductItemAndShouldCallOddPeriodTravelCardService() {
		when(mockOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(getTestProductItemDTO1());
		cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
		cartItemCmd.setTicketType(TicketType.TRAVEL_CARD.code());
		cartItemCmd.setTravelCardType(Durations.OTHER.getDurationType());
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void createItemDTOForBusPassTicketShouldReturnProductItemAndShouldCallProductDataService() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
		cartItemCmd.setTicketType(TicketType.BUS_PASS.code());
		cartItemCmd.setTravelCardType("");

		cartItemCmd.setCardId(new Long(1234));
		cartItemCmd.setStartZone(0);
		cartItemCmd.setEndZone(0);
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockNonOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void createItemDTOForBusPassTicketShouldReturnLostRefundProductItemWithOrdinaryRefundCalculationBasis() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd = CartItemTestUtil.getAnnualBusPassCartItem();
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockNonOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);

	}

	@Test
	public void createItemDTOForBusPassTicketShouldReturnStolenRefundProductItemWithOrdinaryRefundCalculationBasis() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd = CartItemTestUtil.getAnnualBusPassCartItem();
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockNonOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void createItemDTOForBusPassTicketShouldReturnLostRefundProductItemWithProRataRefundCalculationBasis() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd = CartItemTestUtil.getAnnualBusPassCartItem();
		cartItemCmd.setStartDate(DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), -1)));
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void createItemDTOForBusPassTicketShouldReturnStolenRefundProductItemWithProRataRefundCalculationBasis() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd = CartItemTestUtil.getAnnualBusPassCartItem();
		cartItemCmd.setStartDate(DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), -1)));
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockNonOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void updateItemDTOForProductItemShouldCopyPriceFromNewItemToItemTobeUpdated() {
		ProductItemDTO dto1 = getTestProductItemDTO1();
		ProductItemDTO dto2 = getTestProductItemDTO2();
		ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
		assertEquals(dto1.getPrice(), dto2.getPrice());
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void updateItemDTOForProductItemShouldCopyRemainderDateFromNewItemToItemTobeUpdated() {
		ProductItemDTO dto1 = getTestProductItemDTO1();
		ProductItemDTO dto2 = getTestProductItemDTO2();
		dto2.setReminderDate(CartItemTestUtil.REMINDER_DATE_1);
		ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
		assertEquals(dto2.getReminderDate(), dto1.getReminderDate());
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	@Ignore
	/**
	 * The functionality for this was removed, as calc refund was being called where not required
	 */
	public void postProcessCartItemDTOShouldReturnProductItemAndShouldCallProductItemRefundCalculationService() {
		when(mockproductService.getProductName(any(ProductItemDTO.class))).thenReturn(
				getTestProductDTO1().getProductName());
		ProductItemDTO dto2 = getTestProductItemDTO2();
		ItemDTO itemDTO = service.postProcessItemDTO(dto2, true);
		verify(mockProductItemRefundCalculationService, atLeastOnce()).calculateRefund(any(ProductItemDTO.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
		assertEquals(((ProductItemDTO) itemDTO).getName(), getTestProductDTO1().getProductName());
	}

	@Test
	public void postProcessCartItemDTOShouldReturnProductItemAndShouldCallNewProductService() {
		when(mockproductService.getProductName(any(ProductItemDTO.class))).thenReturn(
				getTestProductDTO1().getProductName());
		ProductItemDTO dto2 = getTestProductItemDTO2();
		dto2.setRelatedItem(getTestProductItemDTO2());
		ItemDTO itemDTO = service.postProcessItemDTO(dto2, true);
		verify(mockproductService, atLeastOnce()).getProductName(any(ProductItemDTO.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void hasItemExpiredShouldReturnTrue() {
		when(this.mockSystemParameterService.getIntegerParameterValue(eq(USER_PRODUCT_START_AFTER_DAYS))).thenReturn(1);
		when(this.productItemDTO.getStartDate()).thenReturn(DateTestUtil.getAug19());
		assertTrue(this.service.hasItemExpired(DateTestUtil.getAug22(), this.productItemDTO));
	}

	@Test
	public void hasItemExpiredShouldReturnFalse() {
		when(this.mockSystemParameterService.getIntegerParameterValue(eq(USER_PRODUCT_START_AFTER_DAYS))).thenReturn(1);
		when(this.productItemDTO.getStartDate()).thenReturn(DateTestUtil.getAug22());
		assertFalse(this.service.hasItemExpired(DateTestUtil.getAug19(), this.productItemDTO));
	}

	@Test
	public void updateItemDTOForBackDatedAndDeceased() {
		ProductItemDTO dto1 = getTestProductItemDTO1();
		ProductItemDTO dto2 = getTestProductItemDTOForBackDateAndDeceased();
		ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
		assertEquals(dto1.getTicketBackdated(), dto2.getTicketBackdated());
		assertEquals(dto1.getBackdatedRefundReasonId(), dto2.getBackdatedRefundReasonId());
		assertEquals(dto1.getDeceasedCustomer(), dto2.getDeceasedCustomer());
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void updateItemDTOForDeceasedCustomer() {
		ProductItemDTO dto1 = getTestProductItemDTO1();
		ProductItemDTO dto2 = getTestProductItemDTOForDeceasedCustomer();
		ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
		assertFalse(dto2.getTicketBackdated());
		assertEquals(ZERO, dto2.getBackdatedRefundReasonId());
		assertTrue(dto2.getDeceasedCustomer());
		assertTrue(itemDTO instanceof ProductItemDTO);
	}

	@Test
	public void createItemDTOForTravelCardTicketShouldReturnProductItemWithNoEmailRemainder() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
		cartItemCmd.setTicketType(TicketType.TRAVEL_CARD.code());
		cartItemCmd.setTravelCardType("");
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockNonOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
		ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
		assertEquals(CartAttribute.NO_REMINDER, productItemDTO.getReminderDate());

	}

	@Test
	public void createItemDTOForTravelCardTicketShouldReturnOddPeriodProductItemWithNoEmailRemainder() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
		cartItemCmd.setTicketType(TicketType.TRAVEL_CARD.code());
		cartItemCmd.setTravelCardType(Durations.OTHER.getDurationType());
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		verify(mockOddPeriodTravelCardService, atLeastOnce()).convertCartItemCmdImplToProductItemDTO(
				any(CartItemCmdImpl.class));
		assertTrue(itemDTO instanceof ProductItemDTO);
		ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
		assertEquals(CartAttribute.NO_REMINDER, productItemDTO.getReminderDate());
	}

	@Test
	public void createItemDTOForBusPassTicketShouldReturnProductItemWithNoEmailRemainder() {
		ProductItemDTO productItemDTo = getTestProductItemDTO1();
		productItemDTo.setReminderDate(EmailReminder.NO_REMINDER.code() + CartAttribute.DAYS_BEFORE_EXPIRY);
		when(mockNonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(any(CartItemCmdImpl.class)))
				.thenReturn(productItemDTo);
		cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
		cartItemCmd.setTicketType(TicketType.BUS_PASS.code());
		cartItemCmd.setTravelCardType(CartAttribute.MONTHLY_BUS_PASS);
		cartItemCmd.setEmailReminder(EmailReminder.NO_REMINDER.code());
		cartItemCmd.setCardId(new Long(1234));
		cartItemCmd.setStartZone(0);
		cartItemCmd.setEndZone(0);
		ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
		assertTrue(itemDTO instanceof ProductItemDTO);
		ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
		assertEquals(CartAttribute.NO_REMINDER, productItemDTO.getReminderDate());
	}
}
