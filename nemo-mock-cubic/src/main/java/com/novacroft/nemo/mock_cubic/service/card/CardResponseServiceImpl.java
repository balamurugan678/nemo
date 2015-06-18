package com.novacroft.nemo.mock_cubic.service.card;

import com.novacroft.nemo.common.domain.cubic.*;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.common.utils.XMLUtil;
import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.data_access.CubicCardResponseDAO;
import com.novacroft.nemo.mock_cubic.domain.card.CubicCardResponse;
import com.novacroft.nemo.mock_cubic.domain.card.DiscountEntitlement;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardDetails;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.ICardUpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.common.utils.Converter.convert;
import static com.novacroft.nemo.common.utils.XMLUtil.*;
import static com.novacroft.nemo.mock_cubic.constant.Constant.PRESTIGE_ID;

/**
 * Service class for Card Response.
 */
@Transactional
@Service(value = "cardResponseService")
public class CardResponseServiceImpl implements CardResponseService {
    protected static final Logger logger = LoggerFactory.getLogger(CardResponseServiceImpl.class);

    @Autowired
    protected CubicCardResponseDAO cubicCardResponseDAO;

    @Autowired
    protected OysterCardDetailsService oysterCardDetailsService;

    @Transactional
    public final CubicCardResponse getCubicCardResponseByPrestigeId(final String prestigeId) {
        final CubicCardResponse example = new CubicCardResponse();
        example.setPrestigeId(prestigeId);
        final List<CubicCardResponse> cubicCardResponse = cubicCardResponseDAO.findByExample(example);
        return (cubicCardResponse.size() > 0 ? cubicCardResponse.get(0) : null);
    }

    public final CubicCardResponse getCubicCardResponseByPrestigeIdCardAction(final String prestigeId,
                                                                              final CardAction cardAction) {
        final CubicCardResponse example = new CubicCardResponse();
        example.setPrestigeId(prestigeId);
        example.setAction(cardAction.code());
        final List<CubicCardResponse> cubicCardResponse = cubicCardResponseDAO.findByExample(example);
        return (cubicCardResponse.size() > 0 ?
                        cubicCardResponse.get(0) : 
                            null);
    }

    @Override
    public final void addGetCardResponse(final String prestigeId, final Document xmlDocument, final CardAction cardAction) {
        final CubicCardResponse cardResponse = getCubicCardResponseByPrestigeIdCardAction(prestigeId, cardAction);
        if (cardResponse != null) {
            cardResponse.setModifiedDateTime(new Date());
            cardResponse.setResponse(outputDocument(xmlDocument, false));
            cubicCardResponseDAO.createOrUpdate(cardResponse);
        } else {
            final CubicCardResponse cubicCardResponse = new CubicCardResponse();
            cubicCardResponse.setCreated();
            cubicCardResponse.setPrestigeId(prestigeId);
            cubicCardResponse.setAction(cardAction.code());
            cubicCardResponse.setResponse(outputDocument(xmlDocument, false));
            cubicCardResponseDAO.createOrUpdate(cubicCardResponse);
        }
    }

    protected final OysterCardDetails createCardDetails(final AddCardResponseCmd cmd) {
        final OysterCardDetails cardDetails = new OysterCardDetails();
        convert(cmd, cardDetails);
        setHolderDetails(cmd, cardDetails);
        setPreyPayValue(cmd, cardDetails);
        setPrePayTicketDetails(cmd, cardDetails);
        setDiscountEntitlement(cmd, cardDetails);
        setPendingItems(cmd, cardDetails);
        return cardDetails;
    }

    @Override
    public final Document createGetCardResponseXML(final AddCardResponseCmd cmd){
        Document doc = null;
        try {
            final OysterCardDetails cardDetails = createCardDetails(cmd);
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = dbf.newDocumentBuilder();
            final Document finalDoc = builder.newDocument();
            final Element rootElement = parentElement("CardInfoResponseV2", finalDoc);
            finalDoc.appendChild(rootElement);
            createTextElement("CardCapability", cardDetails, finalDoc, rootElement);
            createTextElement("CardDeposit", cardDetails, finalDoc, rootElement);
            createTextElement("CardType", cardDetails, finalDoc, rootElement);
            createTextElement("CCCLostStolenDateTime", cardDetails, finalDoc, rootElement);
            createHotlistReasons(cardDetails, finalDoc, rootElement);
            createTextElement("AutoloadState", cardDetails, finalDoc, rootElement);
            createCardHolderDetails(cardDetails.getHolderDetails(), finalDoc, rootElement);
            createDiscounts(cardDetails.getDiscounts(), finalDoc, rootElement);
            createCDATAElement("PassengerType", cardDetails, finalDoc, rootElement);
            createCDATAElement("PhotocardNumber", cardDetails, finalDoc, rootElement);
            createPrePayTicket(cardDetails.getPrePayTicketDetails().getPptSlots(), finalDoc, rootElement);
            createPrePayValueDetails(cardDetails.getPrePayValue(), finalDoc, rootElement);
            createPendingItems(cardDetails.getPendingItems(), finalDoc, rootElement);
            createTextElement("PrestigeID", cardDetails, finalDoc, rootElement);
            createTextElement("Registered", cardDetails, finalDoc, rootElement);
            logger.debug(outputDocument(finalDoc, true));
            doc = finalDoc;
        } catch (ParserConfigurationException e) {
            logger.error("Parser Error");
        }
        return doc;
    }

    public final void setCubicCardResponseDAO(final CubicCardResponseDAO cubicCardResponseDAO) {
        this.cubicCardResponseDAO = cubicCardResponseDAO;
    }

    public final CubicCardResponseDAO getCubicCardResponseDAO() {
        return cubicCardResponseDAO;
    }

    protected final void createHotlistReasons(final OysterCardDetails cardDetails, final Document doc,
                                              final Element rootElement) {
        if (!StringUtil.isEmpty(cardDetails.getHotlistReasonsCodeNumbers())) {
            final Element hotlistReasonElement = parentElement("HotlistReasons", doc);
            final String[] codes = cardDetails.getHotlistReasonsCodeNumbers().split(",");
            for (final String code : codes) {
                createTextElement("HotlistReasonCode", code.trim(), doc, hotlistReasonElement);
            }
            rootElement.appendChild(hotlistReasonElement);
        }
    }

    protected final void createCardHolderDetails(final HolderDetails holderDetails, final Document doc,
                                                 final Element rootElement) {
        final Element cardHolderDetails = parentElement("CardHolderDetails", doc);
        createCDATAElement("County", holderDetails, doc, cardHolderDetails);
        createCDATAElement("DayTimePhoneNumber", holderDetails, doc, cardHolderDetails);
        createCDATAElement("EmailAddress", holderDetails, doc, cardHolderDetails);
        createCDATAElement("FirstName", holderDetails, doc, cardHolderDetails);
        createCDATAElement("HouseName", holderDetails, doc, cardHolderDetails);
        createCDATAElement("HouseNumber", holderDetails, doc, cardHolderDetails);
        createCDATAElement("LastName", holderDetails, doc, cardHolderDetails);
        createCDATAElement("MiddleInitial", holderDetails, doc, cardHolderDetails);
        createCDATAElement("Password", holderDetails, doc, cardHolderDetails);
        createCDATAElement("Postcode", holderDetails, doc, cardHolderDetails);
        createCDATAElement("Street", holderDetails, doc, cardHolderDetails);
        createCDATAElement("Title", holderDetails, doc, cardHolderDetails);
        createCDATAElement("Town", holderDetails, doc, cardHolderDetails);
        rootElement.appendChild(cardHolderDetails);
    }

    protected final void createDiscounts(final DiscountEntitlement discounts, final Document doc, final Element rootElement) {
        createTextElement("DiscountEntitlement1", discounts.getDiscountEntitlement1(), doc, rootElement);
        createTextElement("DiscountEntitlement2", discounts.getDiscountEntitlement2(), doc, rootElement);
        createTextElement("DiscountEntitlement3", discounts.getDiscountEntitlement3(), doc, rootElement);
        createTextElement("DiscountExpiry1", discounts.getDiscountExpiry1(), doc, rootElement);
        createTextElement("DiscountExpiry2", discounts.getDiscountExpiry2(), doc, rootElement);
        createTextElement("DiscountExpiry3", discounts.getDiscountExpiry3(), doc, rootElement);
    }

    protected final void createPrePayValueDetails(final PrePayValue prePayValue, final Document doc,
                                                  final Element rootElement) {
        final Element parent = parentElement("PPVDetails", doc);
        createTextElement("Currency", prePayValue, doc, parent);
        createTextElement("Balance", prePayValue, doc, parent);
        rootElement.appendChild(parent);
    }

    private void createPendingItems(final PendingItems pendingItems, final Document doc, final Element rootElement) {
        final Element parent = parentElement("PendingItems", doc);
       	createPendingPrePayValue(pendingItems.getPpvs(), doc, parent);
        createPendingPrePayTicket(pendingItems.getPpts(), doc, parent);
        rootElement.appendChild(parent);
    }

    protected final void createPendingPrePayValue(final List<PrePayValue> prePayValues, final Document doc, final Element parent) {
		for (PrePayValue prePayValue : prePayValues) {
			final Element prePayValueElement = parentElement("PPV", doc);
			createTextElement("RequestSequenceNumber", prePayValue, doc, prePayValueElement);
			createTextElement("RealTimeFlag", prePayValue, doc, prePayValueElement);
			createTextElement("PrepayValue", prePayValue, doc, prePayValueElement);
			createTextElement("Currency", prePayValue, doc, prePayValueElement);
			createTextElement("PickupLocation", prePayValue, doc, prePayValueElement);
			parent.appendChild(prePayValueElement);
		}
    }    

    protected final void createPendingPrePayTicket(final List<PrePayTicket> prePayTickets, final Document doc, final Element parent) {
        for (PrePayTicket prePayTicket : prePayTickets) {
            final Element prePayTicketElement = parentElement("PPT", doc);
            createTextElement("RequestSequenceNumber", prePayTicket, doc, prePayTicketElement);
            createTextElement("RealTimeFlag", prePayTicket, doc, prePayTicketElement);
            createTextElement("ProductCode", prePayTicket, doc, prePayTicketElement);
            createTextElement("ProductPrice", prePayTicket, doc, prePayTicketElement);
            createTextElement("Currency", prePayTicket, doc, prePayTicketElement);
            createTextElement("Startdate", prePayTicket, doc, prePayTicketElement);
            createTextElement("ExpiryDate", prePayTicket, doc, prePayTicketElement);
            createTextElement("PickupLocation", prePayTicket, doc, prePayTicketElement);
            parent.appendChild(prePayTicketElement);
        }
    }

    protected final void setPreyPayValue(final AddCardResponseCmd cmd, final OysterCardDetails cardDetails) {
        final PrePayValue prePayValue = new PrePayValue();
        convert(cmd, prePayValue);
        cardDetails.setPrePayValue(prePayValue);
    }

    protected final void setHolderDetails(final AddCardResponseCmd cmd, final OysterCardDetails cardDetails) {
        final HolderDetails holderDetails = new HolderDetails();
        convert(cmd, holderDetails);
        cardDetails.setHolderDetails(holderDetails);
    }

    protected final void setPrePayTicketDetails(final AddCardResponseCmd cmd, final OysterCardDetails cardDetails) {
        final PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        final List<PrePayTicketSlot> slotList = new ArrayList<PrePayTicketSlot>();
        slotList.add(createPptSlot1(cmd));
        slotList.add(createPptSlot2(cmd));
        slotList.add(createPptSlot3(cmd));
        pptDetails.setPptSlots(slotList);
        cardDetails.setPrePayTicketDetails(pptDetails);
    }

    protected final PrePayTicketSlot createPptSlot1(final AddCardResponseCmd cmd) {
        PrePayTicketSlot pptSlot = new PrePayTicketSlot();
        pptSlot.setDiscount(cmd.getDiscount());
        pptSlot.setExpiryDate(cmd.getPptExpiryDate());
        pptSlot.setPassengerType(cmd.getPptPassengerType());
        pptSlot.setProduct(cmd.getProduct());
        pptSlot.setSlotNumber(cmd.getSlotNumber());
        pptSlot.setStartDate(cmd.getStartDate());
        pptSlot.setState(cmd.getState());
        pptSlot.setZone(cmd.getZone());
        return pptSlot;
    }

    protected final PrePayTicketSlot createPptSlot2(final AddCardResponseCmd cmd) {
        PrePayTicketSlot pptSlot = new PrePayTicketSlot();
        pptSlot.setDiscount(cmd.getDiscount1());
        pptSlot.setExpiryDate(cmd.getPptExpiryDate1());
        pptSlot.setPassengerType(cmd.getPptPassengerType1());
        pptSlot.setProduct(cmd.getProduct1());
        pptSlot.setSlotNumber(cmd.getSlotNumber1());
        pptSlot.setStartDate(cmd.getStartDate1());
        pptSlot.setState(cmd.getState1());
        pptSlot.setZone(cmd.getZone1());
        return pptSlot;
    }

    protected final PrePayTicketSlot createPptSlot3(final AddCardResponseCmd cmd) {
        PrePayTicketSlot pptSlot = new PrePayTicketSlot();
        pptSlot.setDiscount(cmd.getDiscount2());
        pptSlot.setExpiryDate(cmd.getPptExpiryDate2());
        pptSlot.setPassengerType(cmd.getPptPassengerType2());
        pptSlot.setProduct(cmd.getProduct2());
        pptSlot.setSlotNumber(cmd.getSlotNumber2());
        pptSlot.setStartDate(cmd.getStartDate2());
        pptSlot.setState(cmd.getState2());
        pptSlot.setZone(cmd.getZone2());
        return pptSlot;
    }

    protected final void createPrePayTicket(final List<PrePayTicketSlot> slots, final Document doc, final Element rootElement) {
        final Element parent = parentElement("PPTDetails", doc);
        for (PrePayTicketSlot pptSlot : slots) {
            final Element slot = parentElement("PPTSlot", doc);
            createTextElement("SlotNumber", pptSlot, doc, slot);
            createTextElement("Product", pptSlot, doc, slot);
            createTextElement("Zone", pptSlot, doc, slot);
            createTextElement("StartDate", pptSlot, doc, slot);
            createTextElement("ExpiryDate", pptSlot, doc, slot);
            createTextElement("PassengerType", pptSlot, doc, slot);
            createTextElement("Discount", pptSlot, doc, slot);
            createTextElement("State", pptSlot, doc, slot);
            parent.appendChild(slot);
        }
        rootElement.appendChild(parent);
    }

    protected final void setDiscountEntitlement(final AddCardResponseCmd cmd, final OysterCardDetails cardDetails) {
        final DiscountEntitlement discountEntitlement = new DiscountEntitlement();
        discountEntitlement.setDiscountEntitlement1(cmd.getDiscountEntitlement1());
        discountEntitlement.setDiscountExpiry1(cmd.getDiscountExpiry1());
        discountEntitlement.setDiscountEntitlement2(cmd.getDiscountEntitlement2());
        discountEntitlement.setDiscountExpiry2(cmd.getDiscountExpiry2());
        discountEntitlement.setDiscountEntitlement3(cmd.getDiscountEntitlement3());
        discountEntitlement.setDiscountExpiry3(cmd.getDiscountExpiry3());
        cardDetails.setDiscounts(discountEntitlement);
    }

    protected final void setPendingItems(final AddCardResponseCmd cmd, final OysterCardDetails cardDetails) {
        final PendingItems pendingItems = new PendingItems();
        if (cmd.getPickupLocation() != null) {
        	List<PrePayValue> prePayValues = new ArrayList<PrePayValue>();
        	prePayValues.add(createPpv(cmd));
            pendingItems.setPpvs(prePayValues);
        }
        final List<PrePayTicket> prePayTickets = new ArrayList<PrePayTicket>();
        if (cmd.getPendingPptPickupLocation1() != null) {
            prePayTickets.add(createPpt(cmd.getPendingPptRequestSequenceNumber1(), cmd.getPendingPptRealTimeFlag1(),
                    cmd.getPendingPptProductCode1(), cmd.getPendingPptProductPrice1(), cmd.getPendingPptCurrency1(),
                    cmd.getPendingPptStartDate1(), cmd.getPendingPptExpiryDate1(), cmd.getPendingPptPickupLocation1()));
        }
        if (cmd.getPendingPptPickupLocation2() != null) {
            prePayTickets.add(createPpt(cmd.getPendingPptRequestSequenceNumber2(), cmd.getPendingPptRealTimeFlag2(),
                    cmd.getPendingPptProductCode2(), cmd.getPendingPptProductPrice2(), cmd.getPendingPptCurrency2(),
                    cmd.getPendingPptStartDate2(), cmd.getPendingPptExpiryDate2(), cmd.getPendingPptPickupLocation2()));
        }
        if (cmd.getPendingPptPickupLocation3() != null) {
            prePayTickets.add(createPpt(cmd.getPendingPptRequestSequenceNumber3(), cmd.getPendingPptRealTimeFlag3(),
                    cmd.getPendingPptProductCode3(), cmd.getPendingPptProductPrice3(), cmd.getPendingPptCurrency3(),
                    cmd.getPendingPptStartDate3(), cmd.getPendingPptExpiryDate3(), cmd.getPendingPptPickupLocation3()));
        }
        pendingItems.setPpts(prePayTickets);
        cardDetails.setPendingItems(pendingItems);
    }

    protected PrePayTicket createPpt(Integer pendingPptRequestSequenceNumber, String pendingPptRealTimeFlag,
                                     Integer pendingPptProductCode, Integer pendingPptProductPrice, Integer pendingPptCurrency,
                                     String pendingPptStartDate, String pendingPptExpiryDate,
                                     Integer pendingPptPickupLocation) {
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(pendingPptRequestSequenceNumber);
        ppt.setRealTimeFlag(pendingPptRealTimeFlag);
        ppt.setProductCode(pendingPptProductCode);
        ppt.setProductPrice(pendingPptProductPrice);
        ppt.setCurrency(pendingPptCurrency);
        ppt.setStartDate(pendingPptStartDate);
        ppt.setExpiryDate(pendingPptExpiryDate);
        ppt.setPickupLocation(pendingPptPickupLocation);
        return ppt;
    }

    protected final PrePayValue createPpv(AddCardResponseCmd cmd) {
        PrePayValue prePayValue = new PrePayValue();
        convert(cmd, prePayValue);
        return prePayValue;
    }

    @Override
    public final String getCardDetails(final String prestigeId, final String action) {
        final CubicCardResponse cubicCardResponse = new CubicCardResponse();
        cubicCardResponse.setPrestigeId(prestigeId);
        cubicCardResponse.setAction(action);
        final List<CubicCardResponse> cardList = cubicCardResponseDAO.findByExample(cubicCardResponse);
        String response = "";
        if (cardList.size() > 0) {
            response = cardList.get(0).getResponse();
        }
        return response;
    }

    @Override
    public final String getCardDetails(final Document document) {
        String cardDetails = "";
        final Element root = document.getDocumentElement();
        final NodeList list = root.getChildNodes();
        cardDetails = getCardDetails(getPresitegeIdFromDocument(list), CardAction.GET_CARD.code());
        return cardDetails;
    }

    protected final String getPresitegeIdFromDocument(final NodeList list) {
        String prestigeId = "";
        for (int i = 0; i < list.getLength(); i++) {
            final Node node = list.item(i);
            final String nodeName = node.getNodeName();
            if (PRESTIGE_ID.equalsIgnoreCase(nodeName)) {
                prestigeId = XMLUtil.getData(node);
                break;
            }
        }
        return prestigeId;
    }

    @Override
    @Transactional
    public String getCardDetails(CardInfoRequestV2 cardInfoRequestV2) {
        return oysterCardDetailsService.getCardDetails(cardInfoRequestV2);
    }

    @Override
    public String getCardUpdatePrePayTicket(CardUpdatePrePayTicketRequest request) {
        return getCardUpdate(request);
    }

    @Override
    public String getCardUpdatePrePayValue(CardUpdatePrePayValueRequest request) {
        return getCardUpdate(request);
    }

    protected String getCardUpdate(ICardUpdateRequest request) {
        String cardDetails = getCardDetails(request.getPrestigeId().toString(), CardAction.UPDATE_CARD.code());
        if (StringUtil.isEmpty(cardDetails)) {
            cardDetails = getCardDetails(request.getPrestigeId().toString(), CardAction.UPDATE_CARD_ERROR.code());
        }
        return cardDetails;
    }

}
