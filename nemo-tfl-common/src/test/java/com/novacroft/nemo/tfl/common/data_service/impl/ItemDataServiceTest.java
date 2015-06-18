package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItem2;
import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_CREATED_USER_ID_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_2;
import static com.novacroft.nemo.test_support.ItemTestUtil.BLANK;
import static com.novacroft.nemo.test_support.ItemTestUtil.DOMAIN_PACKAGE;
import static com.novacroft.nemo.test_support.ItemTestUtil.DOT;
import static com.novacroft.nemo.test_support.ItemTestUtil.DTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.converter.impl.ItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ItemDAO;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Item data service unit tests.
 */
public class ItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(ItemDataServiceTest.class);

    private ItemDataServiceImpl dataService;
    private ItemDAO mockDao;
    private NemoUserContext mockNemoUserContext;
    private ItemConverterImpl mockItemConverterImpl;

    @Before
    public void setUp() {
        dataService = new ItemDataServiceImpl();
        mockDao = mock(ItemDAO.class);
        mockNemoUserContext = mock(NemoUserContext.class);
        mockItemConverterImpl = mock(ItemConverterImpl.class);

        dataService.setConverter(mockItemConverterImpl);
        dataService.setDao(mockDao);
    }

    @Test
    public void getNewEntityShouldReturnNewAdministrationFeeEntity() {
    	ItemDTO itemDTO = getTestAdministrationFeeItemDTO3();
    	
    	Item item = dataService.getNewEntity(itemDTO);
    	
    	assertEquals(itemDTO.getId(), item.getId());
    	assertEquals(itemDTO.getClass().getSimpleName(), item.getClass().getSimpleName() + DTO);
    	assertEquals(DOMAIN_PACKAGE, item.getClass().getName().replace(DOT + item.getClass().getSimpleName(), BLANK));
    	assertTrue(item instanceof AdministrationFeeItem);
    }
    
    @Test
    public void createOrUpdateShouldCreateOrUpdateItem() {
    	ItemDTO itemDTO = getTestAdministrationFeeItemDTO3();
    	when(mockDao.createOrUpdate((Item) anyObject())).thenReturn(getTestAdministrationFeeItem2());
    	when(mockItemConverterImpl.convertDtoToEntity((ItemDTO) anyObject(), (Item) anyObject())).thenReturn(getTestAdministrationFeeItem2());
    	when(mockItemConverterImpl.convertEntityToDto((Item) anyObject())).thenReturn(getTestAdministrationFeeItemDTO3());
    	
    	ReflectionTestUtils.setField(this.dataService, "nemoUserContext", this.mockNemoUserContext);
    	when(mockNemoUserContext.getUserName()).thenReturn(ADMINISTRATION_FEE_CREATED_USER_ID_2);
    	
    	ItemDTO returnedItemDTO = dataService.createOrUpdate(itemDTO);
    	
    	assertEquals(ADMINISTRATION_FEE_PRICE_2, returnedItemDTO.getPrice());
    	assertEquals(ADMINISTRATION_FEE_CREATED_USER_ID_2, returnedItemDTO.getCreatedUserId());
    	assertTrue(returnedItemDTO instanceof AdministrationFeeItemDTO);
    }

}
