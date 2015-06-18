package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.ADDRESS_ID_1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardList1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.PaymentCardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * PaymentCardService unit tests
 */
public class PaymentCardServiceImplTest {
    private PaymentCardServiceImpl service;
    private AutoLoadChangeSettlementDTO mockSettlementDTO;
    private AutoLoadChangeSettlementDataService mockAutoLoadChangeSettlementDataService;
    private CardDTO mockCardDTO;
    private CartDTO mockCartDTO;
    private GetCardService mockGetCardService;
    private CardInfoResponseV2DTO mockCardInfoResponseV2DTO;
    private CartCmdImpl mockCartCmd;
    private CustomerDataService mockCustomerDataService;
    private CustomerDTO mockCustomerDTO;
    private PaymentCardDTO mockPaymentCardDTO;
    private PaymentCardDataService mockPaymentCardDataService;
    private CyberSourcePostReplyDTO mockCyberSourcePostReplyDTO;
    private CardDataService mockCardDataService;
    private ManagePaymentCardCmdImpl mockManagePaymentCardCmd;
    private List<PaymentCardCmdImpl> paymentCardCmdList;
    private PaymentCardCmdImpl mockPaymentCardCmd;
    private List<PaymentCardDTO> paymentCardList;
    private List<AutoLoadChangeSettlementDTO> autoLoadChangeSettlementList;
    private OrderDTO mockOrderDTO;
    private AddressDataService mockAddressDataService;
    private AddressDTO mockAddressDTO;
    private CyberSourceSoapRequestDTO mockCyberSourceSoapRequestDTO;
    private CyberSourceSoapReplyDTO mockCyberSourceSoapReplyDTO;
    private CyberSourceTransactionDataService mockCyberSourceTransactionDataService;
    private List<PaymentCardSettlementDTO> paymentCardSettlementList;
    private PaymentCardSettlementDTO mockPaymentCardSettlementDTO;
    private PaymentCardSettlementDataService mockPaymentCardSettlementDataService;
    private CountryDataService mockCountryDataService;
    private CountryDTO mockCountryDTO;

    @Before
    public void setUp() {
        this.service = mock(PaymentCardServiceImpl.class);
        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.service.customerDataService = this.mockCustomerDataService;
        this.mockPaymentCardDataService = mock(PaymentCardDataService.class);
        this.service.paymentCardDataService = this.mockPaymentCardDataService;
        this.mockCardDataService = mock(CardDataService.class);
        this.service.cardDataService = this.mockCardDataService;
        this.mockGetCardService = mock(GetCardService.class);
        this.service.getCardService = this.mockGetCardService;
        this.mockAutoLoadChangeSettlementDataService = mock(AutoLoadChangeSettlementDataService.class);
        this.service.autoLoadChangeSettlementDataService = this.mockAutoLoadChangeSettlementDataService;
        this.mockAddressDataService = mock(AddressDataService.class);
        this.service.addressDataService = mockAddressDataService;
        this.mockCyberSourceTransactionDataService = mock(CyberSourceTransactionDataService.class);
        this.service.cyberSourceTransactionDataService = this.mockCyberSourceTransactionDataService;
        this.mockPaymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        this.service.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;
        mockCountryDataService = mock(CountryDataService.class);
        service.countryDataService = mockCountryDataService;

        this.mockSettlementDTO = mock(AutoLoadChangeSettlementDTO.class);
        this.mockCardDTO = mock(CardDTO.class);
        this.mockCartDTO = mock(CartDTO.class);
        this.mockCardInfoResponseV2DTO = mock(CardInfoResponseV2DTO.class);
        this.mockCartCmd = mock(CartCmdImpl.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
        this.mockPaymentCardDTO = mock(PaymentCardDTO.class);
        this.mockCyberSourcePostReplyDTO = mock(CyberSourcePostReplyDTO.class);
        this.mockManagePaymentCardCmd = mock(ManagePaymentCardCmdImpl.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        this.mockAddressDTO = mock(AddressDTO.class);
        this.mockCyberSourceSoapRequestDTO = mock(CyberSourceSoapRequestDTO.class);
        this.mockCyberSourceSoapReplyDTO = mock(CyberSourceSoapReplyDTO.class);
        this.mockPaymentCardSettlementDTO = mock(PaymentCardSettlementDTO.class);
        mockCountryDTO = mock(CountryDTO.class);

        this.mockPaymentCardCmd = mock(PaymentCardCmdImpl.class);
        this.paymentCardCmdList = new ArrayList<PaymentCardCmdImpl>();
        this.paymentCardCmdList.add(this.mockPaymentCardCmd);

        this.paymentCardList = new ArrayList<PaymentCardDTO>();
        this.paymentCardList.add(this.mockPaymentCardDTO);

        this.autoLoadChangeSettlementList = new ArrayList<AutoLoadChangeSettlementDTO>();
        this.autoLoadChangeSettlementList.add(this.mockSettlementDTO);

        this.paymentCardSettlementList = new ArrayList<PaymentCardSettlementDTO>();
        this.paymentCardSettlementList.add(this.mockPaymentCardSettlementDTO);
        
        when(this.mockCartCmd.getCartDTO()).thenReturn(this.mockCartDTO);
    }

    @Test
    public void isAutoLoadActiveShouldReturnShouldReturnFalseWithNull() {
        when(this.service.isAutoLoadOn(anyInt())).thenCallRealMethod();
        assertFalse(this.service.isAutoLoadOn((Integer) null));
    }

    @Test
    public void isAutoLoadActiveShouldReturnShouldReturnFalseWithNoTopUp() {
        when(this.service.isAutoLoadOn(anyInt())).thenCallRealMethod();
        assertFalse(this.service.isAutoLoadOn(AutoLoadState.NO_TOP_UP.state()));
    }

    @Test
    public void isAutoLoadActiveShouldReturnShouldReturnTrue() {
        when(this.service.isAutoLoadOn(anyInt())).thenCallRealMethod();
        assertTrue(this.service.isAutoLoadOn(AutoLoadState.TOP_UP_AMOUNT_2.state()));
    }

    @Test
    public void isAutoLoadRequestedForSettlementShouldReturnFalseWithNull() {
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenCallRealMethod();
        assertFalse(this.service.isAutoLoadRequested((AutoLoadChangeSettlementDTO) null));
    }

    @Test
    public void isAutoLoadRequestedForSettlementShouldReturnFalseWithNotRequestedStatus() {
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockSettlementDTO.getStatus()).thenReturn(SettlementStatus.COMPLETE.code());

        assertFalse(this.service.isAutoLoadRequested(this.mockSettlementDTO));
    }

    @Test
    public void isAutoLoadRequestedForSettlementShouldReturnFalseWithNoTopUp() {
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockSettlementDTO.getStatus()).thenReturn(SettlementStatus.REQUESTED.code());
        when(this.mockSettlementDTO.getAutoLoadState()).thenReturn(AutoLoadState.NO_TOP_UP.state());

        assertFalse(this.service.isAutoLoadRequested(this.mockSettlementDTO));
    }

    @Test
    public void isAutoLoadRequestedForSettlementShouldReturnTrue() {
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockSettlementDTO.getStatus()).thenReturn(SettlementStatus.REQUESTED.code());
        when(this.mockSettlementDTO.getAutoLoadState()).thenReturn(AutoLoadState.TOP_UP_AMOUNT_2.state());

        assertTrue(this.service.isAutoLoadRequested(this.mockSettlementDTO));
    }

    @Test
    public void isAutoLoadRequestedForCardShouldReturnTrue() {
        when(this.service.isAutoLoadRequested(any(CardDTO.class))).thenCallRealMethod();
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenReturn(true);
        when(this.mockAutoLoadChangeSettlementDataService.findLatestByCardId(anyLong())).thenReturn(this.mockSettlementDTO);

        assertTrue(this.service.isAutoLoadRequested(this.mockCardDTO));

        verify(this.service).isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class));
        verify(this.mockAutoLoadChangeSettlementDataService).findLatestByCardId(anyLong());
    }

    @Test
    public void isAutoLoadRequestedForCardShouldReturnFalse() {
        when(this.service.isAutoLoadRequested(any(CardDTO.class))).thenCallRealMethod();
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenReturn(false);
        when(this.mockAutoLoadChangeSettlementDataService.findLatestByCardId(anyLong())).thenReturn(this.mockSettlementDTO);

        assertFalse(this.service.isAutoLoadRequested(this.mockCardDTO));

        verify(this.service).isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class));
        verify(this.mockAutoLoadChangeSettlementDataService).findLatestByCardId(anyLong());
    }

    @Test
    public void isAutoLoadRequestedForCardListShouldReturnTrue() {
        when(this.service.isAutoLoadRequested(anyList())).thenCallRealMethod();
        when(this.service.isAutoLoadRequested(any(CardDTO.class))).thenReturn(true);

        assertTrue(this.service.isAutoLoadRequested(getTestCardList1()));
    }

    @Test
    public void isAutoLoadRequestedForCardListShouldReturnFalse() {
        when(this.service.isAutoLoadRequested(anyList())).thenCallRealMethod();
        when(this.service.isAutoLoadRequested(any(CardDTO.class))).thenReturn(false);

        assertFalse(this.service.isAutoLoadRequested(getTestCardList1()));
    }

    @Test
    public void isAutoLoadOnInCubicShouldReturnTrue() {
        when(this.service.isAutoLoadOnInCubic(anyList())).thenCallRealMethod();
        when(this.mockGetCardService.getCard(anyString())).thenReturn(this.mockCardInfoResponseV2DTO);
        when(this.service.isAutoLoadOn(anyInt())).thenReturn(true);

        assertTrue(this.service.isAutoLoadOnInCubic(getTestCardList1()));

        verify(this.mockGetCardService).getCard(anyString());
    }

    @Test
    public void isAutoLoadOnInCubicShouldReturnFalse() {
        when(this.service.isAutoLoadOnInCubic(anyList())).thenCallRealMethod();
        when(this.mockGetCardService.getCard(anyString())).thenReturn(this.mockCardInfoResponseV2DTO);
        when(this.service.isAutoLoadOn(anyInt())).thenReturn(false);

        assertFalse(this.service.isAutoLoadOnInCubic(getTestCardList1()));

        verify(this.mockGetCardService).getCard(anyString());
    }

    @Test
    public void isPaymentCardLinkedToCardShouldReturnTrue() {
        when(this.service.isPaymentCardLinkedToCard(anyList())).thenCallRealMethod();
        assertTrue(this.service.isPaymentCardLinkedToCard(getTestCardList1()));
    }

    @Test
    public void isPaymentCardLinkedToCardShouldReturnFalse() {
        when(this.service.isPaymentCardLinkedToCard(anyList())).thenCallRealMethod();
        assertFalse(this.service.isPaymentCardLinkedToCard(Collections.EMPTY_LIST));
    }

    @Test
    public void shouldCreateToken() {
        when(this.service.createTokenisedPaymentCard(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.service.createAddressForPaymentCard(any(CyberSourcePostReplyDTO.class))).thenReturn(this.mockAddressDTO);
        when(this.mockCustomerDataService.findById(new Long(2))).thenReturn(this.mockCustomerDTO);
        when(this.mockPaymentCardDataService.createOrUpdate(any(PaymentCardDTO.class))).thenReturn(this.mockPaymentCardDTO);
        when(this.mockCartCmd.getCustomerId()).thenReturn(CUSTOMER_ID_1);
        when(this.mockCartDTO.getCyberSourceReply()).thenReturn(this.mockCyberSourcePostReplyDTO);
        when(this.mockCustomerDTO.getId()).thenReturn(CUSTOMER_ID_1);

        this.service.createTokenisedPaymentCard(this.mockCartCmd);

        verify(this.mockPaymentCardDataService).createOrUpdate(any(PaymentCardDTO.class));
        verify(this.service).createAddressForPaymentCard(any(CyberSourcePostReplyDTO.class));
    
    }

    @Test
    public void isCreateTokenShouldReturnTrue() {
        when(this.service.isCreateToken(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartCmd.getCartDTO()).thenReturn(this.mockCartDTO);
        when(this.mockCartDTO.getCyberSourceReply()).thenReturn(this.mockCyberSourcePostReplyDTO);
        when(this.mockCyberSourcePostReplyDTO.getRequestTransactionType())
                .thenReturn(CyberSourcePostTransactionType.CREATE_PAYMENT_TOKEN.code());

        assertTrue(this.service.isCreateToken(this.mockCartCmd));
    }

    @Test
    public void isCreateTokenShouldReturnFalse() {
        when(this.service.isCreateToken(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartDTO.getCyberSourceReply()).thenReturn(this.mockCyberSourcePostReplyDTO);
        when(this.mockCyberSourcePostReplyDTO.getRequestTransactionType())
                .thenReturn(CyberSourcePostTransactionType.SALE.code());

        assertFalse(this.service.isCreateToken(this.mockCartCmd));
    }

    @Test
    public void isCardInUseShouldReturnFalseForNotLinked() {
        when(this.service.isCardInUse(anyLong())).thenCallRealMethod();
        when(this.mockCardDataService.findByPaymentCardId(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(this.service.isPaymentCardLinkedToCard(anyList())).thenReturn(false);
        when(this.service.isAutoLoadOnInCubic(anyList())).thenReturn(false);
        when(this.service.isAutoLoadRequested(anyList())).thenReturn(false);

        assertFalse(this.service.isCardInUse(TEST_PAYMENT_CARD_ID_1));

        verify(this.mockCardDataService).findByPaymentCardId(anyLong());
    }

    @Test
    public void isCardInUseShouldReturnFalseForAutoLoadNotOnOrRequested() {
        when(this.service.isCardInUse(anyLong())).thenCallRealMethod();
        when(this.mockCardDataService.findByPaymentCardId(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(this.service.isPaymentCardLinkedToCard(anyList())).thenReturn(true);
        when(this.service.isAutoLoadOnInCubic(anyList())).thenReturn(false);
        when(this.service.isAutoLoadRequested(anyList())).thenReturn(false);

        assertFalse(this.service.isCardInUse(TEST_PAYMENT_CARD_ID_1));

        verify(this.mockCardDataService).findByPaymentCardId(anyLong());
    }

    @Test
    public void isCardInUseShouldReturnTrueForAutoLoadOn() {
        when(this.service.isCardInUse(anyLong())).thenCallRealMethod();
        when(this.mockCardDataService.findByPaymentCardId(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(this.service.isPaymentCardLinkedToCard(anyList())).thenReturn(true);
        when(this.service.isAutoLoadOnInCubic(anyList())).thenReturn(true);
        when(this.service.isAutoLoadRequested(anyList())).thenReturn(false);

        assertTrue(this.service.isCardInUse(TEST_PAYMENT_CARD_ID_1));

        verify(this.mockCardDataService).findByPaymentCardId(anyLong());
    }

    @Test
    public void isCardInUseShouldReturnTrueForAutoLoadRequested() {
        when(this.service.isCardInUse(anyLong())).thenCallRealMethod();
        when(this.mockCardDataService.findByPaymentCardId(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(this.service.isPaymentCardLinkedToCard(anyList())).thenReturn(true);
        when(this.service.isAutoLoadOnInCubic(anyList())).thenReturn(false);
        when(this.service.isAutoLoadRequested(anyList())).thenReturn(true);

        assertTrue(this.service.isCardInUse(TEST_PAYMENT_CARD_ID_1));

        verify(this.mockCardDataService).findByPaymentCardId(anyLong());
    }

    @Test
    public void updatePaymentCardsShouldDelete() {
        when(this.service.updatePaymentCards(anyLong(), any(ManagePaymentCardCmdImpl.class))).thenCallRealMethod();
        when(this.service.getPaymentCards(anyLong())).thenReturn(this.mockManagePaymentCardCmd);
        doCallRealMethod().when(this.service).updatePaymentCard(any(PaymentCardCmdImpl.class));
        when(this.mockManagePaymentCardCmd.getPaymentCards()).thenReturn(this.paymentCardCmdList);
        when(this.mockPaymentCardCmd.getPaymentCardDTO()).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getAddressDTO()).thenReturn(this.mockAddressDTO);
        doNothing().when(this.mockPaymentCardDataService).delete(any(PaymentCardDTO.class));
        when(this.mockPaymentCardDataService.createOrUpdate(any(PaymentCardDTO.class))).thenReturn(null);

        this.service.updatePaymentCards(CUSTOMER_ID_1, this.mockManagePaymentCardCmd);

        verify(this.service).getPaymentCards(anyLong());
        verify(this.mockPaymentCardDataService, never()).delete(any(PaymentCardDTO.class));
        verify(this.mockPaymentCardDataService).createOrUpdate(any(PaymentCardDTO.class));
    }

    @Test
    public void updatePaymentCardsShouldUpdate() {
        when(this.service.updatePaymentCards(anyLong(), any(ManagePaymentCardCmdImpl.class))).thenCallRealMethod();
        when(this.service.getPaymentCards(anyLong())).thenReturn(this.mockManagePaymentCardCmd);
        doCallRealMethod().when(this.service).updatePaymentCard(any(PaymentCardCmdImpl.class));
        when(this.mockManagePaymentCardCmd.getPaymentCards()).thenReturn(this.paymentCardCmdList);
        when(this.mockPaymentCardCmd.getPaymentCardDTO()).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getAddressDTO()).thenReturn(this.mockAddressDTO);
        doNothing().when(this.mockPaymentCardDataService).delete(any(PaymentCardDTO.class));
        when(this.mockPaymentCardDataService.createOrUpdate(any(PaymentCardDTO.class))).thenReturn(null);

        this.service.updatePaymentCards(CUSTOMER_ID_1, this.mockManagePaymentCardCmd);

        verify(this.service).getPaymentCards(anyLong());
        verify(this.mockPaymentCardDataService, never()).delete(any(PaymentCardDTO.class));
        verify(this.mockPaymentCardDataService).createOrUpdate(any(PaymentCardDTO.class));
    }

    @Test
    public void shouldGetPaymentCards() {
        when(this.service.getPaymentCards(anyLong())).thenCallRealMethod();
        when(this.mockPaymentCardDataService.findByCustomerId(anyLong())).thenReturn(this.paymentCardList);
        when(this.service.isCardInUse(anyLong())).thenReturn(Boolean.TRUE);

        this.service.getPaymentCards(CUSTOMER_ID_1);

        verify(this.mockPaymentCardDataService).findByCustomerId(anyLong());
        verify(this.service).isCardInUse(anyLong());
    }

    @Test
    public void shouldGetPaymentCardWithNullAddressId() {
        when(this.service.getPaymentCard(anyLong())).thenCallRealMethod();
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getAddressId()).thenReturn(null);

        this.service.getPaymentCard(CUSTOMER_ID_1);

        verify(this.mockPaymentCardDataService, atLeastOnce()).findById(anyLong());
        verify(this.mockAddressDataService, never()).findById(anyLong());
    }

    @Test
    public void shouldGetPaymentCardWithAddressId() {
        when(this.service.getPaymentCard(anyLong())).thenCallRealMethod();
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getAddressId()).thenReturn(ADDRESS_ID_1);
        when(this.mockAddressDataService.findById(anyLong())).thenReturn(this.mockAddressDTO);

        this.service.getPaymentCard(CUSTOMER_ID_1);

        verify(this.mockPaymentCardDataService, atLeastOnce()).findById(anyLong());
        verify(this.mockAddressDataService, atLeastOnce()).findById(anyLong());
    }

    @Test
    public void handlePaymentCardTokenShouldCreateToken() {
        doCallRealMethod().when(this.service).createPaymentCardOnTokenRequest(any(CartCmdImpl.class));
        when(this.service.isCreateToken(any(CartCmdImpl.class))).thenReturn(Boolean.TRUE);
        when(this.service.createTokenisedPaymentCard(any(CartCmdImpl.class))).thenReturn(this.mockPaymentCardDTO);

        this.service.createPaymentCardOnTokenRequest(this.mockCartCmd);

        verify(this.service).isCreateToken(any(CartCmdImpl.class));
        verify(this.service).createTokenisedPaymentCard(any(CartCmdImpl.class));
    }

    @Test
    public void handlePaymentCardTokenShouldNotCreateToken() {
        doCallRealMethod().when(this.service).createPaymentCardOnTokenRequest(any(CartCmdImpl.class));
        when(this.service.isCreateToken(any(CartCmdImpl.class))).thenReturn(Boolean.FALSE);
        when(this.service.createTokenisedPaymentCard(any(CartCmdImpl.class))).thenReturn(this.mockPaymentCardDTO);

        this.service.createPaymentCardOnTokenRequest(this.mockCartCmd);

        verify(this.service).isCreateToken(any(CartCmdImpl.class));
        verify(this.service, never()).createTokenisedPaymentCard(any(CartCmdImpl.class));
    }

    @Test
    public void isAutoLoadOnShouldReturnTrue() {
        when(this.service.isAutoLoadOn(any(AutoLoadChangeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockSettlementDTO.getAutoLoadState()).thenReturn(AutoLoadState.TOP_UP_AMOUNT_2.state());

        assertTrue(this.service.isAutoLoadOn(this.mockSettlementDTO));

        verify(this.mockSettlementDTO).getAutoLoadState();
    }

    @Test
    public void isAutoLoadOnShouldReturnFalse() {
        when(this.service.isAutoLoadOn(any(AutoLoadChangeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockSettlementDTO.getAutoLoadState()).thenReturn(AutoLoadState.NO_TOP_UP.state());

        assertFalse(this.service.isAutoLoadOn(this.mockSettlementDTO));

        verify(this.mockSettlementDTO).getAutoLoadState();
    }

    @Test
    public void shouldLinkPaymentCardToCardForSettlement() {
        doCallRealMethod().when(this.service)
                .linkPaymentCardToCard(any(AutoLoadChangeSettlementDTO.class), any(PaymentCardDTO.class));
        when(this.service.isAutoLoadOn(any(AutoLoadChangeSettlementDTO.class))).thenReturn(true);
        when(this.mockCardDataService.findById(anyLong())).thenReturn(this.mockCardDTO);
        when(this.mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(this.mockCardDTO);
        when(this.mockSettlementDTO.getCardId()).thenReturn(CARD_ID_1);

        this.service.linkPaymentCardToCard(this.mockSettlementDTO, this.mockPaymentCardDTO);

        verify(this.service).isAutoLoadOn(any(AutoLoadChangeSettlementDTO.class));
        verify(this.mockCardDataService).findById(anyLong());
        verify(this.mockCardDataService).createOrUpdate(any(CardDTO.class));
    }

    @Test
    public void shouldLinkPaymentCardToCardForOrder() {
        doCallRealMethod().when(this.service).linkPaymentCardToCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        when(this.mockAutoLoadChangeSettlementDataService.findByOrderId(anyLong()))
                .thenReturn(this.autoLoadChangeSettlementList);
        doNothing().when(this.service).linkPaymentCardToCard(any(AutoLoadChangeSettlementDTO.class), any(PaymentCardDTO.class));

        this.service.linkPaymentCardToCard(this.mockOrderDTO, this.mockPaymentCardDTO);

        verify(this.mockAutoLoadChangeSettlementDataService).findByOrderId(anyLong());
        verify(this.service).linkPaymentCardToCard(any(AutoLoadChangeSettlementDTO.class), any(PaymentCardDTO.class));
    }

    @Test
    public void shouldCreateAddressForPaymentCard() {
        when(this.service.createAddressForPaymentCard(any(CyberSourcePostReplyDTO.class))).thenCallRealMethod();
        when(this.mockAddressDataService.createOrUpdate(any(AddressDTO.class))).thenReturn(this.mockAddressDTO);
        when(mockCountryDataService.findCountryByCode(anyString())).thenReturn(mockCountryDTO);

        this.service.createAddressForPaymentCard(this.mockCyberSourcePostReplyDTO);

        verify(this.mockAddressDataService).createOrUpdate(any(AddressDTO.class));
        verify(mockCountryDataService).findCountryByCode(anyString());
    }

    @Test
    public void isSuccessReplyShouldReturnTrue() {
        when(this.service.isSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenCallRealMethod();
        when(this.mockCyberSourceSoapReplyDTO.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());
        assertTrue(this.service.isSuccessReply(this.mockCyberSourceSoapReplyDTO));
    }

    @Test
    public void isSuccessReplyShouldReturnFalse() {
        when(this.service.isSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenCallRealMethod();
        when(this.mockCyberSourceSoapReplyDTO.getDecision()).thenReturn(CyberSourceDecision.REJECT.code());
        assertFalse(this.service.isSuccessReply(this.mockCyberSourceSoapReplyDTO));
    }

    @Test
    public void isNotSuccessReplyShouldReturnTrue() {
        when(this.service.isNotSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenCallRealMethod();
        when(this.service.isSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenReturn(Boolean.FALSE);
        assertTrue(this.service.isNotSuccessReply(this.mockCyberSourceSoapReplyDTO));
    }

    @Test
    public void isNotSuccessReplyShouldReturnFalse() {
        when(this.service.isNotSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenCallRealMethod();
        when(this.service.isSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.service.isNotSuccessReply(this.mockCyberSourceSoapReplyDTO));
    }

    @Test
    public void shouldPrepareDeleteTokenRequest() {
        when(this.service.prepareDeleteTokenRequest(any(PaymentCardCmdImpl.class))).thenCallRealMethod();
        when(this.mockPaymentCardCmd.getPaymentCardDTO()).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getToken()).thenReturn(StringUtil.EMPTY_STRING);
        when(this.mockPaymentCardDTO.getCustomerId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);
        when(this.mockPaymentCardDTO.getReferenceCode()).thenReturn(StringUtil.EMPTY_STRING);

        this.service.prepareDeleteTokenRequest(this.mockPaymentCardCmd);

        verify(this.mockPaymentCardDTO).getToken();
        verify(this.mockPaymentCardDTO).getCustomerId();
        verify(this.mockPaymentCardDTO).getReferenceCode();
    }

    @Test
    public void shouldDeleteCyberSourceTokenWithSuccessfulReply() {
        doCallRealMethod().when(this.service).deleteCyberSourceToken(any(PaymentCardCmdImpl.class));
        when(this.service.prepareDeleteTokenRequest(any(PaymentCardCmdImpl.class)))
                .thenReturn(this.mockCyberSourceSoapRequestDTO);
        when(this.mockCyberSourceTransactionDataService.deleteToken(any(CyberSourceSoapRequestDTO.class)))
                .thenReturn(this.mockCyberSourceSoapReplyDTO);
        when(this.service.isNotSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenReturn(Boolean.FALSE);
        doNothing().when(this.service)
                .logNotSuccessReply(any(CyberSourceSoapRequestDTO.class), any(CyberSourceSoapReplyDTO.class));

        this.service.deleteCyberSourceToken(this.mockPaymentCardCmd);

        verify(this.service).prepareDeleteTokenRequest(any(PaymentCardCmdImpl.class));
        verify(this.mockCyberSourceTransactionDataService).deleteToken(any(CyberSourceSoapRequestDTO.class));
        verify(this.service).isNotSuccessReply(any(CyberSourceSoapReplyDTO.class));
        verify(this.service, never())
                .logNotSuccessReply(any(CyberSourceSoapRequestDTO.class), any(CyberSourceSoapReplyDTO.class));
    }

    @Test
    public void shouldDeleteCyberSourceTokenWithUnsuccessfulReply() {
        doCallRealMethod().when(this.service).deleteCyberSourceToken(any(PaymentCardCmdImpl.class));
        when(this.service.prepareDeleteTokenRequest(any(PaymentCardCmdImpl.class)))
                .thenReturn(this.mockCyberSourceSoapRequestDTO);
        when(this.mockCyberSourceTransactionDataService.deleteToken(any(CyberSourceSoapRequestDTO.class)))
                .thenReturn(this.mockCyberSourceSoapReplyDTO);
        when(this.service.isNotSuccessReply(any(CyberSourceSoapReplyDTO.class))).thenReturn(Boolean.TRUE);
        doNothing().when(this.service)
                .logNotSuccessReply(any(CyberSourceSoapRequestDTO.class), any(CyberSourceSoapReplyDTO.class));

        this.service.deleteCyberSourceToken(this.mockPaymentCardCmd);

        verify(this.service).prepareDeleteTokenRequest(any(PaymentCardCmdImpl.class));
        verify(this.mockCyberSourceTransactionDataService).deleteToken(any(CyberSourceSoapRequestDTO.class));
        verify(this.service).isNotSuccessReply(any(CyberSourceSoapReplyDTO.class));
        verify(this.service).logNotSuccessReply(any(CyberSourceSoapRequestDTO.class), any(CyberSourceSoapReplyDTO.class));
    }

    @Test
    public void shouldDeletePaymentCard() {
        doCallRealMethod().when(this.service).deletePaymentCard(any(PaymentCardCmdImpl.class));
        doNothing().when(this.mockPaymentCardDataService).delete(any(PaymentCardDTO.class));
        doNothing().when(this.service).deleteCyberSourceToken(any(PaymentCardCmdImpl.class));
        when(this.mockPaymentCardCmd.getPaymentCardDTO()).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getAddressDTO()).thenReturn(this.mockAddressDTO);

        this.service.deletePaymentCard(this.mockPaymentCardCmd);

        verify(this.mockPaymentCardDataService).delete(any(PaymentCardDTO.class));
        verify(this.service).deleteCyberSourceToken(any(PaymentCardCmdImpl.class));
    }

    @Test
    public void isAutoLoadRequestedShouldReturnTrue() {
        when(this.service.isAutoLoadRequested(any(OrderDTO.class))).thenCallRealMethod();
        when(this.mockAutoLoadChangeSettlementDataService.findByOrderId(anyLong()))
                .thenReturn(this.autoLoadChangeSettlementList);
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenReturn(Boolean.TRUE);

        assertTrue(this.service.isAutoLoadRequested(this.mockOrderDTO));

        verify(this.mockAutoLoadChangeSettlementDataService).findByOrderId(anyLong());
        verify(this.service).isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class));
    }

    @Test
    public void isAutoLoadRequestedShouldReturnFalse() {
        when(this.service.isAutoLoadRequested(any(OrderDTO.class))).thenCallRealMethod();
        when(this.mockAutoLoadChangeSettlementDataService.findByOrderId(anyLong()))
                .thenReturn(this.autoLoadChangeSettlementList);
        when(this.service.isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class))).thenReturn(Boolean.FALSE);

        assertFalse(this.service.isAutoLoadRequested(this.mockOrderDTO));

        verify(this.mockAutoLoadChangeSettlementDataService).findByOrderId(anyLong());
        verify(this.service).isAutoLoadRequested(any(AutoLoadChangeSettlementDTO.class));
    }

    @Test
    public void linkPaymentCardToCardOnAutoLoadOrderShouldLink() {
        doCallRealMethod().when(this.service).linkPaymentCardToCardOnAutoLoadOrder(any(OrderDTO.class));
        when(this.service.isAutoLoadRequested(any(OrderDTO.class))).thenReturn(Boolean.TRUE);
        when(this.mockPaymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(this.paymentCardSettlementList);
        doNothing().when(this.service).linkPaymentCardToCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);

        this.service.linkPaymentCardToCardOnAutoLoadOrder(this.mockOrderDTO);

        verify(this.service).isAutoLoadRequested(any(OrderDTO.class));
        verify(this.mockPaymentCardSettlementDataService).findByOrderId(anyLong());
        verify(this.service).linkPaymentCardToCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        verify(this.mockPaymentCardDataService).findById(anyLong());
    }

    @Test
    public void linkPaymentCardToCardOnAutoLoadOrderShouldNotLink() {
        doCallRealMethod().when(this.service).linkPaymentCardToCardOnAutoLoadOrder(any(OrderDTO.class));
        when(this.service.isAutoLoadRequested(any(OrderDTO.class))).thenReturn(Boolean.FALSE);
        when(this.mockPaymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(this.paymentCardSettlementList);
        doNothing().when(this.service).linkPaymentCardToCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);

        this.service.linkPaymentCardToCardOnAutoLoadOrder(this.mockOrderDTO);

        verify(this.service).isAutoLoadRequested(any(OrderDTO.class));
        verify(this.mockPaymentCardSettlementDataService, never()).findByOrderId(anyLong());
        verify(this.service, never()).linkPaymentCardToCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        verify(this.mockPaymentCardDataService, never()).findById(anyLong());
    }

    @Test
    public void shouldUpdateSettlementWithPaymentCard() {
        doCallRealMethod().when(this.service).updateSettlementWithPaymentCard(any(OrderDTO.class), anyLong());
        doNothing().when(this.service).updateSettlementWithPaymentCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);

        this.service.updateSettlementWithPaymentCard(this.mockOrderDTO, PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1);

        verify(this.service).updateSettlementWithPaymentCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        verify(this.mockPaymentCardDataService).findById(anyLong());
    }

    @Test
    public void shouldUpdateSettlementWithPaymentCardWithOrderAndPaymentCardArguments() {
        doCallRealMethod().when(this.service).updateSettlementWithPaymentCard(any(OrderDTO.class), any(PaymentCardDTO.class));
        when(this.mockPaymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(this.paymentCardSettlementList);
        when(this.mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class)))
                .thenReturn(this.mockPaymentCardSettlementDTO);

        this.service.updateSettlementWithPaymentCard(this.mockOrderDTO, this.mockPaymentCardDTO);

        verify(this.mockPaymentCardSettlementDataService).findByOrderId(anyLong());
        verify(this.mockPaymentCardSettlementDataService).createOrUpdate(any(PaymentCardSettlementDTO.class));
    }
}
