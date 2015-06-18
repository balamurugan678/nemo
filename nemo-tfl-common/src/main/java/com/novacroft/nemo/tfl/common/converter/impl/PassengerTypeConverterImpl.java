package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;

@Component(value = "passengerTypeConverter")
public class PassengerTypeConverterImpl extends BaseDtoEntityConverterImpl<PassengerType, PassengerTypeDTO> {

	@Override
	protected PassengerTypeDTO getNewDto() {
		return new PassengerTypeDTO();
	}

	
    @Override
    public PassengerTypeDTO convertEntityToDto(PassengerType passengerType) {
        return super.convertEntityToDto(passengerType);
    }

    @Override
    public PassengerType convertDtoToEntity(PassengerTypeDTO paidTicketDTO, PassengerType passengerType) {
        return super.convertDtoToEntity(paidTicketDTO, passengerType);
    }	
}
