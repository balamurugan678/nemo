package com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import java.util.List;

import org.junit.Test;

import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadErrorActionImpl;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadErrorNotificationActionImpl;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadPickUpWindowExpiredActionImpl;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadPickUpWindowExpiredNotificationActionImpl;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadPickedUpActionImpl;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadPickedUpNotificationActionImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadErrorFilterImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadErrorNotificationFilterImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadPickUpWindowExpiredFilterImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadPickUpWindowExpiredNotificationFilterImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadPickedUpFilterImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadPickedUpNotificationFilterImpl;

/**
 * CubicAdHocDistributionDispatcher unit tests
 */
public class AdHocDistributionDispatcherImplTest {
    @Test
    public void shouldInitialiseActions() {
        AdHocDistributionDispatcherImpl dispatcher = new AdHocDistributionDispatcherImpl();

        dispatcher.adHocLoadPickUpWindowExpiredFilter = new AdHocLoadPickUpWindowExpiredFilterImpl();
        dispatcher.adHocLoadPickUpWindowExpiredAction = new AdHocLoadPickUpWindowExpiredActionImpl();

        dispatcher.adHocLoadErrorFilter = new AdHocLoadErrorFilterImpl();
        dispatcher.adHocLoadErrorAction = new AdHocLoadErrorActionImpl();

        dispatcher.adHocLoadErrorFilter = new AdHocLoadPickedUpFilterImpl();
        dispatcher.adHocLoadErrorAction = new AdHocLoadPickedUpActionImpl();
        
        dispatcher.adHocLoadPickUpWindowExpiredNotificationFilter = new AdHocLoadPickUpWindowExpiredNotificationFilterImpl();
        dispatcher.adHocLoadPickUpWindowExpiredNotificationAction = new AdHocLoadPickUpWindowExpiredNotificationActionImpl();

        dispatcher.adHocLoadErrorNotificationFilter = new AdHocLoadErrorNotificationFilterImpl();
        dispatcher.adHocLoadErrorNotificationAction = new AdHocLoadErrorNotificationActionImpl();

        dispatcher.adHocLoadErrorNotificationFilter = new AdHocLoadPickedUpNotificationFilterImpl();
        dispatcher.adHocLoadErrorNotificationAction = new AdHocLoadPickedUpNotificationActionImpl();

        dispatcher.initialiseActions();

        List resultActions = (List) getField(dispatcher, "actions");
        assertEquals(6, resultActions.size());
    }
}
