package com.novacroft.nemo.tfl.services.test_support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;

public class ItemTestUtil {
    
    public static final Integer PRICE_1 = 1234;
    public static final String START_DATE_1 = "07/08/2014";
    public static final String END_DATE_1 = "07/09/2014";
    public static final String DURATION_1 = "1Month";
    public static final String DURATION_2 = "Unknown";
    public static final String DURATION_OTHER = "Unknown";
    public static final Integer START_ZONE_1 = 1;
    public static final Integer END_ZONE_1 = 5;
    public static final String REMINDER_1 = "2 Days";
    public static final String MONTHLY_TRAVELCARD_PRODUCT_NAME = "Monthly Travelcard Zones 1 to 5";
    public static final String ODD_PERIOD_TRAVELCARD_PRODUCT_NAME = "1 Month 10 Day Travelcard Zones 1 to 5";
    public static final String PAYASYOUGO_PRODUCT_NAME = "Pay As You Go";
    public static final String BUS_PASS_PRODUCT_NAME = "Annual Bus Pass";
    public static final String INVALID_ZONE = "1zone";
    private static final Integer PRICE_2 = 2000;
    private static final Integer PRICE_3 = 7840;
    private static final Long PREPAID_PRODUCT_REFERENCE = 412l;
    public static final Integer USER_PRODUCT_START_AFTER_DAYS = 1;
    
    
    
    public static Item getTravelCardItem(Integer price, Date startDate, Date endDate, String remindetDate, Integer startZone, Integer endZone, String duration, String productName, Long prePaidProductReference){
        Item item = new Item();
        item.setPrice(price);
        item.setStartDate(startDate);
        item.setEndDate(endDate);
        item.setReminderDate(remindetDate);
        item.setStartZone(startZone);
        item.setEndZone(endZone);
        item.setDuration(duration);
        item.setName(productName);
        item.setProductType(ProductItemType.TRAVEL_CARD.code());
        item.setPrePaidProductReference(prePaidProductReference);
        return item;
    }
    
    private static Item getPayAsYouGoItem(Integer price, String productName) {
        Item item = new Item();
        item.setPrice(price);
        item.setName(productName);
        item.setProductType(ProductItemType.PAY_AS_YOU_GO.code());
        return item;
    }
    private static Item getAutoTopUpItem(Integer price, String productName) {
        Item item = new Item();
        item.setPrice(price);
        item.setName(productName);
        item.setProductType(ProductItemType.AUTO_TOP_UP.code());
        return item;
    }
    
    private static Item getBusPassItem(Integer price, String productName, Date startDate, String reminderDate) {
        Item item = new Item();
        item.setPrice(price);
        item.setStartDate(startDate);
        item.setReminderDate(reminderDate);
        item.setName(productName);
        item.setProductType(ProductItemType.BUS_PASS.databaseCode());
        return item;
    }
    
    public static Date getDate(String date){
        return DateUtil.parse(date);
    }
    
    public static Item getTestTravelCardItem1(){
        return getTravelCardItem(PRICE_1, getDate(START_DATE_1), getDate(END_DATE_1), REMINDER_1, START_ZONE_1, END_ZONE_1, DURATION_1, MONTHLY_TRAVELCARD_PRODUCT_NAME,PREPAID_PRODUCT_REFERENCE);
    }
    
    public static Item getTestTravelCardItemWithErrors(){
        Item travelCardItem = getTravelCardItem(PRICE_1, getDate(START_DATE_1), getDate(END_DATE_1), REMINDER_1, START_ZONE_1, END_ZONE_1, DURATION_1, MONTHLY_TRAVELCARD_PRODUCT_NAME,PREPAID_PRODUCT_REFERENCE);
        
        List<Error> errors = new ArrayList<>();
        errors.add(ErrorResultTestUtil.getTestError1());
        travelCardItem.setErrors(new ErrorResult());
        travelCardItem.getErrors().setErrors(errors);
        return travelCardItem;
    }
    public static Item getTestPayAsYouGoItem(){
        return getPayAsYouGoItem(PRICE_2, PAYASYOUGO_PRODUCT_NAME);
    }
   
    public static Item getTestAutoTopUpItem(){
        return getAutoTopUpItem(PRICE_2, PAYASYOUGO_PRODUCT_NAME);
    }
    
    
    public static Item getTestBusPassItem(){
        return getBusPassItem(PRICE_3, BUS_PASS_PRODUCT_NAME, getDate(START_DATE_1), REMINDER_1);
    }

    public static Item getTestItemWithErrors(){
        Item item = new Item();
        item.setErrors(ErrorResultTestUtil.getTestErrorResult1());
        return item;
    }
    
    public static ItemDTO getTestPayAsYouGoItemDTO(){
        return getPayAsYouGoItemDTO(PRICE_2, PAYASYOUGO_PRODUCT_NAME);
    }
    
    private static ItemDTO getPayAsYouGoItemDTO(Integer price, String productName) {
    	ItemDTO item = new ItemDTO();
        item.setPrice(price);
        item.setName(productName);
        return item;
    }
    public static List<ItemDTO> getListOfPayAsYouGoItems() {
        ItemDTO item = getTestPayAsYouGoItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        return items;
    }
}
