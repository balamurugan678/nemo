package com.novacroft.nemo.tfl.services.test_support;

import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.DURATION_1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.DURATION_2;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.DURATION_OTHER;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.END_ZONE_1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.MONTHLY_TRAVELCARD_PRODUCT_NAME;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.PRICE_1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.REMINDER_1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.START_DATE_1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.END_DATE_1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.START_ZONE_1;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

public class ProductDataTestUtil {

    public static final String ERROR_DURATION_MESSAGE = "Duration is null or blank";
    public static final String ERROR_DURATION_FIELD = "duration";
    public static final String GENERIC_ERROR = "Genreic error used to just populate the error description";
    public static final Integer PAY_AS_YOU_GO_AMOUNT_1200 = new Integer(1200);
    public static final Integer PAY_AS_YOU_GO_AMOUNT_1000 = new Integer(1000);
    public static final Integer PAY_AS_YOU_GO_AMOUNT_4000 = new Integer(4000);
    
    public static PrePaidTicket getTestTravelCard() {
        return new PrePaidTicket(DURATION_1, START_ZONE_1.toString(), END_ZONE_1.toString(), START_DATE_1, END_DATE_1, REMINDER_1);
    }
    
    public static PrePaidTicket getTestOddPeriodTravelCard() {
    	return new PrePaidTicket(DURATION_2, START_ZONE_1.toString(), END_ZONE_1.toString(), START_DATE_1, END_DATE_1, REMINDER_1);
	}
    
    public static PrePaidTicket getTestBusPass() {
        return new PrePaidTicket(DURATION_1, START_ZONE_1.toString(), END_ZONE_1.toString(), START_DATE_1, END_DATE_1, REMINDER_1);
    }

    public static PrePaidTicket getTestTravelCardWithNoEndZone() {
        return new PrePaidTicket(DURATION_1, START_ZONE_1.toString(), null, START_DATE_1, END_DATE_1, REMINDER_1);
    }

    public static ProductItemDTO getTestProductItemDTO() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setDuration(DURATION_1);
        productItemDTO.setStartDate(DateUtil.parse(START_DATE_1));
        productItemDTO.setStartZone(START_ZONE_1);
        productItemDTO.setEndZone(END_ZONE_1);
        productItemDTO.setReminderDate(REMINDER_1);
        return productItemDTO;
    }
    
    public static ProductItemDTO getTestOddPeriodProductItemDTO() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setDuration(DURATION_2);
        productItemDTO.setStartDate(DateUtil.parse(START_DATE_1));
        productItemDTO.setStartDate(DateUtil.parse(END_DATE_1));
        productItemDTO.setStartZone(START_ZONE_1);
        productItemDTO.setEndZone(END_ZONE_1);
        productItemDTO.setReminderDate(REMINDER_1);
        return productItemDTO;
    }

    public static ProductItemDTO getTestProductItemDTOBlankDuration() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setDuration("");
        productItemDTO.setStartDate(DateUtil.parse(START_DATE_1));
        productItemDTO.setStartZone(START_ZONE_1);
        productItemDTO.setEndZone(END_ZONE_1);
        productItemDTO.setReminderDate(REMINDER_1);
        return productItemDTO;
    }

    public static ProductDTO getTestProductDTO() {
        ProductDTO p = new ProductDTO();
        p.setDuration(DURATION_1);
        p.setStartZone(START_ZONE_1);
        p.setEndZone(END_ZONE_1);
        p.setTicketPrice(PRICE_1);
        p.setProductName(MONTHLY_TRAVELCARD_PRODUCT_NAME);
        return p;
    }
    
    public static ProductDTO getTestOddPeriodProductDTO() {
        ProductDTO p = new ProductDTO();
        p.setDuration(DURATION_OTHER);
        p.setStartZone(START_ZONE_1);
        p.setEndZone(END_ZONE_1);
        p.setTicketPrice(PRICE_1);
        p.setProductName(MONTHLY_TRAVELCARD_PRODUCT_NAME);
        return p;
    }

    public static ErrorResult getItemErrorResult() {
        ErrorResult errorResult = new ErrorResult();
        List<Error> errors = new ArrayList<>();
        errors.add(getDurationError());
        errorResult.setErrors(errors);
        return errorResult;
    }

    public static Error getDurationError() {
        return getError(ERROR_DURATION_FIELD, ERROR_DURATION_MESSAGE);
    }

    public static Error getError(String field, String description) {
        Error error = new Error();
        error.setField(field);
        error.setDescription(description);
        return error;
    }
    
    public static PayAsYouGo getPayAsYouGoInputWith12Amount(){
        PayAsYouGo dto = new PayAsYouGo();
        dto.setAmount(PAY_AS_YOU_GO_AMOUNT_1200);
        return dto;
    }
    
    public static PayAsYouGo getPayAsYouGoInputWith10Amount(){
        PayAsYouGo dto = new PayAsYouGo();
        dto.setAmount(PAY_AS_YOU_GO_AMOUNT_1000);
        return dto;
    }
    
    public static PayAsYouGo getPayAsYouGoInputWith40Amount(){
        PayAsYouGo dto = new PayAsYouGo();
        dto.setAmount(PAY_AS_YOU_GO_AMOUNT_4000);
        return dto;
    }

}
