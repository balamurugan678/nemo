package com.novacroft.nemo.common.support;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;
import org.junit.Ignore;
import org.junit.Test;

import java.text.MessageFormat;

import static com.novacroft.nemo.test_support.ContentTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for <code>ReloadableDatabaseMessageSource</code>
 */
public class ReloadableDatabaseMessageSourceTest {
    @Test
    public void shouldResolveMessage() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(getTestContentDTOuk1());
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);
        messageSource.setUseCache(false);

        MessageFormat actualResult = messageSource.resolveCode(TEST_CODE_1, UK_LOCALE);
        assertEquals(new MessageFormat(TEST_CONTENT_1), actualResult);
    }

    @Test
    public void shouldResolveMessageFromHierarchy() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1_PARENT, UK_LOCALE.toString())).thenReturn(null);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(getTestContentDTOuk1());
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);
        messageSource.setUseCache(false);

        MessageFormat actualResult = messageSource.resolveCode(TEST_CODE_1_PARENT, UK_LOCALE);
        assertEquals(new MessageFormat(TEST_CONTENT_1), actualResult);
    }

    @Test
    public void shouldResolveMessageWithoutArguments() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(getTestContentDTOuk1());
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);
        messageSource.setUseCache(false);

        String actualResult = messageSource.resolveCodeWithoutArguments(TEST_CODE_1, UK_LOCALE);
        assertEquals(TEST_CONTENT_1, actualResult);
    }

    @Test
    public void shouldRetrieveContentFromDB() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(getTestContentDTOuk1());
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);

        ContentDTO actualResult = messageSource.getContentFromDB(TEST_CODE_1, UK_LOCALE);
        assertEquals(TEST_CONTENT_1, actualResult.getContent());
    }

    @Test
    public void shouldRetrieveFallBackContentFromDB() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(getTestContentDTOuk1());
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);
        messageSource.useFallBackLocale = true;

        ContentDTO actualResult = messageSource.getContentFromDB(TEST_CODE_1, US_LOCALE);
        assertEquals(TEST_CONTENT_1, actualResult.getContent());
    }

    @Test
    public void shouldRetrieveNullContentFromDB() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(getTestContentDTOuk1());
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);
        messageSource.useFallBackLocale = false;

        assertNull(messageSource.getContentFromDB(TEST_CODE_1, US_LOCALE));
    }

    @Ignore // FIXME
    public void shouldRetrieveCodeAsContent() {
        ContentDataService mockDataService = mock(ContentDataService.class);
        when(mockDataService.findByLocaleAndCode(TEST_CODE_1, UK_LOCALE.toString())).thenReturn(null);
        ReloadableDatabaseMessageSource messageSource = new ReloadableDatabaseMessageSource();
        messageSource.setDataService(mockDataService);
        messageSource.useCodeForNullContent = true;

        ContentDTO actualResult = messageSource.resolveContent(TEST_CODE_1, UK_LOCALE);
        assertEquals(TEST_CODE_1, actualResult.getContent());
    }
}
