package com.novacroft.nemo.tfl.batch.application_service.impl.product_fare_loader;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.ProductItemType.BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.ProductItemType.TRAVEL_CARD;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.batch.application_service.product_fare_loader.PrePaidTicketRecordService;
import com.novacroft.nemo.tfl.batch.constant.PassengerType;
import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;

@Service("prePaidTicketRecordService")
public class PrePaidTicketRecordServiceImpl implements PrePaidTicketRecordService {

    private static final String ODD_PERIOD_SEPARATION_HYPHEN = "-";
    private static final String GREEDY_SPACE_MATCHER_PATTERN = "\\s+";
    private static final String MONTHS = "months";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    public static final int PPT_PRODUCT_ADHOC_CODE_INDEX = 0;
    public static final int PPT_PRODUCT_DESCRIPTION_INDEX = 1;
    public static final int PPT_PRODUCT_EFFECTIVE_DATE_INDEX = 2;
    public static final int PPT_PRODUCT_DISCOUNT_TYPE_INDEX = 3;
    public static final int PPT_PRODUCT_PASSENGER_TYPE_CODE_INDEX = 4;
    public static final int PPT_PRODUCT_ZONE1_INDEX = 6;
    public static final int PPT_PRODUCT_ZONE2_INDEX = 7;
    public static final int PPT_PRODUCT_ZONE3_INDEX = 8;
    public static final int PPT_PRODUCT_ZONE4_INDEX = 9;
    public static final int PPT_PRODUCT_ZONE5_INDEX = 10;
    public static final int PPT_PRODUCT_ZONE6_INDEX = 11;
    public static final int PPT_PRODUCT_ZONE7_INDEX = 12;
    public static final int PPT_PRODUCT_ZONE8_INDEX = 13;
    public static final int PPT_PRODUCT_ZONE9_INDEX = 14;
    public static final int PPT_PRODUCT_ZONE10_INDEX = 15;
    public static final int PPT_PRODUCT_ZONE11_INDEX = 16;
    public static final int PPT_PRODUCT_ZONE12_INDEX = 17;
    public static final int PPT_PRODUCT_ZONE13_INDEX = 18;
    public static final int PPT_PRODUCT_ZONE14_INDEX = 19;
    public static final int PPT_PRODUCT_ZONE15_INDEX = 20;
    public static final int PPT_PRODUCT_ZONE16_INDEX = 21;
    public static final int PPT_PRODUCT_DURATION_INDEX = 22;
    public static final int PPT_PRODUCT_ACTIVE_FLAG_INDEX = 23;
    public static final int PPT_PRODUCT_PRICE_INDEX = 24;

    @Override
    public PrePaidTicketRecord createPrepaidRecord(String[] importRecord) {

        PrePaidTicketRecord paidTicketRecord = new PrePaidTicketRecord(getAdhocCode(importRecord), getPrePaidTicketDescription(importRecord),
                        getPrePaidTicketEffectiveDate(importRecord), getPrePaidTicketDiscountCode(importRecord), getPassengerTypeCode(importRecord),
                        null, getPrePaidTicketStartZone(importRecord), getPrePaidTicketEndZone(importRecord), getPrePaidTicketStatus(importRecord),
                        null, getPrePaidTicketPrice(importRecord), getPrePaidTicketFromDurationCode(importRecord),
                        getPrePaidTicketToDurationCode(importRecord), getPrePaidTicketType(importRecord));

        return paidTicketRecord;
    }

    @Override
    public String getAdhocCode(String[] importRecord) {
        return importRecord[PPT_PRODUCT_ADHOC_CODE_INDEX] != null ? importRecord[PPT_PRODUCT_ADHOC_CODE_INDEX].trim() : null;
    }

    @Override
    public String getPrePaidTicketDescription(String[] importRecord) {
        return importRecord[PPT_PRODUCT_DESCRIPTION_INDEX];
    }

    @Override
    public Date getPrePaidTicketEffectiveDate(String[] importRecord) {
        return parse(importRecord[PPT_PRODUCT_EFFECTIVE_DATE_INDEX], new SimpleDateFormat(DateConstant.PRE_PAID_TICKET_SHORT_DATE_PATTERN));
    }

    @Override
    public String getPrePaidTicketStartZone(String[] importRecord) {
        String[] zonesArray = Arrays.copyOfRange(importRecord, PPT_PRODUCT_ZONE1_INDEX, PPT_PRODUCT_ZONE16_INDEX + 1);
        String zonesYesNo = StringUtils.join(zonesArray);
        return Integer.toString(zonesYesNo.indexOf('Y') + 1);
    }

    @Override
    public String getPrePaidTicketEndZone(String[] importRecord) {
        String[] zonesArray = Arrays.copyOfRange(importRecord, PPT_PRODUCT_ZONE1_INDEX, PPT_PRODUCT_ZONE16_INDEX + 1);
        String zonesYesNo = StringUtils.join(zonesArray);
        return Integer.toString(zonesYesNo.lastIndexOf('Y') + 1);
    }

    @Override
    public String getPrePaidTicketFromDurationCode(String[] importRecord) {
        final String durationValue = importRecord[PPT_PRODUCT_DURATION_INDEX];
        return getDurationPeriod(durationValue).getFromDurationCode();
    }

    private DurationPeriodDTO getDurationPeriod(String durationValue) {
        DurationPeriodDTO durationPeriod = null;
        durationValue = StringUtil.trim(durationValue);
        String[] tokenisedArray = durationValue.split(GREEDY_SPACE_MATCHER_PATTERN);

        if (tokenisedArray.length == 1 && Durations.ANNUAL.getDurationType().equalsIgnoreCase(tokenisedArray[0])) {
            durationPeriod = new DurationPeriodDTO(Durations.ANNUAL.getDurationType(), Durations.ANNUAL.getDurationType());
        }

        if (tokenisedArray.length == 2 && !tokenisedArray[0].contains(ODD_PERIOD_SEPARATION_HYPHEN)) {
            final String numericPart = tokenisedArray[0];
            final String secondPart = tokenisedArray[1];
            switch (secondPart.toLowerCase()) {
            case DAY:
                durationPeriod = new DurationPeriodDTO(Durations.SEVEN_DAYS.getDurationType(), Durations.SEVEN_DAYS.getDurationType());
                break;

            case MONTH:

                durationPeriod = new DurationPeriodDTO(Durations.MONTH.getDurationType(), Durations.MONTH.getDurationType());

                break;

            case MONTHS:

                durationPeriod = new DurationPeriodDTO(Durations.findByType(numericPart + StringUtils.capitalize(MONTH)).getDurationType(), Durations
                                .findByType(numericPart + StringUtils.capitalize(MONTH)).getDurationType());

                break;

            default:

                break;
            }

        } else if (tokenisedArray.length == 2 && tokenisedArray[0].contains(ODD_PERIOD_SEPARATION_HYPHEN)) {
            final String[] fromAndToMonth = tokenisedArray[0].split(ODD_PERIOD_SEPARATION_HYPHEN);
            final String fromMonth = fromAndToMonth[0].trim();
            final String toMonth = fromAndToMonth[1].trim();

            durationPeriod = new DurationPeriodDTO(fromMonth + StringUtils.capitalize(MONTH), toMonth + StringUtils.capitalize(MONTH));

        }
        return durationPeriod;
    }

    @Override
    public String getPrePaidTicketToDurationCode(String[] importRecord) {
        final String durationValue = importRecord[PPT_PRODUCT_DURATION_INDEX];
        return getDurationPeriod(durationValue).getToDurationCode();
    }

    @Override
    public String getPrePaidTicketDiscountCode(String[] importRecord) {
        return importRecord[PPT_PRODUCT_DISCOUNT_TYPE_INDEX];
    }

    @Override
    public String getPassengerTypeCode(String[] importRecord) {
        final String passengerTypeCode = importRecord[PPT_PRODUCT_PASSENGER_TYPE_CODE_INDEX];
        PassengerType passengerType = PassengerType.getFromCode(passengerTypeCode);
        return passengerType != null ? passengerType.desc() : passengerTypeCode;
    }

    @Override
    public Boolean getPrePaidTicketStatus(String[] importRecord) {
        return "Y".equalsIgnoreCase(importRecord[PPT_PRODUCT_ACTIVE_FLAG_INDEX]);
    }

    @Override
    public Integer getPrePaidTicketPrice(String[] importRecord) {
        String price = StringUtils.isBlank(importRecord[PPT_PRODUCT_PRICE_INDEX]) ? null : (importRecord[PPT_PRODUCT_PRICE_INDEX]);
        if (price != null) {
            return CurrencyUtil.convertPoundsAndPenceToPenceAsInteger(price);
        }
        return null;
    }

    @Override
    public String getPrePaidTicketType(String[] importRecord) {
        String description = importRecord[PPT_PRODUCT_DESCRIPTION_INDEX];
        if (StringUtils.isNotEmpty(description)) {
            if (description.contains(BUS_PASS.code())) {
                return BUS_PASS.databaseCode();
            } else {
                return TRAVEL_CARD.databaseCode();
            }
        }
        return null;
    }

}
