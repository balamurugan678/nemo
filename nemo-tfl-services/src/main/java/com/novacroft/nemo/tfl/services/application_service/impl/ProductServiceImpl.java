package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getEndDate;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DISCOUNT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PASSENGER_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.services.util.ErrorUtil.getError;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.form_validator.CommonTravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.util.DurationUtil;
import com.novacroft.nemo.tfl.services.application_service.ProductService;
import com.novacroft.nemo.tfl.services.constant.CommandAttribute;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.ItemConverter;
import com.novacroft.nemo.tfl.services.converter.PayAsYouGoConverter;
import com.novacroft.nemo.tfl.services.converter.ProductDataItemConverter;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListContainer;
import com.novacroft.nemo.tfl.services.transfer.ListOption;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;
import com.novacroft.nemo.tfl.services.util.ErrorUtil;
import com.sun.corba.ee.impl.orbutil.codegen.ExpressionFactory.ThisExpression;

import freemarker.template.utility.StringUtil;

@Service("productExternalService")
public class ProductServiceImpl extends BaseService implements ProductService {

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected ProductDataItemConverter productDataConverter;

    @Autowired
    protected CommonTravelCardValidator commonTravelCardValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
    protected PayAsYouGoDataService payAsYouGoDataService;
    @Autowired
    protected PayAsYouGoValidator payAsYouGoValidator;
    @Autowired
    protected PayAsYouGoConverter payAsYouGoWSConverter;
    @Autowired
    protected ItemConverter itemConverter;
    @Autowired
    protected com.novacroft.nemo.tfl.common.application_service.ProductService productService;
    @Autowired
    protected GetCardService getCardService;

    private static final String PAY_AS_YOU_GO_AMOUNTS = "PayAsYouGoAmounts";
    private static final String AUTO_TOP_UP_AMOUNTS = "AutoTopUpAmounts";
    private static final String DURATION = "Duration";
    private static final String TRAVEL_CARD_ZONES = "TravelCardZones";
    private static final String EMAIL_REMINDER = "EmailReminder";
    private static final String START_DATES = "StartDates";
    private static final String PASSENGER_TYPES = "PassengerTypes";
    private static final String DISCOUNT_TYPES = "DiscountTypes";

    protected static final String START_ZONE_ERROR = "startZone.mandatoryFieldEmpty.genericError";
    protected static final String PASSENGER_TYPE_ERROR = "passengerType.mandatoryFieldEmpty.genericError";
    protected static final String DISCOUNT_TYPE_ERROR = "discountType.mandatoryFieldEmpty.genericError";
    protected static final String END_ZONE_ERROR = "endZone.mandatoryFieldEmpty.genericError";
    protected static final String INTEGER_PARSE_ERROR = "integer.parse.error";

    @Override
    public Item getTravelCard(PrePaidTicket travelCard) {
        return getPrePaidTicket(travelCard, ProductItemType.TRAVEL_CARD);
    }

    protected Item getPrePaidTicket(PrePaidTicket prePaidTicket, ProductItemType prePaidTicketType) {
        Item item = new Item();
        item.setErrors(validateZonesPassengerAndDiscountType(prePaidTicket.getStartZone(), prePaidTicket.getEndZone(),
                        prePaidTicket.getPassengerType(), prePaidTicket.getDiscountType()));
        if (item.getErrors() != null && item.getErrors().getErrors() != null) {
            return item;
        }
        ProductDTO convertedProductDTO = productDataConverter.convertToProductDTO(prePaidTicket);
        ProductItemDTO productItemDTO = productDataConverter.convertToProductItemDTO(prePaidTicket);
        productItemDTO.setProductType(prePaidTicketType.databaseCode());
        Errors errors = new BeanPropertyBindingResult(productItemDTO, "productItemDTO");
        commonTravelCardValidator.validate(productItemDTO, errors);
        if (errors.hasErrors()) {
            ErrorResult errorResult = productDataConverter.convertToErrorResult(errors);
            item.setErrors(errorResult);
            return item;
        }
        ProductDTO productDTO = retrievePrePaidTicketProduct(prePaidTicket, convertedProductDTO, productItemDTO, prePaidTicketType.databaseCode());
        item = productDataConverter.convertToItem(productDTO, productItemDTO);
        return item;
    }

    protected ProductDTO retrievePrePaidTicketProduct(PrePaidTicket travelCard, ProductDTO convertedProductDTO, ProductItemDTO productItemDTO,
                    String type) {
        String duration = convertedProductDTO.getDuration();
        ProductDTO productDTO;
        if (Durations.OTHER.getDurationType().equalsIgnoreCase(duration)) {
            productDTO = getOddPeriodProduct(travelCard, travelCard.getPassengerType(), travelCard.getDiscountType(), type);
            if (productDTO != null) {
                productDTO.setStartZone(convertedProductDTO.getStartZone());
                productDTO.setEndZone(convertedProductDTO.getEndZone());
            }
        } else {
            productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(duration,
                            Integer.parseInt(travelCard.getStartZone()), Integer.parseInt(travelCard.getEndZone()), parse(travelCard.getStartDate()),
                            travelCard.getPassengerType(), travelCard.getDiscountType(), type);
            productItemDTO.setEndDate(getEndDate(travelCard.getStartDate(), convertedProductDTO.getDuration()));
        }
        return productDTO;
    }

    protected ProductDTO getOddPeriodProduct(PrePaidTicket prePaidTicket, String passengerType, String discountType, String type) {
        DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(parse(prePaidTicket.getStartDate()),
                        parse(prePaidTicket.getEndDate()));
        return productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(durationPeriod.getFromDurationCode(),
                        durationPeriod.getToDurationCode(), Integer.parseInt(prePaidTicket.getStartZone()),
                        Integer.parseInt(prePaidTicket.getEndZone()), parse(prePaidTicket.getStartDate()), passengerType, discountType, type);

    }

    protected ErrorResult validateZonesPassengerAndDiscountType(String startZone, String endZone, String passengerType, String discountType) {
        List<Error> errors = new ArrayList<>();

        if (startZone == null) {
            errors.add(getError(FIELD_START_ZONE, getContent(START_ZONE_ERROR)));
        }

        if (endZone == null) {
            errors.add(getError(FIELD_END_ZONE, getContent(END_ZONE_ERROR)));
        }

        if (StringUtils.isBlank(passengerType)) {
            errors.add(getError(FIELD_PASSENGER_TYPE, getContent(PASSENGER_TYPE_ERROR)));
        }

        if (StringUtils.isBlank(discountType)) {
            errors.add(getError(FIELD_DISCOUNT_TYPE, getContent(DISCOUNT_TYPE_ERROR)));
        }

        String field = null;
        try {
            if (startZone != null) {
                field = FIELD_START_ZONE;
                Integer.parseInt(startZone);
            }

            if (endZone != null) {
                field = FIELD_END_ZONE;
                Integer.parseInt(endZone);
            }
        } catch (NumberFormatException nfe) {
            errors.add(getError(field, getContent(INTEGER_PARSE_ERROR)));
        }
        if (errors.size() > 0) {

            ErrorResult errorResult = new ErrorResult();
            errorResult.setErrors(errors);
            return errorResult;
        } else {
            return null;
        }
    }

    @Override
    public List<ListContainer> getReferenceData() {
        List<ListContainer> containerList = new ArrayList<>();
        String[] containerTypes = { PageSelectList.PAY_AS_YOU_GO_CREDIT_BALANCES, PageSelectList.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS,
                        PageSelectList.WEBSERVICE_TRAVEL_CARD_DURATIONS, PageSelectList.TRAVEL_CARD_ZONES, PageSelectList.BASKET_EMAIL_REMINDERS };
        int index = 0;
        String containerName = null;
        for (String containerType : containerTypes) {
            SelectListDTO selectListDTO = selectListService.getSelectList(containerType);
            containerName = getContainerName(containerType);
            ListContainer container = getContainerFromSelectList(selectListDTO, containerType, true, index++, containerName);
            containerList.add(container);
        }

        containerList.add(getContainerFromSelectList(cartAdminService.getUserProductStartDateList(), null, false, index, START_DATES));
        containerList.add(getContainerFromSelectList(productService.getPassengerTypeList(), null, false, index, PASSENGER_TYPES));
        containerList.add(getContainerFromSelectList(productService.getDiscountTypeList(), null, false, index, DISCOUNT_TYPES));

        return containerList;
    }

    private String getContainerName(String containerType) {
        switch (containerType) {
        case PageSelectList.PAY_AS_YOU_GO_CREDIT_BALANCES:
            return PAY_AS_YOU_GO_AMOUNTS;
        case PageSelectList.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS:
            return AUTO_TOP_UP_AMOUNTS;
        case PageSelectList.WEBSERVICE_TRAVEL_CARD_DURATIONS:
            return DURATION;
        case PageSelectList.TRAVEL_CARD_ZONES:
            return TRAVEL_CARD_ZONES;
        case PageSelectList.BASKET_EMAIL_REMINDERS:
            return EMAIL_REMINDER;
        case PageSelectList.WEBSERVICE_PASSENGER_TYPES:
            return PASSENGER_TYPES;
        case PageSelectList.WEBSERVICE_DISCOUNT_TYPES:
            return DISCOUNT_TYPES;
        }
        return null;
    }

    public static void main(String args[]){
    	yourMethod({"Hello", "world"});
    }
    
    public static String yourMethod(String values[]){
    	return " ";
    }
    
    private ListContainer getContainerFromSelectList(SelectListDTO selectListDTO, String containerType, Boolean useManagedContentForMeanings,
                    Integer id, String name) {
        ListContainer container = new ListContainer();
        List<ListOption> items = new ArrayList<>();
        container.setId(id);
        if (name != null) {
            container.setName(name);
        } else {
            container.setName(selectListDTO.getName());
        }
        int listOptionIndex = 0;
        for (SelectListOptionDTO selectListOptionDTO : selectListDTO.getOptions()) {
            ListOption item = new ListOption();
            if (null != selectListOptionDTO.getDisplayOrder()) {
                item.setId(selectListOptionDTO.getDisplayOrder());
            } else {
                item.setId(listOptionIndex++);
            }
            String meaning;
            if (useManagedContentForMeanings && containerType != null) {
                meaning = getContent(containerType + "." + selectListOptionDTO.getValue() + ".option",
                                LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString());
            } else {
                meaning = selectListOptionDTO.getMeaning();
            }
            item.setText(meaning);
            item.setValue(selectListOptionDTO.getValue());
            items.add(item);
        }
        container.setItems(items);
        return container;
    }

    @Override
    public Item getPayAsYouGo(PayAsYouGo payAsYouGoInput) {
        Item item = new Item();

        ErrorResult errors = validatePayAsYouGoInput(payAsYouGoInput);
        if (ErrorUtil.hasErrors(errors)) {
            substituteErrorFieldNameWithAPIFieldName(errors);
            item.setErrors(errors);
            return item;
        }

        try {
            PayAsYouGoDTO payAsYouGoDTO = payAsYouGoDataService.findByTicketPrice(payAsYouGoInput.getAmount());
            if (!payAsYouGoDTO.getTicketPrice().equals(payAsYouGoInput.getAmount())) {
                logger.warn(String.format(PrivateError.INVALID_SINGLE_INPUT_PARAMETER.message(), payAsYouGoInput.getAmount()));
                item.setErrors(ErrorUtil.addErrorToList(null, null, CommandAttribute.FIELD_PAY_AS_YOU_GO_AMOUNT,
                                WebServiceResultAttribute.RECORD_NOT_FOUND.name()));
            } else {
                item = payAsYouGoWSConverter.convertToItem(payAsYouGoDTO);
            }
        } catch (Exception lookupException) {
            logger.error(String.format(PrivateError.INVALID_SINGLE_INPUT_PARAMETER.message(), payAsYouGoInput.getAmount()), lookupException);
            item.setErrors(ErrorUtil.addErrorToList(null, null, CommandAttribute.FIELD_PAY_AS_YOU_GO_AMOUNT,
                            WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name()));
        }
        return item;
    }

    protected ErrorResult validatePayAsYouGoInput(PayAsYouGo payAsYouGo) {
        ErrorResult errorResult = new ErrorResult();
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        if (null != payAsYouGo) {
            cmd.setCreditBalance(payAsYouGo.getAmount());
        }
        Errors errors = new BeanPropertyBindingResult(cmd, CartItemCmdImpl.class.getName());
        payAsYouGoValidator.validate(cmd, errors);
        ErrorUtil.addValidationErrorsToList(errorResult, errors.getAllErrors());
        return errorResult;
    }

    protected void substituteErrorFieldNameWithAPIFieldName(ErrorResult errors) {
        for (Error currentError : errors.getErrors()) {
            if (PageCommandAttribute.FIELD_CREDIT_BALANCE.equals(currentError.getField())) {
                currentError.setField(StringUtil.replace(currentError.getField(), PageCommandAttribute.FIELD_CREDIT_BALANCE,
                                CommandAttribute.FIELD_PAY_AS_YOU_GO_AMOUNT));
            }
        }
    }

    @Override
    public Item getBusPass(PrePaidTicket busPass) {
        return getPrePaidTicket(busPass, ProductItemType.BUS_PASS);
    }

}
