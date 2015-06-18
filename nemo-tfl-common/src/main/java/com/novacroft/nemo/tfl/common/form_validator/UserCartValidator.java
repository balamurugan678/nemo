package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getEndDate;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.EXCEEDS_MAXIMUM_PENDING_ITEMS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.EXCEEDS_MAXIMUM_TRAVEL_CARDS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.EXCEEDS_MAXIMUM_TRAVEL_CARDS_INCLUDING_PENDING_OR_EXISTING;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.TRAVEL_CARD_OVERLAP;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.decorator.CartItemCmdImplDecorator;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * User cart validation
 */
@Component("userCartValidator")
public class UserCartValidator extends BaseValidator {

    private static final int EMPTY_CART_COUNT = 0;
	@Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected TopUpTicketService topUpTicketService;
    @Autowired
    protected CartDataService cartDataService;
    @Autowired
    protected ZoneService zoneService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartItemCmdImpl.class.equals(targetClass);
    }

    protected void validate(CartItemCmdImpl cmd, Errors errors, Integer totalItemsToAdd) {

        CartDTO cartDTO = getCartDTOByCartId(cmd.getCartId());
        CardInfoResponseV2DTO cardInfoResponse = getCardService.getCard(getCardNumberFromCardId(cmd.getCardId()));
        rejectIfMaximumPendingItemsIsExceeded(errors, cardInfoResponse, totalItemsToAdd);

        if (!errors.hasErrors() && cmd.getStartDate() != null && cmd.getTravelCardType() != null) {
            cartDTO = topUpTicketService.updateCartDTOWithProductsFromCubic(cartDTO);
            rejectIfProductItemDTOsInCartDTOExceedMaximumAllowedTravelCardsLimit(cartDTO, cmd.getCardId(), errors);
        }

        if (!errors.hasErrors()) {
            rejectIfZonesInCartItemCmdImplOverlapWithAlreadyAddedZonesInProductItemDTOsOfCartDTO(cartDTO, cmd, errors);
        }
    }

    protected void rejectIfMaximumPendingItemsIsExceeded(Errors errors, CardInfoResponseV2DTO cardInfoResponse, Integer totalItemsToAdd) {
        if (cardInfoResponse != null && cardInfoResponse.getPendingItems() != null) {
            Integer pendingItemCount = 0;
            if (cardInfoResponse.getPendingItems().getPpts() != null && cardInfoResponse.getPendingItems().getPpts().size() > 0) {
                pendingItemCount += cardInfoResponse.getPendingItems().getPpts().size();
            }
            if (cardInfoResponse.getPendingItems().getPpvs() != null && cardInfoResponse.getPendingItems().getPpvs().size() > 0) {
                pendingItemCount += cardInfoResponse.getPendingItems().getPpvs().size();
            }

            if (pendingItemCount + totalItemsToAdd > systemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_PENDING_ITEMS)) {
                errors.reject(EXCEEDS_MAXIMUM_PENDING_ITEMS.errorCode());
            }
        }
    }

    protected String getCardNumberFromCardId(Long cardId) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        return (cardDTO != null) ? cardDTO.getCardNumber() : null;
    }

    protected CartDTO getCartDTOByCartId(Long cartId) {
        return cartService.findById(cartId);
    }

    protected void rejectIfProductItemDTOsInCartDTOExceedMaximumAllowedTravelCardsLimit(CartDTO cartDTO, Long cardId, Errors errors) {
        int productItemDTOCount = 0;
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (isItemDTOAProductItemDTO(itemDTO) && isProductItemDTONotExpired(itemDTO)
                            && isProductItemDTOCountGreaterThanEqualToMaximumAllowedTravelCards(++productItemDTOCount)) {
                registerMaximumTravelCardsErrorinErrorsObjectForCardId(errors, cardId);
                break;
            }
        }
    }

    protected boolean isItemDTOAProductItemDTO(ItemDTO itemDTO) {
        return itemDTO instanceof ProductItemDTO;
    }

    protected boolean isProductItemDTONotExpired(ItemDTO itemDTO) {
        ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
        return isTravelCardNotExpired(productItemDTO);
    }

    protected boolean isTravelCardNotExpired(ProductItemDTO productItemDTO) {
        return productItemDTO != null && DateUtil.isAfterOrEqualExcludingTimestamp(productItemDTO.getEndDate(), new Date());
    }

    protected boolean isProductItemDTOCountGreaterThanEqualToMaximumAllowedTravelCards(int productItemDTOCount) {
        return productItemDTOCount >= systemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS);
    }

    protected void registerMaximumTravelCardsErrorinErrorsObjectForCardId(Errors errors, Long cardId) {
        if (isOysterCardIncludesPendingOrExistingTravelCards(cardId)) {
            errors.reject(EXCEEDS_MAXIMUM_TRAVEL_CARDS_INCLUDING_PENDING_OR_EXISTING.errorCode());
        } else {
            errors.reject(EXCEEDS_MAXIMUM_TRAVEL_CARDS.errorCode());
        }
    }

    protected boolean isOysterCardIncludesPendingOrExistingTravelCards(Long cardId) {
        return topUpTicketService.isOysterCardIncludesPendingOrExistingTravelCards(cardId);
    }

    public void rejectIfZonesInCartItemCmdImplOverlapWithAlreadyAddedZonesInProductItemDTOsOfCartDTO(CartDTO cartDTO, CartItemCmdImpl cmd,
                    Errors errors) {
        if (isStartDateExistsInCartItemCmdImpl(cmd) && isStartZoneExistsInCartItemCmdImpl(cmd) && isEndZoneExistsInCartItemCmdImpl(cmd)) {
            for (ItemDTO itemDTO : cartDTO.getCartItems()) {
                if (isProductItemSameAsCartItem(cmd, itemDTO) && isCartItemCmdImplZonesOverlapWithItemDTOZones(cmd, itemDTO)) {
                    errors.reject(TRAVEL_CARD_OVERLAP.errorCode());
                    break;
                }
            }
        }
    }

    protected boolean isCartItemCmdImplZonesOverlapWithItemDTOZones(CartItemCmdImpl cmd, ItemDTO itemDTO) {
        Date startDate = parse(cmd.getStartDate());
        Date endDate = getEndDate(cmd.getStartDate(), cmd.getEndDate(), cmd.getTravelCardType());
        Integer startZone = cmd.getStartZone();
        Integer endZone = cmd.getEndZone();

        return isItemDTOAProductItemDTO(itemDTO)
                        && isZonesOverlapWithProductItemDTOZones(startZone, endZone, itemDTO, startDate, endDate);
    }

    protected boolean isProductItemSameAsCartItem(CartItemCmdImpl cmd, ItemDTO itemDTO) {
        return (isProductItemDTOATravelCard(itemDTO) && isCartItemATravelCard(cmd)) || (isProductItemDTOABusPass(itemDTO) && isCartItemABusPass(cmd));
    }

    protected boolean isStartDateExistsInCartItemCmdImpl(CartItemCmdImpl cmd) {
        return cmd.getStartDate() != null;
    }

    protected boolean isStartZoneExistsInCartItemCmdImpl(CartItemCmdImpl cmd) {
        return cmd.getStartZone() != null;
    }

    protected boolean isEndZoneExistsInCartItemCmdImpl(CartItemCmdImpl cmd) {
        return cmd.getEndZone() != null;
    }

    protected boolean isCartItemATravelCard(CartItemCmdImpl cmd) {
        return cmd.getTicketType().equals(ProductItemType.TRAVEL_CARD.databaseCode());
    }

    protected boolean isCartItemABusPass(CartItemCmdImpl cmd) {
        return cmd.getTicketType().equals(ProductItemType.BUS_PASS.databaseCode());
    }

    protected boolean isProductItemDTOATravelCard(ItemDTO itemDTO) {
        return StringUtils.containsIgnoreCase(itemDTO.getName(), TRAVEL_CARD);
    }

    protected boolean isProductItemDTOABusPass(ItemDTO itemDTO) {
        return StringUtils.containsIgnoreCase(itemDTO.getName(), ProductItemType.BUS_PASS.databaseCode());
    }

    protected boolean isZonesOverlapWithProductItemDTOZones(Integer startZone, Integer endZone, ItemDTO itemDTO, Date startDate, Date endDate) {
        ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
        return zoneService.isZonesOverlapWithProductItemDTOZones(startZone, endZone, productItemDTO, startDate, endDate);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	if (target instanceof CartItemCmdImplDecorator){
    		CartItemCmdImplDecorator decorator = (CartItemCmdImplDecorator) target;
    		validate(decorator, errors, decorator.getItemsInThisCart());
    	}else{
    		 CartItemCmdImpl cartItem = (CartItemCmdImpl) target;
    	      validate(cartItem, errors, EMPTY_CART_COUNT);
    	}

    }

}
