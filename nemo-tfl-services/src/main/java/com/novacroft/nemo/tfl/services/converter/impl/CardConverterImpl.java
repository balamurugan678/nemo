package com.novacroft.nemo.tfl.services.converter.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatStringAsDate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.domain.cubic.HotListReasons;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.converter.CardConverter;
import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.CardDiscount;
import com.novacroft.nemo.tfl.services.transfer.PrePayValue;
import com.novacroft.nemo.tfl.services.transfer.PendingItems;
import com.novacroft.nemo.tfl.services.transfer.Ticket;

@Component("cardResponseConverter")
public class CardConverterImpl implements CardConverter {

    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;

    @Autowired
    ProductDataService productDataService;

    @Override
    public Card convert(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        Card card = new Card();
        Converter.convert(cardInfoResponseV2DTO, card);
        addHotListReasonToCard(cardInfoResponseV2DTO, card);
        addDiscountsToCard(cardInfoResponseV2DTO, card);
        addPrePayValueToCard(cardInfoResponseV2DTO, card);
        addTicketsToCard(cardInfoResponseV2DTO, card);
        addPendingItemsToCard(cardInfoResponseV2DTO, card);
        return card;
    }

    protected void addHotListReasonToCard(CardInfoResponseV2DTO cardInfoResponseV2DTO, Card card) {
        HotListReasons hotListReasons = cardInfoResponseV2DTO.getHotListReasons();
        if (null != hotListReasons && null != hotListReasons.getHotListReasonCodes() && hotListReasons.getHotListReasonCodes().size() != 0) {
            card.setHotListReason(hotListReasons.getHotListReasonCodes().get(0));
        }

    }

    protected void addDiscountsToCard(CardInfoResponseV2DTO cardInfoResponseV2DTO, Card card) {
        addDiscountInfoToCard(card, cardInfoResponseV2DTO.getDiscountEntitlement1(), cardInfoResponseV2DTO.getDiscountExpiry1(), ONE);
        addDiscountInfoToCard(card, cardInfoResponseV2DTO.getDiscountEntitlement2(), cardInfoResponseV2DTO.getDiscountExpiry2(), TWO);
        addDiscountInfoToCard(card, cardInfoResponseV2DTO.getDiscountEntitlement3(), cardInfoResponseV2DTO.getDiscountExpiry3(), THREE);
    }

    protected void addPendingItemsToCard(CardInfoResponseV2DTO cardInfoResponseV2DTO, Card card) {
        if (null != cardInfoResponseV2DTO.getPendingItems()) {
            PendingItems pendingItems = new PendingItems();
            List<PrePayValue> prePayValues = new ArrayList<PrePayValue>();
            List<com.novacroft.nemo.common.domain.cubic.PrePayValue> cubicPrePayValues= cardInfoResponseV2DTO.getPendingItems().getPpvs();
            if (null != prePayValues) {
            	for(com.novacroft.nemo.common.domain.cubic.PrePayValue prePayValue : cubicPrePayValues) {
            		PrePayValue ppv = new PrePayValue();
            		Converter.convert(prePayValue, ppv);
            		prePayValues.add(ppv);
            	}
            	pendingItems.setPrePayValues(prePayValues);
            }
            
            List<Ticket> tickets = new ArrayList<>();
            List<PrePayTicket> prePayTickets = cardInfoResponseV2DTO.getPendingItems().getPpts();
            if (null != prePayTickets) {
                for (PrePayTicket prePayTicket : prePayTickets) {
                    Ticket ticket = new Ticket();
                    Converter.convert(prePayTicket, ticket);
                    ProductDTO productDTO = productDataService.findByProductCode(prePayTicket.getProductCode().toString(),
                                    formatStringAsDate(prePayTicket.getStartDate()));
                    if (null != productDTO) {
                        ticket.setProduct(productDTO.getProductName());
                        ticket.setZone(productDTO.getStartZone() + " - " + productDTO.getEndZone());
                        tickets.add(ticket);
                    }
                }
                pendingItems.setTickets(tickets);
            }
            card.setPendingItems(pendingItems);
        }
    }

    protected void addTicketsToCard(CardInfoResponseV2DTO cardInfoResponseV2DTO, Card card) {
        List<Ticket> tickets = new ArrayList<>();
        PrePayTicketDetails ticketDetails = cardInfoResponseV2DTO.getPptDetails();
        if (null != ticketDetails) {
            List<PrePayTicketSlot> prepayticketslots = ticketDetails.getPptSlots();
            for (PrePayTicketSlot prePayTicketSlot : prepayticketslots) {
                Ticket ticket = new Ticket();
                Converter.convert(prePayTicketSlot, ticket);
                tickets.add(ticket);
            }
        }
        if (tickets.size() > 0) {
            card.setTickets(tickets);
        }
    }

    protected void addPrePayValueToCard(CardInfoResponseV2DTO cardInfoResponseV2DTO, Card card) {
        if (null != cardInfoResponseV2DTO.getPpvDetails()) {
            PrePayValue prePayValue = new PrePayValue();
            Converter.convert(cardInfoResponseV2DTO.getPpvDetails(), prePayValue);
            card.setPrePayValue(prePayValue);
        }
    }

    protected void addDiscountInfoToCard(Card card, String entitlement, String expiry, Integer id) {
        if (!StringUtil.isEmpty(entitlement)) {
            CardDiscount cardDiscount = new CardDiscount();
            cardDiscount.setId(id);
            cardDiscount.setEntitlement(entitlement);
            cardDiscount.setExpiry(expiry);
            List<CardDiscount> cardDiscounts = card.getDiscounts();
            if (null == cardDiscounts) {
                cardDiscounts = new ArrayList<CardDiscount>();
            }
            cardDiscounts.add(cardDiscount);
            card.setDiscounts(cardDiscounts);
        }
    }

    @Override
    public Card convert(CardDTO cardDTO) {
        Card card = new Card();
        card.setPrestigeId(cardDTO.getCardNumber());
        card.setId(cardDTO.getExternalId());
        return card;
    }

}
