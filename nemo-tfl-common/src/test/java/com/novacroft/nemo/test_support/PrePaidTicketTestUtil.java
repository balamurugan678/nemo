package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.domain.Duration;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.domain.Price;
import com.novacroft.nemo.tfl.common.domain.Zone;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

/**
 * Utilities for Pre Paid Ticket tests
 */
public final class PrePaidTicketTestUtil {
    public static final Long ID = 1L;
    public static final String CUBIC_REFERENCE = "cubicReference";
    public static final String PRODUCT_CODE = "102";
    public static final String PRODUCT_CODE_1 = "103";
    public static final String PRODUCT_CODE_2 = "202";
    public static final String TICKET_DESCRIPTION = "Monthly Travelcard";
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer SEVEN = 7;
    public static final String DAY = "Day";
    public static final String MONTH = "Month";
    public static final String CODE_1 = "1";
    public static final String CODE_2 = "2";
    public static final String PASSENGER_TYPE = "Adult";
    public static final String ZONE_1 = "Zone 1";
    public static final String ZONE_2 = "Zone 2";
    public static final String DISCOUNT_TYPE = "18+";
    public static final Integer PRICE_IN_PENCE = 2000;
    public static final String ONE_MONTH = "1Month";
    public static final String ONE_MONTH_DESCRIPTION = "1 Month";
    public static final String TWO_MONTH = "2Month";
    public static final String TWO_MONTH_DESCRIPTION = "2 Month";
    public static final String SEVEN_DAY = "7Day";
    public static final String SEVEN_DAY_DESCRIPTION = "7 Day";
    public static final String TYPE = "Travelcard";

    public static PrePaidTicketDTO getTestPrePaidTicketDTO() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE, TICKET_DESCRIPTION, getTestDurationDTO(),
                        getTestDurationDTO(), getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(),
                        getTestPriceDTOs());
    }

    public static PrePaidTicketDTO getTestPrePaidTicketDTO1() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE, ProductTestUtil.PRODUCT_NAME_1, getTestDurationDTO(),
                        getTestDurationDTO(), getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(),
                        getTestPriceDTOs());
    }
    public static PrePaidTicketDTO getTestDuplicatePrePaidTicketDTO1() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE_1, ProductTestUtil.PRODUCT_NAME_1, getTestDurationDTO(),
                        getTestDurationDTO(), getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(),
                        getTestPriceDTOs());
    }

    public static PrePaidTicketDTO getTestPrePaidTicketDTO2() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE_2, "Annual Pass Card", getTestDurationDTO(),
                        getTestDurationDTO(), getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(),
                        getTestPriceDTOs());
    }

    public static PrePaidTicketDTO getTestPrePaidTicketDTO3() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE, null, getTestDurationDTO(), getTestDurationDTO(),
                        getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(), getTestPriceDTOs());
    }
    
    public static PrePaidTicketDTO getTestPrePaidTicketDTO4() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE, null, getTestDurationDTO(), getTestDurationDTO(),
                        getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(), getTestPriceDTOs2());
    }

    public static PrePaidTicketDTO getTestPrePaidTicketDTOOther() {
        return new PrePaidTicketDTO(ID, get1Jan(), getAug10(), CUBIC_REFERENCE, PRODUCT_CODE, "1-2 Month", getTestDurationDTO(),
                        getTestDurationDTO2Month(), getTestPassengerTypeDTO(), getTestZone1DTO(), getTestZone2DTO(), getTestDiscountTypeDTO(),
                        getTestPriceDTOs());
    }

    public static DurationDTO getTestDurationDTO() {
        return new DurationDTO(ID, ONE, MONTH, ONE_MONTH, ONE_MONTH_DESCRIPTION, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static DurationDTO getTestDurationDTO2Month() {
        return new DurationDTO(ID, TWO, MONTH, TWO_MONTH, TWO_MONTH_DESCRIPTION, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static DurationDTO getTestDurationDTO2() {
        return new DurationDTO(ID, SEVEN, DAY, SEVEN_DAY, SEVEN_DAY_DESCRIPTION, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static PassengerTypeDTO getTestPassengerTypeDTO() {
        return new PassengerTypeDTO(ID, PASSENGER_TYPE, PASSENGER_TYPE, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static ZoneDTO getTestZone1DTO() {
        return new ZoneDTO(ID, CODE_1, ZONE_1, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static ZoneDTO getTestZone2DTO() {
        return new ZoneDTO(ID, CODE_2, ZONE_2, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static DiscountTypeDTO getTestDiscountTypeDTO() {
        return new DiscountTypeDTO(ID, DISCOUNT_TYPE, DISCOUNT_TYPE, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }

    public static List<PriceDTO> getTestPriceDTOs() {
        List<PriceDTO> pricesList = new ArrayList<PriceDTO>();
        pricesList.add(getTestPriceDTO());
        return pricesList;
    }
    
    public static List<PriceDTO> getTestPriceDTOs2() {
        List<PriceDTO> pricesList = new ArrayList<PriceDTO>();
        pricesList.add(getTestPriceDTO());
        pricesList.add(getTestPriceDTO2());
        return pricesList;
    }
    
    public static PriceDTO getTestPriceDTOWithoutId(){
        return new PriceDTO(null, PRICE_IN_PENCE, get1Jan(), null, CUBIC_REFERENCE);
    }

    public static PriceDTO getTestPriceDTO() {
        return new PriceDTO(ID, PRICE_IN_PENCE, get1Jan(), getAug10(), CUBIC_REFERENCE);
    }
    
    public static PriceDTO getTestPriceDTO2() {
        return new PriceDTO(ID, PRICE_IN_PENCE, get1Jan(), null, CUBIC_REFERENCE);
    }

    public static PrePaidTicket getTestPrePaidTicket() {
        PrePaidTicket prePaidTicket = new PrePaidTicket();
        prePaidTicket.setId(ID);
        prePaidTicket.setEffectiveFrom(get1Jan());
        prePaidTicket.setEffectiveTo(getAug10());
        prePaidTicket.setCubicReference(CUBIC_REFERENCE);
        prePaidTicket.setAdHocPrePaidTicketCode(PRODUCT_CODE);
        prePaidTicket.setDescription(TICKET_DESCRIPTION);
        prePaidTicket.setFromDuration(getTestDuration());
        prePaidTicket.setToDuration(getTestDuration());
        prePaidTicket.setPassengerType(getTestPassengerType());
        prePaidTicket.setStartZone(getTestZone1());
        prePaidTicket.setEndZone(getTestZone2());
        prePaidTicket.setDiscountType(getTestDiscountType());
        prePaidTicket.setPrices(getTestPrices());
        return prePaidTicket;
    }
    
    public static PrePaidTicket getTestPrePaidTicket2() {
        PrePaidTicket prePaidTicket = new PrePaidTicket();
        prePaidTicket.setId(ID);
        prePaidTicket.setEffectiveFrom(get1Jan());
        prePaidTicket.setEffectiveTo(getAug10());
        prePaidTicket.setCubicReference(CUBIC_REFERENCE);
        prePaidTicket.setAdHocPrePaidTicketCode(PRODUCT_CODE);
        prePaidTicket.setDescription(TICKET_DESCRIPTION);
        prePaidTicket.setFromDuration(getTestDuration());
        prePaidTicket.setToDuration(getTestDuration());
        prePaidTicket.setPassengerType(getTestPassengerType());
        prePaidTicket.setStartZone(getTestZone1());
        prePaidTicket.setEndZone(getTestZone2());
        prePaidTicket.setDiscountType(getTestDiscountType());
        prePaidTicket.setPrices(getTestPrices2());
        return prePaidTicket;
    }

    public static Duration getTestDuration() {
        Duration duration = new Duration();
        duration.setId(ID);
        duration.setDuration(ONE);
        duration.setUnit(MONTH);
        duration.setCode(ONE_MONTH);
        duration.setName(ONE_MONTH);
        duration.setEffectiveFrom(get1Jan());
        duration.setEffectiveTo(getAug10());
        duration.setCubicReference(CUBIC_REFERENCE);
        return duration;
    }

    public static PassengerType getTestPassengerType() {
        PassengerType passengerType = new PassengerType();
        passengerType.setId(ID);
        passengerType.setCode(PASSENGER_TYPE);
        passengerType.setName(PASSENGER_TYPE);
        passengerType.setEffectiveFrom(get1Jan());
        passengerType.setEffectiveTo(getAug10());
        passengerType.setCubicReference(CUBIC_REFERENCE);
        return passengerType;
    }

    public static Zone getTestZone1() {
        Zone zone = new Zone();
        zone.setId(ID);
        zone.setCode(CODE_1);
        zone.setName(ZONE_1);
        zone.setEffectiveFrom(get1Jan());
        zone.setEffectiveTo(getAug10());
        zone.setCubicReference(CUBIC_REFERENCE);
        return zone;
    }

    public static Zone getTestZone2() {
        Zone zone = new Zone();
        zone.setId(ID);
        zone.setCode(CODE_2);
        zone.setName(ZONE_2);
        zone.setEffectiveFrom(get1Jan());
        zone.setEffectiveTo(getAug10());
        zone.setCubicReference(CUBIC_REFERENCE);
        return zone;
    }

    public static DiscountType getTestDiscountType() {
        DiscountType discountType = new DiscountType();
        discountType.setId(ID);
        discountType.setCode(DISCOUNT_TYPE);
        discountType.setName(DISCOUNT_TYPE);
        discountType.setEffectiveFrom(get1Jan());
        discountType.setEffectiveTo(getAug10());
        discountType.setCubicReference(CUBIC_REFERENCE);
        return discountType;
    }

    public static Set<Price> getTestPrices() {
        Set<Price> pricesList = new HashSet<Price>();
        pricesList.add(getTestPrice());
        return pricesList;
    }
    
    public static Set<Price> getTestPrices2() {
        Set<Price> pricesList = new HashSet<Price>();
        pricesList.add(getTestPrice());
        return pricesList;
    }

    public static Price getTestPrice() {
        Price price = new Price();
        price.setId(ID);
        price.setpriceInPence(PRICE_IN_PENCE);
        price.setEffectiveFrom(get1Jan());
        price.setEffectiveTo(getAug10());
        price.setCubicReference(CUBIC_REFERENCE);
        return price;
    }

    public static ProductDTO getMatchingTestProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(ID);
        productDTO.setProductCode(PRODUCT_CODE);
        productDTO.setProductName(TICKET_DESCRIPTION);
        productDTO.setDuration(ONE_MONTH);
        productDTO.setStartZone(ONE);
        productDTO.setEndZone(TWO);
        productDTO.setTicketPrice(PRICE_IN_PENCE);
        productDTO.setType(TYPE);
        return productDTO;
    }

    public static List<PassengerTypeDTO> getTestPassengerTypeDTOList() {
        List<PassengerTypeDTO> passengerTypes = new ArrayList<PassengerTypeDTO>();
        passengerTypes.add(getTestPassengerTypeDTO());
        return passengerTypes;
    }
    
    public static List<DiscountTypeDTO> getTestDiscountTypeDTOList() {
        List<DiscountTypeDTO> discountTypes = new ArrayList<DiscountTypeDTO>();
        discountTypes.add(getTestDiscountTypeDTO());
        return discountTypes;
    }
}
