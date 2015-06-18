package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ArrayOfTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ObjectFactory;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Tap;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.common.utils.DateUtil.convertDateToXMLGregorian;
import static com.novacroft.nemo.common.utils.DateUtil.convertXMLGregorianToDate;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static com.novacroft.nemo.test_support.JaxbElementTestUtil.*;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Fixtures and utilities for Tap unit tests
 */
public final class TapTestUtil {

    private TapTestUtil() {
    }

    public static final Integer ADDED_S_V_BALANCE_1 = 1;
    public static final Integer AGENT_NO_1 = 2;
    public static final Integer INNER_ZONE_1 = 3;
    public static final Integer JOURNEY_ID_DD_1 = 4;
    public static final String NARRATIVE_DD_1 = "TestNarrative1";
    public static final Integer NLC_1 = 5;
    public static final Integer OUTER_ZONE_1 = 6;
    public static final Integer PAYMENT_METHOD_CODE_1 = 7;
    public static final Integer ROLL_OVER_NUMBER_1 = 8;
    public static final String ROUTE_ID_1 = "TestRoute1";
    public static final Integer SEQUENCE_NO_1 = 9;
    public static final Integer SUPPRESS_CODE_1 = 0;
    public static final Integer S_V_BALANCE_1 = 11;
    public static final Boolean SYNTHETIC_TAP_1 = Boolean.FALSE;
    public static final XMLGregorianCalendar TRANSACTION_TIME_1 = convertDateToXMLGregorian(getAug19());
    public static final Integer TRANSACTION_TYPE_1 = 12;

    public static final Integer ADDED_S_V_BALANCE_2 = 13;
    public static final Integer AGENT_NO_2 = 14;
    public static final Integer INNER_ZONE_2 = 15;
    public static final Integer JOURNEY_ID_DD_2 = 16;
    public static final String NARRATIVE_DD_2 = "TestNarrative2";
    public static final Integer NLC_2 = 17;
    public static final Integer OUTER_ZONE_2 = 18;
    public static final Integer PAYMENT_METHOD_CODE_2 = 19;
    public static final Integer ROLL_OVER_NUMBER_2 = 20;
    public static final String ROUTE_ID_2 = "TestRoute2";
    public static final Integer SEQUENCE_NO_2 = 21;
    public static final Integer SUPPRESS_CODE_2 = 0;
    public static final Integer S_V_BALANCE_2 = 22;
    public static final Boolean SYNTHETIC_TAP_2 = Boolean.FALSE;
    public static final XMLGregorianCalendar TRANSACTION_TIME_2 = convertDateToXMLGregorian(getAug20());
    public static final Integer TRANSACTION_TYPE_2 = 23;

    public static final Tap getTestTap1() {
        return getTestTap(ADDED_S_V_BALANCE_1, AGENT_NO_1, INNER_ZONE_1, JOURNEY_ID_DD_1, NARRATIVE_DD_1, NLC_1, OUTER_ZONE_1,
                PAYMENT_METHOD_CODE_1, ROLL_OVER_NUMBER_1, ROUTE_ID_1, SEQUENCE_NO_1, SUPPRESS_CODE_1, S_V_BALANCE_1,
                SYNTHETIC_TAP_1, TRANSACTION_TIME_1, TRANSACTION_TYPE_1);
    }

    public static final Tap getTestTap2() {
        return getTestTap(ADDED_S_V_BALANCE_2, AGENT_NO_2, INNER_ZONE_2, JOURNEY_ID_DD_2, NARRATIVE_DD_2, NLC_2, OUTER_ZONE_2,
                PAYMENT_METHOD_CODE_2, ROLL_OVER_NUMBER_2, ROUTE_ID_2, SEQUENCE_NO_2, SUPPRESS_CODE_2, S_V_BALANCE_2,
                SYNTHETIC_TAP_2, TRANSACTION_TIME_2, TRANSACTION_TYPE_2);
    }

    public static final List<Tap> getTestTapList1() {
        List<Tap> tapList = new ArrayList<Tap>();
        tapList.add(getTestTap1());
        return tapList;
    }

    public static final List<Tap> getTestTapList2() {
        List<Tap> tapList = getTestTapList1();
        tapList.add(getTestTap2());
        return tapList;
    }

    public static final ArrayOfTap getTestArrayOfTap1() {
        return getTestArrayOfTap(getTestTapList1());
    }

    public static final ArrayOfTap getTestArrayOfTap2() {
        return getTestArrayOfTap(getTestTapList2());
    }

    public static final TapDTO getTestTapDTO1() {
        return getTestTapDTO(ADDED_S_V_BALANCE_1, AGENT_NO_1, INNER_ZONE_1, JOURNEY_ID_DD_1, NARRATIVE_DD_1, NLC_1,
                OUTER_ZONE_1, PAYMENT_METHOD_CODE_1, ROLL_OVER_NUMBER_1, ROUTE_ID_1, SEQUENCE_NO_1, toBoolean(SUPPRESS_CODE_1),
                S_V_BALANCE_1, SYNTHETIC_TAP_1, convertXMLGregorianToDate(TRANSACTION_TIME_1), TRANSACTION_TYPE_1);
    }

    public static final TapDTO getTestTapDTO2() {
        return getTestTapDTO(ADDED_S_V_BALANCE_2, AGENT_NO_2, INNER_ZONE_2, JOURNEY_ID_DD_2, NARRATIVE_DD_2, NLC_2,
                OUTER_ZONE_2, PAYMENT_METHOD_CODE_2, ROLL_OVER_NUMBER_2, ROUTE_ID_2, SEQUENCE_NO_2, toBoolean(SUPPRESS_CODE_2),
                S_V_BALANCE_2, SYNTHETIC_TAP_2, convertXMLGregorianToDate(TRANSACTION_TIME_2), TRANSACTION_TYPE_2);
    }

    public static final List<TapDTO> getTestTapDTOList1() {
        List<TapDTO> tapList = new ArrayList<TapDTO>();
        tapList.add(getTestTapDTO1());
        return tapList;
    }

    public static final List<TapDTO> getTestTapDTOList2() {
        List<TapDTO> tapList = getTestTapDTOList1();
        tapList.add(getTestTapDTO2());
        return tapList;
    }

    public static final Tap getTestTap(Integer addedsvbalance, Integer agentno, Integer innerzone, Integer journeyidDd,
                                       String narrativeDd, Integer nlc, Integer outerzone, Integer paymentmethodcode,
                                       Integer rollovernumber, String routeid, Integer sequenceno, Integer suppresscode,
                                       Integer svbalance, Boolean synthetictap, XMLGregorianCalendar transactiontime,
                                       Integer transactiontype) {
        Tap tap = new ObjectFactory().createTap();
        tap.setAddedsvbalance(getIntegerJaxbElement(addedsvbalance));
        tap.setAgentno(getIntegerJaxbElement(agentno));
        tap.setInnerzone(getIntegerJaxbElement(innerzone));
        tap.setJourneyidDd(journeyidDd);
        tap.setNarrativeDd(getStringJaxbElement(narrativeDd));
        tap.setNlc(getIntegerJaxbElement(nlc));
        tap.setOuterzone(getIntegerJaxbElement(outerzone));
        tap.setPaymentmethodcode(getIntegerJaxbElement(paymentmethodcode));
        tap.setRollovernumber(getIntegerJaxbElement(rollovernumber));
        tap.setRouteid(getStringJaxbElement(routeid));
        tap.setSequenceno(sequenceno);
        tap.setSuppresscode(getIntegerJaxbElement(suppresscode));
        tap.setSvbalance(getIntegerJaxbElement(svbalance));
        tap.setSynthetictap(synthetictap);
        tap.setTransactiontime(getXMLGregorianCalendarJaxbElement(transactiontime));
        tap.setTransactiontype(transactiontype);
        return tap;
    }

    public static final TapDTO getTestTapDTO(Integer addedStoredValueBalance, Integer agentNumber, Integer innerZone,
                                             Integer journeyId, String narrative, Integer nationalLocationCode,
                                             Integer outerZone, Integer paymentMethodCode, Integer rollOverNumber,
                                             String routeId, Integer sequenceNumber, Boolean suppressCode,
                                             Integer storedValueBalance, Boolean syntheticTap, Date transactionAt,
                                             Integer transactionType) {
        return new TapDTO(addedStoredValueBalance, agentNumber, innerZone, journeyId, narrative, nationalLocationCode,
                outerZone, paymentMethodCode, rollOverNumber, routeId, sequenceNumber, suppressCode, storedValueBalance,
                syntheticTap, transactionAt, transactionType);
    }

    public static final ArrayOfTap getTestArrayOfTap(List<Tap> taps) {
        ArrayOfTap arrayOfTap = new ObjectFactory().createArrayOfTap();
        setField(arrayOfTap, "tap", taps);
        return arrayOfTap;
    }
}
