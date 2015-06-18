package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionRecord1;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.common.application_service.AdHocLoadReadyForCollectionStatusService;

public class AdHocLoadReadyForCollectionActionImplTest {

    private AdHocLoadReadyForCollectionActionImpl action;
    private AdHocLoadReadyForCollectionStatusService mockAdHocLoadReadyForCollectionStatusService;    
    
    @Before
    public void setUp() throws Exception {
        action = mock(AdHocLoadReadyForCollectionActionImpl.class);
        mockAdHocLoadReadyForCollectionStatusService = mock(AdHocLoadReadyForCollectionStatusService.class);
        action.adHocLoadReadyForCollectionStatusService = mockAdHocLoadReadyForCollectionStatusService;
    }

    @Test
    public void shouldHandle() {
        doCallRealMethod().when(action).handle((ImportRecord)any());
        doNothing().when(mockAdHocLoadReadyForCollectionStatusService).updateAdHocLoadSettlementStatus((Integer)any(), anyString());        
        CurrentActionRecord currentActionRecord = getTestCurrentActionRecord1();
        action.handle(currentActionRecord);
        verify(mockAdHocLoadReadyForCollectionStatusService, atLeastOnce()).updateAdHocLoadSettlementStatus((Integer)any(), anyString());        
    }
    
    @Test
    public void instantiationTest(){
        assertNotNull(action = new AdHocLoadReadyForCollectionActionImpl());
    }
}
