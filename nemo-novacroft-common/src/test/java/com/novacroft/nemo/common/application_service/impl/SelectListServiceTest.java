package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.data_service.SelectListDataService;
import com.novacroft.nemo.common.data_service.SelectListOptionDataService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.junit.Test;

import java.util.List;

import static com.novacroft.nemo.test_support.SelectListTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Select list service tests
 */
public class SelectListServiceTest {

    @Test
    public void shouldGetSelectList() {
        SelectListDataService mockSelectListDataService = mock(SelectListDataService.class);
        when(mockSelectListDataService.findByName(anyString())).thenReturn(getTestSelectListDTO());

        SelectListOptionDataService mockSelectListOptionDataService = mock(SelectListOptionDataService.class);
        when(mockSelectListOptionDataService.findOptionsByListId(anyLong())).thenReturn(getTestSelectListOptionDTOs());

        SelectListServiceImpl selectListService = new SelectListServiceImpl();
        selectListService.selectListDataService = mockSelectListDataService;
        selectListService.selectListOptionDataService = mockSelectListOptionDataService;

        SelectListDTO actualResult = selectListService.getSelectList(TEST_LIST_1_NAME);

        verify(mockSelectListDataService).findByName(anyString());
        verify(mockSelectListOptionDataService).findOptionsByListId(anyLong());

        assertEquals(TEST_LIST_1_NAME, actualResult.getName());
        assertEquals(2, actualResult.getOptions().size());
        assertEquals(TEST_OPTION_1_VALUE, actualResult.getOptions().get(0).getValue());
        assertEquals(TEST_OPTION_2_VALUE, actualResult.getOptions().get(1).getValue());
    }

    @Test
    public void shouldGetSelectListOptions() {
        SelectListDataService mockSelectListDataService = mock(SelectListDataService.class);
        when(mockSelectListDataService.findByName(anyString())).thenReturn(getTestSelectListDTO());

        SelectListOptionDataService mockSelectListOptionDataService = mock(SelectListOptionDataService.class);
        when(mockSelectListOptionDataService.findOptionsByListId(anyLong())).thenReturn(getTestSelectListOptionDTOs());

        SelectListServiceImpl selectListService = new SelectListServiceImpl();
        selectListService.selectListDataService = mockSelectListDataService;
        selectListService.selectListOptionDataService = mockSelectListOptionDataService;

        List<SelectListOptionDTO> actualResult = selectListService.getSelectListOptions(TEST_LIST_1_NAME);

        verify(mockSelectListDataService).findByName(anyString());
        verify(mockSelectListOptionDataService).findOptionsByListId(anyLong());

        assertEquals(2, actualResult.size());
        assertEquals(TEST_OPTION_1_VALUE, actualResult.get(0).getValue());
        assertEquals(TEST_OPTION_2_VALUE, actualResult.get(1).getValue());
    }
}
