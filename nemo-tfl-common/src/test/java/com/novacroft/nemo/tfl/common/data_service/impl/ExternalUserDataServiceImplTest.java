package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.ExternalUserTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.ExternalUserTestUtil.ID_1;
import static com.novacroft.nemo.test_support.ExternalUserTestUtil.getExternalUser1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.converter.impl.ExternalUserConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ExternalUserDAO;
import com.novacroft.nemo.tfl.common.data_service.ExternalUserDataService;
import com.novacroft.nemo.tfl.common.transfer.ExternalUserDTO;

public class ExternalUserDataServiceImplTest {

    private ExternalUserDataService dataService;

    @Before
    public void setUp() throws Exception {
        dataService = new ExternalUserDataServiceImpl();
        
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }

    @Test
    public void findByUsernameShouldFindUser() {
        ExternalUserDAO mockDao = mock(ExternalUserDAO.class);
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getExternalUser1());
        
        ExternalUserDataService dataService = new ExternalUserDataServiceImpl();
        dataService.setConverter(new ExternalUserConverterImpl());
        dataService.setDao(mockDao);

        ExternalUserDTO resultDto = dataService.findByUsername(USERNAME_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertEquals(USERNAME_1, resultDto.getUsername());
    }

    @Test(expected = DataServiceException.class)
    public void findByUsernameShouldError() {
        ExternalUserDAO mockDao = mock(ExternalUserDAO.class);
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenThrow(new DataServiceException());

        dataService.setConverter(new ExternalUserConverterImpl());
        dataService.setDao(mockDao);

        dataService.findByUsername(USERNAME_1);
        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
    }

    @Test
    public void shouldFindExternalUserIdByUsername() {
        ExternalUserDAO mockDao = mock(ExternalUserDAO.class);
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getExternalUser1());
        
        ExternalUserDataService dataService = new ExternalUserDataServiceImpl();
        dataService.setConverter(new ExternalUserConverterImpl());
        dataService.setDao(mockDao);

        Long resultLong = dataService.findExternalUserIdByUsername(USERNAME_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertEquals(ID_1, resultLong);
    }

    @Test
    public void shouldNotFindExternalUserIdByUsernameIfUsernameDoesNotExist() {
        ExternalUserDAO mockDao = mock(ExternalUserDAO.class);
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(null);
        
        ExternalUserDataService dataService = new ExternalUserDataServiceImpl();
        dataService.setConverter(new ExternalUserConverterImpl());
        dataService.setDao(mockDao);

        Long resultLong = dataService.findExternalUserIdByUsername(USERNAME_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertNull(resultLong);
    }

}
