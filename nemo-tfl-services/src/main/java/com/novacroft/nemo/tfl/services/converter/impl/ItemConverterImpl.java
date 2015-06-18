package com.novacroft.nemo.tfl.services.converter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.converter.ItemConverter;
import com.novacroft.nemo.tfl.services.transfer.Item;

/**
 * Convert nemo-tfl-common ItemDTO objects to nemo-tfl-services Item objects
 */
@Component("itemDTOConverter")
public class ItemConverterImpl implements ItemConverter {

    private static final int ADD_ONE_DAY = 1;
    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected SystemParameterService systemParameterService;

	@Override
	public Item convert(ItemDTO itemDTO) {
		Item item = new Item();
		item.setId(itemDTO.getExternalId());
		item.setPrice(itemDTO.getPrice());
		item.setName(itemDTO.getName());
		if (itemDTO instanceof ProductItemDTO) {
		    convertProductItem((ProductItemDTO) itemDTO, item);
		} else if (itemDTO instanceof AutoTopUpConfigurationItemDTO) {
			AutoTopUpConfigurationItemDTO autoTopUpItemDTO = (AutoTopUpConfigurationItemDTO) itemDTO;
			item.setAutoTopUpAmount(autoTopUpItemDTO.getAutoTopUpAmount());
			item.setProductType(ProductItemType.AUTO_TOP_UP.code());
        } else if (itemDTO instanceof PayAsYouGoItemDTO) {
            item.setProductType(ProductItemType.PAY_AS_YOU_GO.code());
        }
        setActivationWindowStartAndEndDates(item, null);
        return item;
    }

    protected void convertProductItem(ProductItemDTO productItemDTO, Item item) {
        item.setStartDate(productItemDTO.getStartDate());
        item.setFormattedStartDate(DateUtil.formatDate(productItemDTO.getStartDate()));
        item.setEndDate(productItemDTO.getEndDate());
        item.setFormattedEndDate(DateUtil.formatDate(productItemDTO.getEndDate()));
        item.setStartZone(productItemDTO.getStartZone());
        item.setEndZone(productItemDTO.getEndZone());
        item.setTradedDate(productItemDTO.getTradedDate());
        item.setFormattedTradedDate(DateUtil.formatDate(productItemDTO.getTradedDate()));
        item.setReminderDate(productItemDTO.getReminderDate());
        PrePaidTicketDTO prePaidTicketDTO = prePaidTicketDataService.findById(productItemDTO.getProductId());
        item.setDuration(productService.getDurationFromPrePaidTicketDTO(prePaidTicketDTO));
        item.setProductType(prePaidTicketDTO.getType());
        item.setPrePaidProductReference(prePaidTicketDTO.getExternalId());
    }

    protected void setActivationWindowStartAndEndDates(Item item, Date purchaseDate) {
        String productType = item.getProductType();
        Date activationWindowStartDate = DateUtil.getBusinessDay(purchaseDate == null ? new Date() : purchaseDate, ADD_ONE_DAY);
        switch (ProductItemType.lookUpOptionType(productType)) {
        case PAY_AS_YOU_GO:
            item.setActivationWindowStartDate(DateUtil.formatDateWithShortMonth(activationWindowStartDate));
            item.setActivationWindowExpiryDate(DateUtil.formatDateWithShortMonth(DateUtil.addDaysToDate(activationWindowStartDate,
                            DateUtil.SEVEN_DAYS)));
            break;
        case AUTO_TOP_UP:
            item.setActivationWindowStartDate(DateUtil.formatDateWithShortMonth(activationWindowStartDate));
            item.setActivationWindowExpiryDate(DateUtil.formatDateWithShortMonth(DateUtil.addDaysToDate(activationWindowStartDate,
                            DateUtil.SEVEN_DAYS)));
            break;
        case TRAVEL_CARD:
            Date fiveDaysBeforeStartDateForTravelCard = DateUtil.getFiveDaysBefore(item.getStartDate());
            item.setActivationWindowStartDate(DateUtil.formatDateWithShortMonth(DateUtil.isAfter(fiveDaysBeforeStartDateForTravelCard,
                            activationWindowStartDate) ? fiveDaysBeforeStartDateForTravelCard : activationWindowStartDate));
            item.setActivationWindowExpiryDate(DateUtil.formatDateWithShortMonth(DateUtil.getTwoDaysAfter(item.getStartDate())));
            break;
        case BUS_PASS:
            Date fiveDaysBeforeStartDateForBusPass = DateUtil.getFiveDaysBefore(activationWindowStartDate);
            item.setActivationWindowStartDate(DateUtil.formatDateWithShortMonth(DateUtil.isAfter(fiveDaysBeforeStartDateForBusPass,
                            activationWindowStartDate) ? fiveDaysBeforeStartDateForBusPass : activationWindowStartDate));
            item.setActivationWindowExpiryDate(DateUtil.formatDateWithShortMonth(DateUtil.getTwoDaysAfter(activationWindowStartDate)));
            break;
        }
    }

    @Override
    public List<Item> convert(List<ItemDTO> itemDTOList) {
        List<Item> itemList = new ArrayList<Item>(itemDTOList.size());
        for (ItemDTO itemDTO : itemDTOList) {
            itemList.add(convert(itemDTO));
        }
        return itemList;
    }

    @Override
    public List<Item> convert(List<ItemDTO> itemDTOList, Date orderDate) {
        List<Item> itemList = new ArrayList<Item>(itemDTOList.size());
        for (ItemDTO itemDTO : itemDTOList) {
            Item item = convert(itemDTO);
            setActivationWindowStartAndEndDates(item, orderDate);
            itemList.add(item);
        }
        return itemList;
    }
}
