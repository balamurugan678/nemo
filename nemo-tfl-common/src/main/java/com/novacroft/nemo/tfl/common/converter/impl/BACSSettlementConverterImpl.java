package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

@Component
public class BACSSettlementConverterImpl extends
		BaseDtoEntityConverterImpl<BACSSettlement, BACSSettlementDTO> {

	@Override
	protected BACSSettlementDTO getNewDto() {
		return new BACSSettlementDTO();
	}

	@Autowired
    protected DtoEntityConverter<Address, AddressDTO> addressConverter;

    @Override
    public BACSSettlementDTO convertEntityToDto(BACSSettlement bacsSettlement) {
        assert (bacsSettlement.getAddress() != null);
        BACSSettlementDTO bacsSettlementDTO = super.convertEntityToDto(bacsSettlement);
        bacsSettlementDTO.setAddressDTO(this.addressConverter.convertEntityToDto(bacsSettlement.getAddress()));
        return bacsSettlementDTO;
    }

    @Override
    public BACSSettlement convertDtoToEntity(BACSSettlementDTO bacsSettlementDTO, BACSSettlement bacsSettlement) {
        assert (bacsSettlementDTO.getAddressDTO() != null);
        bacsSettlement = super.convertDtoToEntity(bacsSettlementDTO, bacsSettlement);
        bacsSettlement
                .setAddress(this.addressConverter.convertDtoToEntity(bacsSettlementDTO.getAddressDTO(), new Address()));
        return bacsSettlement;
    }
   
}
