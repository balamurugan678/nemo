package com.novacroft.nemo.tfl.common.util.cyber_source;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for CyberSourceUtil
 */
public class CyberSourceUtilTest {

    @Test
    public void isAcceptedShouldReturnTrueWithAcceptDecision() {
        assertTrue(CyberSourceUtil.isAccepted("Accept"));
    }

    @Test
    public void isAcceptedShouldReturnFalseWithNotAcceptDecision() {
        assertFalse(CyberSourceUtil.isAccepted("X"));
    }

    @Test
    public void isCancelledShouldReturnTrueWithCancelDecision() {
        assertTrue(CyberSourceUtil.isCancelled("Cancel"));
    }

    @Test
    public void isCancelledShouldReturnFalseWithNotCancelDecision() {
        assertFalse(CyberSourceUtil.isCancelled("X"));
    }

    @Test
    public void isIncompleteShouldReturnTrueWithReviewDecision() {
        assertTrue(CyberSourceUtil.isIncomplete("Review"));
    }

    @Test
    public void isIncompleteShouldReturnTrueWithDeclineDecision() {
        assertTrue(CyberSourceUtil.isIncomplete("Decline"));
    }

    @Test
    public void isIncompleteShouldReturnTrueWithErrorDecision() {
        assertTrue(CyberSourceUtil.isIncomplete("Error"));
    }

    @Test
    public void isIncompleteShouldReturnFalseWithNotIncompleteDecision() {
        assertFalse(CyberSourceUtil.isIncomplete("X"));
    }

    @Test
    public void resolveEventNameShouldReturnAccepted() {
        assertEquals(EventName.PAYMENT_RESOLVED,
                CyberSourceUtil.resolveEventNameForDecision(CyberSourceDecision.ACCEPT.code()));
    }

    @Test
    public void resolveEventNameShouldReturnCancelled() {
        assertEquals(EventName.PAYMENT_CANCELLED,
                CyberSourceUtil.resolveEventNameForDecision(CyberSourceDecision.CANCEL.code()));
    }

    @Test
    public void resolveEventNameShouldReturnIncomplete() {
        assertEquals(EventName.PAYMENT_INCOMPLETE,
                CyberSourceUtil.resolveEventNameForDecision(CyberSourceDecision.ERROR.code()));
    }

    @Test(expected = ApplicationServiceException.class)
    public void resolveEventNameShouldError() {
        CyberSourceUtil.resolveEventNameForDecision("Rubbish!");
    }

}
