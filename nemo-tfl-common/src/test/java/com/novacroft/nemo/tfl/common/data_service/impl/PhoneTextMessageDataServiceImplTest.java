package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.SystemParameterServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PhoneTextMessageConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PhoneTextMessageDAO;
import com.novacroft.nemo.tfl.common.domain.PhoneTextMessage;

public class PhoneTextMessageDataServiceImplTest {

    private PhoneTextMessageDataServiceImpl service;
    private PhoneTextMessageDAO mockDAO;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        service = new PhoneTextMessageDataServiceImpl();
        mockDAO = mock(PhoneTextMessageDAO.class);
        mockSystemParameterService = mock(SystemParameterServiceImpl.class);
        service.setDao(mockDAO);
        service.setConverter(new PhoneTextMessageConverterImpl());
        service.systemParameterService = mockSystemParameterService;

        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(3);
    }

    @Test
    public void findPhoneTextMessagesToBeSentShouldReturnPhoneTextMessages() {
        List<PhoneTextMessage> PhoneTextMessagesList = new ArrayList<PhoneTextMessage>();
        PhoneTextMessagesList.add(new PhoneTextMessage());
        when(mockDAO.findByQuery(anyString(), anyString(), anyString(), anyInt())).thenReturn(PhoneTextMessagesList);
        assertNotNull(service.findPhoneTextMessagesToBeSent());
    }

    @Test
    public void findPhoneTextMessagesToBeSentShouldReturnNull() {
        when(mockDAO.findByQuery(anyString(), anyString(), anyString(), anyInt())).thenReturn(null);
        assertNull(service.findPhoneTextMessagesToBeSent());

    }

}
