package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd3;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd4;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd5;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndDate1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndZone1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndZone2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndZone3;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardInBetweenZone1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardInBetweenZone2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardSame1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardStartDate1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardStartZone1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardStartZone2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardSurrounding;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardSurrounding2;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@RunWith(Parameterized.class)
public class ZoneServiceImplOverlapZoneAndDateTest {

    private ZoneServiceImpl service;
    private CartItemCmdImpl cartItemCmd;
    private ProductItemDTO productItemDTO;
    private boolean isSurroundingOverlap;
    private boolean isSameZoneOverlap;
    private boolean isStartZoneOverlap;
    private boolean isEndZoneOverlap;
    private boolean isInBetweenZoneOverlap;

    private boolean isSurroundingDateOverlap;
    private boolean isStartDateOverlap;
    private boolean isEndDateOverlap;
    private boolean isInBetweenDateOverlap;
    

    @Before
    public void setup() {
        service = new ZoneServiceImpl();
    }

    public ZoneServiceImplOverlapZoneAndDateTest(String testName, CartItemCmdImpl cartItemCmd, ProductItemDTO productItemDTO, boolean isSurroundingOverlap,
                    boolean isSameOverlap, boolean isStartZoneOverlap, boolean isEndZoneOverlap, boolean isInBetweenOverlap,
                    boolean isSurroundingDateOverlap, boolean isStartDateOverlap, boolean isEndDateOverlap, boolean isInBetweenDateOverlap) {
        this.cartItemCmd = cartItemCmd;
        this.productItemDTO = productItemDTO;
        this.isSurroundingOverlap = isSurroundingOverlap;
        this.isSameZoneOverlap = isSameOverlap;
        this.isStartZoneOverlap = isStartZoneOverlap;
        this.isEndZoneOverlap = isEndZoneOverlap;
        this.isInBetweenZoneOverlap = isInBetweenOverlap;

        this.isSurroundingDateOverlap = isSurroundingDateOverlap;
        this.isStartDateOverlap = isStartDateOverlap;
        this.isEndDateOverlap = isEndDateOverlap;
        this.isInBetweenDateOverlap = isInBetweenDateOverlap;

    }

    @Parameters(name = "{index}: {0}")
    public static Collection<?> parameterisedTestDate() {
        return Arrays.asList(new Object[][] {
                        { "isSurroundingZoneOverlap1", getCartItemCmd1(), getTravelcardSurrounding(), true, false, false, false, false, false, true, false, false},
                        { "isSurroundingZoneOverlap2", getCartItemCmd2(), getTravelcardSurrounding(), true, false, false, true, false, false, true, false, false},
                        { "isSurroundingZoneOverlap3", getCartItemCmd3(), getTravelcardSurrounding(), true, false, true, false, false, false, false, true, false},
                        { "isSurroundingZoneOverlap4", getCartItemCmd5(), getTravelcardSurrounding(), true, false, true, false, false, true, true, true, true},
                        { "isNotSurroundingZoneOverlap1", getCartItemCmd4(), getTravelcardSurrounding(), false, false, false, false, false, true, true, true, true },
                        { "isNotSurroundingZoneOverlap2", getCartItemCmd2(), getTravelcardSurrounding2(), false, false, false, true, false, false, true, false, false},
                        { "isSameZoneOverlap1", getCartItemCmd1(), getTravelcardSame1(), true, true, true, true, true, false, true, false, false},
                        { "isNotSameZoneOverlap", getCartItemCmd5(), getTravelcardSame1(), false, false, true, false, true, true, true, true, true},
                        { "isStartZoneOverlap1", getCartItemCmd1(), getTravelcardStartZone1(), false, false, true, false, false, false, true, false, false},
                        { "isStartZoneOverlap2", getCartItemCmd1(), getTravelcardStartZone2(), false, false, true, false, false, false, true, false, false },
                        { "isEndZoneOverlap1", getCartItemCmd1(), getTravelcardEndZone1(), false, false, false, true, false, false, false, false, true },
                        { "isEndZoneOverlap2", getCartItemCmd1(), getTravelcardEndZone2(), false, false, false, true, false, true, false, false, false },
                        { "isNotEndZoneOverlap1", getCartItemCmd2(), getTravelcardEndZone3(), false, false, false, false, false, true, false, false, false },
                        { "isInBetweenZoneOverlap1", getCartItemCmd1(), getTravelcardInBetweenZone1(), false, false, false, false, true, false, true, false, false },
                        { "isInBetweenZoneOverlap2", getCartItemCmd2(), getTravelcardInBetweenZone2(), false, false, false, false, true, true, false, false, false },
                        { "isNotStartDateOverlap1", getCartItemCmd1(), getTravelcardStartDate1(), false, false, false, false, true, false, false, false, false },
                        { "isNotEndDateOverlap1", getCartItemCmd1(), getTravelcardEndDate1(), false, false, false, false, true, false, false, false, false} });
    }

    @Test
    public void isSameOverlap() {
        assertEquals(this.isSameZoneOverlap, service.isSameZoneOverlap(this.cartItemCmd.getStartZone(), this.cartItemCmd.getEndZone(),
                        productItemDTO.getStartZone(), productItemDTO.getEndZone()));
    }

    @Test
    public void isSurroundingOverlap() {
        assertEquals(this.isSurroundingOverlap,
                        service.isSurroundingZoneOverlap(this.cartItemCmd.getStartZone(), this.cartItemCmd.getEndZone(),
                                        productItemDTO.getStartZone(), productItemDTO.getEndZone()));
    }

    @Test
    public void isStartZoneOverlap() {
        assertEquals(this.isStartZoneOverlap, service.isStartZoneOverlap(cartItemCmd.getStartZone(), cartItemCmd.getEndZone(),
                        productItemDTO.getStartZone(), productItemDTO.getEndZone()));
    }

    @Test
    public void isEndZoneOverlap() {
        assertEquals(this.isEndZoneOverlap, service.isEndZoneOverlap(this.cartItemCmd.getStartZone(), this.cartItemCmd.getEndZone(),
                        this.productItemDTO.getStartZone(), this.productItemDTO.getEndZone()));
    }

    @Test
    public void isInBetweenZoneOverlap() {
        assertEquals(this.isInBetweenZoneOverlap,
                        service.isInBetweenZoneOverlap(this.cartItemCmd.getStartZone(), this.cartItemCmd.getEndZone(),
                                        this.productItemDTO.getStartZone(), this.productItemDTO.getEndZone()));
    }

    @Test
    public void isSurroundingDatesOverlap() {
        assertEquals(this.isSurroundingDateOverlap, service.isSurroundingDateOverlap(parse(this.cartItemCmd.getStartDate()),
                        parse(this.cartItemCmd.getEndDate()), this.productItemDTO.getStartDate(), this.productItemDTO.getEndDate()));
    }

    @Test
    public void isStartDateOverlap() {
        assertEquals(this.isStartDateOverlap,
                        service.isStartDateOverlap(parse(this.cartItemCmd.getStartDate()), parse(this.cartItemCmd.getEndDate()),
                                        this.productItemDTO.getStartDate(), this.productItemDTO.getEndDate()));
    }

    @Test
    public void isEndDateOverlap() {
        assertEquals(this.isEndDateOverlap,
                        service.isEndDateOverlap(parse(this.cartItemCmd.getStartDate()), parse(this.cartItemCmd.getEndDate()),
                                        this.productItemDTO.getStartDate(), this.productItemDTO.getEndDate()));
    }

    @Test
    public void isInBetweenDateOverlap() {
        assertEquals(this.isInBetweenDateOverlap, service.isInBetweenDateOverlap(parse(this.cartItemCmd.getStartDate()),
                        parse(this.cartItemCmd.getEndDate()), this.productItemDTO.getStartDate(), this.productItemDTO.getEndDate()));
    }

    

}
