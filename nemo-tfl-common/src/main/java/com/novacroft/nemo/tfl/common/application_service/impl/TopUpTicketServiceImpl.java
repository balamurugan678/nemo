package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatStringAsDate;
import static com.novacroft.nemo.common.utils.DateUtil.isBefore;
import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.PRO_RATA;
import static com.novacroft.nemo.tfl.common.constant.TicketType.TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToEndZone;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToStartZone;

import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Service("topUpTicketService")
public class TopUpTicketServiceImpl implements TopUpTicketService {
    static final Logger logger = LoggerFactory.getLogger(TopUpTicketServiceImpl.class);
    private static final Integer PREPAY_TICKET_INVALID_STATE = 0;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected CardDataService cardDataService;
    
    @Autowired
    protected ProductDataService productDataService;


    @Override
    public boolean isOysterCardIncludesPendingOrExistingTravelCards(Long cardId) {
    	CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardProductDTOFromCubic(getCardNumberFromCardId(cardId));
    	if (cardInfoResponseV2DTO != null && (isOysterCardIncludesExistingTravelCards(cardInfoResponseV2DTO) ||
    				isOysterCardIncludesPendingTravelCards(cardInfoResponseV2DTO))) {
    		return true;
    	}
    	
    	return false;
    }
    
    protected boolean isOysterCardIncludesExistingTravelCards(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        if (cardInfoResponseV2DTO.getPptDetails() != null && cardInfoResponseV2DTO.getPptDetails().getPptSlots() != null) {
            return !cardInfoResponseV2DTO.getPptDetails().getPptSlots().isEmpty();
        } 
        
        return false;
    }
    
    protected boolean isOysterCardIncludesPendingTravelCards(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        if (cardInfoResponseV2DTO.getPendingItems() != null && cardInfoResponseV2DTO.getPendingItems().getPpts() != null) {
        	return !cardInfoResponseV2DTO.getPendingItems().getPpts().isEmpty();
        } 
        
        return false;
    }
    
    @Override
    public CartCmdImpl updateCartItemCmdWithProductsFromCubic(CartCmdImpl cartCmdImpl) {
    	CartCmdImpl cmd = cartCmdImpl;
    	
    	CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardProductDTOFromCubic(getCardNumberFromCardId(cmd.getCardId()));
    	if (cardInfoResponseV2DTO != null) {
            cmd = updateCartCmdImplWithTickets(cardInfoResponseV2DTO, cmd.getCardId(), cmd);
            cmd = updateCartCmdImplWithPendingTickets(cardInfoResponseV2DTO, cmd.getCardId(), cmd);
        }
    	
    	return cmd;
    }
    
    public String getCardNumberFromCardId(Long cardId) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        return (cardDTO != null) ? cardDTO.getCardNumber() : null;
    }


    protected CardInfoResponseV2DTO getCardProductDTOFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    protected CartCmdImpl updateCartCmdImplWithTickets(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartCmdImpl cartCmdImpl) {
    	CartCmdImpl cmd = cartCmdImpl;
        if (cardInfoResponseV2DTO.getPptDetails() != null && cardInfoResponseV2DTO.getPptDetails().getPptSlots() != null) {
            for (PrePayTicketSlot prePayTicketSlot : cardInfoResponseV2DTO.getPptDetails().getPptSlots()) {
                assert(prePayTicketSlot.getProduct() != null);
                cmd.getCartItemList().add(convertPrePayTicketSlotToCartItemCmdImpl(prePayTicketSlot, cardId));
            }
        } else {
            logger.debug("No PPT Details found for Card with card id = " + cardId);
        }
        
        return cmd;
    }
    
    protected CartItemCmdImpl convertPrePayTicketSlotToCartItemCmdImpl(PrePayTicketSlot prePayTicketSlot, Long cardId) {
        if (isNotBlank(prePayTicketSlot.getProduct())) {
            String travelCardType = getTravelCardDurationFromStartAndEndDate(prePayTicketSlot.getStartDate(), prePayTicketSlot.getExpiryDate());
            Integer startZone = convertCubicZoneDescriptionToStartZone(prePayTicketSlot.getZone());
            Integer endZone = convertCubicZoneDescriptionToEndZone(prePayTicketSlot.getZone());

            CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl(null, prePayTicketSlot.getProduct(), prePayTicketSlot.getStartDate(),
                            prePayTicketSlot.getExpiryDate(), null, null, startZone, endZone, PRO_RATA.code());
            cartItemCmdImpl.setCardId(cardId);
            cartItemCmdImpl.setTicketType(TRAVEL_CARD.code());
            cartItemCmdImpl.setTravelCardType(travelCardType);

            return cartItemCmdImpl;
        } else {
        	return null;
        }
    }
    
    protected CartCmdImpl updateCartCmdImplWithPendingTickets(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartCmdImpl cartCmdImpl) {
    	CartCmdImpl cmd = cartCmdImpl;
        if (cardInfoResponseV2DTO.getPendingItems() != null && cardInfoResponseV2DTO.getPendingItems().getPpts() != null) {
            for (PrePayTicket prePayTicket : cardInfoResponseV2DTO.getPendingItems().getPpts()) {
                cmd.getCartItemList().add(convertPrePayTicketToCartItemCmdImpl(prePayTicket, cardId));
            }
        } else {
            logger.debug("No PPT Details found for Card with card id = " + cardId);
        }
        
        return cmd;
    }
    
    protected CartItemCmdImpl convertPrePayTicketToCartItemCmdImpl(PrePayTicket prePayTicket, Long cardId) {
        if (prePayTicket.getProductCode() > 0) {
            ProductDTO productDTO = productDataService.findByProductCode(String.valueOf(prePayTicket.getProductCode()),
                            formatStringAsDate(prePayTicket.getStartDate()));
            String travelCardType = getTravelCardDurationFromStartAndEndDate(prePayTicket.getStartDate(), prePayTicket.getExpiryDate());
            Integer startZone = productDTO.getStartZone();
            Integer endZone = productDTO.getEndZone();
            
            CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl(null, productDTO.getProductName(), prePayTicket.getStartDate(),
                            prePayTicket.getExpiryDate(), null, null, startZone, endZone, PRO_RATA.code());
            cartItemCmdImpl.setCardId(cardId);
            cartItemCmdImpl.setTicketType(TRAVEL_CARD.code());
            cartItemCmdImpl.setTravelCardType(travelCardType);

            return cartItemCmdImpl;
        } else {
        	return null;
        }
    }
    
    @Override
    public void removeExpiredPrePaidTicketsInACard(CardDTO card, CardInfoResponseV2DTO cardInfo) {
        if (cardInfo.getPptDetails() != null && cardInfo.getPptDetails().getPptSlots() != null) {
            searchAndRemoveExpiredPrePaidTickets(cardInfo.getPptDetails().getPptSlots().iterator());
        }
    }
    
    protected void searchAndRemoveExpiredPrePaidTickets(Iterator<PrePayTicketSlot> ppTicketSlots){
        if(ppTicketSlots != null){
            while(ppTicketSlots.hasNext()){
                if(isDateExpired(ppTicketSlots.next().getExpiryDate())){
                    ppTicketSlots.remove();
                }
            }
        }
    }
    
    protected Boolean isDateExpired(String date) {
        String todayDate = formatDate(new Date());
        return isBefore(formatStringAsDate(date), formatStringAsDate(todayDate));
    }
    
    @Override
    public CartDTO updateCartDTOWithProductsFromCubic(CartDTO cartDTO) {
        
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardProductDTOFromCubic(getCardNumberFromCardId(cartDTO.getCardId()));
        if (cardInfoResponseV2DTO != null) {
            cartDTO = updateCartDTOWithTickets(cardInfoResponseV2DTO, cartDTO.getCardId(), cartDTO);
            cartDTO = updateCartDTOWithPendingTickets(cardInfoResponseV2DTO, cartDTO.getCardId(), cartDTO);
        }
        
        return cartDTO;
    }
    
    protected CartDTO updateCartDTOWithTickets(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartDTO cartDTO) {
        if (cardInfoResponseV2DTO.getPptDetails() != null && cardInfoResponseV2DTO.getPptDetails().getPptSlots() != null) {
            for (PrePayTicketSlot prePayTicketSlot : cardInfoResponseV2DTO.getPptDetails().getPptSlots()) {
                if(null == prePayTicketSlot.getState() || prePayTicketSlot.getState()!= PREPAY_TICKET_INVALID_STATE){
                    ProductItemDTO productItemDTO = convertPrePayTicketSlotToCartItemDTO(prePayTicketSlot, cardId);
                    if (null != productItemDTO) {
                        cartDTO.getCartItems().add(productItemDTO);
                    }
                }
                
            }
        } else {
            logger.debug("No Pre paid ticket Details found for Card with card id = " + cardId);
        }
        
        return cartDTO;
    }
    
    protected CartDTO updateCartDTOWithPendingTickets(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartDTO cartDTO) {
        if (cardInfoResponseV2DTO.getPendingItems() != null && cardInfoResponseV2DTO.getPendingItems().getPpts() != null) {
            for (PrePayTicket prePayTicket : cardInfoResponseV2DTO.getPendingItems().getPpts()) {
                cartDTO.getCartItems().add(convertPrePayTicketToCartItemDTO(prePayTicket, cardId));
            }
        } else {
            logger.debug("No Pre paid pending ticket Details found for Card with card id = " + cardId);
        }
        
        return cartDTO;
    }
    
    protected ProductItemDTO convertPrePayTicketSlotToCartItemDTO(PrePayTicketSlot prePayTicketSlot, Long cardId) {
        ProductItemDTO  productItemDTO = null;
        if (isNotBlank(prePayTicketSlot.getProduct())) {
            Integer startZone = convertCubicZoneDescriptionToStartZone(prePayTicketSlot.getZone());
            Integer endZone = convertCubicZoneDescriptionToEndZone(prePayTicketSlot.getZone());
            
            Date startDate = formatStringAsDate(prePayTicketSlot.getStartDate());
            Date endDate = formatStringAsDate(prePayTicketSlot.getExpiryDate());
            productItemDTO = new ProductItemDTO(null, prePayTicketSlot.getProduct(), cardId, null, null, startDate, endDate, startZone, endZone, null, PRO_RATA.code(), null);
            productItemDTO.setCardId(cardId);
        } 
        return productItemDTO;
    }
    
    protected ProductItemDTO convertPrePayTicketToCartItemDTO(PrePayTicket prePayTicket, Long cardId) {
        ProductItemDTO  productItemDTO = null;
        if (prePayTicket.getProductCode() > 0) {
            ProductDTO productDTO = productDataService.findByProductCode(String.valueOf(prePayTicket.getProductCode()),
                            formatStringAsDate(prePayTicket.getStartDate()));
            Integer startZone = productDTO.getStartZone();
            Integer endZone = productDTO.getEndZone();
            
            Date startDate = formatStringAsDate(prePayTicket.getStartDate());
            Date endDate = formatStringAsDate(prePayTicket.getExpiryDate());
            productItemDTO = new ProductItemDTO(null, productDTO.getProductName(), cardId, null, productDTO.getId(), startDate, endDate, startZone, endZone, null, PRO_RATA.code(), null);
            
            productItemDTO.setCardId(cardId);
        } 
        return productItemDTO;
    }

    @Override
    public CartDTO updateCartDTOWithProductsFromCubic(CartDTO cartDTO, CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        if(null == cardInfoResponseV2DTO){
            cardInfoResponseV2DTO = getCardProductDTOFromCubic(getCardNumberFromCardId(cartDTO.getCardId()));
        }
        
        if (cardInfoResponseV2DTO != null) {
            cartDTO = updateCartDTOWithTickets(cardInfoResponseV2DTO, cartDTO.getCardId(), cartDTO);
            cartDTO = updateCartDTOWithPendingTickets(cardInfoResponseV2DTO, cartDTO.getCardId(), cartDTO);
        }
        
        return cartDTO;
    }
}
