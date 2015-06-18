package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.SelectListConverterImpl;
import com.novacroft.nemo.common.converter.impl.SelectListOptionConverterImpl;
import com.novacroft.nemo.common.data_access.SelectListDAO;
import com.novacroft.nemo.common.domain.SelectList;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.SelectListTestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * select list data service unit tests
 */
public class SelectListDataServiceTest {

    @Test
    public void findByNameShouldReturnRecord() {
        List<SelectList> selectLists = new ArrayList<SelectList>();
        selectLists.add(getTestSelectList1());

        SelectListDAO mockDao = mock(SelectListDAO.class);
        when(mockDao.findByExample(any(SelectList.class))).thenReturn(selectLists);

        SelectListConverterImpl selectListConverter = new SelectListConverterImpl();
        SelectListOptionConverterImpl selectListOptionConverter = new SelectListOptionConverterImpl();
        setField(selectListConverter, "selectListOptionConverter", selectListOptionConverter);

        SelectListDataServiceImpl dataService = new SelectListDataServiceImpl();
        dataService.setDao(mockDao);
        dataService.setConverter(selectListConverter);

        SelectListDTO actualResult = dataService.findByName(TEST_LIST_1_NAME);

        verify(mockDao).findByExample(any(SelectList.class));

        assertEquals(TEST_LIST_1_NAME, actualResult.getName());
    }

    @Test(expected = DataServiceException.class)
    public void findByNameShouldFailWithTooManyResults() {
        List<SelectList> selectLists = new ArrayList<SelectList>();
        selectLists.add(getTestSelectList1());
        selectLists.add(getTestSelectList2());

        SelectListDAO mockDao = mock(SelectListDAO.class);
        when(mockDao.findByExample(any(SelectList.class))).thenReturn(selectLists);

        SelectListConverterImpl selectListConverter = new SelectListConverterImpl();
        SelectListOptionConverterImpl selectListOptionConverter = new SelectListOptionConverterImpl();
        setField(selectListConverter, "selectListOptionConverter", selectListOptionConverter);

        SelectListDataServiceImpl dataService = new SelectListDataServiceImpl();
        dataService.setDao(mockDao);
        dataService.setConverter(selectListConverter);

        dataService.findByName(TEST_LIST_1_NAME);

        verify(mockDao).findByExample(any(SelectList.class));
    }

    @Test
    public void findByNameShouldReturnNull() {
        List<SelectList> selectLists = new ArrayList<SelectList>();

        SelectListDAO mockDao = mock(SelectListDAO.class);
        when(mockDao.findByExample(any(SelectList.class))).thenReturn(selectLists);

        SelectListConverterImpl selectListConverter = new SelectListConverterImpl();
        SelectListOptionConverterImpl selectListOptionConverter = new SelectListOptionConverterImpl();
        setField(selectListConverter, "selectListOptionConverter", selectListOptionConverter);

        SelectListDataServiceImpl dataService = new SelectListDataServiceImpl();
        dataService.setDao(mockDao);
        dataService.setConverter(selectListConverter);

        SelectListDTO actualResult = dataService.findByName(TEST_LIST_1_NAME);

        verify(mockDao).findByExample(any(SelectList.class));

        assertNull(actualResult);
    }

    @Test
    public void shouldGetNewEntity() {
        SelectListDataServiceImpl contentDataService = new SelectListDataServiceImpl();
        SelectList content = contentDataService.getNewEntity();
        assertTrue(content instanceof SelectList);
    }
}
