package com.novacroft.nemo.tfl.common.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.OddPeriodDurationMonths;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;

public class DurationUtilTest {
	
    protected static final Date date_01_05_2014 = new Date(1398916800000l);
    protected static final Date date_03_06_2014 = new Date(1401768000000l);
    protected static final Date date_03_07_2014 = new Date(1404360000000l);
    protected static final Date date_03_08_2014 = new Date(1407038400000l);
    protected static final Date date_03_09_2014 = new Date(1409716800000l);
    protected static final Date date_03_10_2014 = new Date(1412308800000l);
    protected static final Date date_03_11_2014 = new Date(1414990800000l);
    protected static final Date date_03_12_2014 = new Date(1417582800000l);
    protected static final Date date_03_01_2015 = new Date(1420261200000l);
    protected static final Date date_03_02_2015 = new Date(1422939600000l);
    protected static final Date date_03_03_2015 = new Date(1425358800000l);
    protected static final Date date_03_04_2015 = new Date(1428033600000l);

    protected static final Date START_DATE_MONTH = DateUtil.formatStringAsDate("01/01/2014");
    protected static final Date END_DATE_MONTH = DateUtil.formatStringAsDate("31/01/2014");
    protected static final Date START_DATE_SEVEN_DAYS = DateUtil.formatStringAsDate("01/01/2014");
    protected static final Date END_DATE_SEVEN_DAYS = DateUtil.formatStringAsDate("07/01/2014");
    protected static final Date START_DATE_OTHER = DateUtil.formatStringAsDate("01/01/2014");
    protected static final Date END_DATE_OTHER = DateUtil.formatStringAsDate("17/01/2014");
    protected static final Date END_DATE_OTHER_MONTH = DateUtil.formatStringAsDate("02/02/2014");
    protected static final Date START_DATE_THREE_MONTH = DateUtil.formatStringAsDate("01/01/2014");
    protected static final Date END_DATE_THREE_MONTH = DateUtil.formatStringAsDate("31/03/2014");
    protected static final Date START_DATE_SIX_MONTH = DateUtil.formatStringAsDate("01/01/2014");
    protected static final Date END_DATE_SIX_MONTH = DateUtil.formatStringAsDate("30/06/2014");
    protected static final Date START_DATE_ANNUAL = DateUtil.formatStringAsDate("01/01/2014");
    protected static final Date END_DATE_ANNUAL = DateUtil.formatStringAsDate("31/12/2014");
	
	@Test
	public void getDurationForOddPeriodTicketBetweenOneAndTwoMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_06_2014);
		assertEquals(OddPeriodDurationMonths.ONE_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.TWO_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenTwoAndThreeMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_07_2014);
		assertEquals(OddPeriodDurationMonths.TWO_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.THREE_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenThreeAndFourMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_08_2014);
		assertEquals(OddPeriodDurationMonths.THREE_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.FOUR_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenFourAndFiveMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_09_2014);
		assertEquals(OddPeriodDurationMonths.FOUR_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.FIVE_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenFiveAndSixMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_10_2014);
		assertEquals(OddPeriodDurationMonths.FIVE_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.SIX_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	
	@Test
	public void getDurationForOddPeriodTicketBetweenSixAndSevenMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_11_2014);
		assertEquals(OddPeriodDurationMonths.SIX_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.SEVEN_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenSevenAndEightMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_12_2014);
		assertEquals(OddPeriodDurationMonths.SEVEN_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.EIGHT_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenEightAndNineMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_01_2015);
		assertEquals(OddPeriodDurationMonths.EIGHT_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.NINE_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenNineAndTenMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_02_2015);
		assertEquals(OddPeriodDurationMonths.NINE_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.TEN_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenTenAndElevenMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_03_2015);
		assertEquals(OddPeriodDurationMonths.TEN_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.ELEVEN_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
	@Test
	public void getDurationForOddPeriodTicketBetweenElevenAndTwelveMonths() {
		DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(date_01_05_2014, date_03_04_2015);
		assertEquals(OddPeriodDurationMonths.ELEVEN_MONTH.getCode(), durationPeriod.getFromDurationCode());
		assertEquals(OddPeriodDurationMonths.TWELVE_MONTH.getCode(), durationPeriod.getToDurationCode());
	}
	
}

