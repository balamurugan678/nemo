package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItem1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemList1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.GoodwillPaymentItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.GoodwillPaymentItemDAO;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public class GoodwillPaymentItemDataServiceImplTest {

    protected GoodwillPaymentItemDataServiceImpl dataService;
    protected GoodwillPaymentItemDAO mockDao;
    protected GoodwillPaymentItemConverterImpl mockConverter;

    @Before
    public void setUp() {

        dataService = new GoodwillPaymentItemDataServiceImpl();
        mockDao = mock(GoodwillPaymentItemDAO.class);
        mockConverter = mock(GoodwillPaymentItemConverterImpl.class);

        dataService.setConverter(mockConverter);
        dataService.setDao(mockDao);
    }

    @Test
    public void testGoodwillPaymentItemDataServiceFindById() {
        GoodwillPaymentItem gpi = new GoodwillPaymentItem();
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(gpi);
        GoodwillPaymentItem findById = dataService.findById("1");

        assertEquals(gpi, findById);
    }

    @Test
    public void testGoodwillPaymentItemDataServiceFindByCartIdAndCardId() {
        GoodwillPaymentItem gpi = new GoodwillPaymentItem();
        when(mockDao.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(gpi);

        GoodwillPaymentItem findByCartIdAndCardId = dataService.findByCartIdAndCardId(1L, 1L);

        assertEquals(gpi, findByCartIdAndCardId);
    }

    @Test
    public void findAllByCartIdAndCardIdSucess() {
        when(mockDao.findByExample(any(GoodwillPaymentItem.class))).thenReturn(getGoodwillPaymentItemList1());
        when(mockConverter.convertEntityToDto(any(GoodwillPaymentItem.class))).thenReturn(getGoodwillPaymentItemDTOItem1());

        List<GoodwillPaymentItemDTO> resultsDTO = dataService.findAllByCartIdAndCardId(1L, 1L);

        verify(mockDao, atLeastOnce()).findByExample((GoodwillPaymentItem) anyObject());
        assertEquals(getGoodwillPaymentItem1().getPrice(), resultsDTO.get(0).getPrice());
    }

    @Test
    public void findAllByCartIdAndWebAccountIdShouldReturnGoodwillPaymentItemDTOs() {
        when(mockDao.findByExample(any(GoodwillPaymentItem.class))).thenReturn(getGoodwillPaymentItemList1());
        when(mockConverter.convertEntityToDto(any(GoodwillPaymentItem.class))).thenReturn(getGoodwillPaymentItemDTOItem1());

        List<GoodwillPaymentItemDTO> resultsDTO = dataService.findAllByCartIdAndCustomerId(1L, 1L);

        verify(mockDao, atLeastOnce()).findByExample((GoodwillPaymentItem) anyObject());
        assertEquals(getGoodwillPaymentItem1().getPrice(), resultsDTO.get(0).getPrice());
    }
}
