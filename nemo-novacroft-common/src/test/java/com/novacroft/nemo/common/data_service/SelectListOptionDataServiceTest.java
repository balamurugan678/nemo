package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.SelectListOptionConverterImpl;
import com.novacroft.nemo.common.data_access.SelectListOptionDAO;
import com.novacroft.nemo.common.domain.SelectListOption;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.SelectListTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Select list option data service test
 */
public class SelectListOptionDataServiceTest {

    @Test
    public void shouldGetNewEntity() {
        SelectListOptionDataServiceImpl dataService = new SelectListOptionDataServiceImpl();
        SelectListOption option = dataService.getNewEntity();
        assertTrue(option instanceof SelectListOption);
    }

    @Test
    public void shouldFindOptionsByListId() {
        List<SelectListOption> options = new ArrayList<SelectListOption>();
        options.add(getTestSelectListOption1());
        options.add(getTestSelectListOption2());

        SelectListOptionDAO mockDao = mock(SelectListOptionDAO.class);
        when(mockDao.findByQuery(anyString(), anyLong())).thenReturn(options);

        SelectListOptionConverterImpl converter = new SelectListOptionConverterImpl();

        SelectListOptionDataServiceImpl dataService = new SelectListOptionDataServiceImpl();
        dataService.setDao(mockDao);
        dataService.setConverter(converter);

        List<SelectListOptionDTO> actualResult = dataService.findOptionsByListId(TEST_LIST_1_ID);

        verify(mockDao).findByQuery(anyString(), anyInt());

        assertEquals(2, actualResult.size());
        assertEquals(TEST_OPTION_1_VALUE, actualResult.get(0).getValue());
        assertEquals(TEST_OPTION_2_VALUE, actualResult.get(1).getValue());
    }
}
