package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.ContentConverterImpl;
import com.novacroft.nemo.common.data_access.ContentDAO;
import com.novacroft.nemo.common.domain.Content;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.ContentDTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.test_support.ContentTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test content data service
 */
public class ContentDataServiceTest {

    static final String TEST_DUMMY_HQL = "select 1 from sys.dual";

    @Test
    public void findByLocaleAndCodeShouldReturnContent() {
        List<Content> contentList = new ArrayList<Content>();
        contentList.add(getTestContent1());

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contentList);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        ContentDTO actualResult = contentDataService.findByLocaleAndCode(TEST_CODE_1, TEST_LOCALE_1);

        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
        assertEquals(getTestContentDTO1().getCode(), actualResult.getCode());
        assertEquals(getTestContentDTO1().getLocale(), actualResult.getLocale());
    }

    @Test(expected = DataServiceException.class)
    public void findByLocaleAndCodeShouldErrorWithTooManyResults() {
        List<Content> contentList = new ArrayList<Content>();
        contentList.add(getTestContent1());
        contentList.add(getTestContent2());

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contentList);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        contentDataService.findByLocaleAndCode(TEST_CODE_1, TEST_LOCALE_1);

        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
    }

    @Test
    public void findByLocaleAndCodeShouldReturnNull() {
        List<Content> contentList = new ArrayList<Content>();

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString(), anyString(), anyString())).thenReturn(contentList);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        ContentDTO actualResult = contentDataService.findByLocaleAndCode(TEST_CODE_1, TEST_LOCALE_1);

        verify(mockDao).findByQuery(anyString(), anyString(), anyString());
        assertNull(actualResult);
    }

    @Test
    public void findLatestShouldReturnRecord() {
        List<Date> dates = new ArrayList<Date>();
        dates.add(getAug19());

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString())).thenReturn(dates);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        Date actualResult = contentDataService.findLatest("TEST_DUMMY_HQL");
        verify(mockDao).findByQuery("TEST_DUMMY_HQL");
        assertEquals(getAug19(), actualResult);
    }

    @Test
    public void findLatestShouldReturnNull() {
        List<Date> dates = new ArrayList<Date>();

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString())).thenReturn(dates);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        Date actualResult = contentDataService.findLatest("TEST_DUMMY_HQL");
        verify(mockDao).findByQuery("TEST_DUMMY_HQL");
        assertNull(actualResult);
    }

    @Test
    public void findLatestCreatedDateTimeShouldReturnRecord() {
        List<Date> dates = new ArrayList<Date>();
        dates.add(getAug19());

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString())).thenReturn(dates);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        Date actualResult = contentDataService.findLatestCreatedDateTime();
        verify(mockDao).findByQuery(anyString());
        assertEquals(getAug19(), actualResult);
    }

    @Test
    public void findLatestModifiedDateTimeShouldReturnRecord() {
        List<Date> dates = new ArrayList<Date>();
        dates.add(getAug19());
        dates.add(getAug20());

        ContentDAO mockDao = mock(ContentDAO.class);
        when(mockDao.findByQuery(anyString())).thenReturn(dates);

        ContentConverterImpl contentConverter = new ContentConverterImpl();

        ContentDataServiceImpl contentDataService = new ContentDataServiceImpl();
        contentDataService.setDao(mockDao);
        contentDataService.setConverter(contentConverter);

        Date actualResult = contentDataService.findLatestModifiedDateTime();
        verify(mockDao).findByQuery(anyString());
        assertEquals(getAug19(), actualResult);
    }

    @Test
    public void shouldGetNewEntity() {
        ContentDataService contentDataService = new ContentDataServiceImpl();
        Content content = contentDataService.getNewEntity();
        assertTrue(content instanceof Content);
    }
}
