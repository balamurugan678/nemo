package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_2;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestSettlement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.novacroft.nemo.tfl.common.converter.impl.AdHocLoadSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.BACSSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ChequeSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.SettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.SettlementDAO;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import com.novacroft.nemo.tfl.common.domain.Settlement;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

public class SettlementDataServiceImplTest {
    private static final Long EXPECTED_GENERATED_NUMBER = 123l;

    private static final Long CUSTOMER_ID = 5001L;
    
    private SettlementDataServiceImpl service;
    private SettlementDAO mockDAO;
    private AdHocLoadSettlementConverterImpl adHocLoadSettlementCovverter;
    private BACSSettlementConverterImpl bacsSettlementConverter;
    private ChequeSettlementConverterImpl chequeSettlementConverter;
    
    @Before
    public void setUp() {
        service = new SettlementDataServiceImpl();
        adHocLoadSettlementCovverter = mock(AdHocLoadSettlementConverterImpl.class);
        bacsSettlementConverter = mock(BACSSettlementConverterImpl.class);
        chequeSettlementConverter = mock(ChequeSettlementConverterImpl.class);
        mockDAO = mock(SettlementDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new SettlementConverterImpl());
        service.adHocLoadSettlementCovverter = adHocLoadSettlementCovverter;
        service.bacsSettlementConverter = bacsSettlementConverter;
        service.chequeSettlementConverter = chequeSettlementConverter;
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findBySettlementIdShouldReturnNull() {
        when(mockDAO.findByExample(any(Settlement.class))).thenReturn(new ArrayList<Settlement>());
        assertNull(service.findBySettlementId(ORDER_ID));
    }
    
    @Test
    public void findBySettlementIdShouldReturnFirstDTO() {
        when(mockDAO.findByExample(any(Settlement.class)))
                .thenReturn(Arrays.asList(getTestSettlement(CARD_ID_1, ORDER_ID), 
                                getTestSettlement(CARD_ID_2, ORDER_ID)));
        assertEquals(CARD_ID_1, service.findBySettlementId(ORDER_ID).getId());
    }
    
    @Test
    public void findByOrderIdShouldReturnNull() {
        when(mockDAO.findByExample(any(Settlement.class))).thenReturn(new ArrayList<Settlement>());
        assertNull(service.findByOrderId(ORDER_ID));
    }
    
    @Test
    public void findByOrderIdShouldReturnDTOs() {
        when(mockDAO.findByExample(any(Settlement.class)))
                .thenReturn(Arrays.asList(getTestSettlement(CARD_ID_1, ORDER_ID), 
                                getTestSettlement(CARD_ID_2, ORDER_ID)));
        List<SettlementDTO> actualList = service.findByOrderId(ORDER_ID); 
        assertEquals(2, actualList.size());
        assertEquals(CARD_ID_1, actualList.get(0).getId());
    }
    
    @Test
    public void shouldCreate() {
        SettlementDataServiceImpl spyService = spy(service);
        when(mockDAO.getNextSequenceNumber(anyString())).thenReturn(EXPECTED_GENERATED_NUMBER);
        doAnswer(returnsFirstArg()).when(spyService).createOrUpdate(any(SettlementDTO.class));
        SettlementDTO actualDTO = spyService.create(new SettlementDTO());
        assertEquals(EXPECTED_GENERATED_NUMBER, actualDTO.getId());
    }
    
    @Test
    public void testFindPolymorphicChildTypeSettlementByOrderIdReturnsNull(){
        List<Settlement> settlementList = new ArrayList<Settlement>();
        
        when(mockDAO.findByExample(any(Settlement.class))).thenReturn(settlementList);
        
        List<SettlementDTO> findPolymorphicChildTypeSettlementByOrderId = service.findPolymorphicChildTypeSettlementByOrderId(ORDER_ID);
        
        assertNull(findPolymorphicChildTypeSettlementByOrderId);

        
    }
    
    @Test
    public void testFindPolymorphicChildTypeSettlementByOrderIdReturnsOneOfEachChildType(){
        
        List<Settlement> settlementList = new ArrayList<Settlement>();
        settlementList.add(new AutoLoadChangeSettlement());
        settlementList.add(new WebAccountCreditSettlement());
        settlementList.add(new AdHocLoadSettlement());
        settlementList.add(new BACSSettlement());
        settlementList.add(new ChequeSettlement());
        settlementList.add(new PaymentCardSettlement());
        
        
        when(mockDAO.findByExample(any(Settlement.class))).thenReturn(settlementList);
        when(adHocLoadSettlementCovverter.convertEntityToDto(any(AdHocLoadSettlement.class))).thenReturn(new AdHocLoadSettlementDTO());
       
        List<SettlementDTO> findPolymorphicChildTypeSettlementByOrderId = service.findPolymorphicChildTypeSettlementByOrderId(ORDER_ID);
        
        assertEquals(findPolymorphicChildTypeSettlementByOrderId.size(), settlementList.size());
        assert(findPolymorphicChildTypeSettlementByOrderId.size() > 0);
    }
    
    
    @Test
    public void testFindAllRefundSettlementsInPast12Months(){
        service.findAllRefundSettlementsInPast12Months(CUSTOMER_ID);
        
        Mockito.verify(mockDAO).findByQuery(anyString(), Mockito.anyObject());
    }
}
