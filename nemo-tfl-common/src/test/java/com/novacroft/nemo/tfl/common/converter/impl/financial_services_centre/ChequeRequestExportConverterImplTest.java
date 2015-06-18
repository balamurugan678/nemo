package com.novacroft.nemo.tfl.common.converter.impl.financial_services_centre;

import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeRequestExportDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChequeRequestExportConverterImplTest {
    private ChequeRequestExportConverterImpl converter;
    private ChequeRequestExportDTO mockChequeRequestExportDTO;
    private AddressExportDTO mockAddressExportDTO;

    @Before
    public void setUp() {
        this.converter = mock(ChequeRequestExportConverterImpl.class);

        this.mockChequeRequestExportDTO = mock(ChequeRequestExportDTO.class);
        this.mockAddressExportDTO = mock(AddressExportDTO.class);
        when(this.mockChequeRequestExportDTO.getAddressExportDTO()).thenReturn(this.mockAddressExportDTO);
    }

    @Test
    public void shouldGetPaymentMethod() {
        when(this.converter.getPaymentMethod(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getPaymentMethod()).thenReturn(PAYMENT_METHOD);
        assertEquals(PAYMENT_METHOD, this.converter.getPaymentMethod(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerVatRegistrationNumber() {
        when(this.converter.getCustomerVatRegistrationNumber(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        assertEquals(EMPTY_STRING, this.converter.getCustomerVatRegistrationNumber(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerReference() {
        when(this.converter.getCustomerReference(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        assertEquals(EMPTY_STRING, this.converter.getCustomerReference(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerAddressPostCode() {
        when(this.converter.getCustomerAddressPostCode(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenReturn(true);
        when(this.mockAddressExportDTO.getCustomerAddressPostCode()).thenReturn(CUSTOMER_ADDRESS_POST_CODE);
        assertEquals(CUSTOMER_ADDRESS_POST_CODE, this.converter.getCustomerAddressPostCode(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerAddressLine4() {
        when(this.converter.getCustomerAddressLine4(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenReturn(true);
        when(this.mockAddressExportDTO.getCustomerAddressTown()).thenReturn(CUSTOMER_ADDRESS_TOWN);
        assertEquals(CUSTOMER_ADDRESS_TOWN, this.converter.getCustomerAddressLine4(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerAddressLine3() {
        when(this.converter.getCustomerAddressLine3(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenReturn(true);
        when(this.mockAddressExportDTO.getCustomerAddressStreet()).thenReturn(CUSTOMER_ADDRESS_STREET);
        assertEquals(CUSTOMER_ADDRESS_STREET, this.converter.getCustomerAddressLine3(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerAddressLine2() {
        when(this.converter.getCustomerAddressLine2(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenReturn(true);
        when(this.mockAddressExportDTO.getCustomerAddressLine2()).thenReturn(CUSTOMER_ADDRESS_LINE_2);
        assertEquals(CUSTOMER_ADDRESS_LINE_2, this.converter.getCustomerAddressLine2(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerAddressLine1() {
        when(this.converter.getCustomerAddressLine1(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenReturn(true);
        when(this.mockAddressExportDTO.getCustomerAddressLine1()).thenReturn(CUSTOMER_ADDRESS_LINE_1);
        assertEquals(CUSTOMER_ADDRESS_LINE_1, this.converter.getCustomerAddressLine1(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCustomerName() {
        when(this.converter.getCustomerName(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getCustomerName()).thenReturn(CUSTOMER_NAME);
        assertEquals(CUSTOMER_NAME, this.converter.getCustomerName(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetTaxCode() {
        when(this.converter.getTaxCode(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getTaxCode()).thenReturn(TAX_CODE);
        assertEquals(TAX_CODE, this.converter.getTaxCode(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetNetAmount() {
        when(this.converter.getNetAmount(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getNetAmount()).thenReturn(NET_AMOUNT);
        assertEquals(NET_AMOUNT_AS_STRING, this.converter.getNetAmount(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetBaselineDate() {
        when(this.converter.getBaselineDate(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getBaselineDate()).thenReturn(DateTestUtil.getAug19());
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.converter.getBaselineDate(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetAccountNumber() {
        when(this.converter.getAccountNumber(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getAccountNumber()).thenReturn(ACCOUNT_NUMBER);
        assertEquals(ACCOUNT_NUMBER_AS_STRING, this.converter.getAccountNumber(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetAccountType() {
        when(this.converter.getAccountType(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getAccountType()).thenReturn(ACCOUNT_TYPE);
        assertEquals(ACCOUNT_TYPE, this.converter.getAccountType(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetDocumentHeaderText() {
        when(this.converter.getDocumentHeaderText(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getDocumentHeaderText()).thenReturn(DOCUMENT_HEADER_TEXT);
        assertEquals(DOCUMENT_HEADER_TEXT, this.converter.getDocumentHeaderText(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetReferenceNumber() {
        when(this.converter.getReferenceNumber(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getReferenceNumber()).thenReturn(REFERENCE_NUMBER_AS_STRING);
        assertEquals(REFERENCE_NUMBER_AS_STRING, this.converter.getReferenceNumber(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCurrency() {
        when(this.converter.getCurrency(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getCurrency()).thenReturn(CURRENCY);
        assertEquals(CURRENCY, this.converter.getCurrency(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetCompanyCode() {
        when(this.converter.getCompanyCode(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getCompanyCode()).thenReturn(COMPANY_CODE);
        assertEquals(COMPANY_CODE, this.converter.getCompanyCode(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetDocumentType() {
        when(this.converter.getDocumentType(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getDocumentType()).thenReturn(DOCUMENT_TYPE);
        assertEquals(DOCUMENT_TYPE, this.converter.getDocumentType(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetPostingDate() {
        when(this.converter.getPostingDate(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getPostingDate()).thenReturn(DateTestUtil.getAug19());
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.converter.getPostingDate(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetDocumentDate() {
        when(this.converter.getDocumentDate(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getDocumentDate()).thenReturn(DateTestUtil.getAug19());
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.converter.getDocumentDate(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldGetDocumentId() {
        when(this.converter.getDocumentId(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getDocumentId()).thenReturn(DOCUMENT_ID);
        assertEquals(DOCUMENT_ID_AS_STRING, this.converter.getDocumentId(this.mockChequeRequestExportDTO));
    }

    @Test
    public void shouldConvert() {
        when(this.converter.convert(any(ChequeRequestExportDTO.class))).thenCallRealMethod();

        when(this.converter.getDocumentId(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getDocumentDate(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getPostingDate(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getDocumentType(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCompanyCode(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCurrency(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getReferenceNumber(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getDocumentHeaderText(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getAccountType(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getAccountNumber(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getBaselineDate(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getNetAmount(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerName(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerAddressLine1(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerAddressLine2(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerAddressLine3(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerAddressLine4(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerAddressPostCode(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerReference(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getCustomerVatRegistrationNumber(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);
        when(this.converter.getPaymentMethod(any(ChequeRequestExportDTO.class))).thenReturn(EMPTY_STRING);

        String[] result = this.converter.convert(this.mockChequeRequestExportDTO);

        assertEquals(39, result.length);

        verify(this.converter).getDocumentId(any(ChequeRequestExportDTO.class));
        verify(this.converter).getDocumentDate(any(ChequeRequestExportDTO.class));
        verify(this.converter).getPostingDate(any(ChequeRequestExportDTO.class));
        verify(this.converter).getDocumentType(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCompanyCode(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCurrency(any(ChequeRequestExportDTO.class));
        verify(this.converter).getReferenceNumber(any(ChequeRequestExportDTO.class));
        verify(this.converter).getDocumentHeaderText(any(ChequeRequestExportDTO.class));
        verify(this.converter).getAccountType(any(ChequeRequestExportDTO.class));
        verify(this.converter).getAccountNumber(any(ChequeRequestExportDTO.class));
        verify(this.converter).getBaselineDate(any(ChequeRequestExportDTO.class));
        verify(this.converter).getNetAmount(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerName(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerAddressLine1(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerAddressLine2(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerAddressLine3(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerAddressLine4(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerAddressPostCode(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerReference(any(ChequeRequestExportDTO.class));
        verify(this.converter).getCustomerVatRegistrationNumber(any(ChequeRequestExportDTO.class));
        verify(this.converter).getPaymentMethod(any(ChequeRequestExportDTO.class));
    }

    @Test
    public void isAddressNotNullShouldReturnTrue() {
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        assertTrue(this.converter.isAddressNotNull(this.mockChequeRequestExportDTO));

    }

    @Test
    public void isAddressNotNullShouldReturnFalse() {
        when(this.converter.isAddressNotNull(any(ChequeRequestExportDTO.class))).thenCallRealMethod();
        when(this.mockChequeRequestExportDTO.getAddressExportDTO()).thenReturn(null);
        assertFalse(this.converter.isAddressNotNull(this.mockChequeRequestExportDTO));
    }
}