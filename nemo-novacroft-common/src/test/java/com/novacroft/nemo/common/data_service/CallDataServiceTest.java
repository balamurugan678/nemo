package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.CallConverterImpl;
import com.novacroft.nemo.common.data_access.CallDAO;
import com.novacroft.nemo.common.data_service.impl.CallDataServiceImpl;
import com.novacroft.nemo.common.domain.Call;
import com.novacroft.nemo.common.transfer.CallDTO;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.test_support.CallTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CallTestUtil.getCall1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CallDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(CallDataService.class);
    static final String TEST_DUMMY_HQL = "select 1 from sys.dual";

    Query query = mock(Query.class);
    Query query1 = mock(Query.class);

    CallDAO mockDao = mock(CallDAO.class);
    CallDataService dataService = new CallDataServiceImpl();

    @Before
    public void setUp() {
        List<Call> callList = new ArrayList<Call>();
        Call call = getCall1();
        callList.add(getCall1());
        when(mockDao.findById(anyLong())).thenReturn(call);
        when(mockDao.findByQuery(anyString(), anyObject())).thenReturn(callList);
        when(mockDao.findByQuery(anyString(), anyObject(), anyObject())).thenReturn(callList);
        when(mockDao.createOrUpdate((Call) any())).thenReturn(call);

        dataService.setConverter(new CallConverterImpl());
        dataService.setDao(mockDao);

    }

    @Test
    public void findByCustomerIdShouldFindCall() {
        List<CallDTO> calls = dataService.findByCustomerId(1L);
        verify(mockDao).findByQuery(anyString(), anyObject());
        assertEquals(FIRST_NAME_1, calls.get(0).getFirstName());
    }

    @Test
    public void findByCustomerIdDateShouldFindCall() {
        List<CallDTO> calls = dataService.findByCustomerIdDate(1L, new Date());
        verify(mockDao).findByQuery(anyString(), anyObject(), anyObject());
        assertEquals(FIRST_NAME_1, calls.get(0).getFirstName());
    }

}
