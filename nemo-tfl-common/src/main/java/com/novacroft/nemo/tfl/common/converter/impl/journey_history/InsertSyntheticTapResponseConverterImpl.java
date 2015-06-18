package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.journey_history.InsertSyntheticTapResponseConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.TapResponseConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.InsertSyntheticTapResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ArrayOfFAETapResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.FAETapResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTapResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.SyntheticOysterTapTransactionsResponse;

@Component
public class InsertSyntheticTapResponseConverterImpl implements	InsertSyntheticTapResponseConverter {

	@Autowired 
	protected TapResponseConverter tapResponseConverter;
	
	@Override
	public InsertSyntheticTapResponseDTO convertModelToDto(InsertSyntheticOysterTapResponse response) {
		final InsertSyntheticTapResponseDTO responseDto = new InsertSyntheticTapResponseDTO();
		final SyntheticOysterTapTransactionsResponse tapTransactionsResponse = response.getInsertSyntheticOysterTapResult().getValue();
		if(tapTransactionsResponse != null){
			responseDto.setDayKey(tapTransactionsResponse.getDayKey());
			responseDto.setDayKeyAsDate(DateUtil.convertXMLGregorianToDate(tapTransactionsResponse.getDayKeyAsDate()));
			responseDto.setPrestigeId(tapTransactionsResponse.getPrestigeId());
			responseDto.setTotalOriginalTravelSpendInPence(tapTransactionsResponse.getTotalOriginalTravelSpendInPence());
			
			final List<FAETapResponse> listOfTaps = getArrayOfTaps(tapTransactionsResponse);
			 if (listOfTaps != null) {
				for (FAETapResponse tapsResponse : listOfTaps) {
					responseDto.getTapResponseDtoList().add(tapResponseConverter.convertModelToDto(tapsResponse));

				}
			}
			 
		}
		return responseDto;
	}

	private List<FAETapResponse> getArrayOfTaps(SyntheticOysterTapTransactionsResponse tapTransactionsResponse) {
		JAXBElement<ArrayOfFAETapResponse> arrayOfTaps =  tapTransactionsResponse.getTaps();
		if(arrayOfTaps != null && arrayOfTaps.getValue() != null){
			return arrayOfTaps.getValue().getFAETapResponse();
		}
		return null;
	}

}
