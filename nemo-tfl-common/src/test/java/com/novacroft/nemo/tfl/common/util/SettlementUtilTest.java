package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * SettlementUtil unit tests
 */
public class SettlementUtilTest {
    private AutoLoadChangeSettlementDTO mockAutoLoadChangeSettlementDTO;

    @Before
    public void setUp() {
        this.mockAutoLoadChangeSettlementDTO = mock(AutoLoadChangeSettlementDTO.class);
    }

    @Test
    public void isAutoLoadOnForStateShouldReturnTrue() {
        assertTrue(SettlementUtil.isAutoLoadOn(AutoLoadState.TOP_UP_AMOUNT_2.state()));
    }

    @Test
    public void isAutoLoadOnForStateShouldReturnFalse() {
        assertFalse(SettlementUtil.isAutoLoadOn(AutoLoadState.NO_TOP_UP.state()));
    }

    @Test
    public void isAutoLoadOnForStateShouldReturnFalseForNull() {
        assertFalse(SettlementUtil.isAutoLoadOn((Integer) null));
    }

    @Test
    public void isAutoLoadOnForDtoShouldReturnTrue() {
        when(this.mockAutoLoadChangeSettlementDTO.getAutoLoadState()).thenReturn(AutoLoadState.TOP_UP_AMOUNT_2.state());
        assertTrue(SettlementUtil.isAutoLoadOn(this.mockAutoLoadChangeSettlementDTO));
    }

    @Test
    public void isAutoLoadOnForDtoShouldReturnFalse() {
        when(this.mockAutoLoadChangeSettlementDTO.getAutoLoadState()).thenReturn(AutoLoadState.NO_TOP_UP.state());
        assertFalse(SettlementUtil.isAutoLoadOn(this.mockAutoLoadChangeSettlementDTO));
    }

    @Test
    public void isAutoLoadOnForDtoShouldReturnFalseForNull() {
        assertFalse(SettlementUtil.isAutoLoadOn((AutoLoadChangeSettlementDTO) null));
    }

    @Test
    public void isRequestedShouldReturnTrue() {
        when(this.mockAutoLoadChangeSettlementDTO.getStatus()).thenReturn(SettlementStatus.REQUESTED.code());
        assertTrue(SettlementUtil.isRequested(this.mockAutoLoadChangeSettlementDTO));
    }

    @Test
    public void isRequestedShouldReturnFalseWithNullDto() {
        assertFalse(SettlementUtil.isRequested(null));
    }

    @Test
    public void isRequestedShouldReturnFalseWithNullStatus() {
        when(this.mockAutoLoadChangeSettlementDTO.getStatus()).thenReturn(null);
        assertFalse(SettlementUtil.isRequested(this.mockAutoLoadChangeSettlementDTO));
    }

    @Test
    public void isRequestedShouldReturnFalseWithNotRequested() {
        when(this.mockAutoLoadChangeSettlementDTO.getStatus()).thenReturn(SettlementStatus.COMPLETE.code());
        assertFalse(SettlementUtil.isRequested(this.mockAutoLoadChangeSettlementDTO));
    }
}
