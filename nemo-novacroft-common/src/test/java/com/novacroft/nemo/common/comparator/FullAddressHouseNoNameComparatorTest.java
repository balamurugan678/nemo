package com.novacroft.nemo.common.comparator;


import static com.novacroft.nemo.test_support.CommonAddressTestUtil.getTestCommonAddressDTO1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.getTestCommonAddressDTO1A;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.getTestCommonAddressDTO2;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.getTestCommonAddressDTOABC;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.getTestCommonAddressDTOBCD;
import static com.novacroft.nemo.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.common.constant.ComparatorConstant.SORT_BEFORE;
import static com.novacroft.nemo.common.constant.ComparatorConstant.SORT_EQUALS;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.novacroft.nemo.common.comparator.FullAddressHouseNoNameComparator;

/**
 * FullAddressHouseNoNameComparator unit tests
 */
public class FullAddressHouseNoNameComparatorTest {

    @Test
    public void shouldSortBeforeByHouseNoName1And2() {
    	FullAddressHouseNoNameComparator comparator = new FullAddressHouseNoNameComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getTestCommonAddressDTO1(), getTestCommonAddressDTO2()));
    }

    @Test
    public void shouldSortBeforeByHouseNoName1And1A() {
    	FullAddressHouseNoNameComparator comparator = new FullAddressHouseNoNameComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getTestCommonAddressDTO1(), getTestCommonAddressDTO1A()));
    }
    
    @Test
    public void shouldSortAfterByHouseNoName1AAnd1() {
    	FullAddressHouseNoNameComparator comparator = new FullAddressHouseNoNameComparator();
        assertEquals(SORT_AFTER, comparator.compare(getTestCommonAddressDTO1A(), getTestCommonAddressDTO1()));
    }
    
    @Test
    public void shouldSortAfterHouseNoName2And1() {
    	FullAddressHouseNoNameComparator comparator = new FullAddressHouseNoNameComparator();
        assertEquals(SORT_AFTER, comparator.compare(getTestCommonAddressDTO2(), getTestCommonAddressDTO1()));
    }

    @Test
    public void shouldSortEqualHouseNoName1And1() {
    	FullAddressHouseNoNameComparator comparator = new FullAddressHouseNoNameComparator();
        assertEquals(SORT_EQUALS, comparator.compare(getTestCommonAddressDTO1(), getTestCommonAddressDTO1()));
    }

    @Test
    public void shouldSortBeforeByHouseNoNameABCAndBCD() {
    	FullAddressHouseNoNameComparator comparator = new FullAddressHouseNoNameComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getTestCommonAddressDTOABC(), getTestCommonAddressDTOBCD()));
    }


}