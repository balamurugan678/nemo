package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.RefundScenarioHotListReasonTypeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.RefundScenarioHotListReasonTypeDAO;
import com.novacroft.nemo.tfl.common.domain.RefundScenarioHotListReasonType;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

public class RefundScenarioHotListReasonTypeDataServiceImplTest {
    private RefundScenarioHotListReasonTypeDataServiceImpl service;
    private RefundScenarioHotListReasonTypeDAO mockDAO;
    private RefundScenarioHotListReasonTypeConverterImpl mockConverter;
    
    @Before
    public void setUp() {
        service = new RefundScenarioHotListReasonTypeDataServiceImpl();
        mockDAO = mock(RefundScenarioHotListReasonTypeDAO.class);
        mockConverter = mock(RefundScenarioHotListReasonTypeConverterImpl.class);
        service.setDao(mockDAO);
        service.setConverter(mockConverter);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findByRefundScenarioShouldReturnNullIfNullEnum() {
        assertNull(service.findByRefundScenario(null));
    }
    
    @Test
    public void findByRefundScenarioShouldReturnNullIfEmptyQueryResult() {
        when(mockDAO.findByExample(any(RefundScenarioHotListReasonType.class)))
                .thenReturn(new ArrayList<RefundScenarioHotListReasonType>());
        assertNull(service.findByRefundScenario(RefundScenarioEnum.DESTROYED));
    }
    
    @Test
    public void findByRefundScenarioShouldReturnDTO() {
        RefundScenarioHotListReasonTypeDTO mockDTO = mock(RefundScenarioHotListReasonTypeDTO.class);
        List<RefundScenarioHotListReasonType> twoItemList = 
                        Arrays.asList(new RefundScenarioHotListReasonType(), new RefundScenarioHotListReasonType());
        when(mockDAO.findByExample(any(RefundScenarioHotListReasonType.class))).thenReturn(twoItemList);
        when(mockConverter.convertEntityToDto(twoItemList.get(0))).thenReturn(mockDTO);
        assertEquals(mockDTO, service.findByRefundScenario(RefundScenarioEnum.DESTROYED));
    }
}
