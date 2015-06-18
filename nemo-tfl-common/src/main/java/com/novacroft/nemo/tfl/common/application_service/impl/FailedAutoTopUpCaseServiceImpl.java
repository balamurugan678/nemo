package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.FailedAutoTopUpCaseService;
import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpHistoryDataService;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Get the oyster card information from the cubic
 */

@Service("failedAutoTopUpCaseService")
public class FailedAutoTopUpCaseServiceImpl implements FailedAutoTopUpCaseService {
    
	@Autowired
    protected FailedAutoTopUpCaseDataService failedAutoTopUpCaseDataService;
	@Autowired
    protected FailedAutoTopUpHistoryDataService failedAutoTopUpHistoryDataService ;
	
	@Override
	public boolean isOysterCardWithFailedAutoTopUpCase(String cardNumber) {
		return failedAutoTopUpCaseDataService.isOysterCardWithFailedAutoTopUpCase(cardNumber);
	}

	@Override
	public FailedAutoTopUpCaseDTO findByCardNumber(String oysterCardNumber) {
		return failedAutoTopUpCaseDataService.findByCardNumber(oysterCardNumber);
	}
	
	@Override
	@Transactional
	public FailedAutoTopUpCaseCmdImpl updateFailedAutoTopUpCaseDetails(FailedAutoTopUpCaseCmdImpl cmd) {
		updateFailedAutoTopUpCase(cmd);
		updateFailedAutoTopUpCaseHistory(cmd);
		return cmd;
	}

	protected FailedAutoTopUpCaseCmdImpl updateFailedAutoTopUpCase(FailedAutoTopUpCaseCmdImpl cmd) {
		FailedAutoTopUpCaseDTO failedAutoTopUpCaseDTO = this.failedAutoTopUpCaseDataService.findByCustomerIdWithCaseDetails(cmd.getCustomerId());
        assert (failedAutoTopUpCaseDTO != null);
        convert(cmd, failedAutoTopUpCaseDTO);
        failedAutoTopUpCaseDTO = this.failedAutoTopUpCaseDataService.createOrUpdate(failedAutoTopUpCaseDTO);
        convert(failedAutoTopUpCaseDTO,cmd);
		return cmd;		
	}
	
	protected FailedAutoTopUpCaseCmdImpl updateFailedAutoTopUpCaseHistory(FailedAutoTopUpCaseCmdImpl cmd) {
		FailedAutoTopUpHistoryDTO failedAutoTopUpHistoryDTO = new FailedAutoTopUpHistoryDTO();
        convert(cmd, failedAutoTopUpHistoryDTO);
        failedAutoTopUpHistoryDTO = this.failedAutoTopUpHistoryDataService.createOrUpdate(failedAutoTopUpHistoryDTO);
        convert(failedAutoTopUpHistoryDTO,cmd);
		return cmd;	
	}
	
}
