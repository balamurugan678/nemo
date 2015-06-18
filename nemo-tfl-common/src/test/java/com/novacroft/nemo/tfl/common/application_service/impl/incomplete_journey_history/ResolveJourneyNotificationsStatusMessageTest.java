package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.getTestIncompleteJourneyDTOWithNotificationDTO;
import static com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history.ResolveJourneyNotificationsReferencesServiceImpl.AUTOFILL_NOTIFIED_COMMENCED_DESCRIPTION;
import static com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history.ResolveJourneyNotificationsReferencesServiceImpl.AUTOFILL_NOTIFIED_OF_COMPLETION_DESCRIPTION;
import static com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history.ResolveJourneyNotificationsReferencesServiceImpl.SSR_FAILED;
import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

@RunWith(Parameterized.class)
public class ResolveJourneyNotificationsStatusMessageTest {
    private ResolveJourneyNotificationsReferencesServiceImpl mockService;
    
    private NotificationStatus testStatus;
    private String expectedDescription;
    
    public ResolveJourneyNotificationsStatusMessageTest(NotificationStatus testStatus,
                    String expectedDesString) {
        this.testStatus = testStatus;
        this.expectedDescription = expectedDesString;
    }
    
    @Before
    public void setUp() {
        mockService = mock(ResolveJourneyNotificationsReferencesServiceImpl.class);
        
        when(mockService.getContent(anyString())).then(returnsFirstArg());
        when(mockService.getContent(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(StringUtil.EMPTY_STRING);
        doCallRealMethod().when(mockService).resolveStatusMessage(any(InCompleteJourneyDTO.class));
    }
    
    @Parameters
    public static Collection<?> parameterizedTestData() {
        return Arrays.asList(new Object[][] {
            {NotificationStatus.SSR_COMMENCED_BUT_FAILED,  SSR_FAILED},
            {NotificationStatus.SSR_VOIDED, SSR_FAILED},
            {NotificationStatus.AUTOFILL_NOTIFIED_COMMENCED, AUTOFILL_NOTIFIED_COMMENCED_DESCRIPTION},
            {NotificationStatus.AUTOFILL_NOTIFIED_OF_COMPLETION, AUTOFILL_NOTIFIED_OF_COMPLETION_DESCRIPTION},
            {NotificationStatus.AUTOFILL_NOT_NOTIFIED, ""}
        });
    }
    
    @Test
    public void shouldResolveStatusMessage() {
        InCompleteJourneyDTO testDTO = getTestIncompleteJourneyDTOWithNotificationDTO();
        testDTO.getJourneyNotificationDTO().setSsrAutoFillNotificationStatus(testStatus);
        mockService.resolveStatusMessage(testDTO);
        assertEquals(expectedDescription, testDTO.getJourneyDisplayDTO().getJourneyDescription());
    }
}
