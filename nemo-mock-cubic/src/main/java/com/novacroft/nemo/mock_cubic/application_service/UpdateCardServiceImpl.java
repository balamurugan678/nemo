package com.novacroft.nemo.mock_cubic.application_service;

import static com.novacroft.nemo.common.utils.Converter.convert;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.CARD_NOT_FOUND_ERROR_CODE;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.CARD_NOT_FOUND_ERROR_DESCRIPTION;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.NO_AVAILABLE_PPT_SLOT_ERROR_CODE;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.NO_AVAILABLE_PPT_SLOT_ERROR_DESCRIPTION;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_CODE;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_DESCRIPTION;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.REMOVAL_REQUEST_SEQUENCE_NUMBER_NOT_FOUND_ERROR_CODE;
import static com.novacroft.nemo.mock_cubic.application_service.UpdateResponseServiceImpl.REMOVAL_REQUEST_SEQUENCE_NUMBER_NOT_FOUND_ERROR_DESCRIPTION;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.mock_cubic.constant.PptSlot;
import com.novacroft.nemo.mock_cubic.data_access.CubicCardResponseDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPptPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPpvPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayTicketDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayValueDataService;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

@Service(value = "updateCardService")
@Scope(value = "prototype")
public class UpdateCardServiceImpl implements UpdateCardService {

    protected OysterCardPpvPendingDTO pendingPpvDTO = new OysterCardPpvPendingDTO();
    protected OysterCardPptPendingDTO pendingPptDTO = new OysterCardPptPendingDTO();
    protected OysterCardPptPendingDTO tempPptDTO = new OysterCardPptPendingDTO();

    @Autowired
    protected OysterCardPendingDataService pendingDataService;
    @Autowired
    protected OysterCardPptPendingDataService pendingPptDataService;
    @Autowired
    protected OysterCardPpvPendingDataService pendingPpvDataService;
    @Autowired
    protected OysterCardDataService oysterCardDataService;
    @Autowired
    protected UpdateResponseService updateResponseService;
    @Autowired
    protected CubicCardResponseDAO cubicCardResponseDAO;
    @Autowired
    protected OysterCardPrepayTicketDataService oysterCardPrepayTicketDataService;
    @Autowired
    protected OysterCardPrepayValueDataService oysterCardPrepayValueDataService;
    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;

    @Override
    @Transactional
    public String update(CardUpdatePrePayValueRequest requestModel) {
        return updatePending(updateConvert(requestModel));
    }

    @Override
    @Transactional
    public String update(CardUpdatePrePayTicketRequest requestModel) {
        return updatePending(updateConvert(requestModel));
    }

    protected AddRequestCmd updateConvert(CardUpdatePrePayValueRequest requestModel) {
        return new AddRequestCmd(generateUniqueRequestSequenceNumber(), requestModel.getRealTimeFlag(), requestModel.getPrestigeId(),
                        requestModel.getAction(), requestModel.getCurrency(), requestModel.getPickupLocation(), requestModel.getPaymentMethod(),
                        requestModel.getUserId(), requestModel.getPassword(), requestModel.getPrePayValue());
    }

    protected AddRequestCmd updateConvert(CardUpdatePrePayTicketRequest requestModel) {
        return new AddRequestCmd(generateUniqueRequestSequenceNumber(), requestModel.getRealTimeFlag(), requestModel.getPrestigeId(),
                        requestModel.getAction(), requestModel.getCurrency(), requestModel.getPickupLocation(), requestModel.getPaymentMethod(),
                        requestModel.getUserId(), requestModel.getPassword(), requestModel.getProductCode(), requestModel.getProductPrice(),
                        requestModel.getStartDate(), requestModel.getExpiryDate());
    }

    protected Long generateUniqueRequestSequenceNumber() {
        return this.cubicCardResponseDAO.getNextRequestSequenceNumber();
    }

    @Override
    public String update(AddRequestCmd cmd) {
        if (cmd.getPrePayValue() != null) {
            return updatePPV(cmd);
        } else {
            return updatePPT(cmd);
        }
    }

    @Transactional
    @Deprecated
    protected String updatePPV(AddRequestCmd cmd) {
        pendingPpvDTO = pendingPpvDataService.findByCardNumber(cmd.getPrestigeId());
        if (pendingPpvDTO == null && oysterCardDataService.findByCardNumber(cmd.getPrestigeId()) == null) {
            return updateResponseService.generateErrorResponse(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        } else {
            pendingPpvDTO = createOysterCardPpvPendingDTO();
        }

        pendingPpvDTO = populatePPV(pendingPpvDTO, cmd);
        pendingPpvDataService.createOrUpdate(pendingPpvDTO);
        return updateResponseService.generateAddSuccessResponse(cmd);
    }

    @Transactional
    @Deprecated
    protected String updatePPT(AddRequestCmd cmd) {
        pendingPptDTO = pendingPptDataService.findByCardNumber(cmd.getPrestigeId());
        if (pendingPptDTO == null) {
            return updateResponseService.generateErrorResponse(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        }

        PptSlot slot = findSlot(pendingPptDTO, cmd.getRequestSequenceNumber());

        if (slot != PptSlot.NOT_FOUND) {
            pendingPptDTO = updateSlot(slot, pendingPptDTO, cmd);
        } else {
            slot = findEmptySlot(pendingPptDTO);
            if (slot != PptSlot.NOT_FOUND) {
                pendingPptDTO = updateSlot(slot, pendingPptDTO, cmd);
            } else {
                return updateResponseService.generateErrorResponse(NO_AVAILABLE_PPT_SLOT_ERROR_CODE, NO_AVAILABLE_PPT_SLOT_ERROR_DESCRIPTION);
            }
        }

        pendingPptDataService.createOrUpdate(pendingPptDTO);
        return updateResponseService.generateAddSuccessResponse(cmd);
    }

    @Transactional
    @Override
    public String remove(RemoveRequestCmd cmd) {
        cmd.setRequestSequenceNumber(generateUniqueRequestSequenceNumber());

        OysterCardPendingDTO pendingDTO = pendingDataService.findByExternalId(cmd.getOriginalRequestSequenceNumber());

        if (pendingDTO != null) {
            return removePending(pendingDTO, cmd);
        } else {
            return updateResponseService.generateErrorResponse(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        }
    }

    @Override
    @Transactional
    public String remove(CardRemoveUpdateRequest requestModel) {
        RemoveRequestCmd cmd = new RemoveRequestCmd();
        convert(requestModel, cmd);
        return remove(cmd);
    }

    @Transactional
    protected String removePPV(RemoveRequestCmd cmd) {
        pendingPpvDataService.delete(pendingPpvDTO);
        return updateResponseService.generateRemoveSuccessResponse(cmd);
    }

    @Transactional
    protected String removePending(OysterCardPendingDTO pendingDTO, RemoveRequestCmd cmd) {
        pendingDataService.delete(pendingDTO);
        return updateResponseService.generateRemoveSuccessResponse(cmd);
    }

    @Transactional
    protected String removePPT(RemoveRequestCmd cmd) {
        tempPptDTO = pendingPptDTO.copy();
        PptSlot slot = findSlot(pendingPptDTO, cmd.getOriginalRequestSequenceNumber());
        pendingPptDTO = clearSlot(slot, pendingPptDTO);
        if (tempPptDTO.equals(pendingPptDTO)) {
            return updateResponseService.generateErrorResponse(REMOVAL_REQUEST_SEQUENCE_NUMBER_NOT_FOUND_ERROR_CODE,
                            REMOVAL_REQUEST_SEQUENCE_NUMBER_NOT_FOUND_ERROR_DESCRIPTION);
        }
        pendingPptDTO.setNullable(true);
        pendingPptDataService.createOrUpdate(pendingPptDTO);
        return updateResponseService.generateRemoveSuccessResponse(cmd);
    }

    protected OysterCardPpvPendingDTO populatePPV(OysterCardPpvPendingDTO dto, AddRequestCmd cmd) {
        dto.setPrestigeId(cmd.getPrestigeId());
        dto.setRequestSequenceNumber(cmd.getRequestSequenceNumber());
        dto.setRealtimeFlag(cmd.getRealTimeFlag());
        dto.setPrePayValue(cmd.getPrePayValue());
        dto.setCurrency(cmd.getCurrency());
        dto.setPickupLocation(cmd.getPickupLocation());
        return dto;
    }

    protected OysterCardPptPendingDTO clearSlot(PptSlot slot, OysterCardPptPendingDTO dto) {
        switch (slot) {
        case ONE:
            dto.setRequestSequenceNumber1(null);
            dto.setRealTimeFlag1(null);
            dto.setProductCode1(null);
            dto.setProductPrice1(null);
            dto.setCurrency1(null);
            dto.setStartDate1(null);
            dto.setExpiryDate1(null);
            dto.setPickUpLocation1(null);
            break;
        case TWO:
            dto.setRequestSequenceNumber2(null);
            dto.setRealTimeFlag2(null);
            dto.setProductCode2(null);
            dto.setProductPrice2(null);
            dto.setCurrency2(null);
            dto.setStartDate2(null);
            dto.setExpiryDate2(null);
            dto.setPickUpLocation2(null);
            break;
        case THREE:
            dto.setRequestSequenceNumber3(null);
            dto.setRealTimeFlag3(null);
            dto.setProductCode3(null);
            dto.setProductPrice3(null);
            dto.setCurrency3(null);
            dto.setStartDate3(null);
            dto.setExpiryDate3(null);
            dto.setPickUpLocation3(null);
            break;
        default:
            break;
        }
        return dto;
    }

    protected PptSlot findEmptySlot(OysterCardPptPendingDTO dto) {
        if (dto.getRequestSequenceNumber1() == null) {
            return PptSlot.ONE;
        } else if (dto.getRequestSequenceNumber2() == null) {
            return PptSlot.TWO;
        } else if (dto.getRequestSequenceNumber3() == null) {
            return PptSlot.THREE;
        } else {
            return PptSlot.NOT_FOUND;
        }
    }

    protected PptSlot findEmptySlot(OysterCardPrepayTicketDTO dto) {
        if (dto.getStartDate1() == null) {
            return PptSlot.ONE;
        } else if (dto.getStartDate2() == null) {
            return PptSlot.TWO;
        } else if (dto.getStartDate3() == null) {
            return PptSlot.THREE;
        } else {
            return PptSlot.NOT_FOUND;
        }
    }

    protected PptSlot findSlot(OysterCardPptPendingDTO dto, Long originalRequestSequenceNumber) {
        if (dto.getRequestSequenceNumber1() == originalRequestSequenceNumber) {
            return PptSlot.ONE;
        } else if (dto.getRequestSequenceNumber2() == originalRequestSequenceNumber) {
            return PptSlot.TWO;
        } else if (dto.getRequestSequenceNumber3() == originalRequestSequenceNumber) {
            return PptSlot.THREE;
        } else {
            return PptSlot.NOT_FOUND;
        }
    }

    protected OysterCardPpvPendingDTO createOysterCardPpvPendingDTO() {
        return new OysterCardPpvPendingDTO();
    }

    @Transactional
    @Override
    public String updatePending(AddRequestCmd cmd) {
        List<OysterCardPendingDTO> pendingItems = pendingDataService.findByCardNumber(cmd.getPrestigeId());
        if (pendingItems == null && oysterCardDataService.findByCardNumber(cmd.getPrestigeId()) == null) {
                return updateResponseService.generateErrorResponse(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        }
        OysterCardPendingDTO pendingDTO = populatePending(new OysterCardPendingDTO(), cmd);

        if (pendingDataService.isLimitExceededForPendingItems(Long.valueOf(cmd.getPrestigeId()))) {
            return updateResponseService.generateErrorResponse(NO_AVAILABLE_PPT_SLOT_ERROR_CODE, NO_AVAILABLE_PPT_SLOT_ERROR_DESCRIPTION);
        }
        if (cmd.getPrePayValue() != null
                        && pendingDataService.isBalanceTotalExceededForPendingItems(Long.valueOf(cmd.getPrestigeId()), cmd.getPrePayValue())) {
            return updateResponseService.generateErrorResponse(PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_CODE,
                            PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_DESCRIPTION);
        }
        OysterCardPendingDTO createOrUpdate = pendingDataService.createOrUpdate(pendingDTO);
        cmd.setRequestSequenceNumber(createOrUpdate.getExternalId());
        return updateResponseService.generateAddSuccessResponse(cmd);
    }

    protected OysterCardPendingDTO populatePending(OysterCardPendingDTO dto, AddRequestCmd cmd) {
        dto.setPrestigeId(cmd.getPrestigeId());
        dto.setRequestSequenceNumber(cmd.getRequestSequenceNumber());
        dto.setRealtimeFlag(cmd.getRealTimeFlag());
        dto.setCurrency(cmd.getCurrency());
        dto.setPickupLocation(cmd.getPickupLocation());

        if (cmd.getPrePayValue() != null && cmd.getPrePayValue() > 0) {
            dto.setPrePayValue(cmd.getPrePayValue());
        } else {
            dto.setProductCode(cmd.getProductCode());
            dto.setProductPrice(cmd.getProductPrice());
            dto.setStartDate(parse(cmd.getStartDate()));
            dto.setExpiryDate(parse(cmd.getExpiryDate()));

        }
        return dto;
    }

    @Transactional
    @Override
    public String populatePrePayTicketFromPendingTicket(String cardNumber) {
        String message = "";
        List<OysterCardPendingDTO> pendingItems = pendingDataService.findByCardNumber(cardNumber);
        if (pendingItems == null || pendingItems.size() == 0) {
            return "No Pending Items";
        }
        OysterCardPrepayTicketDTO cardPrepayTicketDTO = oysterCardPrepayTicketDataService.findByCardNumber(cardNumber);
        OysterCardPrepayTicketDTO prePayTicket = new OysterCardPrepayTicketDTO();
        convert(cardPrepayTicketDTO, prePayTicket);

        Boolean prePayTicketAdded = false;
        for (OysterCardPendingDTO pending : pendingItems) {
            if (pending.getPrePayValue() != null) {
                OysterCardPrepayValueDTO oysterCardPrepayValueDTO = oysterCardPrepayValueDataService.findByCardNumber(cardNumber);
                OysterCardPrepayValueDTO prePayValueDTO = new OysterCardPrepayValueDTO();
                convert(oysterCardPrepayValueDTO, prePayValueDTO);
                oysterCardPrepayValueDTO.setBalance(oysterCardPrepayValueDTO.getBalance() + pending.getPrePayValue());
                message += " Pre Pay Value added ";
                prePayTicketAdded = true;
                oysterCardPrepayValueDataService.createOrUpdate(oysterCardPrepayValueDTO);
            } else {

                Boolean availableSlot1 = isSlotOneAvailable(cardPrepayTicketDTO, prePayTicket);
                Boolean availableSlot2 = isSlotTwoAvailable(cardPrepayTicketDTO, prePayTicket);
                ;
                Boolean availableSlot3 = isSlotThreeAvailable(cardPrepayTicketDTO, prePayTicket);
                ;
                Boolean productAdded = false;
                if (availableSlot1 || availableSlot2 || availableSlot3) {
                    PrePaidTicketDTO product = prePaidTicketDataService
                                    .findByProductCode(pending.getProductCode().toString(), pending.getStartDate());
                    if (product != null) {
                        prePayTicket.setPrestigeId(pending.getPrestigeId());
                        if (availableSlot1) {
                            populateSlot1(prePayTicket, product, pending);
                            message += " Pre Pay Ticket Added Slot 1 ";
                            productAdded = true;
                        }
                        if (availableSlot2 && !productAdded) {
                            populateSlot2(prePayTicket, product, pending);
                            message += " Pre Pay Ticket Added Slot 2 ";
                            productAdded = true;
                        }
                        if (availableSlot3 && !productAdded) {
                            populateSlot3(prePayTicket, product, pending);
                            message += " Pre Pay Ticket Added Slot 3 ";
                        }
                        prePayTicketAdded = productAdded;
                    } else {
                        message += String.format(" Pre Pay Ticket Product Not Added as not found: %s", pending.getProductCode());
                    }
                }
            }
        }
        if (prePayTicketAdded) {
            oysterCardPrepayTicketDataService.createOrUpdate(prePayTicket);
        }

        return message;
    }

    protected Boolean isSlotOneAvailable(OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO, OysterCardPrepayTicketDTO prePayTicket) {
        return oysterCardPrepayTicketDTO.getStartDate1() == null 
                        && prePayTicket.getStartDate1() == null;
    }

    protected Boolean isSlotTwoAvailable(OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO, OysterCardPrepayTicketDTO prePayTicket) {
        return oysterCardPrepayTicketDTO.getStartDate2() == null 
                        && prePayTicket.getStartDate2() == null;
    }

    protected Boolean isSlotThreeAvailable(OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO, OysterCardPrepayTicketDTO prePayTicket) {
        return oysterCardPrepayTicketDTO.getStartDate3() == null
                        && prePayTicket.getStartDate3() == null;
    }

    protected void populateSlot1(OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO, PrePaidTicketDTO prePaidTicketDTO,
                    OysterCardPendingDTO oysterCardPendingDTO) {
        oysterCardPrepayTicketDTO.setExpiryDate1(oysterCardPendingDTO.getExpiryDate());
        oysterCardPrepayTicketDTO.setStartDate1(oysterCardPendingDTO.getStartDate());
        oysterCardPrepayTicketDTO.setProduct1(prePaidTicketDTO.getDescription());
        oysterCardPrepayTicketDTO.setZone1(prePaidTicketDTO.getStartZoneDTO().getName() + " to " + prePaidTicketDTO.getEndZoneDTO().getName());
        oysterCardPrepayTicketDTO.setState1(1L);
        oysterCardPrepayTicketDTO.setPassengerType1(prePaidTicketDTO.getPassengerTypeDTO().getName());
        oysterCardPrepayTicketDTO.setDiscount1(prePaidTicketDTO.getDiscountTypeDTO().getName());
    }

    protected void populateSlot2(OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO, PrePaidTicketDTO prePaidTicketDTO,
                    OysterCardPendingDTO oysterCardPendingDTO) {
        oysterCardPrepayTicketDTO.setExpiryDate2(oysterCardPendingDTO.getExpiryDate());
        oysterCardPrepayTicketDTO.setStartDate2(oysterCardPendingDTO.getStartDate());
        oysterCardPrepayTicketDTO.setProduct2(prePaidTicketDTO.getDescription());
        oysterCardPrepayTicketDTO.setZone2(prePaidTicketDTO.getStartZoneDTO().getName() + " to " + prePaidTicketDTO.getEndZoneDTO().getName());
        oysterCardPrepayTicketDTO.setState2(1L);
        oysterCardPrepayTicketDTO.setPassengerType2(prePaidTicketDTO.getPassengerTypeDTO().getName());
        oysterCardPrepayTicketDTO.setDiscount2(prePaidTicketDTO.getDiscountTypeDTO().getName());
    }

    protected void populateSlot3(OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO, PrePaidTicketDTO prePaidTicketDTO,
                    OysterCardPendingDTO oysterCardPendingDTO) {
        oysterCardPrepayTicketDTO.setExpiryDate3(oysterCardPendingDTO.getExpiryDate());
        oysterCardPrepayTicketDTO.setStartDate3(oysterCardPendingDTO.getStartDate());
        oysterCardPrepayTicketDTO.setProduct3(prePaidTicketDTO.getDescription());
        oysterCardPrepayTicketDTO.setZone3(prePaidTicketDTO.getStartZoneDTO().getName() + " to " + prePaidTicketDTO.getEndZoneDTO().getName());
        oysterCardPrepayTicketDTO.setState3(1L);
        oysterCardPrepayTicketDTO.setPassengerType3(prePaidTicketDTO.getPassengerTypeDTO().getName());
        oysterCardPrepayTicketDTO.setDiscount3(prePaidTicketDTO.getDiscountTypeDTO().getName());
    }

    @Transactional
    @Override
    public String populatePrePayTicketFromPendingTicket2(String cardNumber) {
        String message = "";
        List<OysterCardPendingDTO> pendingItems = pendingDataService.findByCardNumber(cardNumber);
        List<OysterCardPendingDTO> collectedPendingItems = new ArrayList<OysterCardPendingDTO>();
        if (pendingItems.size() == 0) {
            message = "No Pending Items";
        }
        OysterCardPrepayTicketDTO cardPrepayTicketDTO = oysterCardPrepayTicketDataService.findByCardNumber(cardNumber);
        OysterCardPrepayTicketDTO prePayTicket = new OysterCardPrepayTicketDTO();
        convert(cardPrepayTicketDTO, prePayTicket);

        for (OysterCardPendingDTO pending : pendingItems) {
            if (pending.getPrePayValue() != null) {
                message += addPPVToCard(cardNumber, pending, collectedPendingItems);
            } else {
                message += addPPTToCard(cardNumber, pending, collectedPendingItems);
            }
        }
        for (OysterCardPendingDTO collected : collectedPendingItems) {
            pendingDataService.delete(collected);
        }
        return message;
    }

    @Transactional
    protected String addPPVToCard(String cardNumber, OysterCardPendingDTO pending, List<OysterCardPendingDTO> collectedPendingItems) {
        OysterCardPrepayValueDTO oysterCardPrepayValueDTO = oysterCardPrepayValueDataService.findByCardNumber(cardNumber);
        OysterCardPrepayValueDTO prePayValueDTO = new OysterCardPrepayValueDTO();
        convert(oysterCardPrepayValueDTO, prePayValueDTO);
        oysterCardPrepayValueDTO.setBalance((oysterCardPrepayValueDTO.getBalance() == null ? 0 : oysterCardPrepayValueDTO.getBalance())
                        + pending.getPrePayValue());
        oysterCardPrepayValueDataService.createOrUpdate(oysterCardPrepayValueDTO);
        collectedPendingItems.add(pending);
        return String.format(" Pre Pay Value added ", pending.getPrePayValue());
    }

    @SuppressWarnings("static-access")
    @Transactional
    protected String addPPTToCard(String cardNumber, OysterCardPendingDTO pending, List<OysterCardPendingDTO> collectedPendingItems) {
        OysterCardPrepayTicketDTO cardPrepayTicketDTO = oysterCardPrepayTicketDataService.findByCardNumber(cardNumber);
        OysterCardPrepayTicketDTO prePayTicket = new OysterCardPrepayTicketDTO();
        convert(cardPrepayTicketDTO, prePayTicket);
        PptSlot emptySlot = findEmptySlot(prePayTicket);
        if (!emptySlot.equals(emptySlot.NOT_FOUND)) {
            prePayTicket = updateSlot(emptySlot, prePayTicket, pending);
            oysterCardPrepayTicketDataService.createOrUpdate(prePayTicket);
            collectedPendingItems.add(pending);
            return String.format(" Pre Pay Ticket added Slot", emptySlot.toString());
        } else {
            return " No Empty Slots";
        }

    }

    protected OysterCardPrepayTicketDTO updateSlot(PptSlot slot, OysterCardPrepayTicketDTO dto, OysterCardPendingDTO pending) {
        PrePaidTicketDTO product = prePaidTicketDataService.findByProductCode(pending.getProductCode().toString(), pending.getStartDate());
        if (product != null) {
            switch (slot) {
            case ONE:
                dto.setSlotNumber1(1L);
                dto.setExpiryDate1(pending.getExpiryDate());
                dto.setStartDate1(pending.getStartDate());
                dto.setProduct1(product.getDescription());
                dto.setZone1(product.getStartZoneDTO().getName() + " to " + product.getEndZoneDTO().getName());
                dto.setState1(1L);
                dto.setPassengerType1(product.getPassengerTypeDTO().getName());
                dto.setDiscount1(product.getDiscountTypeDTO().getName());
                break;
            case TWO:
                dto.setSlotNumber2(2L);
                dto.setExpiryDate2(pending.getExpiryDate());
                dto.setStartDate2(pending.getStartDate());
                dto.setProduct1(product.getDescription());
                dto.setZone1(product.getStartZoneDTO().getName() + " to " + product.getEndZoneDTO().getName());
                dto.setState2(1L);
                dto.setPassengerType2(product.getPassengerTypeDTO().getName());
                dto.setDiscount2(product.getDiscountTypeDTO().getName());
                break;
            case THREE:
                dto.setSlotNumber3(3L);
                dto.setExpiryDate3(pending.getExpiryDate());
                dto.setStartDate3(pending.getStartDate());
                dto.setProduct1(product.getDescription());
                dto.setZone1(product.getStartZoneDTO().getName() + " to " + product.getEndZoneDTO().getName());
                dto.setState3(1L);
                dto.setPassengerType3(product.getPassengerTypeDTO().getName());
                dto.setDiscount3(product.getDiscountTypeDTO().getName());
                break;
            default:
                break;
            }
        }
        return dto;

    }

    protected OysterCardPptPendingDTO updateSlot(PptSlot slot, OysterCardPptPendingDTO dto, AddRequestCmd cmd) {
        switch (slot) {
        case ONE:
            dto.setRequestSequenceNumber1(cmd.getRequestSequenceNumber());
            dto.setRealTimeFlag1(cmd.getRealTimeFlag());
            dto.setProductCode1(cmd.getProductCode());
            dto.setProductPrice1(cmd.getProductPrice());
            dto.setCurrency1(cmd.getCurrency());
            dto.setStartDate1(parse(cmd.getStartDate()));
            dto.setExpiryDate1(parse(cmd.getExpiryDate()));
            dto.setPickUpLocation1(cmd.getPickupLocation());
            break;
        case TWO:
            dto.setRequestSequenceNumber2(cmd.getRequestSequenceNumber());
            dto.setRealTimeFlag2(cmd.getRealTimeFlag());
            dto.setProductCode2(cmd.getProductCode());
            dto.setProductPrice2(cmd.getProductPrice());
            dto.setCurrency2(cmd.getCurrency());
            dto.setStartDate2(parse(cmd.getStartDate()));
            dto.setExpiryDate2(parse(cmd.getExpiryDate()));
            dto.setPickUpLocation2(cmd.getPickupLocation());
            break;
        case THREE:
            dto.setRequestSequenceNumber3(cmd.getRequestSequenceNumber());
            dto.setRealTimeFlag3(cmd.getRealTimeFlag());
            dto.setProductCode3(cmd.getProductCode());
            dto.setProductPrice3(cmd.getProductPrice());
            dto.setCurrency3(cmd.getCurrency());
            dto.setStartDate3(parse(cmd.getStartDate()));
            dto.setExpiryDate3(parse(cmd.getExpiryDate()));
            dto.setPickUpLocation3(cmd.getPickupLocation());
            break;
        default:
            break;
        }

        return dto;
    }

}