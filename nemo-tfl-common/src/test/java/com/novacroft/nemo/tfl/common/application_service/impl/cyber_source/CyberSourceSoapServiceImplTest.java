package com.novacroft.nemo.tfl.common.application_service.impl.cyber_source;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.*;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CyberSourceSoapServiceImplTest {
    private CyberSourceSoapServiceImpl service;
    private PaymentCardDataService mockPaymentCardDataService;
    private PaymentCardSettlementDataService mockPaymentCardSettlementDataService;
    private AddressDataService mockAddressDataService;
    private CustomerDataService mockCustomerDataService;

    private OrderDTO mockOrderDTO;
    private PaymentCardSettlementDTO mockPaymentCardSettlementDTO;
    private PaymentCardDTO mockPaymentCardDTO;
    private AddressDTO mockAddressDTO;
    private CustomerDTO mockCustomerDTO;

    @Before
    public void setUp() {
        this.service = mock(CyberSourceSoapServiceImpl.class);

        this.mockPaymentCardDataService = mock(PaymentCardDataService.class);
        this.service.paymentCardDataService = this.mockPaymentCardDataService;

        this.mockPaymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        this.service.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;

        this.mockAddressDataService = mock(AddressDataService.class);
        this.service.addressDataService = this.mockAddressDataService;

        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.service.customerDataService = this.mockCustomerDataService;

        this.mockOrderDTO = mock(OrderDTO.class);
        this.mockPaymentCardSettlementDTO = mock(PaymentCardSettlementDTO.class);
        this.mockPaymentCardDTO = mock(PaymentCardDTO.class);
        this.mockAddressDTO = mock(AddressDTO.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
    }

    @Test
    public void shouldPreparePaymentRequestData() {
        when(this.service.preparePaymentRequestData(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyString()))
                .thenCallRealMethod();
        when(this.mockPaymentCardSettlementDataService.findById(anyLong())).thenReturn(this.mockPaymentCardSettlementDTO);
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);
        when(this.mockAddressDataService.findById(anyLong())).thenReturn(this.mockAddressDTO);
        when(this.mockCustomerDataService.findById(anyLong())).thenReturn(this.mockCustomerDTO);
        when(this.mockCustomerDataService.findById(mockOrderDTO.getCustomerId())).thenReturn(this.mockCustomerDTO);

        when(this.mockPaymentCardSettlementDTO.getId()).thenReturn(SettlementTestUtil.PAYMENT_CARD_ID_1);
        when(this.mockPaymentCardSettlementDTO.getPaymentCardId()).thenReturn(PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1);
        when(this.mockPaymentCardSettlementDTO.getTransactionUuid()).thenReturn(SettlementTestUtil.TRANSACTION_UUID_1);
        when(this.mockPaymentCardSettlementDTO.getAmount()).thenReturn(OrderTestUtil.TOTAL_AMOUNT);

        when(this.mockOrderDTO.getOrderNumber()).thenReturn(OrderTestUtil.ORDER_NUMBER);

        when(this.mockPaymentCardDTO.getToken()).thenReturn(StringUtil.EMPTY_STRING);
        when(this.mockPaymentCardDTO.getFirstName()).thenReturn(StringUtil.EMPTY_STRING);
        when(this.mockPaymentCardDTO.getLastName()).thenReturn(StringUtil.EMPTY_STRING);

        when(this.mockCustomerDTO.getEmailAddress()).thenReturn(CustomerTestUtil.EMAIL_ADDRESS_1);
        when(this.mockCustomerDTO.getId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);

        when(this.mockAddressDTO.getCountry()).thenReturn(CountryTestUtil.getTestCountryDTO1());
        when(this.mockAddressDTO.getPostcode()).thenReturn(AddressTestUtil.POSTCODE_1);
        when(this.mockAddressDTO.getTown()).thenReturn(AddressTestUtil.TOWN_1);
        when(this.mockAddressDTO.getHouseNameNumber()).thenReturn(AddressTestUtil.HOUSE_NAME_NUMBER_1);
        when(this.mockAddressDTO.getStreet()).thenReturn(AddressTestUtil.STREET_1);

        this.service.preparePaymentRequestData(this.mockOrderDTO, this.mockPaymentCardSettlementDTO, StringUtil.EMPTY_STRING);

        verify(this.mockPaymentCardSettlementDataService).findById(anyLong());
        verify(this.mockPaymentCardDataService).findById(anyLong());
        verify(this.mockAddressDataService).findById(anyLong());
        verify(this.mockCustomerDataService).findById(mockOrderDTO.getCustomerId());
    }
}