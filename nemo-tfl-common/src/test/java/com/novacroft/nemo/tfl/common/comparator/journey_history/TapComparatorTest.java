package com.novacroft.nemo.tfl.common.comparator.journey_history;

import static com.novacroft.nemo.test_support.DateTestUtil.AUG_22_AT_1723;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_22_AT_1804;
import static com.novacroft.nemo.test_support.DateTestUtil.DATE_FORMAT;
import static com.novacroft.nemo.test_support.DateTestUtil.getDate;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_BEFORE;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_EQUALS;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;

@RunWith(Parameterized.class)
public class TapComparatorTest {
    private static final Integer SEQUENCE_NUMBER_INT_1 = 1;
    private static final Integer SEQUENCE_NUMBER_INT_2 = 2;
    
    private String transactionDate1;
    private String transactionDate2;
    private Integer sequenceNumber1;
    private Integer sequenceNumber2;
    private int expectedCompareResult;
    
    public TapComparatorTest(String transactionDate1, String transactionDate2,
                    Integer sequenceNumber1, Integer sequenceNumber2, int expectedCompareResult) {
        this.transactionDate1 = transactionDate1;
        this.transactionDate2 = transactionDate2;
        this.sequenceNumber1 = sequenceNumber1;
        this.sequenceNumber2 = sequenceNumber2;
        this.expectedCompareResult = expectedCompareResult;
    }
    
    @Parameters
    public static Collection<?> parameterizedTestData() {
        return Arrays.asList(new Object[][] {
            {AUG_22_AT_1723, AUG_22_AT_1804, null, null, SORT_BEFORE},
            {AUG_22_AT_1804, AUG_22_AT_1723, null, null, SORT_AFTER},
            
            {AUG_22_AT_1723, AUG_22_AT_1723, SEQUENCE_NUMBER_INT_1, SEQUENCE_NUMBER_INT_2, SORT_BEFORE},
            {AUG_22_AT_1723, AUG_22_AT_1723, SEQUENCE_NUMBER_INT_2, SEQUENCE_NUMBER_INT_1, SORT_AFTER},
            
            {AUG_22_AT_1723, AUG_22_AT_1723, SEQUENCE_NUMBER_INT_1, SEQUENCE_NUMBER_INT_1, SORT_EQUALS}
        });
    }
    
    @Test
    public void testTapComparator() {
        TapDTO tapDTO1 = getTestTap(transactionDate1, sequenceNumber1);
        TapDTO tapDTO2 = getTestTap(transactionDate2, sequenceNumber2);
        TapComparator comparator = new TapComparator();
        assertEquals(expectedCompareResult, 
                        comparator.compare(tapDTO1, tapDTO2));
    }

    private TapDTO getTestTap(String transactionAt, Integer sequenceNumber) {
        TapDTO tapDTO = new TapDTO();
        tapDTO.setTransactionAt(getDate(DATE_FORMAT, transactionAt));
        tapDTO.setSequenceNumber(sequenceNumber);
        return tapDTO;
    }
}
