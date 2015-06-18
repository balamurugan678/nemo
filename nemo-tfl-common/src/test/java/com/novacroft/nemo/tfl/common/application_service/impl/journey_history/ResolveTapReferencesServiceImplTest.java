package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.PseudoTransactionTypeTestUtil.TRANSACTION_DISPLAY_DESCRIPTION_1;
import static com.novacroft.nemo.test_support.PseudoTransactionTypeTestUtil.getTestPseudoTransactionTypeLookupDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.novacroft.nemo.test_support.JourneyTestUtil;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;

public class ResolveTapReferencesServiceImplTest {

    @Test
    public void shouldResolveBusLocationTrue() {
        ResolveTapReferencesServiceImpl service = mock(ResolveTapReferencesServiceImpl.class);

        doCallRealMethod().when(service).resolveBusFlag(any(TapDTO.class));
        when(service.getContent(anyString())).thenReturn("U");

        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);
        service.resolveBusFlag(tap);

        assertTrue(tap.getTapDisplay().getLocationBusFlag());
        assertFalse(tap.getTapDisplay().getLocationUndergroundFlag());
        assertFalse(tap.getTapDisplay().getLocationNationalRailFlag());
    }

    @Test
    public void shouldResolveLondonUndergroundLocationTrue() {
        ResolveTapReferencesServiceImpl service = mock(ResolveTapReferencesServiceImpl.class);

        doCallRealMethod().when(service).resolveUndergroundFlag(any(TapDTO.class));
        when(service.getContent(anyString())).thenReturn("London Underground");

        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);
        tap.getTapDisplay().setNationalLocationName(tap.getTapDisplay().getNationalLocationName() + " [London Underground]");
        service.resolveUndergroundFlag(tap);

        assertTrue(tap.getTapDisplay().getLocationUndergroundFlag());
        assertFalse(tap.getTapDisplay().getLocationBusFlag());
        assertFalse(tap.getTapDisplay().getLocationNationalRailFlag());
    }

    @Test
    public void shouldResolveRailLocationTrue() {
        ResolveTapReferencesServiceImpl service = mock(ResolveTapReferencesServiceImpl.class);

        doCallRealMethod().when(service).resolveRailFlag(any(TapDTO.class));
        when(service.getContent(anyString())).thenReturn("National Rail");

        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);
        tap.getTapDisplay().setNationalLocationName(tap.getTapDisplay().getNationalLocationName() + " [National Rail]");
        service.resolveRailFlag(tap);

        assertTrue(tap.getTapDisplay().getLocationNationalRailFlag());
        assertFalse(tap.getTapDisplay().getLocationBusFlag());
        assertFalse(tap.getTapDisplay().getLocationUndergroundFlag());
    }

    @Test
    public void isKnownLocationShouldReturnTrue() {
        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        assertTrue(service.isKnownLocation(99));
    }

    @Test
    public void isKnownLocationShouldReturnFalseWithNull() {
        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        assertFalse(service.isKnownLocation(null));
    }

    @Test
    public void isKnownLocationShouldReturnFalseWithZero() {
        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        assertFalse(service.isKnownLocation(0));
    }

    @Test
    public void isKnownLocationShouldReturnFalseWithMinus1() {
        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        assertFalse(service.isKnownLocation(-1));
    }

    @Test
    public void shouldResolveCreditValue() {
        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);
        tap.setAddedStoredValueBalance(99);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.resolveCreditAmount(tap);
        assertNotNull(tap.getTapDisplay().getCreditAmount());
    }

    @Test
    public void shouldResolveChargeValue() {
        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);
        tap.setAddedStoredValueBalance(-99);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.resolveChargeAmount(tap);
        assertNotNull(tap.getTapDisplay().getChargeAmount());
    }

    @Test
    public void shouldNotResolveZeroChargeValue() {
        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);
        tap.setAddedStoredValueBalance(0);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.resolveChargeAmount(tap);
        assertNotNull(tap.getTapDisplay().getChargeAmount());
    }

    @Test
    public void shouldLookupLocationName() {
        LocationDataService mockLocationDataService = mock(LocationDataService.class);
        when(mockLocationDataService.findById(anyLong())).thenReturn(getTestLocationDTO1());

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.locationDataService = mockLocationDataService;

        assertEquals(getTestLocationDTO1().getName(), service.lookupLocationName(1));
    }

    @Test
    public void shouldLookupLocationNameWhenNull() {
        LocationDataService mockLocationDataService = mock(LocationDataService.class);
        when(mockLocationDataService.findById(anyLong())).thenReturn(null);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.locationDataService = mockLocationDataService;

        assertEquals("", service.lookupLocationName(1));
    }

    @Test
    public void shouldResolveTransactionTime() {
        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.resolveTransactionTime(tap);
        assertEquals("12:00", tap.getTapDisplay().getTransactionTime().trim());
    }

    @Test
    public void shouldResolveTransactionType() {
        PseudoTransactionTypeLookupDataService mockPseudoTransactionTypeLookupDataService = mock(PseudoTransactionTypeLookupDataService.class);
        when(mockPseudoTransactionTypeLookupDataService.findById(anyLong())).thenReturn(getTestPseudoTransactionTypeLookupDTO1());

        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.pseudoTransactionTypeLookupDataService = mockPseudoTransactionTypeLookupDataService;

        service.resolveTransactionType(tap);

        assertEquals(TRANSACTION_DISPLAY_DESCRIPTION_1, tap.getTapDisplay().getTransactionTypeDescription().trim());
    }

    @Test
    public void shouldResolveTransactionTypeWithNullTransactionType() {
        PseudoTransactionTypeLookupDataService mockPseudoTransactionTypeLookupDataService = mock(PseudoTransactionTypeLookupDataService.class);
        when(mockPseudoTransactionTypeLookupDataService.findById(anyLong())).thenReturn(null);

        JourneyDTO journey = JourneyTestUtil.getTestJourneyDTO1();
        TapDTO tap = journey.getTaps().get(0);

        ResolveTapReferencesServiceImpl service = new ResolveTapReferencesServiceImpl();
        service.pseudoTransactionTypeLookupDataService = mockPseudoTransactionTypeLookupDataService;

        service.resolveTransactionType(tap);

        assertEquals("", tap.getTapDisplay().getTransactionTypeDescription().trim());
    }

}
