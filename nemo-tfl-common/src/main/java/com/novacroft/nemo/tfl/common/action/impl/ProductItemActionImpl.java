package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.common.utils.DateUtil.getBusinessDay;
import static com.novacroft.nemo.common.utils.DateUtil.getMidnightDay;
import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.NonOddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.OddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.ProductItemRefundCalculationService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.EmailReminder;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Action to handle the product item specific actions
 */
@Component("productItemAction")
public class ProductItemActionImpl implements ItemDTOAction {

    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected BusPassService busPassService;
    @Autowired
    protected ProductItemRefundCalculationService productItemRefundCalculationService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected OddPeriodTravelCardService oddPeriodTravelCardService;
    @Autowired
    protected NonOddPeriodTravelCardService nonOddPeriodTravelCardService;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
        ProductItemDTO productItemDTO = null;
        if (cartItemCmd.getTicketType().contains(ProductItemType.BUS_PASS.code())) {
            productItemDTO = buildBusPassItemFromCartItemCmdImpl(cartItemCmd);
        } else {
            productItemDTO = buildTravelCardItemFromCartItemCmdImpl(cartItemCmd);
        }
        productItemDTO = updateNoneForNoEmailReminder(productItemDTO);
        return productItemDTO;
    }

    protected ProductItemDTO buildBusPassItemFromCartItemCmdImpl(CartItemCmdImpl cmd) {
        ProductItemDTO productItem = new ProductItemDTO();
        productItem = nonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(cmd);
        return productItem;
    }

    protected ProductItemDTO buildTravelCardItemFromCartItemCmdImpl(CartItemCmdImpl cmd) {
        ProductItemDTO productItem = null;
        if (cmd != null) {
            if (cmd.getTravelCardType().equalsIgnoreCase(Durations.OTHER.getDurationType())) {
                productItem = oddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(cmd);
            } else {
                productItem = nonOddPeriodTravelCardService.convertCartItemCmdImplToProductItemDTO(cmd);
            }
        }
        return productItem;
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        ProductItemDTO target = (ProductItemDTO) itemDTOToBeUpdated;
        ProductItemDTO source = (ProductItemDTO) newItemDTO;
        target.setStartDate(source.getStartDate());
        target.setEndDate(source.getEndDate());
        target.setPrice(source.getPrice());
        target.setReminderDate(source.getReminderDate());
        return target;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO, Boolean isRefundCalculationRequired) {
        ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
        productItemDTO = setNameForProductItemDTO(productItemDTO);
        productItemDTO = setNameForTradedTicketProductItemDTO(productItemDTO);
        if (isRefundCalculationRequired) {
            productItemDTO.setRefund(productItemRefundCalculationService.calculateRefund(productItemDTO));
        }
        return productItemDTO;
    }

    protected ProductItemDTO setNameForProductItemDTO(ProductItemDTO productItemDTO) {
        ProductItemDTO updateProductItemDTO = productItemDTO;
        updateProductItemDTO.setName(productService.getProductName(productItemDTO));
        return updateProductItemDTO;
    }

    protected ProductItemDTO setNameForTradedTicketProductItemDTO(ProductItemDTO productItemDTO) {
        ProductItemDTO updateProductItemDTO = productItemDTO;
        if (updateProductItemDTO.getRelatedItem() != null) {
            updateProductItemDTO.getRelatedItem().setName(productService.getProductName((ProductItemDTO) updateProductItemDTO.getRelatedItem()));
        }
        return updateProductItemDTO;
    }

    @Override
    public Boolean hasItemExpired(Date currentDate, ItemDTO itemDTO) {
        ProductItemDTO productItem = (ProductItemDTO) itemDTO;
        Date userCartStartDate = getMidnightDay(getBusinessDay(currentDate,
                        systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)));
        if (productItem.getStartDate() != null && isAfter(userCartStartDate, productItem.getStartDate())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public ItemDTO updateItemDTOForBackDatedAndDeceased(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        ProductItemDTO target = (ProductItemDTO) itemDTOToBeUpdated;
        ProductItemDTO source = (ProductItemDTO) newItemDTO;
        if (null != source && null != source.getTicketBackdated() && source.getTicketBackdated()) {
            target.setTicketBackdated(source.getTicketBackdated());
            target.setBackdatedRefundReasonId(source.getBackdatedRefundReasonId());
        }
        if (null != source && null != source.getDeceasedCustomer() && source.getDeceasedCustomer()) {
            target.setDeceasedCustomer(source.getDeceasedCustomer());
        }
        return target;
    }

    protected ProductItemDTO updateNoneForNoEmailReminder(ProductItemDTO productItemDTO) {
        String reminderDate = productItemDTO.getReminderDate();
        if (null != reminderDate) {
            String reminderDateExcludingDaysPriorString = reminderDate.replace(CartAttribute.DAYS_BEFORE_EXPIRY, StringUtil.EMPTY_STRING).trim();
            if (EmailReminder.NO_REMINDER.code().equals(reminderDateExcludingDaysPriorString)) {
                productItemDTO.setReminderDate(CartAttribute.NO_REMINDER);
            }
        }
        return productItemDTO;
    }
}
