package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.test_support.FileTestUtil.DUMMY_RESOURCE_NAME;
import static com.novacroft.nemo.test_support.JourneyTestUtil.DUMMY_HTML_STRING;
import static com.novacroft.nemo.test_support.JourneyTestUtil.DUMMY_INVALID_HTML_STRING;
import static com.novacroft.nemo.test_support.JourneyTestUtil.EXPECTED_JOURNEY_DESCRIPTION_UNFINISHED;
import static com.novacroft.nemo.test_support.JourneyTestUtil.EXPECTED_JOURNEY_DESCRIPTION_UNSTARTED;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getJourneyDescriptionForUnFinishedJourney;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getJourneyDescriptionForUnStartedJourney;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDTO3;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDTO4;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyHistoryDTOForUnFinishedJourney;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyHistoryDTOForUnStartedJourney;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getUnFinishedJourneyDescription;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getUnStartedJourneyDescription;
import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.novacroft.nemo.common.application_service.FreemarkerTemplateService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JourneyHistoryPDFServiceImplTest {

    private JourneyHistoryPDFServiceImpl mockService;
    
    private ITextRenderer mockTextRenderer;
    
    @Before
    public void setUp(){
        mockService = mock(JourneyHistoryPDFServiceImpl.class);
        mockTextRenderer = mock(ITextRenderer.class); 
    }
    
    @Test
    public void shouldGetNameAndAddress() {
        PersonalDetailsService mockPersonalDetailsService = mock(PersonalDetailsService.class);
        when(mockPersonalDetailsService.getPersonalDetailsByCardNumber(anyString())).thenReturn(getTestPersonalDetailsCmd1());

        JourneyHistoryPDFServiceImpl service = new JourneyHistoryPDFServiceImpl();
        service.personalDetailsService = mockPersonalDetailsService;

        List<String> result = service.getNameAndAddress("");

        assertEquals(4, result.size());
        assertTrue(result.contains("test-first-name-1 test-initial-1 test-last-name-1"));
        assertTrue(result.contains("Cirrus Park"));
        assertTrue(result.contains("Lower Farm Road"));
        assertTrue(result.contains("Moulton Park, NN3 6UR"));
    }

    @Test
    public void shouldGetResourceUrlAsString() {
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getResource(anyString())).thenReturn(new ClassPathResource(DUMMY_RESOURCE_NAME));

        JourneyHistoryPDFServiceImpl service = new JourneyHistoryPDFServiceImpl();
        service.applicationContext = mockApplicationContext;

        String result = service.getResourceUrlAsString(DUMMY_RESOURCE_NAME);

        assertTrue(result.contains(DUMMY_RESOURCE_NAME));
    }

    @Test(expected = ApplicationServiceException.class)
    public void getResourceUrlAsStringShouldError() throws IOException {

        Resource mockResource = mock(Resource.class);
        when(mockResource.getURL()).thenThrow(new IOException("test error"));

        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getResource(anyString())).thenReturn(mockResource);

        JourneyHistoryPDFServiceImpl service = new JourneyHistoryPDFServiceImpl();
        service.applicationContext = mockApplicationContext;

        service.getResourceUrlAsString("");
    }

    @Test
    public void shouldGetModel() {
        SystemParameterService mockSystemParameterService = mock(SystemParameterService.class);
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn("");

        JourneyHistoryPDFServiceImpl service = mock(JourneyHistoryPDFServiceImpl.class);
        when(service.getModel(any(JourneyHistoryDTO.class))).thenCallRealMethod();
        when(service.getResourceUrlAsString(anyString())).thenReturn("test");
        when(service.getNameAndAddress(anyString())).thenReturn(Collections.EMPTY_LIST);
        service.systemParameterService = mockSystemParameterService;

        Map<String, Object> result = service.getModel(new JourneyHistoryDTO());
        assertTrue(result.containsKey("journeyHistory"));
        assertTrue(result.containsKey("logo"));
        assertTrue(result.containsKey("oyster"));
        assertTrue(result.containsKey("icon1"));
        assertTrue(result.containsKey("icon2"));
        assertTrue(result.containsKey("now"));
        assertTrue(result.containsKey("nameAndAddressLines"));
    }

    @Test
    public void shouldConvertJourneyHistoryModelToHtml() throws IOException, TemplateException {
        Template mockTemplate = mock(Template.class);
        doNothing().when(mockTemplate).process(anyMap(), any(Writer.class));

        FreemarkerTemplateService mockFreemarkerTemplateService = mock(FreemarkerTemplateService.class);
        when(mockFreemarkerTemplateService.getTemplate(anyString())).thenReturn(mockTemplate);

        JourneyHistoryPDFServiceImpl service = mock(JourneyHistoryPDFServiceImpl.class);
        when(service.convertJourneyHistoryModelToHtml(any(JourneyHistoryDTO.class))).thenCallRealMethod();
        when(service.getModel(any(JourneyHistoryDTO.class))).thenReturn(Collections.EMPTY_MAP);
        service.freemarkerTemplateService = mockFreemarkerTemplateService;

        service.convertJourneyHistoryModelToHtml(new JourneyHistoryDTO());

        verify(mockTemplate).process(anyMap(), any(Writer.class));
        verify(mockFreemarkerTemplateService).getTemplate(anyString());
        verify(service).getModel(any(JourneyHistoryDTO.class));
    }

    @Test(expected = ApplicationServiceException.class)
    public void convertJourneyHistoryModelToHtmlShouldError() throws IOException, TemplateException {
        Template mockTemplate = mock(Template.class);
        doThrow(new TemplateException("test error", null)).when(mockTemplate).process(anyMap(), any(Writer.class));

        FreemarkerTemplateService mockFreemarkerTemplateService = mock(FreemarkerTemplateService.class);
        when(mockFreemarkerTemplateService.getTemplate(anyString())).thenReturn(mockTemplate);

        JourneyHistoryPDFServiceImpl service = mock(JourneyHistoryPDFServiceImpl.class);
        when(service.convertJourneyHistoryModelToHtml(any(JourneyHistoryDTO.class))).thenCallRealMethod();
        when(service.getModel(any(JourneyHistoryDTO.class))).thenReturn(Collections.EMPTY_MAP);
        service.freemarkerTemplateService = mockFreemarkerTemplateService;

        service.convertJourneyHistoryModelToHtml(new JourneyHistoryDTO());

        verify(mockTemplate).process(anyMap(), any(Writer.class));
        verify(mockFreemarkerTemplateService).getTemplate(anyString());
        verify(service).getModel(any(JourneyHistoryDTO.class));
    }

    @Test
    public void shouldCreatePDF() {
        JourneyHistoryPDFServiceImpl service = mock(JourneyHistoryPDFServiceImpl.class);
        when(service.createPDF(any(JourneyHistoryDTO.class))).thenCallRealMethod();
        when(service.convertJourneyHistoryModelToHtml(any(JourneyHistoryDTO.class))).thenReturn("");
        when(service.generatePdfFromHtml(anyString())).thenReturn(new byte[]{});

        service.createPDF(new JourneyHistoryDTO());

        verify(service).convertJourneyHistoryModelToHtml(any(JourneyHistoryDTO.class));
        verify(service).generatePdfFromHtml(anyString());
    }
    
    @Test
    public void alterJourneyDescriptionForUnStartedShouldAlterJourneyDescriptionTest() {
        doCallRealMethod().when(mockService).alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(any(JourneyHistoryDTO.class));
        doCallRealMethod().when(mockService).findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(any(JourneyDayDTO.class));
        JourneyHistoryDTO journeyHistory = getTestJourneyHistoryDTOForUnStartedJourney();
        mockService.alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(journeyHistory);
        assertEquals(EXPECTED_JOURNEY_DESCRIPTION_UNSTARTED, getJourneyDescriptionForUnStartedJourney(journeyHistory));
    }

    @Test
    public void alterJourneyDescriptionForUnfinishedJourneysShouldAlterJourneyDescriptionTest() {
        doCallRealMethod().when(mockService).alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(any(JourneyHistoryDTO.class));
        doCallRealMethod().when(mockService).findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(any(JourneyDayDTO.class));
        JourneyHistoryDTO journeyHistory = getTestJourneyHistoryDTOForUnFinishedJourney();
        mockService.alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(journeyHistory);
        assertEquals(EXPECTED_JOURNEY_DESCRIPTION_UNFINISHED, getJourneyDescriptionForUnFinishedJourney(journeyHistory));
    }
    
    @Test
    public void alterJourneyDescriptionForNoUnStartedJourneysShouldNotAlterJourneyDescriptionTest() {
        doCallRealMethod().when(mockService).alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(any(JourneyHistoryDTO.class));
        doCallRealMethod().when(mockService).findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(any(JourneyDayDTO.class));
        JourneyHistoryDTO journeyHistory = getTestJourneyHistoryDTOForUnFinishedJourney();
        mockService.alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(journeyHistory);
        assertNull(getJourneyDescriptionForUnStartedJourney(journeyHistory));
    }
    
    @Test
    public void alterJourneyDescriptionForNoUnfinishedJourneysShouldNotAlterJourneyDescriptionTest() {
        doCallRealMethod().when(mockService).alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(any(JourneyHistoryDTO.class));
        doCallRealMethod().when(mockService).findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(any(JourneyDayDTO.class));
        JourneyHistoryDTO journeyHistory = getTestJourneyHistoryDTOForUnStartedJourney();
        mockService.alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(journeyHistory);
        assertNull(getJourneyDescriptionForUnFinishedJourney(journeyHistory));
    }

    @Test
    public void findUnStartedAndUnFinishedJourneysShouldFindUnStartedJourneyAndAlterJourneyDescriptionTest(){
        doCallRealMethod().when(mockService).findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(any(JourneyDayDTO.class));
        JourneyDayDTO journeyDay = getTestJourneyDayDTO3();
        mockService.findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(journeyDay);
        assertEquals(EXPECTED_JOURNEY_DESCRIPTION_UNSTARTED, getUnStartedJourneyDescription(journeyDay));
    }
    
    @Test
    public void findUnStartedAndUnFinishedJourneysShouldFindUnFinishedJourneyAndAlterJourneyDescriptionTest(){
        doCallRealMethod().when(mockService).findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(any(JourneyDayDTO.class));
        JourneyDayDTO journeyDay = getTestJourneyDayDTO4();
        mockService.findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(journeyDay);
        assertEquals(EXPECTED_JOURNEY_DESCRIPTION_UNFINISHED, getUnFinishedJourneyDescription(journeyDay));
    }
    
    @Test
    public void generatePdfFromHtmlTest(){
        doCallRealMethod().when(mockService).generatePdfFromHtml(anyString());
        when(mockService.getRenderer(anyString())).thenReturn(mockTextRenderer);
        assertNotNull(mockService.generatePdfFromHtml(DUMMY_HTML_STRING));
    }
    
    @Test(expected = ApplicationServiceException.class)
    public void generatePdfFromHtmlTestShouldThrowException(){
        doCallRealMethod().when(mockService).generatePdfFromHtml(anyString());
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(DUMMY_INVALID_HTML_STRING);
        when(mockService.getRenderer(anyString())).thenReturn(renderer);
        assertNotNull(mockService.generatePdfFromHtml(DUMMY_HTML_STRING));
    }

    @Test
    public void getImageUrlAsStringShouldInvokeServiceTest(){
        mockService.getImageUrlAsString(DUMMY_RESOURCE_NAME);
        verify(mockService).getImageUrlAsString(anyString());
    }
    
    @Test
    public void getRendererTest(){
        doCallRealMethod().when(mockService).getRenderer(anyString());
        assertNotNull(mockService.getRenderer(DUMMY_HTML_STRING));
        assertTrue(mockService.getRenderer(DUMMY_HTML_STRING) instanceof ITextRenderer);
    }
    
}
