package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.MonthEnum;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;

import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.get31Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.getApr03;
import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.createIncompleteJourneyNotificationDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollateIncompleteJourneyServiceImplTest {
    
    @Test
    public void shouldCollateRefundEligibleJourneysByMonth() {
        List<IncompleteJourneyNotificationDTO> notificationDTOs = Arrays.asList(
                        createIncompleteJourneyNotificationDTO(Boolean.TRUE, get1Jan()),
                        createIncompleteJourneyNotificationDTO(Boolean.TRUE, get31Jan()),
                        createIncompleteJourneyNotificationDTO(Boolean.FALSE, getApr03()));
        
        CollateIncompleteJourneyServiceImpl service = new CollateIncompleteJourneyServiceImpl();
        List<IncompleteJourneyMonthDTO> actualResult = service.collateRefundEligibleJourneysByMonth(notificationDTOs);
        assertTrue(actualResult.size() == 1);
        
        IncompleteJourneyMonthDTO actualMonthDTO = actualResult.get(0);
        assertEquals(MonthEnum.JANUARY, actualMonthDTO.getJourneyMonth());
        assertTrue(actualMonthDTO.getIncompleteJourneyList().size() == 2);
    }
}
