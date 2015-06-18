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
import com.novacroft.nemo.tfl.common.converter.impl.EmailMessageConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.EmailMessageDAO;
import com.novacroft.nemo.tfl.common.domain.EmailMessage;

public class EmailMessageDataServiceImplTest {

    private EmailMessageDataServiceImpl service;
    private EmailMessageDAO mockDAO;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        service = new EmailMessageDataServiceImpl();
        mockDAO = mock(EmailMessageDAO.class);
        mockSystemParameterService = mock(SystemParameterServiceImpl.class);
        service.setDao(mockDAO);
        service.setConverter(new EmailMessageConverterImpl());
        service.systemParameterService = mockSystemParameterService;

        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(3);
    }

    @Test
    public void findEmailMessagesToBeSentShouldReturnEmailMessages() {
        List<EmailMessage> emailMessagesList = new ArrayList<EmailMessage>();
        emailMessagesList.add(new EmailMessage());
        when(mockDAO.findByQuery(anyString(), anyString(), anyString(), anyInt())).thenReturn(emailMessagesList);
        assertNotNull(service.findEmailMessagesToBeSent());
    }

    @Test
    public void findEmailMessagesToBeSentShouldReturnNull() {
        when(mockDAO.findByQuery(anyString(), anyString(), anyString(), anyInt())).thenReturn(null);
        assertNull(service.findEmailMessagesToBeSent());
    }

}
