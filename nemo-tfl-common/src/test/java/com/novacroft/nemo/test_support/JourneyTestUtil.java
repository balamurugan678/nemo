package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.common.utils.DateUtil.convertDateToXMLGregorian;
import static com.novacroft.nemo.common.utils.DateUtil.convertXMLGregorianToDate;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug21;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.JaxbElementTestUtil.getIntegerJaxbElement;
import static com.novacroft.nemo.test_support.JaxbElementTestUtil.getStringJaxbElement;
import static com.novacroft.nemo.test_support.JaxbElementTestUtil.getXMLGregorianCalendarJaxbElement;
import static com.novacroft.nemo.test_support.TapTestUtil.getTestArrayOfTap2;
import static com.novacroft.nemo.test_support.TapTestUtil.getTestTapDTOList2;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isJourneyUnFinished;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isJourneyUnStarted;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDisplayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ArrayOfTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Journey;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ObjectFactory;

/**
 * Fixtures and tests for Journey tests
 */
public final class JourneyTestUtil {
    public static final Integer AGENT_NO_1 = 1;
    public static final Integer ADDED_S_V_BALANCE_1 = 2;
    public static final Integer AUTO_COMPLETION_FLAG_1 = 0;
    public static final Integer BEST_VALUE_ACTUAL_FARE_1 = 3;
    public static final String BEST_VALUE_CAPPING_SCHEME_1 = "TestBestValueCappingScheme1";
    public static final Integer BUS_OFF_PEAK_RUN_TOT_1 = 4;
    public static final Integer BUS_PEAK_RUN_TOT_1 = 5;
    public static final Integer CARD_DISCOUNT_ID_1 = 6;
    public static final Integer CARD_PASSENGER_TYPE_ID_1 = 7;
    public static final Integer CARD_TYPE_1 = 8;
    public static final String CHARGE_DESC_1 = "TestChargeDesc1";
    public static final String CONCESSION_NARRATIVE_1 = "TestConcessionNarrative1";
    public static final Integer DAILY_CAPPING_FLAG_1 = 0;
    public static final Integer DISCOUNTED_FARE_1 = 9;
    public static final String DISCOUNT_NARRATIVE_1 = "TestDiscountNarrative1";
    public static final XMLGregorianCalendar EX_DATE_TIME_1 = convertDateToXMLGregorian(getAug20());
    public static final Integer EX_LOCATION_1 = 10;
    public static final Integer INNER_ZONE_1 = 11;
    public static final Integer JOURNEY_ID_1 = 12;
    public static final Integer MODE_OF_TRAVEL_1 = 13;
    public static final String NARRATIVE_DD_1 = "TestNarrative1";
    public static final String OFF_PEAK_CAP_1 = "TestOffPeakCap1";
    public static final String OSI_1 = "TestOsi1";
    public static final Integer OUTER_ZONE_1 = 14;
    public static final Integer PAY_G_USED_1 = 0;
    public static final Integer PAYMENT_METHOD_CODE_1 = 15;
    public static final String PEAK_CAP_1 = "TestPeakCap1";
    public static final String PEAK_FARE_1 = "TestPeakFare1";
    public static final Long PRESTIGE_ID_1 = 16L;
    public static final Integer PRODUCT_CODE_1 = 17;
    public static final XMLGregorianCalendar PRODUCT_EXPIRY_DATE_1 = convertDateToXMLGregorian(getAug21());
    public static final XMLGregorianCalendar PRODUCT_START_DATE_1 = convertDateToXMLGregorian(getAug19());
    public static final Integer PRODUCT_TIME_VALIDITY_CODE_1 = 18;
    public static final Integer PRODUCT_ZONAL_VALIDITY_CODE_1 = 19;
    public static final Integer PTTID_1 = 20;
    public static final Integer RAIL_OFF_PEAK_MAX_OUT_ZONE_1 = 21;
    public static final Integer RAIL_OFF_PEAK_MIN_INN_ZONE_1 = 22;
    public static final Integer RAIL_OFF_PEAK_RUN_TOT_1 = 23;
    public static final Integer RAIL_PEAK_MAX_OUT_ZONE_1 = 24;
    public static final Integer RAIL_PEAK_MIN_INN_ZONE_1 = 25;
    public static final Integer RAIL_PEAK_RUN_TOT_1 = 26;
    public static final String ROUTE_ID_1 = "TestRouteId1";
    public static final Integer RUNNING_TOTAL_1 = 27;
    public static final Integer SUPPRESS_CODE_1 = 0;
    public static final Integer S_V_BALANCE_1 = 28;
    public static final Integer TFR_DISCOUNTS_APPLIED_FLAG_1 = 0;
    public static final XMLGregorianCalendar TRAFFIC_DATE_1 = convertDateToXMLGregorian(getAug20());
    public static final Integer TRAVEL_CARD_USED_1 = 0;
    public static final String TRAVEL_TYPE_1 = "TestTravelType1";
    public static final XMLGregorianCalendar TXN_DATE_TIME_1 = convertDateToXMLGregorian(getAug20());
    public static final Integer TXN_LOCATION_1 = 29;
    public static final String VALID_TICKETT_ON_CMS_1 = "TestValidTicketOnCms1";
    public static final Integer ZONAL_INDICATOR_1 = 30;

    public static final Boolean TOP_UP_ACTIVATED_1 = Boolean.FALSE;
    public static final String TRANSACTION_LOCATION_NAME_1 = "Mornington Crescent";
    public static final String EXIT_LOCATION_NAME_1 = "Piccadilly Circus";
    public static final Date EFFECTIVE_TRAFFIC_ON_1 = getAug20();
    public static final String PSEUDO_TRANSACTION_TYPE_DISPLAY_DESCRIPTION_1 = "";
    public static final String JOURNEY_START_TIME_1 = "17:23";
    public static final String JOURNEY_END_TIME_1 = "18:04";
    public static final String JOURNEY_TIME_1 = "17:23 - 18:04";
    public static final String JOURNEY_DESCRIPTION_1 = "Mornington Crescent to Piccadilly Circus";
    public static final Integer CHARGE_AMOUNT_1 = 1234;
    public static final Integer CREDIT_AMOUNT_1 = null;
    public static final Boolean WARNING_1 = Boolean.FALSE;
    public static final Boolean MANUALLY_CORRECTED_1 = Boolean.FALSE;

    public static final String TRANSACTION_LOCATION_NAME_2 = "Covent Garden";
    public static final String EXIT_LOCATION_NAME_2 = "Bank";
    public static final Date EFFECTIVE_TRAFFIC_ON_2 = getAug21();
    public static final String PSEUDO_TRANSACTION_TYPE_DISPLAY_DESCRIPTION_2 = "";
    public static final String JOURNEY_START_TIME_2 = "17:23";
    public static final String JOURNEY_END_TIME_2 = "18:04";
    public static final String JOURNEY_TIME_2 = "17:23 - 18:04";
    public static final String JOURNEY_DESCRIPTION_2 = "Covent Garden to Bank";
    public static final Integer CHARGE_AMOUNT_2 = 1234;
    public static final Integer CREDIT_AMOUNT_2 = null;
    public static final Boolean WARNING_2 = Boolean.FALSE;
    public static final Boolean MANUALLY_CORRECTED_2 = Boolean.FALSE;

    public static final Integer AGENT_NO_2 = 1;
    public static final Integer ADDED_S_V_BALANCE_2 = 2;
    public static final Integer AUTO_COMPLETION_FLAG_2 = 0;
    public static final Integer BEST_VALUE_ACTUAL_FARE_2 = 3;
    public static final String BEST_VALUE_CAPPING_SCHEME_2 = "TestBestValueCappingScheme1";
    public static final Integer BUS_OFF_PEAK_RUN_TOT_2 = 4;
    public static final Integer BUS_PEAK_RUN_TOT_2 = 5;
    public static final Integer CARD_DISCOUNT_ID_2 = 6;
    public static final Integer CARD_PASSENGER_TYPE_ID_2 = 7;
    public static final Integer CARD_TYPE_2 = 8;
    public static final String CHARGE_DESC_2 = "TestChargeDesc1";
    public static final String CONCESSION_NARRATIVE_2 = "TestConcessionNarrative1";
    public static final Integer DAILY_CAPPING_FLAG_2 = 0;
    public static final Integer DISCOUNTED_FARE_2 = 9;
    public static final String DISCOUNT_NARRATIVE_2 = "TestDiscountNarrative1";
    public static final XMLGregorianCalendar EX_DATE_TIME_2 = convertDateToXMLGregorian(getAug20());
    public static final Integer EX_LOCATION_2 = 10;
    public static final Integer INNER_ZONE_2 = 11;
    public static final Integer JOURNEY_ID_2 = 12;
    public static final Integer MODE_OF_TRAVEL_2 = 13;
    public static final String NARRATIVE_DD_2 = "TestNarrative1";
    public static final String OFF_PEAK_CAP_2 = "TestOffPeakCap1";
    public static final String OSI_2 = "TestOsi1";
    public static final Integer OUTER_ZONE_2 = 14;
    public static final Integer PAY_G_USED_2 = 0;
    public static final Integer PAYMENT_METHOD_CODE_2 = 15;
    public static final String PEAK_CAP_2 = "TestPeakCap1";
    public static final String PEAK_FARE_2 = "TestPeakFare1";
    public static final Long PRESTIGE_ID_2 = 16L;
    public static final Integer PRODUCT_CODE_2 = 17;
    public static final XMLGregorianCalendar PRODUCT_EXPIRY_DATE_2 = convertDateToXMLGregorian(getAug21());
    public static final XMLGregorianCalendar PRODUCT_START_DATE_2 = convertDateToXMLGregorian(getAug19());
    public static final Integer PRODUCT_TIME_VALIDITY_CODE_2 = 18;
    public static final Integer PRODUCT_ZONAL_VALIDITY_CODE_2 = 19;
    public static final Integer PTTID_2 = 20;
    public static final Integer PTTID_UNSTARTED = 501;
    public static final Integer PTTID_UNFINISHED = 502;
    public static final Integer RAIL_OFF_PEAK_MAX_OUT_ZONE_2 = 21;
    public static final Integer RAIL_OFF_PEAK_MIN_INN_ZONE_2 = 22;
    public static final Integer RAIL_OFF_PEAK_RUN_TOT_2 = 23;
    public static final Integer RAIL_PEAK_MAX_OUT_ZONE_2 = 24;
    public static final Integer RAIL_PEAK_MIN_INN_ZONE_2 = 25;
    public static final Integer RAIL_PEAK_RUN_TOT_2 = 26;
    public static final String ROUTE_ID_2 = "TestRouteId1";
    public static final Integer RUNNING_TOTAL_2 = 27;
    public static final Integer SUPPRESS_CODE_2 = 0;
    public static final Integer S_V_BALANCE_2 = 28;
    public static final Integer TFR_DISCOUNTS_APPLIED_FLAG_2 = 0;
    public static final XMLGregorianCalendar TRAFFIC_DATE_2 = convertDateToXMLGregorian(getAug20());
    public static final Integer TRAVEL_CARD_USED_2 = 0;
    public static final String TRAVEL_TYPE_2 = "TestTravelType1";
    public static final XMLGregorianCalendar TXN_DATE_TIME_2 = convertDateToXMLGregorian(getAug20());
    public static final Integer TXN_LOCATION_2 = 29;
    public static final String VALID_TICKETT_ON_CMS_2 = "TestValidTicketOnCms1";
    public static final Integer ZONAL_INDICATOR_2 = 30;
    public static final String EXPECTED_JOURNEY_DESCRIPTION_UNSTARTED = "[No touch-in] to Bank";
    public static final String EXPECTED_JOURNEY_DESCRIPTION_UNFINISHED = "[No touch-out] from Covent Garden";

    public static final byte[] DUMMY_PDF = "dummy-pdf".getBytes();
    public static final byte[] DUMMY_CSV = "dummy-csv".getBytes();
    public static final String DUMMY_HTML_STRING = "<html><head></head><body></body></html>";
    public static final String DUMMY_INVALID_HTML_STRING = "<html><head></head><body>!£$%^*<hello></hello>()</body></html>";
    
    public static final Integer TOTAL_SPENT_100 = 100;

    private JourneyTestUtil() {
    }

    public static Journey getTestJourney1() {
        return getTestJourney(AGENT_NO_1, ADDED_S_V_BALANCE_1, AUTO_COMPLETION_FLAG_1, BEST_VALUE_ACTUAL_FARE_1,
                BEST_VALUE_CAPPING_SCHEME_1, BUS_OFF_PEAK_RUN_TOT_1, BUS_PEAK_RUN_TOT_1, CARD_DISCOUNT_ID_1,
                CARD_PASSENGER_TYPE_ID_1, CARD_TYPE_1, CHARGE_DESC_1, CONCESSION_NARRATIVE_1, DAILY_CAPPING_FLAG_1,
                DISCOUNTED_FARE_1, DISCOUNT_NARRATIVE_1, EX_DATE_TIME_1, EX_LOCATION_1, INNER_ZONE_1, JOURNEY_ID_1,
                MODE_OF_TRAVEL_1, NARRATIVE_DD_1, OFF_PEAK_CAP_1, OSI_1, OUTER_ZONE_1, PAY_G_USED_1, PAYMENT_METHOD_CODE_1,
                PEAK_CAP_1, PEAK_FARE_1, PRESTIGE_ID_1, PRODUCT_CODE_1, PRODUCT_EXPIRY_DATE_1, PRODUCT_START_DATE_1,
                PRODUCT_TIME_VALIDITY_CODE_1, PRODUCT_ZONAL_VALIDITY_CODE_1, PTTID_1, RAIL_OFF_PEAK_MAX_OUT_ZONE_1,
                RAIL_OFF_PEAK_MIN_INN_ZONE_1, RAIL_OFF_PEAK_RUN_TOT_1, RAIL_PEAK_MAX_OUT_ZONE_1, RAIL_PEAK_MIN_INN_ZONE_1,
                RAIL_PEAK_RUN_TOT_1, ROUTE_ID_1, RUNNING_TOTAL_1, SUPPRESS_CODE_1, S_V_BALANCE_1, getTestArrayOfTap2(),
                TFR_DISCOUNTS_APPLIED_FLAG_1, TRAFFIC_DATE_1, TRAVEL_CARD_USED_1, TRAVEL_TYPE_1, TXN_DATE_TIME_1,
                TXN_LOCATION_1, VALID_TICKETT_ON_CMS_1, ZONAL_INDICATOR_1);
    }

    public static JourneyDTO getTestJourneyDTO1() {
        JourneyDTO testJourneyDTO =
                getTestJourneyDTO(ADDED_S_V_BALANCE_1, AGENT_NO_1, toBoolean(AUTO_COMPLETION_FLAG_1), BEST_VALUE_ACTUAL_FARE_1,
                        BEST_VALUE_CAPPING_SCHEME_1, BUS_OFF_PEAK_RUN_TOT_1, BUS_PEAK_RUN_TOT_1, CARD_DISCOUNT_ID_1,
                        CARD_PASSENGER_TYPE_ID_1, CARD_TYPE_1, CHARGE_DESC_1, CONCESSION_NARRATIVE_1,
                        toBoolean(DAILY_CAPPING_FLAG_1), DISCOUNTED_FARE_1, DISCOUNT_NARRATIVE_1,
                        convertXMLGregorianToDate(EX_DATE_TIME_1), EX_LOCATION_1, JOURNEY_ID_1, INNER_ZONE_1, MODE_OF_TRAVEL_1,
                        NARRATIVE_DD_1, OFF_PEAK_CAP_1, OSI_1, OUTER_ZONE_1, toBoolean(PAY_G_USED_1), PAYMENT_METHOD_CODE_1,
                        PEAK_CAP_1, PEAK_FARE_1, PRESTIGE_ID_1, PRODUCT_CODE_1,
                        convertXMLGregorianToDate(PRODUCT_EXPIRY_DATE_1), convertXMLGregorianToDate(PRODUCT_START_DATE_1),
                        PRODUCT_TIME_VALIDITY_CODE_1, PRODUCT_ZONAL_VALIDITY_CODE_1, PTTID_1, RAIL_OFF_PEAK_MAX_OUT_ZONE_1,
                        RAIL_OFF_PEAK_MIN_INN_ZONE_1, RAIL_OFF_PEAK_RUN_TOT_1, RAIL_PEAK_MAX_OUT_ZONE_1,
                        RAIL_PEAK_MIN_INN_ZONE_1, RAIL_PEAK_RUN_TOT_1, ROUTE_ID_1, RUNNING_TOTAL_1, toBoolean(SUPPRESS_CODE_1),
                        S_V_BALANCE_1, getTestTapDTOList2(), toBoolean(TFR_DISCOUNTS_APPLIED_FLAG_1),
                        convertXMLGregorianToDate(TRAFFIC_DATE_1), toBoolean(TRAVEL_CARD_USED_1), TRAVEL_TYPE_1,
                        convertXMLGregorianToDate(TXN_DATE_TIME_1), TXN_LOCATION_1, VALID_TICKETT_ON_CMS_1, ZONAL_INDICATOR_1);
        testJourneyDTO.setJourneyDisplay(getJourneyDisplayDTO1());
        return testJourneyDTO;
    }

    public static JourneyDTO getTestJourneyDTO2() {
        JourneyDTO testJourneyDTO =
                getTestJourneyDTO(ADDED_S_V_BALANCE_2, AGENT_NO_2, toBoolean(AUTO_COMPLETION_FLAG_2), BEST_VALUE_ACTUAL_FARE_2,
                        BEST_VALUE_CAPPING_SCHEME_2, BUS_OFF_PEAK_RUN_TOT_2, BUS_PEAK_RUN_TOT_2, CARD_DISCOUNT_ID_2,
                        CARD_PASSENGER_TYPE_ID_2, CARD_TYPE_2, CHARGE_DESC_2, CONCESSION_NARRATIVE_2,
                        toBoolean(DAILY_CAPPING_FLAG_2), DISCOUNTED_FARE_2, DISCOUNT_NARRATIVE_2,
                        convertXMLGregorianToDate(EX_DATE_TIME_2), EX_LOCATION_2, JOURNEY_ID_2, INNER_ZONE_2, MODE_OF_TRAVEL_2,
                        NARRATIVE_DD_2, OFF_PEAK_CAP_2, OSI_2, OUTER_ZONE_2, toBoolean(PAY_G_USED_2), PAYMENT_METHOD_CODE_2,
                        PEAK_CAP_2, PEAK_FARE_2, PRESTIGE_ID_2, PRODUCT_CODE_2,
                        convertXMLGregorianToDate(PRODUCT_EXPIRY_DATE_2), convertXMLGregorianToDate(PRODUCT_START_DATE_2),
                        PRODUCT_TIME_VALIDITY_CODE_2, PRODUCT_ZONAL_VALIDITY_CODE_2, PTTID_2, RAIL_OFF_PEAK_MAX_OUT_ZONE_2,
                        RAIL_OFF_PEAK_MIN_INN_ZONE_2, RAIL_OFF_PEAK_RUN_TOT_2, RAIL_PEAK_MAX_OUT_ZONE_2,
                        RAIL_PEAK_MIN_INN_ZONE_2, RAIL_PEAK_RUN_TOT_2, ROUTE_ID_2, RUNNING_TOTAL_2, toBoolean(SUPPRESS_CODE_2),
                        S_V_BALANCE_2, getTestTapDTOList2(), toBoolean(TFR_DISCOUNTS_APPLIED_FLAG_2),
                        convertXMLGregorianToDate(TRAFFIC_DATE_2), toBoolean(TRAVEL_CARD_USED_2), TRAVEL_TYPE_2,
                        convertXMLGregorianToDate(TXN_DATE_TIME_2), TXN_LOCATION_2, VALID_TICKETT_ON_CMS_2, ZONAL_INDICATOR_2);
        testJourneyDTO.setJourneyDisplay(getJourneyDisplayDTO2());
        return testJourneyDTO;
    }
    
    public static JourneyDTO getTestJourneyDTO3() {
        JourneyDTO testJourneyDTO =
                getTestJourneyDTO(ADDED_S_V_BALANCE_2, AGENT_NO_2, toBoolean(AUTO_COMPLETION_FLAG_2), BEST_VALUE_ACTUAL_FARE_2,
                        BEST_VALUE_CAPPING_SCHEME_2, BUS_OFF_PEAK_RUN_TOT_2, BUS_PEAK_RUN_TOT_2, CARD_DISCOUNT_ID_2,
                        CARD_PASSENGER_TYPE_ID_2, CARD_TYPE_2, CHARGE_DESC_2, CONCESSION_NARRATIVE_2,
                        toBoolean(DAILY_CAPPING_FLAG_2), DISCOUNTED_FARE_2, DISCOUNT_NARRATIVE_2,
                        convertXMLGregorianToDate(EX_DATE_TIME_2), EX_LOCATION_2, JOURNEY_ID_2, INNER_ZONE_2, MODE_OF_TRAVEL_2,
                        NARRATIVE_DD_2, OFF_PEAK_CAP_2, OSI_2, OUTER_ZONE_2, toBoolean(PAY_G_USED_2), PAYMENT_METHOD_CODE_2,
                        PEAK_CAP_2, PEAK_FARE_2, PRESTIGE_ID_2, PRODUCT_CODE_2,
                        convertXMLGregorianToDate(PRODUCT_EXPIRY_DATE_2), convertXMLGregorianToDate(PRODUCT_START_DATE_2),
                        PRODUCT_TIME_VALIDITY_CODE_2, PRODUCT_ZONAL_VALIDITY_CODE_2, PTTID_UNSTARTED, RAIL_OFF_PEAK_MAX_OUT_ZONE_2,
                        RAIL_OFF_PEAK_MIN_INN_ZONE_2, RAIL_OFF_PEAK_RUN_TOT_2, RAIL_PEAK_MAX_OUT_ZONE_2,
                        RAIL_PEAK_MIN_INN_ZONE_2, RAIL_PEAK_RUN_TOT_2, ROUTE_ID_2, RUNNING_TOTAL_2, toBoolean(SUPPRESS_CODE_2),
                        S_V_BALANCE_2, getTestTapDTOList2(), toBoolean(TFR_DISCOUNTS_APPLIED_FLAG_2),
                        convertXMLGregorianToDate(TRAFFIC_DATE_2), toBoolean(TRAVEL_CARD_USED_2), TRAVEL_TYPE_2,
                        convertXMLGregorianToDate(TXN_DATE_TIME_2), TXN_LOCATION_2, VALID_TICKETT_ON_CMS_2, ZONAL_INDICATOR_2);
        testJourneyDTO.setJourneyDisplay(getJourneyDisplayDTO3());
        return testJourneyDTO;
    }
    
    public static JourneyDTO getTestJourneyDTO4() {
        JourneyDTO testJourneyDTO =
                getTestJourneyDTO(ADDED_S_V_BALANCE_2, AGENT_NO_2, toBoolean(AUTO_COMPLETION_FLAG_2), BEST_VALUE_ACTUAL_FARE_2,
                        BEST_VALUE_CAPPING_SCHEME_2, BUS_OFF_PEAK_RUN_TOT_2, BUS_PEAK_RUN_TOT_2, CARD_DISCOUNT_ID_2,
                        CARD_PASSENGER_TYPE_ID_2, CARD_TYPE_2, CHARGE_DESC_2, CONCESSION_NARRATIVE_2,
                        toBoolean(DAILY_CAPPING_FLAG_2), DISCOUNTED_FARE_2, DISCOUNT_NARRATIVE_2,
                        convertXMLGregorianToDate(EX_DATE_TIME_2), EX_LOCATION_2, JOURNEY_ID_2, INNER_ZONE_2, MODE_OF_TRAVEL_2,
                        NARRATIVE_DD_2, OFF_PEAK_CAP_2, OSI_2, OUTER_ZONE_2, toBoolean(PAY_G_USED_2), PAYMENT_METHOD_CODE_2,
                        PEAK_CAP_2, PEAK_FARE_2, PRESTIGE_ID_2, PRODUCT_CODE_2,
                        convertXMLGregorianToDate(PRODUCT_EXPIRY_DATE_2), convertXMLGregorianToDate(PRODUCT_START_DATE_2),
                        PRODUCT_TIME_VALIDITY_CODE_2, PRODUCT_ZONAL_VALIDITY_CODE_2, PTTID_UNFINISHED, RAIL_OFF_PEAK_MAX_OUT_ZONE_2,
                        RAIL_OFF_PEAK_MIN_INN_ZONE_2, RAIL_OFF_PEAK_RUN_TOT_2, RAIL_PEAK_MAX_OUT_ZONE_2,
                        RAIL_PEAK_MIN_INN_ZONE_2, RAIL_PEAK_RUN_TOT_2, ROUTE_ID_2, RUNNING_TOTAL_2, toBoolean(SUPPRESS_CODE_2),
                        S_V_BALANCE_2, getTestTapDTOList2(), toBoolean(TFR_DISCOUNTS_APPLIED_FLAG_2),
                        convertXMLGregorianToDate(TRAFFIC_DATE_2), toBoolean(TRAVEL_CARD_USED_2), TRAVEL_TYPE_2,
                        convertXMLGregorianToDate(TXN_DATE_TIME_2), TXN_LOCATION_2, VALID_TICKETT_ON_CMS_2, ZONAL_INDICATOR_2);
        testJourneyDTO.setJourneyDisplay(getJourneyDisplayDTO4());
        return testJourneyDTO;
    }
    
    public static List<JourneyDTO> getTestJourneyListDTO1() {
        List<JourneyDTO> journeyDTOList = new ArrayList<JourneyDTO>();
        journeyDTOList.add(getTestJourneyDTO1());
        return journeyDTOList;
    }

    public static List<JourneyDTO> getTestJourneyListDTO2() {
        List<JourneyDTO> journeyDTOList = getTestJourneyListDTO1();
        journeyDTOList.add(getTestJourneyDTO2());
        return journeyDTOList;
    }

    public static List<JourneyDTO> getTestJourneyListDTO3() {
        List<JourneyDTO> journeyDTOList = getTestJourneyListDTO1();
        journeyDTOList.add(getTestJourneyDTO3());
        return journeyDTOList;
    }
    
    public static List<JourneyDTO> getTestJourneyListDTO4() {
        List<JourneyDTO> journeyDTOList = getTestJourneyListDTO1();
        journeyDTOList.add(getTestJourneyDTO4());
        return journeyDTOList;
    }
    
    public static JourneyDisplayDTO getJourneyDisplayDTO1() {
        return getJourneyDisplayDTO(TOP_UP_ACTIVATED_1, TRANSACTION_LOCATION_NAME_1, EXIT_LOCATION_NAME_1, EFFECTIVE_TRAFFIC_ON_1,
                PSEUDO_TRANSACTION_TYPE_DISPLAY_DESCRIPTION_1, JOURNEY_START_TIME_1, JOURNEY_END_TIME_1, JOURNEY_TIME_1,
                JOURNEY_DESCRIPTION_1, CHARGE_AMOUNT_1, CREDIT_AMOUNT_1, WARNING_1, MANUALLY_CORRECTED_2);
    }

    public static JourneyDisplayDTO getJourneyDisplayDTO2() {
        return getJourneyDisplayDTO(TOP_UP_ACTIVATED_1, TRANSACTION_LOCATION_NAME_2, EXIT_LOCATION_NAME_2, EFFECTIVE_TRAFFIC_ON_2,
                PSEUDO_TRANSACTION_TYPE_DISPLAY_DESCRIPTION_2, JOURNEY_START_TIME_2, JOURNEY_END_TIME_2, JOURNEY_TIME_2,
                JOURNEY_DESCRIPTION_2, CHARGE_AMOUNT_2, CREDIT_AMOUNT_2, WARNING_2, MANUALLY_CORRECTED_1);
    }

    public static JourneyDisplayDTO getJourneyDisplayDTO3() {
        return getJourneyDisplayDTO(TOP_UP_ACTIVATED_1, null, EXIT_LOCATION_NAME_2, EFFECTIVE_TRAFFIC_ON_2,
                PSEUDO_TRANSACTION_TYPE_DISPLAY_DESCRIPTION_2, JOURNEY_START_TIME_2, JOURNEY_END_TIME_2, JOURNEY_TIME_2,
                JOURNEY_DESCRIPTION_2, CHARGE_AMOUNT_2, CREDIT_AMOUNT_2, WARNING_2, MANUALLY_CORRECTED_1);
    }
    
    public static JourneyDisplayDTO getJourneyDisplayDTO4() {
        return getJourneyDisplayDTO(TOP_UP_ACTIVATED_1, TRANSACTION_LOCATION_NAME_2, null, EFFECTIVE_TRAFFIC_ON_2,
                PSEUDO_TRANSACTION_TYPE_DISPLAY_DESCRIPTION_2, JOURNEY_START_TIME_2, JOURNEY_END_TIME_2, JOURNEY_TIME_2,
                JOURNEY_DESCRIPTION_2, CHARGE_AMOUNT_2, CREDIT_AMOUNT_2, WARNING_2, MANUALLY_CORRECTED_1);
    }
    
    public static JourneyDayDTO getTestJourneyDayDTO1() {
        return getTestJourneyDayDTO(getAug19(), getTestJourneyListDTO2(), 1, 0);
    }

    public static JourneyDayDTO getTestJourneyDayDTO2() {
        return getTestJourneyDayDTO(getAug20(), getTestJourneyListDTO2(), 2, 0);
    }
    
    public static JourneyDayDTO getTestJourneyDayDTO3() {
        return getTestJourneyDayDTO(getAug20(), getTestJourneyListDTO3(), 2, 0);
    }
    
    public static JourneyDayDTO getTestJourneyDayDTO4() {
        return getTestJourneyDayDTO(getAug20(), getTestJourneyListDTO4(), 2, 0);
    }
    
    public static JourneyDayDTO getTestJourneyDayDTOWithSpent100() {
        return getTestJourneyDayDTO(getAug19(), getTestJourneyListDTO2(), 1, TOTAL_SPENT_100);
    }

    public static List<JourneyDayDTO> getTestJourneyDayDtoList1() {
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        journeyDays.add(getTestJourneyDayDTO1());
        return journeyDays;
    }

    public static List<JourneyDayDTO> getTestJourneyDayDtoList2() {
        List<JourneyDayDTO> journeyDays = getTestJourneyDayDtoList1();
        journeyDays.add(getTestJourneyDayDTO2());
        return journeyDays;
    }

    public static List<JourneyDayDTO> getTestJourneyDayDtoList3() {
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        journeyDays.add(getTestJourneyDayDTO3());
        return journeyDays;
    }
    
    public static List<JourneyDayDTO> getTestJourneyDayDtoList4() {
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        journeyDays.add(getTestJourneyDayDTO4());
        return journeyDays;
    }
    
    public static JourneyHistoryDTO getTestJourneyHistoryDTO1() {
        return getTestJourneyHistoryDTO(OYSTER_NUMBER_1, getAug19(), getAug22());
    }

    public static Journey getTestJourney(Integer agentno, Integer addedsvbalance, Integer autocompletionflag,
                                         Integer bestvalueactualfare, String bestvaluecappingscheme, Integer busoffpeakruntot,
                                         Integer buspeakruntot, Integer carddiscountid, Integer cardpassengertypeid,
                                         Integer cardtype, String chargedesc, String concessionnarrative,
                                         Integer dailycappingflag, Integer discountedfare, String discountnarrative,
                                         XMLGregorianCalendar exdatetime, Integer exlocation, Integer innerzone,
                                         Integer journeyid, Integer modeoftravel, String narrativeDd, String offpeakcap,
                                         String osi, Integer outerzone, Integer paygused, Integer paymentmethodcode,
                                         String peakcap, String peakfare, Long prestigeid, Integer productcode,
                                         XMLGregorianCalendar productexpirydate, XMLGregorianCalendar productstartdate,
                                         Integer producttimevaliditycode, Integer productzonalvaliditycode, Integer pttid,
                                         Integer railoffpeakmaxOutzone, Integer railoffpeakminInnzone,
                                         Integer railoffpeakruntot, Integer railpeakmaxOutzone, Integer railpeakminInnzone,
                                         Integer railpeakruntot, String routeid, Integer runningtotal, Integer suppresscode,
                                         Integer svbalance, ArrayOfTap taps, Integer tfrdiscountsappliedflag,
                                         XMLGregorianCalendar trafficdate, Integer travelcardused, String traveltype,
                                         XMLGregorianCalendar txndatetime, Integer txnlocation, String validtickettonCMS,
                                         Integer zonalindicator) {
        Journey journey = new ObjectFactory().createJourney();
        journey.setAgentno(getIntegerJaxbElement(agentno));
        journey.setAddedsvbalance(getIntegerJaxbElement(addedsvbalance));
        journey.setAutocompletionflag(autocompletionflag);
        journey.setBestvalueactualfare(getIntegerJaxbElement(bestvalueactualfare));
        journey.setBestvaluecappingscheme(getStringJaxbElement(bestvaluecappingscheme));
        journey.setBusoffpeakruntot(getIntegerJaxbElement(busoffpeakruntot));
        journey.setBuspeakruntot(getIntegerJaxbElement(buspeakruntot));
        journey.setCarddiscountid(getIntegerJaxbElement(carddiscountid));
        journey.setCardpassengertypeid(getIntegerJaxbElement(cardpassengertypeid));
        journey.setCardtype(getIntegerJaxbElement(cardtype));
        journey.setChargedesc(getStringJaxbElement(chargedesc));
        journey.setConcessionnarrative(getStringJaxbElement(concessionnarrative));
        journey.setDailycappingflag(dailycappingflag);
        journey.setDiscountedfare(getIntegerJaxbElement(discountedfare));
        journey.setDiscountnarrative(getStringJaxbElement(discountnarrative));
        journey.setExdatetime(getXMLGregorianCalendarJaxbElement(exdatetime));
        journey.setExlocation(getIntegerJaxbElement(exlocation));
        journey.setInnerzone(getIntegerJaxbElement(innerzone));
        journey.setJourneyid(journeyid);
        journey.setModeoftravel(getIntegerJaxbElement(modeoftravel));
        journey.setNarrativeDd(getStringJaxbElement(narrativeDd));
        journey.setOffpeakcap(getStringJaxbElement(offpeakcap));
        journey.setOsi(getStringJaxbElement(osi));
        journey.setOuterzone(getIntegerJaxbElement(outerzone));
        journey.setPaygused(paygused);
        journey.setPaymentmethodcode(getIntegerJaxbElement(paymentmethodcode));
        journey.setPeakcap(getStringJaxbElement(peakcap));
        journey.setPeakfare(getStringJaxbElement(peakfare));
        journey.setPrestigeid(prestigeid);
        journey.setProductcode(getIntegerJaxbElement(productcode));
        journey.setProductexpirydate(getXMLGregorianCalendarJaxbElement(productexpirydate));
        journey.setProductstartdate(getXMLGregorianCalendarJaxbElement(productstartdate));
        journey.setProducttimevaliditycode(getIntegerJaxbElement(producttimevaliditycode));
        journey.setProductzonalvaliditycode(getIntegerJaxbElement(productzonalvaliditycode));
        journey.setPttid(pttid);
        journey.setRailoffpeakmaxOutzone(getIntegerJaxbElement(railoffpeakmaxOutzone));
        journey.setRailoffpeakminInnzone(getIntegerJaxbElement(railoffpeakminInnzone));
        journey.setRailoffpeakruntot(getIntegerJaxbElement(railoffpeakruntot));
        journey.setRailpeakmaxOutzone(getIntegerJaxbElement(railpeakmaxOutzone));
        journey.setRailpeakminInnzone(getIntegerJaxbElement(railpeakminInnzone));
        journey.setRailpeakruntot(getIntegerJaxbElement(railpeakruntot));
        journey.setRouteid(getStringJaxbElement(routeid));
        journey.setRunningtotal(getIntegerJaxbElement(runningtotal));
        journey.setSuppresscode(suppresscode);
        journey.setSvbalance(getIntegerJaxbElement(svbalance));
        journey.setTaps(new JAXBElement<ArrayOfTap>(new QName("test"), ArrayOfTap.class, taps));
        journey.setTfrdiscountsappliedflag(tfrdiscountsappliedflag);
        journey.setTrafficdate(trafficdate);
        journey.setTravelcardused(travelcardused);
        journey.setTraveltype(getStringJaxbElement(traveltype));
        journey.setTxndatetime(getXMLGregorianCalendarJaxbElement(txndatetime));
        journey.setTxnlocation(getIntegerJaxbElement(txnlocation));
        journey.setValidtickettonCMS(getStringJaxbElement(validtickettonCMS));
        journey.setZonalindicator(getIntegerJaxbElement(zonalindicator));
        return journey;
    }

    public static JourneyDTO getTestJourneyDTO(Integer addedStoredValueBalance, Integer agentNumber, Boolean autoCompletionFlag,
                                               Integer bestValueActualFare, String bestValueCappingScheme,
                                               Integer busOffPeakRunTotal, Integer busPeakRunTotal, Integer cardDiscountId,
                                               Integer cardPassengerTypeId, Integer cardType, String chargeDescription,
                                               String concessionNarrative, Boolean dailyCappingFlag, Integer discountedFare,
                                               String discountNarrative, Date exitAt, Integer exitLocation, Integer journeyId,
                                               Integer innerZone, Integer modeOfTravel, String narrative, String offPeakCap,
                                               String osi, Integer outerZone, Boolean payAsYouGoUsed, Integer paymentMethodCode,
                                               String peakCap, String peakFare, Long prestigeId, Integer productCode,
                                               Date productExpiresOn, Date productStartsOn, Integer productTimeValidityCode,
                                               Integer productZonalValidityCode, Integer pseudoTransactionTypeId,
                                               Integer railOffPeakMaxOuterZone, Integer railOffPeakMinInnerZone,
                                               Integer railOffPeakRunTotal, Integer railPeakMaxOuterZone,
                                               Integer railPeakMinInnerZone, Integer railPeakRunTotal, String routeId,
                                               Integer runningTotal, Boolean suppressCode, Integer storedValueBalance,
                                               List<TapDTO> taps, Boolean transferDiscountsAppliedFlag, Date trafficOn,
                                               Boolean travelCardUsed, String travelType, Date transactionAt,
                                               Integer transactionLocation, String validTicketOnCMS, Integer zonalIndicator) {
        return new JourneyDTO(addedStoredValueBalance, agentNumber, autoCompletionFlag, bestValueActualFare,
                bestValueCappingScheme, busOffPeakRunTotal, busPeakRunTotal, cardDiscountId, cardPassengerTypeId, cardType,
                chargeDescription, concessionNarrative, dailyCappingFlag, discountedFare, discountNarrative, exitAt,
                exitLocation, journeyId, innerZone, modeOfTravel, narrative, offPeakCap, osi, outerZone, payAsYouGoUsed,
                paymentMethodCode, peakCap, peakFare, prestigeId, productCode, productExpiresOn, productStartsOn,
                productTimeValidityCode, productZonalValidityCode, pseudoTransactionTypeId, railOffPeakMaxOuterZone,
                railOffPeakMinInnerZone, railOffPeakRunTotal, railPeakMaxOuterZone, railPeakMinInnerZone, railPeakRunTotal,
                routeId, runningTotal, suppressCode, storedValueBalance, taps, transferDiscountsAppliedFlag, trafficOn,
                travelCardUsed, travelType, transactionAt, transactionLocation, validTicketOnCMS, zonalIndicator);
    }

    public static JourneyDisplayDTO getJourneyDisplayDTO(Boolean topUpActivated, String transactionLocationName, String exitLocationName,
                                                         Date effectiveTrafficOn,
                                                         String pseudoTransactionTypeDisplayDescription,
                                                         String journeyStartTime, String journeyEndTime, String journeyTime,
                                                         String journeyDescription, Integer chargeAmount, Integer creditAmount,
                                                         Boolean warning, Boolean manuallyCorrected) {
        return new JourneyDisplayDTO(topUpActivated, transactionLocationName, exitLocationName, effectiveTrafficOn,
                pseudoTransactionTypeDisplayDescription, journeyStartTime, journeyEndTime, journeyTime, journeyDescription,
                chargeAmount, creditAmount, warning, manuallyCorrected);
    }

    public static JourneyDayDTO getTestJourneyDayDTO(Date effectiveTrafficOn, List<JourneyDTO> journeys, Integer dailyBalance, Integer totalSpent) {
        return new JourneyDayDTO(effectiveTrafficOn, journeys, dailyBalance, totalSpent);
    }

    public static JourneyHistoryDTO getTestJourneyHistoryDTO(String cardNumber, Date rangeFrom, Date rangeTo) {
        return new JourneyHistoryDTO(cardNumber, rangeFrom, rangeTo);
    }

    public static JourneyHistoryDTO getTestJourneyHistoryDTOForUnStartedJourney() {
        JourneyHistoryDTO testJourneyHistory = getTestJourneyHistoryDTO1();
        testJourneyHistory.setJourneyDays(getTestJourneyDayDtoList3());
        return testJourneyHistory;
    }

    public static JourneyHistoryDTO getTestJourneyHistoryDTOForUnFinishedJourney() {
        JourneyHistoryDTO testJourneyHistory = getTestJourneyHistoryDTO1();
        testJourneyHistory.setJourneyDays(getTestJourneyDayDtoList4());
        return testJourneyHistory;
    }

    public static String getJourneyDescriptionForUnStartedJourney(JourneyHistoryDTO journeyHistory) {
        for (JourneyDayDTO journeyDay : journeyHistory.getJourneyDays()) {
            for (JourneyDTO journey : journeyDay.getJourneys()) {
                if (isJourneyUnStarted(journey) && journey.getJourneyDisplay() != null){
                    return journey.getJourneyDisplay().getJourneyDescription();
                }
            }
        }
        return null;
    }

    public static String getJourneyDescriptionForUnFinishedJourney(JourneyHistoryDTO journeyHistory) {
        for (JourneyDayDTO journeyDay : journeyHistory.getJourneyDays()) {
            for (JourneyDTO journey : journeyDay.getJourneys()) {
                if (isJourneyUnFinished(journey) && journey.getJourneyDisplay() != null) {
                    return journey.getJourneyDisplay().getJourneyDescription();
                }
            }
        }
        return null;
    }

    public static String getUnStartedJourneyDescription(JourneyDayDTO journeyDay){
        for (JourneyDTO journey : journeyDay.getJourneys()) {
            if (isJourneyUnStarted(journey) && journey.getJourneyDisplay() != null){
                return journey.getJourneyDisplay().getJourneyDescription();
            }
        }
        return null;
    }
    
    public static String getUnFinishedJourneyDescription(JourneyDayDTO journeyDay){
        for (JourneyDTO journey : journeyDay.getJourneys()) {
            if (isJourneyUnFinished(journey) && journey.getJourneyDisplay() != null){
                return journey.getJourneyDisplay().getJourneyDescription();
            }
        }
        return null;
    }
}
