package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * cheque settlement converter
 */
@Component("chequeSettlementConverter")
public class ChequeSettlementConverterImpl extends BaseDtoEntityConverterImpl<ChequeSettlement, ChequeSettlementDTO> {
    @Autowired
    protected DtoEntityConverter<Address, AddressDTO> addressConverter;

    @Override
    public ChequeSettlementDTO convertEntityToDto(ChequeSettlement chequeSettlement) {
        assert (chequeSettlement.getAddress() != null);
        ChequeSettlementDTO chequeSettlementDTO = super.convertEntityToDto(chequeSettlement);
        chequeSettlementDTO.setAddressDTO(this.addressConverter.convertEntityToDto(chequeSettlement.getAddress()));
        return chequeSettlementDTO;
    }

    @Override
    public ChequeSettlement convertDtoToEntity(ChequeSettlementDTO chequeSettlementDTO, ChequeSettlement chequeSettlement) {
        assert (chequeSettlementDTO.getAddressDTO() != null);
        chequeSettlement = super.convertDtoToEntity(chequeSettlementDTO, chequeSettlement);
        chequeSettlement
                .setAddress(this.addressConverter.convertDtoToEntity(chequeSettlementDTO.getAddressDTO(), new Address()));
        return chequeSettlement;
    }

    @Override
    protected ChequeSettlementDTO getNewDto() {
        return new ChequeSettlementDTO();
    }
}
