package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

/**
 * ResolveJourneyReferencesService unit tests
 */

import com.novacroft.nemo.tfl.common.constant.PseudoTransactionType;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import org.junit.Test;

import static com.novacroft.nemo.test_support.DateTestUtil.*;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDTO1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyListDTO2;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.PseudoTransactionTypeTestUtil.TRANSACTION_DISPLAY_DESCRIPTION_1;
import static com.novacroft.nemo.test_support.PseudoTransactionTypeTestUtil.getTestPseudoTransactionTypeLookupDTO1;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResolveJourneyReferencesServiceImplTest {

    @Test
    public void shouldLookupLocationName() {
        LocationDataService mockLocationDataService = mock(LocationDataService.class);
        when(mockLocationDataService.findById(anyLong())).thenReturn(getTestLocationDTO1());

        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.locationDataService = mockLocationDataService;

        assertEquals(getTestLocationDTO1().getName(), service.lookupLocationName(1));
    }

    @Test
    public void lookupLocationNameShouldReturnBlank() {
        LocationDataService mockLocationDataService = mock(LocationDataService.class);
        when(mockLocationDataService.findById(anyLong())).thenReturn(null);

        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.locationDataService = mockLocationDataService;

        assertEquals("", service.lookupLocationName(1));
    }

    @Test
    public void isKnownLocationShouldReturnTrue() {
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        assertTrue(service.isKnownLocation(99));
    }

    @Test
    public void isKnownLocationShouldReturnFalseWithNull() {
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        assertFalse(service.isKnownLocation(null));
    }

    @Test
    public void isKnownLocationShouldReturnFalseWithZero() {
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        assertFalse(service.isKnownLocation(0));
    }

    @Test
    public void isKnownLocationShouldReturnFalseWithMinus1() {
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        assertFalse(service.isKnownLocation(-1));
    }

    @Test
    public void shouldResolveWarning() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setWarning(null);
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveWarning(journey);
        assertNotNull(journey.getJourneyDisplay().getWarning());
    }

    @Test
    public void shouldResolveCreditAmountWithCreditValue() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.setAddedStoredValueBalance(99);
        journey.getJourneyDisplay().setCreditAmount(null);
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveCreditAmount(journey);
        assertNotNull(journey.getJourneyDisplay().getCreditAmount());
    }

    @Test
    public void shouldResolveCreditAmountWithChargeValue() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.setAddedStoredValueBalance(-99);
        journey.getJourneyDisplay().setCreditAmount(null);
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveCreditAmount(journey);
        assertNull(journey.getJourneyDisplay().getCreditAmount());
    }

    @Test
    public void shouldResolveChargeAmountWithChargeValue() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.setAddedStoredValueBalance(-99);
        journey.getJourneyDisplay().setChargeAmount(null);
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveChargeAmount(journey);
        assertNotNull(journey.getJourneyDisplay().getChargeAmount());
    }

    @Test
    public void shouldResolveChargeAmountWithCreditValue() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.setAddedStoredValueBalance(99);
        journey.getJourneyDisplay().setChargeAmount(null);
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveChargeAmount(journey);
        assertNull(journey.getJourneyDisplay().getChargeAmount());
    }

    @Test
    public void shouldResolveJourneyDescriptionForAutoTopUp() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.setPseudoTransactionTypeId(PseudoTransactionType.TOP_UP_1.pseudoTransactionTypeId());
        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveJourneyDescription(any(JourneyDTO.class));
        when(service.getContent(anyString())).thenReturn("test-content");
        service.resolveJourneyDescription(journey);
        assertEquals("test-content Mornington Crescent", journey.getJourneyDisplay().getJourneyDescription().trim());
    }

    @Test
    public void shouldResolveJourneyDescriptionForNonAutoTopUp() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.setPseudoTransactionTypeId(PseudoTransactionType.COMPLETE_JOURNEY.pseudoTransactionTypeId());
        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveJourneyDescription(any(JourneyDTO.class));
        when(service.getContent(anyString())).thenReturn("test-content");
        service.resolveJourneyDescription(journey);
        assertEquals("Mornington Crescent test-content Piccadilly Circus",
                journey.getJourneyDisplay().getJourneyDescription().trim());
    }

    @Test
    public void shouldResolveJourneyTime() {
        JourneyDTO journey = getTestJourneyDTO1();
        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveJourneyTime(any(JourneyDTO.class));
        when(service.getContent(anyString())).thenReturn("test-content");
        service.resolveJourneyTime(journey);
        assertEquals("12:00 test-content 12:00", journey.getJourneyDisplay().getJourneyTime().trim());
    }

    @Test
    public void shouldResolveJourneyEndTime() {
        JourneyDTO journey = getTestJourneyDTO1();
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveJourneyEndTime(journey);
        assertEquals("12:00", journey.getJourneyDisplay().getJourneyEndTime().trim());
    }

    @Test
    public void shouldResolveJourneyStartTime() {
        JourneyDTO journey = getTestJourneyDTO1();
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveJourneyStartTime(journey);
        assertEquals("12:00", journey.getJourneyDisplay().getJourneyStartTime().trim());
    }

    @Test
    public void shouldResolveTransactionType() {
        PseudoTransactionTypeLookupDataService mockPseudoTransactionTypeLookupDataService =
                mock(PseudoTransactionTypeLookupDataService.class);
        when(mockPseudoTransactionTypeLookupDataService.findById(anyLong()))
                .thenReturn(getTestPseudoTransactionTypeLookupDTO1());

        JourneyDTO journey = getTestJourneyDTO1();

        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.pseudoTransactionTypeLookupDataService = mockPseudoTransactionTypeLookupDataService;

        service.resolveTransactionType(journey);

        assertEquals(TRANSACTION_DISPLAY_DESCRIPTION_1,
                journey.getJourneyDisplay().getPseudoTransactionTypeDisplayDescription().trim());
    }

    @Test
    public void shouldResolveTransactionTypeWithNullTransactionType() {
        PseudoTransactionTypeLookupDataService mockPseudoTransactionTypeLookupDataService =
                mock(PseudoTransactionTypeLookupDataService.class);
        when(mockPseudoTransactionTypeLookupDataService.findById(anyLong())).thenReturn(null);

        JourneyDTO journey = getTestJourneyDTO1();

        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.pseudoTransactionTypeLookupDataService = mockPseudoTransactionTypeLookupDataService;

        service.resolveTransactionType(journey);

        assertEquals("", journey.getJourneyDisplay().getPseudoTransactionTypeDisplayDescription().trim());
    }

    @Test
    public void shouldResolveEffectiveJourneyDateWithNullDate() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setEffectiveTrafficOn(null);
        journey.setTrafficOn(getAug23At0429());
        journey.setTransactionAt(null);
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveEffectiveJourneyDate(journey);
        assertEquals(getAug23At0000(), journey.getJourneyDisplay().getEffectiveTrafficOn());
    }

    @Test
    public void shouldResolveEffectiveJourneyDateWithDateBeforeFourThirty() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setEffectiveTrafficOn(null);
        journey.setTrafficOn(getAug23At0429());
        journey.setTransactionAt(getAug23At0429());
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveEffectiveJourneyDate(journey);
        assertEquals(getAug22At0000(), journey.getJourneyDisplay().getEffectiveTrafficOn());
    }

    @Test
    public void shouldResolveEffectiveJourneyDateWithDateAfterFourThirty() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setEffectiveTrafficOn(null);
        journey.setTrafficOn(getAug23At0431());
        journey.setTransactionAt(getAug23At0431());
        ResolveJourneyReferencesServiceImpl service = new ResolveJourneyReferencesServiceImpl();
        service.resolveEffectiveJourneyDate(journey);
        assertEquals(getAug23At0000(), journey.getJourneyDisplay().getEffectiveTrafficOn());
    }

    @Test
    public void shouldResolveExitLocationWithKnownLocation() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setExitLocationName(null);

        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveExitLocation(any(JourneyDTO.class));
        when(service.isKnownLocation(anyInt())).thenReturn(Boolean.TRUE);
        when(service.lookupLocationName(anyInt())).thenReturn(LOCATION_NAME_1);

        service.resolveExitLocation(journey);

        assertEquals(LOCATION_NAME_1, journey.getJourneyDisplay().getExitLocationName());
    }

    @Test
    public void shouldResolveExitLocationWithUnknownLocation() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setExitLocationName(null);

        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveExitLocation(any(JourneyDTO.class));
        when(service.isKnownLocation(anyInt())).thenReturn(Boolean.FALSE);
        when(service.lookupLocationName(anyInt())).thenReturn(LOCATION_NAME_1);

        service.resolveExitLocation(journey);

        assertNull(journey.getJourneyDisplay().getExitLocationName());
    }

    @Test
    public void shouldResolveTransactionLocationWithKnownLocation() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setTransactionLocationName(null);

        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveTransactionLocation(any(JourneyDTO.class));
        when(service.isKnownLocation(anyInt())).thenReturn(Boolean.TRUE);
        when(service.lookupLocationName(anyInt())).thenReturn(LOCATION_NAME_1);

        service.resolveTransactionLocation(journey);

        assertEquals(LOCATION_NAME_1, journey.getJourneyDisplay().getTransactionLocationName());
    }

    @Test
    public void shouldResolveTransactionLocationWithUnknownLocation() {
        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setTransactionLocationName(null);

        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveTransactionLocation(any(JourneyDTO.class));
        when(service.isKnownLocation(anyInt())).thenReturn(Boolean.FALSE);
        when(service.lookupLocationName(anyInt())).thenReturn(LOCATION_NAME_1);

        service.resolveTransactionLocation(journey);

        assertNull(journey.getJourneyDisplay().getTransactionLocationName());
    }

    @Test
    public void shouldResolveJourneyReferences() {
        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveJourneyReferences(any(JourneyDTO.class));

        doNothing().when(service).resolveTransactionLocation(any(JourneyDTO.class));
        doNothing().when(service).resolveExitLocation(any(JourneyDTO.class));
        doNothing().when(service).resolveEffectiveJourneyDate(any(JourneyDTO.class));
        doNothing().when(service).resolveTransactionType(any(JourneyDTO.class));
        doNothing().when(service).resolveJourneyStartTime(any(JourneyDTO.class));
        doNothing().when(service).resolveJourneyEndTime(any(JourneyDTO.class));
        doNothing().when(service).resolveJourneyTime(any(JourneyDTO.class));
        doNothing().when(service).resolveJourneyDescription(any(JourneyDTO.class));
        doNothing().when(service).resolveChargeAmount(any(JourneyDTO.class));
        doNothing().when(service).resolveCreditAmount(any(JourneyDTO.class));
        doNothing().when(service).resolveWarning(any(JourneyDTO.class));

        service.resolveJourneyReferences(getTestJourneyDTO1());

        verify(service).resolveTransactionLocation(any(JourneyDTO.class));
        verify(service).resolveExitLocation(any(JourneyDTO.class));
        verify(service).resolveEffectiveJourneyDate(any(JourneyDTO.class));
        verify(service).resolveTransactionType(any(JourneyDTO.class));
        verify(service).resolveJourneyStartTime(any(JourneyDTO.class));
        verify(service).resolveJourneyEndTime(any(JourneyDTO.class));
        verify(service).resolveJourneyTime(any(JourneyDTO.class));
        verify(service).resolveJourneyDescription(any(JourneyDTO.class));
        verify(service).resolveChargeAmount(any(JourneyDTO.class));
        verify(service).resolveCreditAmount(any(JourneyDTO.class));
        verify(service).resolveWarning(any(JourneyDTO.class));
    }

    @Test
    public void shouldResolveReferences() {
        ResolveJourneyReferencesServiceImpl service = mock(ResolveJourneyReferencesServiceImpl.class);
        doCallRealMethod().when(service).resolveReferences(anyList());
        doNothing().when(service).resolveJourneyReferences(any(JourneyDTO.class));

        service.resolveReferences(getTestJourneyListDTO2());

        verify(service, times(getTestJourneyListDTO2().size())).resolveJourneyReferences(any(JourneyDTO.class));
    }
}
