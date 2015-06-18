package com.novacroft.nemo.tfl.services.converter.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.ContactTestUtil;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil.getTestCustomer1;
import static com.novacroft.nemo.tfl.services.test_support.DeleteCustomerServiceTestUtil.getTestDeleteCustomer1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTOWithEmail;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getContactDTOList;

public class CustomerConverterTest {

    private CustomerConverterImpl converter;

    @Before
    public void setUp() throws Exception {
        converter = new CustomerConverterImpl();
    }

    @Test
    public void convertToCustomerDTO() {
        assertNotNull(converter.convertToCustomerDTO(getTestCustomer1()));
    }

    @Test
    public void convertToDeleteCustomerDTO() {
        assertNotNull(converter.convertToCustomerDTO(getTestDeleteCustomer1(), getTestCustomerDTOWithEmail()));
    }

    @Test
    public void convertToCustomer() {
        List<ContactDTO> contactDTOs = new ArrayList<>();
        contactDTOs.add(ContactTestUtil.getTestContactDTOHomePhone1());
        contactDTOs.add(ContactTestUtil.getTestContactDTOMobilePhone1());
        assertNotNull(converter.convertToCustomer(getTestCustomerDTOWithEmail(), getTestAddressDTO1(), contactDTOs));
    }

    @Test
    public void convertToContactDTOs() {
        assertNotNull(converter.convertToContactDTOs(getTestCustomer1(), CUSTOMER_ID_1));
    }

    @Test
    public void convertToAddressDTONewAddress() {
        assertNotNull(converter.convertToAddressDTO(getTestCustomer1()));
    }

    @Test
    public void convertToAddressDTOUpdateAddress() {
        assertNotNull(converter.convertToAddressDTO(getTestCustomer1(), getTestAddressDTO1()));
    }

    @Test
    public void convertToDeleteCustomer() {
        assertNotNull(converter.convertToDeleteCustomer(getTestCustomerDTOWithEmail()));
    }

    @Test
    public void updateContactDTOsShouldUpdatePhoneNumbers() {
        assertNotNull(converter.updateContactDTOs(getTestCustomer1(), getContactDTOList()));
    }
}
