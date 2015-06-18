package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.CallTypeConverterImpl;
import com.novacroft.nemo.common.data_access.CallTypeDAO;
import com.novacroft.nemo.common.data_service.impl.CallTypeDataServiceImpl;
import com.novacroft.nemo.common.domain.CallType;
import com.novacroft.nemo.common.transfer.CallTypeDTO;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.CallTestUtil.CALLTYPE_ID_1;
import static com.novacroft.nemo.test_support.CallTestUtil.getCallType1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CallTypeDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(CallTypeDataService.class);
    static final String TEST_DUMMY_HQL = "select 1 from sys.dual";

    Query query = mock(Query.class);
    Query query1 = mock(Query.class);

    CallTypeDAO mockDao = mock(CallTypeDAO.class);
    CallTypeDataService dataService = new CallTypeDataServiceImpl();
    CallTypeConverterImpl mockConverter = mock(CallTypeConverterImpl.class);
    CallTypeDataService mockDataService = mock(CallTypeDataServiceImpl.class);

    @Before
    public void setUp() {
        List<CallType> callTypeList = new ArrayList<CallType>();
        CallType callType = getCallType1();
        callTypeList.add(getCallType1());
        when(mockDao.findAll()).thenReturn(callTypeList);
        when(mockDao.findById(anyLong())).thenReturn(callType);
        when(mockDao.findByQuery(anyString(), anyObject())).thenReturn(callTypeList);
        when(mockDao.findByQuery(anyString(), anyObject(), anyObject())).thenReturn(callTypeList);

        dataService.setConverter(new CallTypeConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findAllShouldLoadAllCallTypes() {
        List<CallTypeDTO> findAll = dataService.findAll();
        verify(mockDao).findAll();
        assertEquals(CALLTYPE_ID_1, findAll.get(0).getId());
    }

    @Test
    public void getNewEntityReturnEntity() {
        CallType newEntity = dataService.getNewEntity();
        assertNotNull(newEntity);
    }

    @Test
    public void testSetters() {
        mockDataService.setConverter(mockConverter);
        verify(mockDataService).setConverter(mockConverter);
        mockDataService.setDao(mockDao);
        verify(mockDataService).setDao(mockDao);
    }

}
