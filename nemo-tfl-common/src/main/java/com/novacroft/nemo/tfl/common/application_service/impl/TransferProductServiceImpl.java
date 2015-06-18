package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.TransferConstants.IS_AUTO_TOP_UP_ENABLED_IN_TARGET_CARD;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.IS_TRANSFER_SUCCESSFUL;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.SEVEN_DAYS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.ConvertCubicCardDetailsToCartDTOService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TransferProductService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.AutoTopUpConfirmationEmailService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Service("transferProductService")
public class TransferProductServiceImpl implements TransferProductService {

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CartDataService cartDataService;

    @Autowired
    protected OrderDataService orderDataService;

    @Autowired
    protected AutoTopUpConfigurationService autoTopUpConfigurationService;

    @Autowired
    protected CardUpdateService cardUpdateService;

    @Autowired
    protected HotlistCardService hotlistCardService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected ConvertCubicCardDetailsToCartDTOService convertCubicCardDetailsToCartDTOService;

    @Autowired
    protected PayAsYouGoService payAsYouGoService;
    @Autowired
    protected TravelCardService travelCardService;

    @Autowired
    protected CustomerDataService customerDataService;

    @Autowired
    protected BaseEmailPreparationService baseEmailPreparationService;

    @Autowired
    protected AutoTopUpConfirmationEmailService transferProductsConfirmationEmailService;
    @Autowired
    protected LocationDataService locationDataService;

    @Autowired
    protected PayAsYouGoDataService payAsYouGoDataService;

    @Autowired
    protected CardRefundableDepositDataService cardRefundableDepositDataService;

    @Autowired
    protected HotlistReasonDataService hotlistReasonDataService;

    static final Logger logger = LoggerFactory.getLogger(TransferProductServiceImpl.class);

    @Override
    @Transactional
    public Map<String, Object> transferProductFromSourceCardToTargetCard(HttpSession session, Long sourcecardId, Long targetCardId, Long stationId,
                    Boolean isLostOrStolen) {
        Map<String, Object> transferProductsTransactionData = new HashMap<String, Object>();
        transferProductsTransactionData.put(IS_AUTO_TOP_UP_ENABLED_IN_TARGET_CARD, !isNoAutoTopUpEnabledInTargetCard(targetCardId));
        CartDTO sourceCartDTO = createAndPersistSourceCardItemsToSourceCart(session, sourcecardId);
        CartDTO targetCartDTO = createAndPersistTargetCardItemsToTargetCart(sourceCartDTO, targetCardId);
        addSourceCardRefundableDepositAmountToTargetCardPayGAmount(sourceCartDTO, targetCartDTO, isLostOrStolen);
        CardDTO cardDTO = findCardById(targetCardId);
        OrderDTO orderDTO = createOrderFromCart(cardDTO.getCustomerId(), 0, stationId, targetCardId, targetCartDTO);
        targetCartDTO.setOrder(orderDTO);
        handleAutoLoadConfigurationFromSourceCardToTargetCard(sourceCartDTO, targetCartDTO, stationId);
        updateTransferredProductsInTargetCardToCubic(targetCartDTO, stationId);
        hotListSourceCard(sourcecardId);
        sendConfirmationEmail(sourceCartDTO, stationId, targetCartDTO);

        transferProductsTransactionData.put(IS_TRANSFER_SUCCESSFUL, Boolean.TRUE);
        return transferProductsTransactionData;

    }

    protected void addSourceCardRefundableDepositAmountToTargetCardPayGAmount(CartDTO sourceCartDTO, CartDTO targetCartDTO, Boolean isLostOrStolen) {
        CardRefundableDepositItemDTO sourceCardRefundableDepositItem = null;
        PayAsYouGoItemDTO targetCardPayAsYouGoItemDTO = null;
        for (ItemDTO sourceCartItem : sourceCartDTO.getCartItems()) {
            if (sourceCartItem.getClass().equals(CardRefundableDepositItemDTO.class)) {
                sourceCardRefundableDepositItem = (CardRefundableDepositItemDTO) sourceCartItem;
            }
        }
        for (ItemDTO targetCartItem : targetCartDTO.getCartItems()) {
            if (targetCartItem.getClass().equals(PayAsYouGoItemDTO.class)) {
                targetCardPayAsYouGoItemDTO = (PayAsYouGoItemDTO) targetCartItem;
            }
        }
        if (null != sourceCardRefundableDepositItem && null != targetCardPayAsYouGoItemDTO) {
            PayAsYouGoDTO payAsYouGoDTO = payAsYouGoDataService.findById(targetCardPayAsYouGoItemDTO.getPayAsYouGoId());
            CardRefundableDepositDTO cardRefundableDepositDTO = cardRefundableDepositDataService.findById(sourceCardRefundableDepositItem
                            .getCardRefundableDepositId());
            Integer effectiveTargetPayAsYouGoAmount = null;
            if (isLostOrStolen) {
                effectiveTargetPayAsYouGoAmount = payAsYouGoDTO.getTicketPrice();
            } else {
                effectiveTargetPayAsYouGoAmount = payAsYouGoDTO.getTicketPrice() + cardRefundableDepositDTO.getPrice();
            }
            payAsYouGoDTO = payAsYouGoDataService.findByTicketPrice(effectiveTargetPayAsYouGoAmount);
            targetCardPayAsYouGoItemDTO.setPayAsYouGoId(payAsYouGoDTO.getId());
            targetCardPayAsYouGoItemDTO.setPrice(effectiveTargetPayAsYouGoAmount);
        }
    }

    protected void sendConfirmationEmail(CartDTO sourcecartDTO, Long stationId, CartDTO targetCartDTO) {
        EmailArgumentsDTO emailArguments = new EmailArgumentsDTO();
        LocationDTO locationDTO = locationDataService.findById(stationId);
        CardDTO cardDTO = this.cardDataService.findById(sourcecartDTO.getCardId());
        CustomerDTO customerDTO = this.customerDataService.findById(cardDTO.getCustomerId());
        emailArguments.setToAddress(customerDTO.getEmailAddress());
        emailArguments.setSalutation(this.baseEmailPreparationService.getSalutation(customerDTO));
        emailArguments.setPickUpLocationName(locationDTO.getName());
        emailArguments.setRangeFrom(new Date());
        emailArguments.setRangeTo(DateUtil.addDaysToDate(new Date(), SEVEN_DAYS));
        emailArguments.setReferenceNumber(targetCartDTO.getOrder().getOrderNumber());
        this.transferProductsConfirmationEmailService.sendConfirmationMessage(emailArguments);

    }

    protected CartDTO createAndPersistSourceCardItemsToSourceCart(HttpSession session, Long sourcecardId) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO sourceCartDTO = cartService.findById(cartSessionData.getCartId());
        cartService.emptyCart(sourceCartDTO);
        sourceCartDTO.setCardId(sourcecardId);
        sourceCartDTO.setCartType(CartType.TRANSFER_PRODUCT.code());
        sourceCartDTO = convertCubicCardDetailsToCartDTOService.populateCartItemsToCartDTOFromCubic(sourceCartDTO, sourcecardId);
        return persistCartDTO(sourceCartDTO);
    }

    @Transactional()
    protected CartDTO persistCartDTO(CartDTO cartDTO) {
        return cartService.updateCartWithoutRefundCalculationInPostProcess(cartDTO);
    }

    protected CartDTO createAndPersistTargetCardItemsToTargetCart(CartDTO sourceCartDTO, Long targetCardId) {
        CartDTO targetCartDTO = new CartDTO();
        targetCartDTO.setCardId(targetCardId);
        targetCartDTO.setCartType(CartType.TRANSFER_PRODUCT.code());
        List<ItemDTO> sourceCartItemDTOs = sourceCartDTO.getCartItems();
        List<ItemDTO> targetCartItemDTOs = new ArrayList<ItemDTO>();
        for (ItemDTO sourceItemDTO : sourceCartItemDTOs) {
            ItemDTO targetItemDTO = cloneItem(targetCardId, sourceItemDTO);
            if (null != targetItemDTO) {
                houseKeepingSourceItems(sourceItemDTO, targetItemDTO, targetCardId);
                targetCartItemDTOs.add(targetItemDTO);
            }
        }
        targetCartDTO.setCartItems(targetCartItemDTOs);
        return targetCartDTO;
    }

    protected ItemDTO cloneItem(Long targetCardId, ItemDTO sourceItemDTO) {
        ItemDTO targetItemDTO = null;
        if (sourceItemDTO.getClass().equals(ProductItemDTO.class)) {
            targetItemDTO = (ProductItemDTO) Converter.convert(sourceItemDTO, new ProductItemDTO());
        } else if (sourceItemDTO.getClass().equals(PayAsYouGoItemDTO.class)) {
            targetItemDTO = (PayAsYouGoItemDTO) Converter.convert(sourceItemDTO, new PayAsYouGoItemDTO());
        } else if (sourceItemDTO.getClass().equals(AutoTopUpConfigurationItemDTO.class)) {
            if (isNoAutoTopUpEnabledInTargetCard(targetCardId)) {
                targetItemDTO = (AutoTopUpConfigurationItemDTO) Converter.convert(sourceItemDTO, new AutoTopUpConfigurationItemDTO());
            }
        } else if (sourceItemDTO.getClass().equals(CardRefundableDepositItemDTO.class)) {
            targetItemDTO = (CardRefundableDepositItemDTO) Converter.convert(sourceItemDTO, new CardRefundableDepositItemDTO());
        }
        return targetItemDTO;
    }

    protected OrderDTO createOrderFromCart(Long customerId, Integer totalAmount, Long stationId, Long targetCardId, CartDTO targetCartDTO) {
        OrderDTO orderDTO = new OrderDTO(customerId, new Date(), totalAmount, OrderStatus.PAID.code(), stationId);
        orderDTO.setCardId(targetCardId);
        orderDTO = updateOrderStatusAndItems(targetCartDTO, orderDTO);
        return orderDTO;
    }

    protected OrderDTO updateOrderStatusAndItems(CartDTO cartDTO, OrderDTO orderDTO) {
        if (isOrderStatusPaid(orderDTO)) {
            moveCartItemsToOrder(cartDTO, orderDTO);
        }
        orderDTO = updateOrderRecord(orderDTO);
        return orderDTO;
    }

    protected CardDTO handleAutoLoadConfigurationFromSourceCardToTargetCard(CartDTO sourceCartDTO, CartDTO targetCartDTO, Long stationId) {
        CardDTO targetCardDTO = null;
        if (sourceCartDTO.isAutoTopUpPresent() && isNoAutoTopUpEnabledInTargetCard(targetCartDTO.getCardId())) {
            this.autoTopUpConfigurationService.changeConfiguration(targetCartDTO.getCardId(), targetCartDTO.getOrder().getId(),
                            sourceCartDTO.getAutoTopUpAmount(), stationId);
            targetCardDTO = findCardById(targetCartDTO.getCardId());
            CardDTO sourceCardDTO = findCardById(sourceCartDTO.getCardId());
            targetCardDTO.setPaymentCardId(sourceCardDTO.getPaymentCardId());
            this.cardDataService.createOrUpdate(targetCardDTO);
        }
        return targetCardDTO;
    }

    protected void updateTransferredProductsInTargetCardToCubic(CartDTO targetCartDTO, Long stationId) {
        CartCmdImpl cartCmd = new CartCmdImpl();
        cartCmd.setCartDTO(targetCartDTO);
        cartCmd.setStationId(stationId);
        boolean prePayValueUpdateError = false;
        boolean prePayTicketUpdateError = false;
        prePayValueUpdateError = payAsYouGoService.updatePrePayValueToCubic(cartCmd);
        if (!prePayValueUpdateError) {
            prePayTicketUpdateError = travelCardService.addPrePayTicketToCubic(cartCmd);
        }
        verifyCubicUpdatesAllSuccessful(prePayValueUpdateError, prePayTicketUpdateError);
    }
    
    protected void verifyCubicUpdatesAllSuccessful(boolean prePayValueUpdateError, boolean prePayTicketUpdateError) {
        if (prePayTicketUpdateError || prePayValueUpdateError) {
            String errorText = String.format(PrivateError.CARD_UPDATE_REQUEST_ERROR_DETECTED.message(), prePayValueUpdateError, prePayTicketUpdateError);
            logger.error(errorText);
            throw new ApplicationServiceException(errorText);
        }
    }

    protected void hotListSourceCard(Long sourceCardId) {
        CardDTO sourceCardDTO = findCardById(sourceCardId);
        HotlistReasonDTO hotlistReasonDTO = hotlistReasonDataService.findByDescription(TransferConstants.CARD_TRANSFERRED);
        hotlistCardService.toggleCardHotlisted(sourceCardDTO.getCardNumber(), hotlistReasonDTO.getId().intValue());
        cardUpdateService.createLostOrStolenEventForHotlistedCard(sourceCardDTO.getCardNumber(), hotlistReasonDTO.getId().intValue());
    }

    protected void houseKeepingSourceItems(ItemDTO sourceItemDTO, ItemDTO targetItemDTO, Long targetCardId) {
        nullifyPropertiesOftargetItemDTO(targetItemDTO);
        setTargetCardId(targetItemDTO, targetCardId);
        linkTargetCartItemToSourceCartItem(sourceItemDTO, targetItemDTO);
    }

    protected void nullifyPropertiesOftargetItemDTO(ItemDTO itemDTO) {
        itemDTO.setId(null);
        itemDTO.setCardId(null);
    }

    protected void setTargetCardId(ItemDTO itemDTO, Long targetCardId) {
        itemDTO.setCardId(targetCardId);
    }

    protected void linkTargetCartItemToSourceCartItem(ItemDTO sourceItem, ItemDTO targetItem) {
        targetItem.setRelatedItem(sourceItem);
    }

    protected boolean isNoAutoTopUpEnabledInTargetCard(Long targetCardId) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = convertCubicCardDetailsToCartDTOService.getCardDetailsFromCubic(targetCardId);
        return !cardInfoResponseV2DTO.isAutoTopUpEnabled();
    }

    protected boolean isOrderStatusPaid(OrderDTO orderDTO) {
        return OrderStatus.PAID.code().equals(orderDTO.getStatus());
    }

    protected OrderDTO moveCartItemsToOrder(CartDTO cartDTO, OrderDTO orderDTO) {
        List<ItemDTO> orderItems = new ArrayList<ItemDTO>();
        for (ItemDTO cartItemDTO : cartDTO.getCartItems()) {
            cartItemDTO.setNullable(true);
            cartItemDTO.setCartId(null);
            cartItemDTO.setOrderId(orderDTO.getId());
            orderItems.add(cartItemDTO);
        }
        orderDTO.setOrderItems(orderItems);
        return orderDTO;
    }

    protected OrderDTO updateOrderRecord(OrderDTO orderDTO) {
        return this.orderDataService.create(orderDTO);
    }

    protected CardDTO findCardById(Long cardId) {
        return cardDataService.findById(cardId);
    }
}
