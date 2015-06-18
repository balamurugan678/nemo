package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.CART_ID_2;
import static com.novacroft.nemo.test_support.CartTestUtil.CUSTOMER_ID_2;
import static com.novacroft.nemo.test_support.CartTestUtil.TEST_APPROVAL_ID;
import static com.novacroft.nemo.test_support.CartTestUtil.WEB_ACCOUNT_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.WEB_ACCOUNT_ID_2;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCart1;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCart2;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartIdList;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartWithApprovalId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.converter.impl.CartConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CartDAO;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * CartDataService unit tests
 */
public class CartDataServiceImplTest {
    private CartDataServiceImpl service;
    private CartDataServiceImpl mockService;
    private CartDAO mockDao;
    private CartConverterImpl converter;
    private ItemDataService mockItemDataService;
    private WorkflowDataService mockWorkflowDataService;

    @Before
    public void setUp() {
        this.service = new CartDataServiceImpl();
        mockService = mock(CartDataServiceImpl.class);
        this.mockDao = mock(CartDAO.class);
        this.converter = new CartConverterImpl();
        mockItemDataService = mock(ItemDataService.class);
        mockWorkflowDataService = mock(WorkflowDataService.class);
        
        this.service.setDao(mockDao);
        this.service.setConverter(converter);
        service.itemDataService = mockItemDataService;
        service.workflowDataService = mockWorkflowDataService;
        
        mockService.setDao(mockDao);
        mockService.setConverter(converter);
        mockService.itemDataService = mockItemDataService;
        mockService.workflowDataService = mockWorkflowDataService;

        when(mockItemDataService.setDateTime(any(ItemDTO.class))).then(returnsFirstArg());
        when(mockItemDataService.setUserId(any(ItemDTO.class))).then(returnsFirstArg());
    }
    
    @Test
    public void findByCardIdShouldFindCart() {
    	when(mockDao.findByExampleUniqueResult((Cart) anyObject())).thenReturn(getTestCart1());

        CartDTO resultsDTO = service.findByCardId(CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((Cart) anyObject());
        assertEquals(WEB_ACCOUNT_ID_1, resultsDTO.getWebaccountId());
    }
    
    @Test
    public void findByCardIdShouldReturnNull() {
        when(mockDao.findByExampleUniqueResult((Cart) anyObject())).thenReturn(null);
        assertNull(service.findByCardId(CARD_ID_1));
    }
    
    @Test
    public void findByCustomerIdShouldFindCart() {
    	when(mockDao.findByExampleUniqueResult((Cart) anyObject())).thenReturn(getTestCart2());

        CartDTO resultsDTO = service.findByCustomerId(CUSTOMER_ID_2);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((Cart) anyObject());
        assertEquals(WEB_ACCOUNT_ID_2, resultsDTO.getWebaccountId());
    }
    
    @Test
    public void findByApprovalIdShouldFindCart() {
        when(mockDao.findByExampleUniqueResult((Cart) anyObject())).thenReturn(getTestCartWithApprovalId());

        CartDTO resultsDTO = service.findByApprovalId(CUSTOMER_ID_2);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((Cart) anyObject());
        assertEquals(TEST_APPROVAL_ID, resultsDTO.getApprovalId());
    }
    
    @Test
    public void findByCustomerIdShouldReturnNull() {
        when(mockDao.findByExampleUniqueResult((Cart) anyObject())).thenReturn(null);
        assertNull(service.findByCustomerId(CUSTOMER_ID_2));
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findCartListByCustomerIdShouldReturnEmptyList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(new ArrayList<Long>());
        assertTrue(service.findCartListByCustomerId(CUSTOMER_ID_2).isEmpty());
    }
    
    @Test
    public void findCartListByCustomerIdShouldReturnDTOList() {
        when(mockDao.findByQuery(anyString(), anyVararg()))
            .thenReturn(getTestCartIdList());
        List<Long> actualCartIdList = service.findCartListByCustomerId(CUSTOMER_ID_2);
        assertEquals(CART_ID_2, actualCartIdList.get(0));
    }
    
    @Test
    public void findByWebAccountIdShouldReturnNull() {
        when(mockDao.findByExampleUniqueResult(any(Cart.class))).thenReturn(null);
        assertNull(service.findByWebAccountId(WEB_ACCOUNT_ID_2));
    }
    
    @Test
    public void findByWebAccountIdShouldReturnCartDTO() {
        when(mockDao.findByExampleUniqueResult(any(Cart.class))).thenReturn(getTestCart2());
        assertEquals(WEB_ACCOUNT_ID_2, service.findByWebAccountId(WEB_ACCOUNT_ID_2).getWebaccountId());
    }
    
    @Test
    public void addApprovalIdShouldAddApprovalIdToCartDTO(){
        ReflectionTestUtils.setField(mockService, "dao", mockDao);
        when(mockDao.getNextApprovalId()).thenReturn(TEST_APPROVAL_ID);       
        when(mockService.addApprovalId(any(CartDTO.class))).thenCallRealMethod();
        when(mockWorkflowDataService.isApprovalIdBeingUsed(anyLong())).thenReturn(Boolean.FALSE);
        when(mockService.createOrUpdate(any(CartDTO.class))).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                CartDTO processedCartDTO = (CartDTO) args[0];
                return processedCartDTO;
            }
        });
        assertEquals(TEST_APPROVAL_ID, mockService.addApprovalId(CartTestUtil.getTestCartDTO1()).getApprovalId());
    }
    
    @Test
    public void addApprovalIdShouldCycleIfApprovalIdIsUsed() {
        ReflectionTestUtils.setField(mockService, "dao", mockDao);
        when(mockDao.getNextApprovalId()).thenReturn(TEST_APPROVAL_ID);
        when(mockService.addApprovalId(any(CartDTO.class))).thenCallRealMethod();
        when(mockWorkflowDataService.isApprovalIdBeingUsed(anyLong())).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockService.createOrUpdate(any(CartDTO.class))).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                CartDTO processedCartDTO = (CartDTO) args[0];
                return processedCartDTO;
            }
        });
        assertEquals(TEST_APPROVAL_ID, mockService.addApprovalId(CartTestUtil.getTestCartDTO1()).getApprovalId());
        verify(mockService, times(2)).addApprovalId(any(CartDTO.class));
    }

    @Test
    public void testCreateOrUpdate(){
        CartDataServiceImpl mockService = mock(CartDataServiceImpl.class);
        ReflectionTestUtils.setField(mockService, "dao", mockDao);
        when(mockService.setItemDateTime(any(CartDTO.class))).thenReturn(null);
        mockService.createOrUpdate(CartTestUtil.getTestCartDTO1());
        verify(mockService).createOrUpdate(any(CartDTO.class));
    }
    
    @Test
    public void testSetItemDateTime(){
        service.setItemDateTime(CartTestUtil.getTestCartDTOWithBusAndTravelCardItems());
        verify(mockItemDataService,atLeastOnce()).setUserId(any(ItemDTO.class));
        verify(mockItemDataService,atLeastOnce()).setDateTime(any(ItemDTO.class));
    }
    
}
