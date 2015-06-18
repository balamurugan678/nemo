package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.MonthEnum.getMonthForDate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.CollateIncompleteJourneyService;
import com.novacroft.nemo.tfl.common.constant.MonthEnum;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
@Service
public class CollateIncompleteJourneyServiceImpl implements	CollateIncompleteJourneyService {
	
	@Override
	public List<IncompleteJourneyMonthDTO>  collateRefundEligibleJourneysByMonth(final List<IncompleteJourneyNotificationDTO> notificationsList){
		List<IncompleteJourneyMonthDTO> inCompleteJourneyMonths = new ArrayList<>();
		for(IncompleteJourneyNotificationDTO notificationDTO : notificationsList){
			if (Boolean.TRUE.equals(notificationDTO.getEligibleForSSR())) {
				addNotificationToMonth(inCompleteJourneyMonths, notificationDTO);
			}
		}
		return inCompleteJourneyMonths;
    }
	
	
	
	protected  void addNotificationToMonth(final List<IncompleteJourneyMonthDTO> inCompleteJourneyMonths, final IncompleteJourneyNotificationDTO notificationDTO) {
		final MonthEnum monthKey = getMonthForDate(notificationDTO.getLinkedDayKeyAsDate());
		final IncompleteJourneyMonthDTO monthDTO = getMonth(inCompleteJourneyMonths, monthKey);
		InCompleteJourneyDTO incomleteJourneyDTO = new InCompleteJourneyDTO();
		incomleteJourneyDTO.setJourneyNotificationDTO(notificationDTO);
		monthDTO.getIncompleteJourneyList().add(incomleteJourneyDTO);
		
	}


	protected IncompleteJourneyMonthDTO getMonth(final List<IncompleteJourneyMonthDTO> inCompleteJourneyMonths, final MonthEnum monthKey) {
		for(IncompleteJourneyMonthDTO monthDTO : inCompleteJourneyMonths){
			if(monthKey.equals(monthDTO.getJourneyMonth())){
				return monthDTO; 
			}
		}
		IncompleteJourneyMonthDTO monthDto = new IncompleteJourneyMonthDTO();
		monthDto.setJourneyMonth(monthKey);
		inCompleteJourneyMonths.add(monthDto);
		return monthDto;
	}
	
}
