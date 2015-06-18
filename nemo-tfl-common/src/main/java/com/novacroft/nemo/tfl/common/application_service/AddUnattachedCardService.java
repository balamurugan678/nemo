package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.util.JsonResponse;
import org.springframework.validation.BindingResult;

public interface AddUnattachedCardService {
	
	CustomerDTO createNewCustomer(AddUnattachedCardCmdImpl cmd, BindingResult result);

    PersonalDetailsCmdImpl retrieveOysterDetails(String cardNumber);

    JsonResponse compareCubicDataToOyster(HolderDetails holderdetails, Boolean isHotlisted, PersonalDetailsCmdImpl personalDetailsByCustomerId);

    void saveOysterCardRecord(AddUnattachedCardCmdImpl cmd, CustomerDTO customer);

    PersonalDetailsCmdImpl retrieveOysterDetailsByCustomerID(Long customerId);

}
