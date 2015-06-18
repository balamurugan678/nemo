package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.EXPIRED_DATE_10_10_2014;
import static com.novacroft.nemo.test_support.DateTestUtil.FUTURE_DATE_10_10_9999;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.OysterCardDiscountType;
import com.novacroft.nemo.tfl.common.constant.OysterCardPassengerType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public class LostOrStolenEligibilityServiceImplTest {

    private LostOrStolenEligibilityServiceImpl service;
    private GetCardService mockGetCardService;
    private CardDataService mockCardDataService;

    private CardDTO mockCardDTO;
    private CardInfoResponseV2DTO mockCardInfoResponseV2DTO;

    @Before
    public void setUp() {
        this.service = mock(LostOrStolenEligibilityServiceImpl.class);

        this.mockGetCardService = mock(GetCardService.class);
        this.service.getCardService = this.mockGetCardService;

        this.mockCardDataService = mock(CardDataService.class);
        this.service.cardDataService = this.mockCardDataService;

        this.mockCardDTO = mock(CardDTO.class);
        this.mockCardInfoResponseV2DTO = mock(CardInfoResponseV2DTO.class);
    }

    @Test
    public void isCardEligibleToBeReportedLostOrStolenShouldReturnTrue() {
        when(this.service.isCardEligibleToBeReportedLostOrStolen(anyLong())).thenCallRealMethod();
        when(this.mockCardDataService.findById(anyLong())).thenReturn(this.mockCardDTO);
        when(this.mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(this.mockGetCardService.getCard(anyString())).thenReturn(this.mockCardInfoResponseV2DTO);
        when(this.service.isEligible(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);

        assertTrue(this.service.isCardEligibleToBeReportedLostOrStolen(CARD_ID_1));

        verify(this.mockCardDataService).findById(anyLong());
        verify(this.mockCardDTO).getCardNumber();
        verify(this.mockGetCardService).getCard(anyString());
        verify(this.service).isEligible(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isCardEligibleToBeReportedLostOrStolenShouldReturnFalse() {
        when(this.service.isCardEligibleToBeReportedLostOrStolen(anyLong())).thenCallRealMethod();
        when(this.mockCardDataService.findById(anyLong())).thenReturn(this.mockCardDTO);
        when(this.mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(this.mockGetCardService.getCard(anyString())).thenReturn(this.mockCardInfoResponseV2DTO);
        when(this.service.isEligible(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);

        assertFalse(this.service.isCardEligibleToBeReportedLostOrStolen(CARD_ID_1));

        verify(this.mockCardDataService).findById(anyLong());
        verify(this.mockCardDTO).getCardNumber();
        verify(this.mockGetCardService).getCard(anyString());
        verify(this.service).isEligible(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isEligibleShouldReturnTrue() {
        when(this.service.isEligible(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isAdultCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(this.service.isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(this.service.isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);

        assertTrue(this.service.isEligible(this.mockCardInfoResponseV2DTO));

        verify(this.service).isAdultCard(any(CardInfoResponseV2DTO.class));
        verify(this.service).isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class));
        verify(this.service).isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isEligibleShouldReturnFalseWithNotAdult() {
        when(this.service.isEligible(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isAdultCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);
        when(this.service.isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(this.service.isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);

        assertFalse(this.service.isEligible(this.mockCardInfoResponseV2DTO));

        verify(this.service).isAdultCard(any(CardInfoResponseV2DTO.class));
        verify(this.service, never()).isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class));
        verify(this.service, never()).isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isEligibleShouldReturnFalseWithEighteenPlus() {
        when(this.service.isEligible(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isAdultCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(this.service.isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);
        when(this.service.isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);

        assertFalse(this.service.isEligible(this.mockCardInfoResponseV2DTO));

        verify(this.service).isAdultCard(any(CardInfoResponseV2DTO.class));
        verify(this.service).isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class));
        verify(this.service, never()).isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isEligibleShouldReturnFalseWithApprentice() {
        when(this.service.isEligible(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isAdultCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(this.service.isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(this.service.isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);

        assertFalse(this.service.isEligible(this.mockCardInfoResponseV2DTO));

        verify(this.service).isAdultCard(any(CardInfoResponseV2DTO.class));
        verify(this.service).isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class));
        verify(this.service).isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isAdultCardShouldReturnTrue() {
        when(this.service.isAdultCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.mockCardInfoResponseV2DTO.getPassengerType()).thenReturn(OysterCardPassengerType.ADULT.code());
        assertTrue(this.service.isAdultCard(this.mockCardInfoResponseV2DTO));
        verify(this.mockCardInfoResponseV2DTO).getPassengerType();
    }

    @Test
    public void isAdultCardShouldReturnFalse() {
        when(this.service.isAdultCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.mockCardInfoResponseV2DTO.getPassengerType()).thenReturn(EMPTY);
        assertFalse(this.service.isAdultCard(this.mockCardInfoResponseV2DTO));
        verify(this.mockCardInfoResponseV2DTO).getPassengerType();
    }

    @Test
    public void isDiscountOnCardShouldReturnFalse() {
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);

        assertFalse(this.service.isDiscountOnCard(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));

        verify(this.service).isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service).isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service).isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountOnCardShouldReturnTrueForEntitlement1() {
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlementExpired(anyString())).thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);

        assertTrue(this.service.isDiscountOnCard(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));

        verify(this.service).isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service, never())
                .isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service, never())
                .isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountOnCardShouldReturnTrueForEntitlement2() {
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlementExpired(anyString())).thenReturn(Boolean.TRUE);
        assertTrue(this.service.isDiscountOnCard(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));

        verify(this.service).isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service).isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service, never())
                .isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountOnCardShouldReturnTrueForEntitlement3() {
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        when(this.service.isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlementExpired(anyString())).thenReturn(Boolean.TRUE);
        assertTrue(this.service.isDiscountOnCard(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));

        verify(this.service).isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service).isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
        verify(this.service).isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isEighteenPlusDiscountOnCardShouldReturnTrue() {
        when(this.service.isEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.service.isEighteenPlusDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isEighteenPlusDiscountOnCardShouldReturnFalse() {
        when(this.service.isEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.service.isEighteenPlusDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isNotEighteenPlusDiscountOnCardShouldReturnTrue() {
        when(this.service.isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);
        assertTrue(this.service.isNotEighteenPlusDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isNotEighteenPlusDiscountOnCardShouldReturnFalse() {
        when(this.service.isNotEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.service.isNotEighteenPlusDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isEighteenPlusDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isApprenticeDiscountOnCardShouldReturnTrue() {
        when(this.service.isApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.service.isApprenticeDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isApprenticeDiscountOnCardShouldReturnFalse() {
        when(this.service.isApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.service.isApprenticeDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isDiscountOnCard(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class));
    }

    @Test
    public void isNotApprenticeDiscountOnCardShouldReturnTrue() {
        when(this.service.isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);
        assertTrue(this.service.isNotApprenticeDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isNotApprenticeDiscountOnCardShouldReturnFalse() {
        when(this.service.isNotApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(this.service.isApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.service.isNotApprenticeDiscountOnCard(this.mockCardInfoResponseV2DTO));
        verify(this.service).isApprenticeDiscountOnCard(any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void isDiscountEntitlement1ShouldReturnTrue() {
        when(this.service.isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlementExpired(anyString())).thenReturn(Boolean.TRUE);
        assertTrue(this.service.isDiscountEntitlement1(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));
        verify(this.service).isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountEntitlement1ShouldReturnFalse() {
        when(this.service.isDiscountEntitlement1(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenReturn(Boolean.FALSE);
        assertFalse(this.service.isDiscountEntitlement1(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));
        verify(this.service).isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountEntitlement2ShouldReturnTrue() {
        when(this.service.isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlementExpired(anyString())).thenReturn(Boolean.TRUE);
        assertTrue(this.service.isDiscountEntitlement2(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));
        verify(this.service).isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountEntitlement2ShouldReturnFalse() {
        when(this.service.isDiscountEntitlement2(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenReturn(Boolean.FALSE);
        assertFalse(this.service.isDiscountEntitlement2(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));
        verify(this.service).isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountEntitlement3ShouldReturnTrue() {
        when(this.service.isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenReturn(Boolean.TRUE);
        when(this.service.isDiscountEntitlementExpired(anyString())).thenReturn(Boolean.TRUE);
        assertTrue(this.service.isDiscountEntitlement3(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));
        verify(this.service).isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountEntitlement3ShouldReturnFalse() {
        when(this.service.isDiscountEntitlement3(any(CardInfoResponseV2DTO.class), any(OysterCardDiscountType.class)))
                .thenCallRealMethod();
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenReturn(Boolean.FALSE);
        assertFalse(this.service.isDiscountEntitlement3(this.mockCardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE));
        verify(this.service).isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class));
    }

    @Test
    public void isDiscountEntitlementShouldReturnTrue() {
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenCallRealMethod();
        assertTrue(this.service
                .isDiscountEntitlement(OysterCardDiscountType.APPRENTICE.code(), OysterCardDiscountType.APPRENTICE));
    }

    @Test
    public void isDiscountEntitlementShouldReturnFalse() {
        when(this.service.isDiscountEntitlement(anyString(), any(OysterCardDiscountType.class))).thenCallRealMethod();
        assertFalse(this.service.isDiscountEntitlement(EMPTY, OysterCardDiscountType.APPRENTICE));
    }
    
    @Test
    public void isDiscountEntitlementExpired() {
        when(this.service.isDiscountEntitlementExpired(anyString())).thenCallRealMethod();
        assertFalse(this.service.isDiscountEntitlementExpired(EXPIRED_DATE_10_10_2014));
    }
    
    @Test
    public void isDiscountEntitlementNotExpired() {
        when(this.service.isDiscountEntitlementExpired(anyString())).thenCallRealMethod();
        assertTrue(this.service.isDiscountEntitlementExpired(FUTURE_DATE_10_10_9999));
    }
    
    @Test
    public void isDiscountEntitlementExpiredForEMPTY() {
        when(this.service.isDiscountEntitlementExpired(anyString())).thenCallRealMethod();
        assertFalse(this.service.isDiscountEntitlementExpired(StringUtils.EMPTY));
    }
}