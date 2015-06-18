package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.HOTLIST_REASON_1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

/**
 * Utilities for Card tests
 */
public final class CardTestUtil extends CommonCardTestUtil {

    public static final Integer AUTO_TOPUP_AMT = 2000;
    public static final Integer AUTO_TOPUP_AMT_ZERO = 0;
    public static final Integer AUTO_TOPUP_CREDIT_BALANCE = 2000;
    public static final Integer LOW_CREDIT_BALANCE = 500;
    public static final Integer HIGH_CREDIT_BALANCE = 5000;
    public static final Integer MINIMUM_AUTO_TOP_UP_AMT = 1000;
    public static final Long CARD_ID = 100l;
    public static final Integer NULL_AUTO_TOPUP_AMOUNT = 0;
    public static final Integer ZERO_CREDIT_BALANCE = 0;
    public static final Long EXTERNAL_CARD_ID = 200L;
    public static final String TEST_OBJECT_MESSAGE = "test_object_message";
    public static final String TEST_OBJECT_NAME = "test_object_name";
    public static final Long TRANSFER_CARD_ID = 14901L;
    public static final int CHECKSUM_LENGTH = 2;
    public static final int OYSTER_CARD_NUMBER_LENGTH = 12;

    public static List<CardDTO> getTestCards1And2() {
        List<CardDTO> testCards = new ArrayList<CardDTO>(2);
        testCards.add(getTestCardDTO1());
        testCards.add(getTestCardDTO2());
        return testCards;
    }

    public static List<CardDTO> getTestCard1() {
        return new ArrayList<CardDTO>() {
            {
                add(getTestCardDTO1());
            }
        };
    }

    public static List<CardDTO> getTestHotlistedCards1And2() {
        List<CardDTO> testCards = new ArrayList<CardDTO>(2);
        HotlistReason hotlistReason = new HotlistReason();
        hotlistReason.setId(1L);
        hotlistReason.setDescription("test description");
        CardDTO testCardDTO1 = getTestCardDTO1();
        CardDTO testCardDTO2 = getTestCardDTO2();
        testCardDTO1.setHotlistReason(hotlistReason);
        testCardDTO2.setHotlistReason(hotlistReason);
        testCardDTO1.setHotlistStatus("readytoexport");
        testCardDTO2.setHotlistStatus("readytoexport");
        testCards.add(testCardDTO1);
        testCards.add(testCardDTO2);
        return testCards;
    }

    public static CardDTO getTestCardDTO1() {
        CardDTO cardDTO = getTestCard(CARD_ID_1, OYSTER_NUMBER_1, CUSTOMER_ID_1);
        cardDTO.setExternalId(EXTERNAL_CARD_ID);
        return cardDTO;
    }

    public static CardDTO getTestCardDTO2() {
        return getTestCard(CARD_ID_2, OYSTER_NUMBER_2, CUSTOMER_ID_1);
    }

    public static CardDTO getTestCardDTOWithoutCardNumber() {
        return getTestCard(CARD_ID_1, null, CUSTOMER_ID_1);
    }
   
    public static CardDTO getTestCardDTOWithInvalidCardNumber() {
        return getTestCard(CARD_ID_1, INVALID_CARD_NUMBER, CUSTOMER_ID_1);
    }
   
    public static List<CardDTO> getTestCardList1() {
        List<CardDTO> cards = new ArrayList<CardDTO>();
        cards.add(getTestCardDTO1());
        return cards;
    }

    public static SelectListDTO getTestCards1List() {
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("cards");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        selectListDTO.getOptions().add(getTestCard1Option());
        return selectListDTO;
    }

    public static SelectListDTO getTestCards2List() {
        SelectListDTO selectListDTO = getTestCards1List();
        selectListDTO.getOptions().add(getTestCard1Option());
        return selectListDTO;
    }

    public static SelectListOptionDTO getTestCard1Option() {
        return getTestSelectListOptionDTO(2L, CARD_ID_1.toString(), 10);
    }

    public static SelectListOptionDTO getTestCard2Option() {
        return getTestSelectListOptionDTO(4L, CARD_ID_2.toString(), 20);
    }

    public static CardDTO getTestCard(Long cardId, String cardNumber, Long customerId) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(cardId);
        cardDTO.setCardNumber(cardNumber);
        cardDTO.setCustomerId(customerId);
        return cardDTO;
    }

    public static CardDTO getTestCard(String cardNumber, Long customerId) {
        return getTestCard(null, cardNumber, customerId);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTO(Long id, String value, Integer displayOrder) {
        SelectListOptionDTO optionDTO = new SelectListOptionDTO();
        optionDTO.setId(id);
        optionDTO.setValue(value);
        optionDTO.setDisplayOrder(displayOrder);
        return optionDTO;
    }

    public static Card getTestCardObject1() {
        return getTestCardObject(CARD_ID_1, OYSTER_NUMBER_1, CUSTOMER_ID_1);
    }

    public static Card getTestCardObject2() {
        return getTestCardObject(CARD_ID_2, OYSTER_NUMBER_2, CUSTOMER_ID_1);
    }

    public static Card getTestCardObject3() {
        return getTestCardObject(CARD_ID_3, OYSTER_NUMBER_3, WEBACCOUNT_ID_3);
    }

    public static Card getTestCardObject(Long cardId, String cardNumber, Long customerId) {
        Card card = new Card();
        card.setId(cardId);
        card.setCardNumber(cardNumber);
        card.setCustomerId(customerId);
        return card;
    }

    public static CardDTO getTestCardDTOHotlistStatus() {
        CardDTO cardDTO = getTestCard(CARD_ID_1, OYSTER_NUMBER_1, CUSTOMER_ID_1);
        HotlistReason hotlistReason = new HotlistReason();
        hotlistReason.setId(18L);
        hotlistReason.setDescription(HOTLIST_REASON_1);
        cardDTO.setHotlistReason(hotlistReason);
        return cardDTO;
    }

    public static CardDTO getTestCardDTONotHotlistStatus() {
        CardDTO cardDTO = getTestCard(CARD_ID_1, OYSTER_NUMBER_1, CUSTOMER_ID_1);
        HotlistReason hotlistReason = new HotlistReason();
        cardDTO.setHotlistReason(hotlistReason);
        return cardDTO;
    }

    public static List<ObjectError> getTestListOfValidationErrors() {
        ObjectError error = getTestObjectError();
        List<ObjectError> objectErrorsList = new ArrayList<ObjectError>();
        objectErrorsList.add(error);
        return objectErrorsList;
    }

    public static ObjectError getTestObjectError() {
        return new ObjectError(TEST_OBJECT_NAME, TEST_OBJECT_MESSAGE);
    }
    
    public static List<CardDTO> getListOfCards() {
        
        List<CardDTO> listOfCards = new ArrayList<CardDTO>();
        listOfCards.add(getTestCard(CARD_ID_1, OYSTER_NUMBER_1, CUSTOMER_ID_1));
        listOfCards.add(getTestCard(CARD_ID_2, OYSTER_NUMBER_2, CUSTOMER_ID_1));
        listOfCards.add(getTestCard(CARD_ID_3, OYSTER_NUMBER_3, WEBACCOUNT_ID_3));
        return listOfCards;
        
    }
}
