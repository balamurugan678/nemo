package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.converter.impl.CardConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardDAO;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

/**
 * Card data service implementation
 */
@Service(value = "cardDataService")
@Transactional
public class CardDataServiceImpl extends BaseDataServiceImpl<Card, CardDTO> implements CardDataService {
    static final Logger logger = LoggerFactory.getLogger(CardDataServiceImpl.class);

    public CardDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(CardDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CardConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Card getNewEntity() {
        return new Card();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<CardDTO> findByCustomerId(Long customerId) {
    	final String hsql = "from Card c where c.customerId = ?";
    	List<Card> cards = dao.findByQuery(hsql, customerId);
    	if (cards != null) {
    		List<CardDTO> dtoCards = new ArrayList<CardDTO>();
    		for (Card card : cards) {
    			dtoCards.add(converter.convertEntityToDto(card));
    		}
    		return dtoCards;
    	} else {
    		throw new DataServiceException(String.format(PrivateError.CARD_DETAILS_NOT_AVAILABLE.message(), customerId));
    	}
    }

    @Override
    public List<CardDTO> findByUsername(String username) {
        final String hsql =
                "select c from Card c, Customer cu where c.customerId = cu.id and c.cardNumber != null and cu.username = " +
                        "? order by c.cardNumber";
        return convert(dao.findByQuery(hsql, username));
    }

    @Override
    public CardDTO findByCardNumber(String cardNumber) {
        final String hsql = "from Card c where c.cardNumber = ?";
        Card card = dao.findByQueryUniqueResult(hsql, cardNumber);
        if (card != null) {
            return this.converter.convertEntityToDto(card);
        }
        return null;
    }

    @Override
    public CardDTO findByCustomerIdAndCardNumber(Long customerId, String cardNumber) {
        final String hsql = "from Card c where c.customerId = ? and c.cardNumber = ?";
        Card card = dao.findByQueryUniqueResult(hsql, customerId, cardNumber);
        return (card != null) ? this.converter.convertEntityToDto(card) : null;
    }

    @Override
    public List<CardDTO> findByPaymentCardId(Long paymentCardId) {
        final String hsql = "select c from Card c where c.paymentCardId = ?";
        return convert(dao.findByQuery(hsql, paymentCardId));
    }

    @Override
    public List<CardDTO> findHotlistedCards() {
        final String hsql = "from Card c where c.hotlistReason.id > 0";
        @SuppressWarnings("unchecked") List<Card> cards = dao.findByQuery(hsql);
        if (cards != null) {
            List<CardDTO> dtoCards = new ArrayList<CardDTO>();
            for (Card card : cards) {
                dtoCards.add(converter.convertEntityToDto(card));
            }
            return dtoCards;
        } else {
            throw new DataServiceException(String.format(PrivateError.CARD_DETAILS_NOT_AVAILABLE.message(), StringUtil.EMPTY_STRING));
        }
    }

    @Override
    public List<CardDTO> findHotlistedCardsWithReason() {
        final String hsql = "from Card c where c.hotlistReason.id > 0";
        @SuppressWarnings("unchecked") List<Card> cards = dao.findByQuery(hsql);
        if (cards != null) {
            List<CardDTO> dtoCards = new ArrayList<CardDTO>();
            for (Card card : cards) {
                card.getHotlistReason();
                dtoCards.add(converter.convertEntityToDto(card));
            }
            return dtoCards;
        } else {
            throw new DataServiceException(String.format(PrivateError.CARD_DETAILS_NOT_AVAILABLE.message(), StringUtil.EMPTY_STRING));
        }
    }
    
    @Override
    public List<CardDTO> getAllCardsFromUserExceptCurrent(String cardNumber) {
        List<CardDTO> cards = findByCustomerId(findByCardNumber(cardNumber).getCustomerId());
        return deleteCardFromList(cards, cardNumber);
    }
    
    protected List<CardDTO> deleteCardFromList(List<CardDTO> cards, String cardNumber) {
        CardDTO currentCard = findByCardNumber(cardNumber);
        for (CardDTO card : cards) {
            if (currentCard.getCardNumber().equals(card.getCardNumber())) {
                cards.remove(card);
                break;
            }
        }
        return cards;
    }
    
    @Override
    public CardDTO findByCustomerIdAndExternalId(Long customerId, Long externalId) {
        final String hsql = "from Card c where c.customerId = ? and c.externalId = ?";
        Card card = dao.findByQueryUniqueResult(hsql, customerId, externalId);
        return (card != null) ? this.converter.convertEntityToDto(card) : null;
    }

}
