package com.novacroft.nemo.tfl.services.converter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;
import com.novacroft.nemo.tfl.services.constant.SettlementType;
import com.novacroft.nemo.tfl.services.converter.AddressConverter;
import com.novacroft.nemo.tfl.services.converter.SettlementConverter;
import com.novacroft.nemo.tfl.services.transfer.Settlement;

@Component("externalSettlementConverter")
public class SettlementConverterImpl implements SettlementConverter {

    @Autowired
    protected ItemDataService itemDataService;
    @Autowired
    protected AddressConverter addressConverter;

    @Override
    public Settlement convertDTO(SettlementDTO settlementDTO) {
        if (settlementDTO != null) {
            Settlement settlement = new Settlement();
            settlement.setId(settlementDTO.getExternalId());
            settlement.setStatus(settlementDTO.getStatus());
            settlement.setAmount(settlementDTO.getAmount());
            settlement.setSettlementNumber(settlementDTO.getSettlementNumber());
            settlement.setSettlementDate(settlementDTO.getSettlementDate());

            if (settlementDTO instanceof WebCreditSettlementDTO) {
                return convert((WebCreditSettlementDTO) settlementDTO, settlement);
            }
            if (settlementDTO instanceof AdHocLoadSettlementDTO) {
                return convert((AdHocLoadSettlementDTO) settlementDTO, settlement);
            }
            if (settlementDTO instanceof PaymentCardSettlementDTO) {
                return convert((PaymentCardSettlementDTO) settlementDTO, settlement);
            }
            if (settlementDTO instanceof BACSSettlementDTO) {
                return convert((BACSSettlementDTO) settlementDTO, settlement);
            }
            if (settlementDTO instanceof ChequeSettlementDTO) {
                return convert((ChequeSettlementDTO) settlementDTO, settlement);
            }
            if (settlementDTO instanceof AutoLoadChangeSettlementDTO) {
                return convert((AutoLoadChangeSettlementDTO) settlementDTO, settlement);
            }
        }
        return null;
    }

    protected Settlement convert(WebCreditSettlementDTO settlementDTO, Settlement settlement) {
        settlement.setTransactionType(settlementDTO.getTransactionType());
        settlement.setTransactionUuid(settlementDTO.getTransactionUuid());
        settlement.setDecision(settlementDTO.getDecision());
        settlement.setMessage(settlementDTO.getMessage());
        settlement.setReasonCode(settlementDTO.getReasonCode());
        settlement.setAuthorisedAmount(settlementDTO.getAuthorisedAmount());
        settlement.setAuthorisationTime(settlementDTO.getAuthorisationTime());
        settlement.setAuthorisationTransactionReferenceNumber(settlementDTO.getAuthorisationTransactionReferenceNumber());
        settlement.setType(SettlementType.getSettlementType(WebCreditSettlementDTO.class).name());
        return settlement;
    }

    protected Settlement convert(AdHocLoadSettlementDTO settlementDTO, Settlement settlement) {
        settlement.setRequestSequenceNumber(settlementDTO.getRequestSequenceNumber());
        settlement.setPickUpNationalLocationCode(settlementDTO.getPickUpNationalLocationCode());
        settlement.setExpiresOn(settlementDTO.getExpiresOn());
        if (settlementDTO.getItem() != null) {
            Long externalId = settlementDTO.getItem().getExternalId();
            settlement.setItemId(externalId != null ? externalId : getExternalItemId(settlementDTO.getId()));
        }
        settlement.setType(SettlementType.getSettlementType(AdHocLoadSettlementDTO.class).name());
        return settlement;
    }

    protected Settlement convert(PaymentCardSettlementDTO settlementDTO, Settlement settlement) {
        settlement.setTransactionType(settlementDTO.getTransactionType());
        settlement.setTransactionUuid(settlementDTO.getTransactionUuid());
        settlement.setDecision(settlementDTO.getDecision());
        settlement.setMessage(settlementDTO.getMessage());
        settlement.setReasonCode(settlementDTO.getReasonCode());
        settlement.setAuthorisedAmount(settlementDTO.getAuthorisedAmount());
        settlement.setAuthorisationTime(settlementDTO.getAuthorisationTime());
        settlement.setAuthorisationTransactionReferenceNumber(settlementDTO.getAuthorisationTransactionReferenceNumber());
        settlement.setType(SettlementType.getSettlementType(PaymentCardSettlementDTO.class).name());
        return settlement;
    }

    protected Settlement convert(BACSSettlementDTO settlementDTO, Settlement settlement) {
        settlement = convertPayeeSettlement(settlementDTO.getPayeeName(), settlementDTO.getAddressDTO(), settlementDTO.getPaymentReference(),
                        settlement);
        settlement.setBankAccount(settlementDTO.getBankAccount());
        settlement.setSortCode(settlementDTO.getSortCode());
        settlement.setBankPaymentDate(settlementDTO.getBankPaymentDate());
        settlement.setPaymentFailedDate(settlementDTO.getPaymentFailedDate());
        if (settlementDTO.getFinancialServicesRejectCode() != null) {
            settlement.setBacsRejectCode(settlementDTO.getFinancialServicesRejectCode().name());
            settlement.setBacsRejectCodeDescription(settlementDTO.getFinancialServicesRejectCode().toString());
        }
        settlement.setType(SettlementType.getSettlementType(BACSSettlementDTO.class).name());
        return settlement;
    }

    protected Settlement convert(ChequeSettlementDTO settlementDTO, Settlement settlement) {
        settlement = convertPayeeSettlement(settlementDTO.getPayeeName(), settlementDTO.getAddressDTO(), settlementDTO.getPaymentReference(),
                        settlement);
        settlement.setChequeSerialNumber(settlementDTO.getChequeSerialNumber());
        settlement.setPrintedOn(settlementDTO.getPrintedOn());
        settlement.setClearedOn(settlementDTO.getClearedOn());
        settlement.setOutdatedOn(settlementDTO.getOutdatedOn());
        settlement.setType(SettlementType.getSettlementType(ChequeSettlementDTO.class).name());
        return settlement;
    }

    protected Settlement convertPayeeSettlement(String payeeName, AddressDTO addressDTO, Long paymentReference, Settlement settlement) {
        settlement.setPayeeName(payeeName);
        settlement.setAddress(addressConverter.convert(addressDTO));
        settlement.setPaymentReference(paymentReference);
        return settlement;
    }

    protected Settlement convert(AutoLoadChangeSettlementDTO settlementDTO, Settlement settlement) {
        settlement.setRequestSequenceNumber(settlementDTO.getRequestSequenceNumber());
        settlement.setPickUpNationalLocationCode(settlementDTO.getPickUpNationalLocationCode());
        settlement.setAutoLoadState(settlementDTO.getAutoLoadState());
        settlement.setType(SettlementType.getSettlementType(AutoLoadChangeSettlementDTO.class).name());
        return settlement;
    }

    protected Long getExternalItemId(Long internalItemId) {
        ItemDTO itemDTO = itemDataService.findById(internalItemId);
        if (itemDTO != null) {
            return itemDTO.getExternalId();
        }
        return null;
    }

    @Override
    public List<Settlement> convertDTOs(List<SettlementDTO> settlementDTOs) {
        List<Settlement> settlements = new ArrayList<Settlement>();
        if (settlementDTOs != null && settlementDTOs.size() > 0) {
            for (SettlementDTO settlementDTO : settlementDTOs) {
                Settlement settlement = convertDTO(settlementDTO);
                if (settlement != null) {
                    settlements.add(settlement);
                }
            }
        }
        return settlements;
    }

}
