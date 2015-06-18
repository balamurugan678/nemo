package com.novacroft.nemo.mock_cubic.application_service;

import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.CARD_NOT_FOUND_ERROR_CODE;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.CARD_NOT_FOUND_ERROR_DESCRIPTION;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.common.domain.cubic.HotListReasons;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardHotListReasonsDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPptPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPpvPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayTicketDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayValueDataService;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardHotListReasonsDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;

@Service(value = "oysterCardDetailsService")
public class OysterCardDetailsServiceImpl implements OysterCardDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(OysterCardDetailsServiceImpl.class);
    @Autowired
    protected OysterCardDataService oysterCardDataService;
    @Autowired
    protected OysterCardHotListReasonsDataService oysterCardHotListReasonsDataService;
    @Autowired
    protected OysterCardPptPendingDataService oysterCardPptPendingDataService;
    @Autowired
    protected OysterCardPpvPendingDataService oysterCardPpvPendingDataService;
    @Autowired
    protected OysterCardPendingDataService oysterCardPendingDataService;
    @Autowired
    protected OysterCardPrepayTicketDataService oysterCardPrepayTicketDataService;
    @Autowired
    protected OysterCardPrepayValueDataService oysterCardPrepayValueDataService;
    @Autowired
    protected XmlModelConverter<CardInfoResponseV2> cardInfoResponseV2Converter;
    @Autowired
    protected UpdateResponseService updateResponseService;
    @Autowired
    protected XmlModelConverter<CardInfoResponseV2> cardInfoResponseV2EmptyTagConverter;

    @Override
    @Transactional
    public void createOrUpdateOysterCard(AddCardResponseCmd cmd) {
        logger.info("Starting CUBIC card response update");
        createOrUpdateCard(cmd);
        createOrUpdateCardPptPending(cmd);
        createOrUpdateCardPpvPending(cmd);
        createOrUpdateCardPrepayTicket(cmd);
        createOrUpdateCardPrepayValue(cmd);
        logger.info("Finished CUBIC card response update");
    }

    @Override
    public void createOrUpdateCard(AddCardResponseCmd cmd) {
        OysterCardDTO oysterCard = this.oysterCardDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCard == null) {
            oysterCard = createOysterCardDTO();
        }
        oysterCard = populateOysterCard(oysterCard, cmd);
        oysterCard = this.oysterCardDataService.createOrUpdate(oysterCard);
    }

    protected OysterCardDTO createOysterCardDTO() {
        return new OysterCardDTO();
    }

    @Override
    public void createOrUpdateCardPptPending(AddCardResponseCmd cmd) {
        OysterCardPptPendingDTO oysterCardPptPending =
                this.oysterCardPptPendingDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCardPptPending == null) {
            oysterCardPptPending = createOysterCardPptPendingDTO();
        }
        oysterCardPptPending = populateOysterCardPptPending(oysterCardPptPending, cmd);
        oysterCardPptPending = this.oysterCardPptPendingDataService.createOrUpdate(oysterCardPptPending);
    }

    protected OysterCardPptPendingDTO createOysterCardPptPendingDTO() {
        return new OysterCardPptPendingDTO();
    }

    @Override
    public void createOrUpdateCardPpvPending(AddCardResponseCmd cmd) {
        OysterCardPpvPendingDTO oysterCardPpvPending =
                this.oysterCardPpvPendingDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCardPpvPending == null) {
            oysterCardPpvPending = createOysterCardPpvPendingDTO();
        }
        oysterCardPpvPending = populateOysterCardPpvPending(oysterCardPpvPending, cmd);
        oysterCardPpvPending = this.oysterCardPpvPendingDataService.createOrUpdate(oysterCardPpvPending);
    }

    protected OysterCardPpvPendingDTO createOysterCardPpvPendingDTO() {
        return new OysterCardPpvPendingDTO();
    }

    @Override
    public void createOrUpdateCardPrepayTicket(AddCardResponseCmd cmd) {
        OysterCardPrepayTicketDTO oysterCardPpt = this.oysterCardPrepayTicketDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCardPpt == null) {
            oysterCardPpt = createOysterCardPrepayTicketDTO();
        }
        oysterCardPpt = populateOysterCardPrepayTicket(oysterCardPpt, cmd);
        oysterCardPpt = this.oysterCardPrepayTicketDataService.createOrUpdate(oysterCardPpt);
    }

    protected OysterCardPrepayTicketDTO createOysterCardPrepayTicketDTO() {
        return new OysterCardPrepayTicketDTO();
    }

    @Override
    public void createOrUpdateCardPrepayValue(AddCardResponseCmd cmd) {
        OysterCardPrepayValueDTO oysterCardPpv = this.oysterCardPrepayValueDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCardPpv == null) {
            oysterCardPpv = createOysterCardPrepayValueDTO();
        }
        oysterCardPpv = populateOysterCardPrepayValue(oysterCardPpv, cmd);
        oysterCardPpv = this.oysterCardPrepayValueDataService.createOrUpdate(oysterCardPpv);
    }
    
    @Override
    @Transactional
    public void freePrePayTicketSlots(AddCardResponseCmd cmd) {
        
        OysterCardPrepayTicketDTO oysterCardPpt = this.oysterCardPrepayTicketDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCardPpt != null) {
            oysterCardPrepayTicketDataService.delete(oysterCardPpt);
        }
        
        OysterCardPptPendingDTO oysterCardPptPending = this.oysterCardPptPendingDataService.findByCardNumber(cmd.getPrestigeId());
        if (oysterCardPptPending != null) {
            oysterCardPptPendingDataService.delete(oysterCardPptPending);
        }      
    }


    protected OysterCardPrepayValueDTO createOysterCardPrepayValueDTO() {
        return new OysterCardPrepayValueDTO();
    }

    protected Long getLongNumber(Integer number) {
        Long longNumber = null;
        if (number != null) {
            longNumber = new Long(number.intValue());
        }
        return longNumber;
    }

    protected Integer getIntegerNumber(Long number) {
        Integer integerNumber = null;
        if (number != null) {
            integerNumber = Integer.valueOf(number.intValue());
        }
        return integerNumber;
    }

    protected OysterCardPpvPendingDTO populateOysterCardPpvPending(OysterCardPpvPendingDTO oysterCardPpvPending,
                                                                   AddCardResponseCmd cmd) {
        oysterCardPpvPending.setPrestigeId(cmd.getPrestigeId());
        oysterCardPpvPending.setCurrency(this.getLongNumber(cmd.getCurrency()));
        oysterCardPpvPending.setPickupLocation(this.getLongNumber(cmd.getPickupLocation()));
        oysterCardPpvPending.setPrePayValue(this.getLongNumber(cmd.getPrePayValue()));
        oysterCardPpvPending.setRealtimeFlag(cmd.getRealTimeFlag());
        oysterCardPpvPending.setRequestSequenceNumber(this.getLongNumber(cmd.getRequestSequenceNumber()));
        return oysterCardPpvPending;
    }

    protected OysterCardDTO populateOysterCard(OysterCardDTO oysterCard, AddCardResponseCmd cmd) {
        oysterCard.setPrestigeId(cmd.getPrestigeId());
        oysterCard.setPhotoCardNumber(cmd.getPhotocardNumber());
        oysterCard.setRegistered(this.getLongNumber(cmd.getRegistered()));
        oysterCard.setPassengerType(cmd.getPassengerType());
        oysterCard.setAutoloadState(this.getLongNumber(cmd.getAutoloadState()));
        oysterCard.setCardCapability(this.getLongNumber(cmd.getCardCapability()));
        oysterCard.setCardType(this.getLongNumber(cmd.getCardType()));
        oysterCard.setCccLostStolenDateTime(DateUtil.parse(cmd.getcCCLostStolenDateTime()));
        oysterCard.setCardDeposit(this.getLongNumber(cmd.getCardDeposit()));
        oysterCard.setDiscountEntitlement1(cmd.getDiscountEntitlement1());
        oysterCard.setDiscountExpiry1(DateUtil.parse(cmd.getDiscountExpiry1()));
        oysterCard.setDiscountEntitlement2(cmd.getDiscountEntitlement2());
        oysterCard.setDiscountExpiry2(DateUtil.parse(cmd.getDiscountExpiry2()));
        oysterCard.setDiscountEntitlement3(cmd.getDiscountEntitlement3());
        oysterCard.setDiscountExpiry3(DateUtil.parse(cmd.getDiscountExpiry3()));
        oysterCard.setTitle(cmd.getTitle());
        oysterCard.setFirstName(cmd.getFirstName());
        oysterCard.setMiddleInitial(cmd.getMiddleInitial());
        oysterCard.setLastName(cmd.getLastName());
        oysterCard.setDayTimePhoneNumber(cmd.getDayTimeTelephoneNumber());
        oysterCard.setHouseNumber(cmd.getHouseNumber());
        oysterCard.setHouseName(cmd.getHouseName());
        oysterCard.setStreet(cmd.getStreet());
        oysterCard.setTown(cmd.getTown());
        oysterCard.setCounty(cmd.getCounty());
        oysterCard.setPostcode(cmd.getPostcode());
        oysterCard.setEmailAddress(cmd.getEmailAddress());
        oysterCard.setPassword(cmd.getPassword());
        return oysterCard;
    }

    protected OysterCardPptPendingDTO populateOysterCardPptPending(OysterCardPptPendingDTO oysterCardPptPending,
                                                                   AddCardResponseCmd cmd) {
        oysterCardPptPending.setPrestigeId(cmd.getPrestigeId());

        if (cmd.getPendingPptRequestSequenceNumber1() != null) {
            oysterCardPptPending.setRequestSequenceNumber1(this.getLongNumber(cmd.getPendingPptRequestSequenceNumber1()));
            oysterCardPptPending.setRealTimeFlag1(cmd.getPendingPptRealTimeFlag1());
            oysterCardPptPending.setProductCode1(this.getLongNumber(cmd.getPendingPptProductCode1()));
            oysterCardPptPending.setProductPrice1(this.getLongNumber(cmd.getPendingPptProductPrice1()));
            oysterCardPptPending.setCurrency1(this.getLongNumber(cmd.getPendingPptCurrency1()));
            oysterCardPptPending.setStartDate1(DateUtil.parse(cmd.getPendingPptStartDate1()));
            oysterCardPptPending.setExpiryDate1(DateUtil.parse(cmd.getPendingPptExpiryDate1()));
            oysterCardPptPending.setPickUpLocation1(this.getLongNumber(cmd.getPendingPptPickupLocation1()));
        }

        if (cmd.getPendingPptRequestSequenceNumber2() != null) {
            oysterCardPptPending.setRequestSequenceNumber2(this.getLongNumber(cmd.getPendingPptRequestSequenceNumber2()));
            oysterCardPptPending.setRealTimeFlag2(cmd.getPendingPptRealTimeFlag2());
            oysterCardPptPending.setProductCode2(this.getLongNumber(cmd.getPendingPptProductCode2()));
            oysterCardPptPending.setProductPrice2(this.getLongNumber(cmd.getPendingPptProductPrice2()));
            oysterCardPptPending.setCurrency2(this.getLongNumber(cmd.getPendingPptCurrency2()));
            oysterCardPptPending.setStartDate2(DateUtil.parse(cmd.getPendingPptStartDate2()));
            oysterCardPptPending.setExpiryDate2(DateUtil.parse(cmd.getPendingPptExpiryDate2()));
            oysterCardPptPending.setPickUpLocation2(this.getLongNumber(cmd.getPendingPptPickupLocation2()));
        }

        if (cmd.getPendingPptRequestSequenceNumber3() != null) {
            oysterCardPptPending.setRequestSequenceNumber3(this.getLongNumber(cmd.getPendingPptRequestSequenceNumber3()));
            oysterCardPptPending.setRealTimeFlag3(cmd.getPendingPptRealTimeFlag3());
            oysterCardPptPending.setProductCode3(this.getLongNumber(cmd.getPendingPptProductCode3()));
            oysterCardPptPending.setProductPrice3(this.getLongNumber(cmd.getPendingPptProductPrice3()));
            oysterCardPptPending.setCurrency3(this.getLongNumber(cmd.getPendingPptCurrency3()));
            oysterCardPptPending.setStartDate3(DateUtil.parse(cmd.getPendingPptStartDate3()));
            oysterCardPptPending.setExpiryDate3(DateUtil.parse(cmd.getPendingPptExpiryDate3()));
            oysterCardPptPending.setPickUpLocation3(this.getLongNumber(cmd.getPendingPptPickupLocation3()));
        }
        return oysterCardPptPending;
    }

    protected OysterCardPrepayTicketDTO populateOysterCardPrepayTicket(OysterCardPrepayTicketDTO oysterCardPpt,
                                                                       AddCardResponseCmd cmd) {
        oysterCardPpt.setPrestigeId(cmd.getPrestigeId());

        if (cmd.getSlotNumber() != null) {
            oysterCardPpt.setDiscount1(cmd.getDiscount());
            oysterCardPpt.setPassengerType1(cmd.getPptPassengerType());
            oysterCardPpt.setProduct1(cmd.getProduct());
            oysterCardPpt.setSlotNumber1(this.getLongNumber(cmd.getSlotNumber()));
            oysterCardPpt.setStartDate1(DateUtil.parse(cmd.getStartDate()));
            oysterCardPpt.setExpiryDate1(DateUtil.parse(cmd.getPptExpiryDate()));
            oysterCardPpt.setState1(this.getLongNumber(cmd.getState()));
            oysterCardPpt.setZone1(cmd.getZone());
        }

        if (cmd.getSlotNumber1() != null) {
            oysterCardPpt.setDiscount2(cmd.getDiscount1());
            oysterCardPpt.setPassengerType2(cmd.getPptPassengerType1());
            oysterCardPpt.setProduct2(cmd.getProduct1());
            oysterCardPpt.setSlotNumber2(this.getLongNumber(cmd.getSlotNumber1()));
            oysterCardPpt.setStartDate2(DateUtil.parse(cmd.getStartDate1()));
            oysterCardPpt.setExpiryDate2(DateUtil.parse(cmd.getPptExpiryDate1()));
            oysterCardPpt.setState2(this.getLongNumber(cmd.getState1()));
            oysterCardPpt.setZone2(cmd.getZone1());
        }

        if (cmd.getSlotNumber2() != null) {
            oysterCardPpt.setDiscount3(cmd.getDiscount2());
            oysterCardPpt.setPassengerType3(cmd.getPptPassengerType2());
            oysterCardPpt.setProduct3(cmd.getProduct2());
            oysterCardPpt.setSlotNumber3(this.getLongNumber(cmd.getSlotNumber2()));
            oysterCardPpt.setStartDate3(DateUtil.parse(cmd.getStartDate2()));
            oysterCardPpt.setExpiryDate3(DateUtil.parse(cmd.getPptExpiryDate2()));
            oysterCardPpt.setState3(this.getLongNumber(cmd.getState2()));
            oysterCardPpt.setZone3(cmd.getZone2());
        }
        return oysterCardPpt;
    }

    protected OysterCardPrepayValueDTO populateOysterCardPrepayValue(OysterCardPrepayValueDTO oysterCardPpv,
                                                                     AddCardResponseCmd cmd) {
        oysterCardPpv.setPrestigeId(cmd.getPrestigeId());
        oysterCardPpv.setBalance(this.getLongNumber(cmd.getBalance()));
        oysterCardPpv.setCurrency(this.getLongNumber(cmd.getCurrency()));
        return oysterCardPpv;
    }

    @Override
    public String getCardDetails(CardInfoRequestV2 cardInfoRequestV2) {
        return this.getCardDetails(cardInfoRequestV2.getPrestigeId());
    }

    protected String getCardDetails(final String prestigeId) {
        CardInfoResponseV2 cardInfoResponseV2 = new CardInfoResponseV2();
        OysterCardDTO oysterCard = this.oysterCardDataService.findByCardNumber(prestigeId);
        if (oysterCard != null) {
            cardInfoResponseV2 = getOysterCardDetails(oysterCard, cardInfoResponseV2);
        } else {
            return updateResponseService.generateErrorResponse(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        }

        OysterCardHotListReasonsDTO oysterCardHotListReasons =
                this.oysterCardHotListReasonsDataService.findByCardNumber(prestigeId);
        if (oysterCardHotListReasons != null) {
            cardInfoResponseV2 = getOysterCardHotListReasonsDetails(oysterCardHotListReasons, cardInfoResponseV2);
        }

        OysterCardPrepayValueDTO oysterCardPpv = this.oysterCardPrepayValueDataService.findByCardNumber(prestigeId);
        if (oysterCardPpv != null) {
            cardInfoResponseV2 = getOysterCardPrepayValueDetails(oysterCardPpv, cardInfoResponseV2);
        }

        OysterCardPrepayTicketDTO oysterCardPpt = this.oysterCardPrepayTicketDataService.findByCardNumber(prestigeId);
        if (oysterCardPpt != null) {
            cardInfoResponseV2 = getOysterCardPrepayTicketDetails(oysterCardPpt, cardInfoResponseV2);
        }
        
        
        List<OysterCardPendingDTO> oysterCardPending = this.oysterCardPendingDataService.findByCardNumber(prestigeId);
        getOysterCardPendingDetails(oysterCardPending, cardInfoResponseV2);
        return this.cardInfoResponseV2EmptyTagConverter.convertModelToXml(cardInfoResponseV2);
    }

    protected CardInfoResponseV2 getOysterCardPendingDetails(OysterCardPpvPendingDTO oysterCardPpvPending,
                                                             OysterCardPptPendingDTO oysterCardPptPending,
                                                             CardInfoResponseV2 cardInfoResponseV2) {
        PendingItems pendingItems = new PendingItems();
        if (oysterCardPpvPending != null) {
            pendingItems = getPendingItemsPpv(pendingItems, oysterCardPpvPending);
        }
        if (oysterCardPptPending != null) {
            pendingItems = getPendingItemsPpt(pendingItems, oysterCardPptPending);
        }
        cardInfoResponseV2.setPendingItems(pendingItems);

        return cardInfoResponseV2;
    }
    
	protected CardInfoResponseV2 getOysterCardPendingDetails(List<OysterCardPendingDTO> pending, CardInfoResponseV2 cardInfoResponseV2) {
		PendingItems pendingItems = new PendingItems();
		setPendingItemsPpt(pendingItems, pending);
		setPendingItemsPpv(pendingItems, pending);
		cardInfoResponseV2.setPendingItems(pendingItems);

		return cardInfoResponseV2;
	}
    
	protected void setPendingItemsPpv(PendingItems pendingItems, List<OysterCardPendingDTO> pending) {
		List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
		for(OysterCardPendingDTO oysterCardPending : pending){
			if (null != oysterCardPending.getPrePayValue()) {
				PrePayValue prePayValue = new PrePayValue();
		        prePayValue.setRequestSequenceNumber(this.getIntegerNumber(oysterCardPending.getRequestSequenceNumber()));
		        prePayValue.setRealTimeFlag(oysterCardPending.getRealtimeFlag());
		        prePayValue.setPrePayValue(this.getIntegerNumber(oysterCardPending.getPrePayValue()));
		        prePayValue.setCurrency(this.getIntegerNumber(oysterCardPending.getCurrency()));
		        prePayValue.setPickupLocation(this.getIntegerNumber(oysterCardPending.getPickupLocation()));
		        ppvs.add(prePayValue);
		
			}
		}
        pendingItems.setPpvs(ppvs);
	}
	
	protected void setPendingItemsPpt(PendingItems pendingItems, List<OysterCardPendingDTO> pending) {
		List<PrePayTicket> ppts = new ArrayList<PrePayTicket>();
		for(OysterCardPendingDTO oysterCardPending : pending){
			if (null != oysterCardPending.getProductCode()) {
				PrePayTicket prePayTicket = new PrePayTicket();
		        prePayTicket.setRequestSequenceNumber(this.getIntegerNumber(oysterCardPending.getRequestSequenceNumber()));
		        prePayTicket.setRealTimeFlag(oysterCardPending.getRealtimeFlag());
		        prePayTicket.setProductCode(this.getIntegerNumber(oysterCardPending.getProductCode()));
		        prePayTicket.setProductPrice(this.getIntegerNumber(oysterCardPending.getProductPrice()));
		        prePayTicket.setCurrency(this.getIntegerNumber(oysterCardPending.getCurrency()));
		        prePayTicket.setStartDate(DateUtil.formatDate(oysterCardPending.getStartDate()));
		        prePayTicket.setExpiryDate(DateUtil.formatDate(oysterCardPending.getExpiryDate()));
		        prePayTicket.setPickupLocation(this.getIntegerNumber(oysterCardPending.getPickupLocation()));
		        ppts.add(prePayTicket);
			}
		}
        pendingItems.setPpts(ppts);
	}

    protected PendingItems getPendingItemsPpt(PendingItems pendingItems, OysterCardPptPendingDTO oysterCardPptPending) {
        List<PrePayTicket> pptsPending = new ArrayList<PrePayTicket>();
        if (oysterCardPptPending.getRequestSequenceNumber1() != null) {
            PrePayTicket prePayTicket = new PrePayTicket();
            prePayTicket = getPptPendingSlot1(prePayTicket, oysterCardPptPending);
            pptsPending.add(prePayTicket);
        }
        if (oysterCardPptPending.getRequestSequenceNumber2() != null) {
            PrePayTicket prePayTicket = new PrePayTicket();
            prePayTicket = getPptPendingSlot2(prePayTicket, oysterCardPptPending);
            pptsPending.add(prePayTicket);
        }
        if (oysterCardPptPending.getRequestSequenceNumber3() != null) {
            PrePayTicket prePayTicket = new PrePayTicket();
            prePayTicket = getPptPendingSlot3(prePayTicket, oysterCardPptPending);
            pptsPending.add(prePayTicket);
        }
        pendingItems.setPpts(pptsPending);

        return pendingItems;

    }

    protected PendingItems getPendingItemsPpv(PendingItems pendingItems, OysterCardPpvPendingDTO oysterCardPpvPending) {
    	List<PrePayValue> ppvsPending = new ArrayList<PrePayValue>();
        PrePayValue prePayValue = new PrePayValue();
        prePayValue.setRequestSequenceNumber(this.getIntegerNumber(oysterCardPpvPending.getRequestSequenceNumber()));
        prePayValue.setRealTimeFlag(oysterCardPpvPending.getRealtimeFlag());
        prePayValue.setPrePayValue(this.getIntegerNumber(oysterCardPpvPending.getPrePayValue()));
        prePayValue.setCurrency(this.getIntegerNumber(oysterCardPpvPending.getCurrency()));
        prePayValue.setPickupLocation(this.getIntegerNumber(oysterCardPpvPending.getPickupLocation()));
        ppvsPending.add(prePayValue);
        pendingItems.setPpvs(ppvsPending);
        return pendingItems;

    }

    protected CardInfoResponseV2 getOysterCardPrepayTicketDetails(OysterCardPrepayTicketDTO oysterCardPpt,
                                                                  CardInfoResponseV2 cardInfoResponseV2) {
        PrePayTicketDetails prePayTicketDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> pptSlots = new ArrayList<PrePayTicketSlot>();
        if (oysterCardPpt.getSlotNumber1() != null) {
            PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
            prePayTicketSlot = getPrePayTicketSlot1(prePayTicketSlot, oysterCardPpt);
            pptSlots.add(prePayTicketSlot);
        }
        if (oysterCardPpt.getSlotNumber2() != null) {
            PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
            prePayTicketSlot = getPrePayTicketSlot2(prePayTicketSlot, oysterCardPpt);
            pptSlots.add(prePayTicketSlot);
        }
        if (oysterCardPpt.getSlotNumber3() != null) {
            PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
            prePayTicketSlot = getPrePayTicketSlot3(prePayTicketSlot, oysterCardPpt);
            pptSlots.add(prePayTicketSlot);
        }
        prePayTicketDetails.setPptSlots(pptSlots);
        cardInfoResponseV2.setPptDetails(prePayTicketDetails);

        return cardInfoResponseV2;
    }

    protected CardInfoResponseV2 getOysterCardPrepayValueDetails(OysterCardPrepayValueDTO oysterCardPpv,
                                                                 CardInfoResponseV2 cardInfoResponseV2) {
        PrePayValue prePayValue = new PrePayValue();
        prePayValue.setBalance(this.getIntegerNumber(oysterCardPpv.getBalance()));
        prePayValue.setCurrency(this.getIntegerNumber(oysterCardPpv.getCurrency()));
        cardInfoResponseV2.setPpvDetails(prePayValue);

        return cardInfoResponseV2;
    }

    protected CardInfoResponseV2 getOysterCardHotListReasonsDetails(OysterCardHotListReasonsDTO oysterCardHotListReasons,
                                                                    CardInfoResponseV2 cardInfoResponseV2) {
        HotListReasons hotListReasons = new HotListReasons();
        List<Integer> hotListReasonCodes = new ArrayList<Integer>();
        hotListReasonCodes.add(this.getIntegerNumber(oysterCardHotListReasons.getHotListReasonCode()));
        hotListReasons.setHotListReasonCodes(hotListReasonCodes);
        cardInfoResponseV2.setHotListReasons(hotListReasons);

        return cardInfoResponseV2;
    }

    protected CardInfoResponseV2 getOysterCardDetails(OysterCardDTO oysterCard, CardInfoResponseV2 cardInfoResponseV2) {
        cardInfoResponseV2.setPrestigeId(oysterCard.getPrestigeId());
        cardInfoResponseV2.setPhotoCardNumber(oysterCard.getPhotoCardNumber());
        cardInfoResponseV2.setRegistered(this.getIntegerNumber(oysterCard.getRegistered()));
        cardInfoResponseV2.setPassengerType(oysterCard.getPassengerType());
        cardInfoResponseV2.setAutoLoadState(this.getIntegerNumber(oysterCard.getAutoloadState()));
        cardInfoResponseV2.setCardCapability(this.getIntegerNumber(oysterCard.getCardCapability()));
        cardInfoResponseV2.setCardType(this.getIntegerNumber(oysterCard.getCardType()));
        cardInfoResponseV2.setCccLostStolenDateTime(oysterCard.getCccLostStolenDateTime());
        cardInfoResponseV2.setCardDeposit(this.getIntegerNumber(oysterCard.getCardDeposit()));
        cardInfoResponseV2.setDiscountEntitlement1(oysterCard.getDiscountEntitlement1());
        cardInfoResponseV2.setDiscountEntitlement2(oysterCard.getDiscountEntitlement2());
        cardInfoResponseV2.setDiscountEntitlement3(oysterCard.getDiscountEntitlement3());
        cardInfoResponseV2.setDiscountExpiry1(DateUtil.formatDate(oysterCard.getDiscountExpiry1()));
        cardInfoResponseV2.setDiscountExpiry2(DateUtil.formatDate(oysterCard.getDiscountExpiry2()));
        cardInfoResponseV2.setDiscountExpiry3(DateUtil.formatDate(oysterCard.getDiscountExpiry3()));

        HolderDetails holderDetails = new HolderDetails();
        holderDetails.setTitle(oysterCard.getTitle());
        holderDetails.setFirstName(oysterCard.getFirstName());
        holderDetails.setMiddleInitial(oysterCard.getMiddleInitial());
        holderDetails.setLastName(oysterCard.getLastName());
        holderDetails.setHouseName(oysterCard.getHouseName());
        holderDetails.setHouseNumber(oysterCard.getHouseNumber());
        holderDetails.setStreet(oysterCard.getStreet());
        holderDetails.setTown(oysterCard.getTown());
        holderDetails.setCounty(oysterCard.getCounty());
        holderDetails.setPostcode(oysterCard.getPostcode());
        holderDetails.setDayTimeTelephoneNumber(oysterCard.getDayTimePhoneNumber());
        holderDetails.setEmailAddress(oysterCard.getEmailAddress());
        holderDetails.setPassword(oysterCard.getPassword());
        cardInfoResponseV2.setHolderDetails(holderDetails);

        return cardInfoResponseV2;
    }

    protected PrePayTicket getPptPendingSlot1(PrePayTicket prePayTicket, OysterCardPptPendingDTO oysterCardPpt) {
        prePayTicket.setRequestSequenceNumber(this.getIntegerNumber(oysterCardPpt.getRequestSequenceNumber1()));
        prePayTicket.setRealTimeFlag(oysterCardPpt.getRealTimeFlag1());
        prePayTicket.setProductCode(this.getIntegerNumber(oysterCardPpt.getProductCode1()));
        prePayTicket.setProductPrice(this.getIntegerNumber(oysterCardPpt.getProductPrice1()));
        prePayTicket.setCurrency(this.getIntegerNumber(oysterCardPpt.getCurrency1()));
        prePayTicket.setStartDate(DateUtil.formatDate(oysterCardPpt.getStartDate1()));
        prePayTicket.setExpiryDate(DateUtil.formatDate(oysterCardPpt.getExpiryDate1()));
        prePayTicket.setPickupLocation(this.getIntegerNumber(oysterCardPpt.getPickUpLocation1()));

        return prePayTicket;
    }

    protected PrePayTicket getPptPendingSlot2(PrePayTicket prePayTicket, OysterCardPptPendingDTO oysterCardPpt) {
        prePayTicket.setRequestSequenceNumber(this.getIntegerNumber(oysterCardPpt.getRequestSequenceNumber2()));
        prePayTicket.setRealTimeFlag(oysterCardPpt.getRealTimeFlag2());
        prePayTicket.setProductCode(this.getIntegerNumber(oysterCardPpt.getProductCode2()));
        prePayTicket.setProductPrice(this.getIntegerNumber(oysterCardPpt.getProductPrice2()));
        prePayTicket.setCurrency(this.getIntegerNumber(oysterCardPpt.getCurrency2()));
        prePayTicket.setStartDate(DateUtil.formatDate(oysterCardPpt.getStartDate2()));
        prePayTicket.setExpiryDate(DateUtil.formatDate(oysterCardPpt.getExpiryDate2()));
        prePayTicket.setPickupLocation(this.getIntegerNumber(oysterCardPpt.getPickUpLocation2()));

        return prePayTicket;
    }

    protected PrePayTicket getPptPendingSlot3(PrePayTicket prePayTicket, OysterCardPptPendingDTO oysterCardPpt) {
        prePayTicket.setRequestSequenceNumber(this.getIntegerNumber(oysterCardPpt.getRequestSequenceNumber3()));
        prePayTicket.setRealTimeFlag(oysterCardPpt.getRealTimeFlag3());
        prePayTicket.setProductCode(this.getIntegerNumber(oysterCardPpt.getProductCode3()));
        prePayTicket.setProductPrice(this.getIntegerNumber(oysterCardPpt.getProductPrice3()));
        prePayTicket.setCurrency(this.getIntegerNumber(oysterCardPpt.getCurrency3()));
        prePayTicket.setStartDate(DateUtil.formatDate(oysterCardPpt.getStartDate3()));
        prePayTicket.setExpiryDate(DateUtil.formatDate(oysterCardPpt.getExpiryDate3()));
        prePayTicket.setPickupLocation(this.getIntegerNumber(oysterCardPpt.getPickUpLocation3()));

        return prePayTicket;
    }

    protected PrePayTicketSlot getPrePayTicketSlot1(PrePayTicketSlot prePayTicketSlot,
                                                    OysterCardPrepayTicketDTO oysterCardPpt) {
        prePayTicketSlot.setSlotNumber(this.getIntegerNumber(oysterCardPpt.getSlotNumber1()));
        prePayTicketSlot.setState(this.getIntegerNumber(oysterCardPpt.getState1()));
        prePayTicketSlot.setDiscount(oysterCardPpt.getDiscount1());
        prePayTicketSlot.setStartDate(DateUtil.formatDate(oysterCardPpt.getStartDate1()));
        prePayTicketSlot.setExpiryDate(DateUtil.formatDate(oysterCardPpt.getExpiryDate1()));
        prePayTicketSlot.setPassengerType(oysterCardPpt.getPassengerType1());
        prePayTicketSlot.setProduct(oysterCardPpt.getProduct1());
        prePayTicketSlot.setZone(oysterCardPpt.getZone1());

        return prePayTicketSlot;
    }

    protected PrePayTicketSlot getPrePayTicketSlot2(PrePayTicketSlot prePayTicketSlot,
                                                    OysterCardPrepayTicketDTO oysterCardPpt) {
        prePayTicketSlot.setSlotNumber(this.getIntegerNumber(oysterCardPpt.getSlotNumber2()));
        prePayTicketSlot.setState(this.getIntegerNumber(oysterCardPpt.getState2()));
        prePayTicketSlot.setDiscount(oysterCardPpt.getDiscount2());
        prePayTicketSlot.setStartDate(DateUtil.formatDate(oysterCardPpt.getStartDate2()));
        prePayTicketSlot.setExpiryDate(DateUtil.formatDate(oysterCardPpt.getExpiryDate2()));
        prePayTicketSlot.setPassengerType(oysterCardPpt.getPassengerType2());
        prePayTicketSlot.setProduct(oysterCardPpt.getProduct2());
        prePayTicketSlot.setZone(oysterCardPpt.getZone2());

        return prePayTicketSlot;
    }

    protected PrePayTicketSlot getPrePayTicketSlot3(PrePayTicketSlot prePayTicketSlot,
                                                    OysterCardPrepayTicketDTO oysterCardPpt) {
        prePayTicketSlot.setSlotNumber(this.getIntegerNumber(oysterCardPpt.getSlotNumber3()));
        prePayTicketSlot.setState(this.getIntegerNumber(oysterCardPpt.getState3()));
        prePayTicketSlot.setDiscount(oysterCardPpt.getDiscount3());
        prePayTicketSlot.setStartDate(DateUtil.formatDate(oysterCardPpt.getStartDate3()));
        prePayTicketSlot.setExpiryDate(DateUtil.formatDate(oysterCardPpt.getExpiryDate3()));
        prePayTicketSlot.setPassengerType(oysterCardPpt.getPassengerType3());
        prePayTicketSlot.setProduct(oysterCardPpt.getProduct3());
        prePayTicketSlot.setZone(oysterCardPpt.getZone3());

        return prePayTicketSlot;
    }

}
