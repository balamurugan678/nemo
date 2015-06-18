package com.novacroft.nemo.tfl.common.util;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.OddPeriodDurationMonths;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;

public final class DurationUtil {

	public static DurationPeriodDTO getDurationForOddPeriod(Date startDate, Date endDate){
		Integer durationInMonths = DateUtil.getDateDiffInMonths(startDate, endDate);
		DurationPeriodDTO durationPeriod; 
		switch (durationInMonths) {
		case 1:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.ONE_MONTH.getCode(),OddPeriodDurationMonths.TWO_MONTH.getCode());
			break;
		case 2:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.TWO_MONTH.getCode(),OddPeriodDurationMonths.THREE_MONTH.getCode());
			break;
		case 3:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.THREE_MONTH.getCode(),OddPeriodDurationMonths.FOUR_MONTH.getCode());
			break;
		case 4:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.FOUR_MONTH.getCode(),OddPeriodDurationMonths.FIVE_MONTH.getCode());
			break;
		case 5:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.FIVE_MONTH.getCode(),OddPeriodDurationMonths.SIX_MONTH.getCode());
			break;
		case 6:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.SIX_MONTH.getCode(),OddPeriodDurationMonths.SEVEN_MONTH.getCode());
			break;
		case 7:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.SEVEN_MONTH.getCode(),OddPeriodDurationMonths.EIGHT_MONTH.getCode());
			break;
		case 8:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.EIGHT_MONTH.getCode(),OddPeriodDurationMonths.NINE_MONTH.getCode());
			break;
		case 9:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.NINE_MONTH.getCode(),OddPeriodDurationMonths.TEN_MONTH.getCode());
			break;
		case 10:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.TEN_MONTH.getCode(),OddPeriodDurationMonths.ELEVEN_MONTH.getCode());
			break;
		case 11:
			durationPeriod = new DurationPeriodDTO(OddPeriodDurationMonths.ELEVEN_MONTH.getCode(),OddPeriodDurationMonths.TWELVE_MONTH.getCode());
			break;
		default:
			durationPeriod = null;
			break;
		}
		return durationPeriod;
	}
	
	private DurationUtil() {
	}

}
