package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.DAY_WEEK_DATE_PATTERN;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.ProductDateUtil.getProductStartDates;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PRODUCT_AVAILABLE_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PRODUCT_START_AFTER_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.START_DATES;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.AdministrationFeeService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

@Service(value = "cartAdministrationService")
public class CartAdministrationServiceImpl extends BaseService implements CartAdministrationService {

    static final Logger logger = LoggerFactory.getLogger(CartAdministrationServiceImpl.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected ShippingMethodDataService shippingMethodDataService;
    @Autowired
    protected CartDataService cartDataService;
    @Autowired
    protected AdministrationFeeService administrationFeeService;

    @Override
    public CartDTO applyRefundableDeposit(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        assert (null != cartDTO);
        if (!isRefundableDepositPresentInCart(cartDTO)) {
            cartDTO = cartService.addUpdateItem(cartDTO, cartItemCmd, CardRefundableDepositItemDTO.class);
        }
        return cartDTO;
    }

    @Override
    public CartDTO applyShippingCost(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        if (null == cartItemCmd.getShippingMethodType() || StringUtil.isBlank(cartItemCmd.getShippingMethodType())) {
            if (null == cartDTO.getShippingMethodItem()) {
                cartItemCmd.setShippingMethodType(CartAttribute.SHIPPING_METHOD_FIRST_CLASS);
            } else {
                cartItemCmd.setShippingMethodType(cartDTO.getShippingMethodItem().getName());
            }
        }
        cartDTO = cartService.addUpdateItem(cartDTO, cartItemCmd, ShippingMethodItemDTO.class);
        return cartDTO;
    }

    @Override
    public CartDTO removeRefundableDepositAndShippingCost(CartDTO cartDTO) {
        assert (null != cartDTO);
        if (isRefundableDepositPresentInCart(cartDTO) && !isCartContainUserAddedItems(cartDTO)) {
            CardRefundableDepositItemDTO depositItem = getRefundableDepositFromCart(cartDTO);
            if (null != depositItem) {
                cartDTO = cartService.deleteItem(cartDTO, depositItem.getId());
            }
            ShippingMethodItemDTO shippingCostItem = getShippingCostFromCart(cartDTO);
            if (null != shippingCostItem) {
                cartDTO = cartService.deleteItem(cartDTO, shippingCostItem.getId());
            }
        }
        return cartDTO;
    }

    @Override
    public SelectListDTO getProductStartDateList() {
        List<Date> startDateList = getProductStartDates(systemParameterService.getIntegerParameterValue(PRODUCT_START_AFTER_DAYS), systemParameterService.getIntegerParameterValue(PRODUCT_AVAILABLE_DAYS), systemParameterService.getIntegerParameterValue(MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START), systemParameterService.getIntegerParameterValue(MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START));
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(START_DATES);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (Date startDate : startDateList) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(formatDate(startDate), formatDate(startDate, DAY_WEEK_DATE_PATTERN)));
        }
        return selectListDTO;
    }

    @Override
    public SelectListDTO getUserProductStartDateList() {
        List<Date> startDateList = getProductStartDates(systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS), systemParameterService.getIntegerParameterValue(PRODUCT_AVAILABLE_DAYS), systemParameterService.getIntegerParameterValue(MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START), systemParameterService.getIntegerParameterValue(MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START));
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(START_DATES);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (Date startDate : startDateList) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(formatDate(startDate), formatDate(startDate, DAY_WEEK_DATE_PATTERN)));
        }
        return selectListDTO;
    }

    protected boolean isCartContainUserAddedItems(CartDTO cartDTO) {
        boolean userAddedItemsExist = false;
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (!(itemDTO instanceof CardRefundableDepositItemDTO) && !(itemDTO instanceof ShippingMethodItemDTO)) {
                userAddedItemsExist = true;
                break;
            }
        }
        return userAddedItemsExist;
    }

    protected boolean isRefundableDepositPresentInCart(CartDTO cartDTO) {
        if (null != getRefundableDepositFromCart(cartDTO)) {
            return true;
        }
        return false;
    }

    protected CardRefundableDepositItemDTO getRefundableDepositFromCart(CartDTO cartDTO) {
        return cartDTO.getCardRefundableDepositItem();
    }

    protected boolean isShippingCostPresentInCart(CartDTO cartDTO) {
        if (null != getShippingCostFromCart(cartDTO)) {
            return true;
        }
        return false;
    }

    protected ShippingMethodItemDTO getShippingCostFromCart(CartDTO cartDTO) {
        return cartDTO.getShippingMethodItem();
    }

    @Override
    public Long addApprovalId(CartDTO cartDTO) {
        cartDataService.addApprovalId(cartDTO);
        return cartDTO.getApprovalId();
    }

    @Override
    public void addOrRemoveAdministrationFeeToCart(CartDTO cartDTO, Long cardId, String cartType) {
	 
    	int travelCardWithBusPassAmount = 0;
    	Boolean isTravelCardOrBusPassPresent = false;
        Long administrationFeeCartItemId=null;
        
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof ProductItemDTO) {
            	isTravelCardOrBusPassPresent = true;
            	travelCardWithBusPassAmount += getRefundAmountInItemDTO(itemDTO);
            }
            if (itemDTO instanceof AdministrationFeeItemDTO) { 
                administrationFeeCartItemId=itemDTO.getId();
            }
        }
        
    	if(isTravelCardOrBusPassPresent){
	    	addUpdateAdministrationFeeItem(cartDTO, cardId, cartType);
    	} else if(administrationFeeCartItemId != null){
    		deleteAdministrationFeeItem(cartDTO, administrationFeeCartItemId);
    	}
    	updateAdministrationFeeAmount(cartDTO,travelCardWithBusPassAmount, getDefaultAdministrationFeeValue(cartType),cardId,cartType);
    }

    protected Integer getDefaultAdministrationFeeValue(String cartType){
        AdministrationFeeDTO administrationFeeDTO = administrationFeeService.getAdministrationFeeDTO(cartType);
        return administrationFeeDTO != null? administrationFeeDTO.getPrice() : 0;
    }
    
    protected void updateAdministrationFeeAmount(CartDTO cartDTO, int travelCardWithBusPassAmount, int defaultAdminFeeAmount, Long cardId, String cartType) {
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof AdministrationFeeItemDTO && 
                            (travelCardWithBusPassAmount<itemDTO.getPrice() || travelCardWithBusPassAmount < defaultAdminFeeAmount)){
                itemDTO.setPrice(travelCardWithBusPassAmount);
                addUpdateAdministrationFeeItem(cartDTO, cardId, cartType);
            } else if (itemDTO instanceof AdministrationFeeItemDTO && 
                            travelCardWithBusPassAmount >= defaultAdminFeeAmount) {
                itemDTO.setPrice(defaultAdminFeeAmount);
                addUpdateAdministrationFeeItem(cartDTO, cardId, cartType);
            }
        }
    }

	protected void addUpdateAdministrationFeeItem(CartDTO cartDTO, Long cardId,
			String cartType) {
		CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl();
		cartItemCmdImpl.setCardId(cardId);
		cartItemCmdImpl.setCartType(cartType);
		cartService.addUpdateItem(cartDTO, cartItemCmdImpl, AdministrationFeeItemDTO.class);
	}

	protected void deleteAdministrationFeeItem(CartDTO cartDTO, Long cartItemId) {
    		assert cartItemId != null;
    		cartService.deleteItem(cartDTO, cartItemId);
	}
    
    protected int getRefundAmountInItemDTO(ItemDTO itemDTO) {
        if (itemDTO instanceof ProductItemDTO) {
            ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
            if (productItemDTO.getRefund() != null) {
            return productItemDTO.getRefund().getRefundAmount() != null ? productItemDTO.getRefund().getRefundAmount().intValue() : null; 
            }
        } 
        return itemDTO.getPrice() == null ? 0 : itemDTO.getPrice();
    }
}
