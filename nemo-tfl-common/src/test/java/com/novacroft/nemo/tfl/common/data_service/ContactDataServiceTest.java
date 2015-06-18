package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.converter.impl.ContactConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ContactDAO;
import com.novacroft.nemo.tfl.common.data_service.impl.ContactDataServiceImpl;
import com.novacroft.nemo.tfl.common.domain.Contact;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.ContactTestUtil.*;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ContactDataServiceTest {
    static final Logger logger = LoggerFactory.getLogger(ContactDataServiceTest.class);

    private ContactDataService dataService;
    private ContactDAO mockDao;
    private NemoUserContext mockNemoUserContext;

    @Before
    public void setUp() {
        dataService = new ContactDataServiceImpl();
        mockDao = mock(ContactDAO.class);
        mockNemoUserContext = mock(NemoUserContext.class);

        dataService.setConverter(new ContactConverterImpl());
        dataService.setDao(mockDao);
        ReflectionTestUtils.setField(dataService, "nemoUserContext", mockNemoUserContext);
    }

    @Test
    public void findHomePhoneByCustomerIdShouldFindContact() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(getTestContactHomePhone1());

        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contacts);

        ContactDTO contactDto = dataService.findHomePhoneByCustomerId(CUSTOMER_ID_1);
        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
        assertEquals(NAME_1, contactDto.getName());
    }

    @SuppressWarnings("unused")
    @Test(expected = DataServiceException.class)
    public void findHomePhoneByCustomerIdShouldError() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(getTestContactHomePhone1());
        contacts.add(getTestContactHomePhone2());

        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contacts);

        ContactDTO contactDto = dataService.findHomePhoneByCustomerId(CUSTOMER_ID_1);
        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
    }

    @Test
    public void findHomePhoneByCustomerIdShouldFindNull() {
        List<Contact> contacts = new ArrayList<Contact>();

        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contacts);

        ContactDTO contactDto = dataService.findHomePhoneByCustomerId(CUSTOMER_ID_1);
        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
        assertNull(contactDto);
    }

    @Test
    public void findMobilePhoneByCustomerIdShouldFindContact() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(getTestContactMobilePhone1());

        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contacts);

        ContactDTO contactDto = dataService.findMobilePhoneByCustomerId(CUSTOMER_ID_1);
        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
        assertEquals(NAME_1, contactDto.getName());
    }

    @SuppressWarnings("unused")
    @Test(expected = DataServiceException.class)
    public void findMobilePhoneByCustomerIdShouldError() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(getTestContactMobilePhone1());
        contacts.add(getTestContactMobilePhone2());

        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contacts);

        ContactDTO contactDto = dataService.findMobilePhoneByCustomerId(CUSTOMER_ID_1);
        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
    }

    @Test
    public void findMobilePhoneByCustomerIdShouldFindNull() {
        List<Contact> contacts = new ArrayList<Contact>();

        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contacts);

        ContactDTO contactDto = dataService.findMobilePhoneByCustomerId(CUSTOMER_ID_1);
        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
        assertNull(contactDto);
    }

    @Test
    public void shouldCreateOrUpdateHomePhoneContact() {
        when(mockDao.createOrUpdate((Contact) anyObject())).thenReturn(getTestContactHomePhone1());
        when(mockDao.findById(anyLong())).thenReturn(getTestContactHomePhone1());

        ContactDTO contactDto = dataService.createOrUpdateHomePhone(getTestContactDTOHomePhone1());
        verify(mockDao).createOrUpdate((Contact) anyObject());
        assertNotNull(contactDto);
    }

    @Test
    public void shouldCreateOrUpdateMobilePhoneContact() {
        when(mockDao.createOrUpdate((Contact) anyObject())).thenReturn(getTestContactMobilePhone1());
        when(mockDao.findById(anyLong())).thenReturn(getTestContactMobilePhone1());

        ContactDTO contactDto = dataService.createOrUpdateMobilePhone(getTestContactDTOMobilePhone1());
        verify(mockDao).createOrUpdate((Contact) anyObject());
        assertNotNull(contactDto);
    }

}
