package com.novacroft.nemo.tfl.common.constant;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * AutoLoadState unit tests
 */
public class AutoLoadStateTest {
    @Test(expected = IllegalArgumentException.class)
    public void lookUpStateShouldError() {
        AutoLoadState.lookUpState(99);
    }

    @Test
    public void shouldLookUpState() {
        assertEquals((int) 2, (int) AutoLoadState.lookUpState(2000));
    }

}
