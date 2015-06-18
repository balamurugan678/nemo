package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.UnattachedCardDetailsComparator;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.CustomerValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

@Service(value = "addUnattachedCardService")
public class AddUnattachedCardServiceImpl implements AddUnattachedCardService {

    @Autowired
    protected CardDataService cardOysterOnlineService;
    @Autowired
    protected PersonalDetailsService personalDetailsService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CustomerValidator customerValidator;

    @Override
    public PersonalDetailsCmdImpl retrieveOysterDetails(String cardNumber) {
        CustomerDTO customer = new CustomerDTO();
        PersonalDetailsCmdImpl personalDetailsByCustomerId = new PersonalDetailsCmdImpl();
        CardDTO cardDataOysterOnline = cardOysterOnlineService.findByCardNumber(cardNumber);

        if (cardDataOysterOnline != null && cardDataOysterOnline.getCustomerId() != null) {
            customer = customerDataService.findById(cardDataOysterOnline.getCustomerId());
            if (customer != null && customer.getId() != null) {
                personalDetailsByCustomerId = personalDetailsService.getPersonalDetailsByCustomerId(customer.getId());
            }
        }
        return personalDetailsByCustomerId;
    }

    @Override
    public JsonResponse compareCubicDataToOyster(HolderDetails holderdetails, Boolean isHotlisted,
                                                 PersonalDetailsCmdImpl personalDetailsByCustomerId) {
        // create list of comparable entities
        ArrayList<UnattachedCardDetailsComparator> detailsforComparison = new ArrayList<UnattachedCardDetailsComparator>();

        detailsforComparison.add(new UnattachedCardDetailsComparator("HouseName",
                StringUtil.convertNulltoEmptyString(holderdetails.getHouseName()) + StringUtil.SPACE +
                        StringUtil.convertNulltoEmptyString(holderdetails.getHouseNumber()),
                StringUtil.convertNulltoEmptyString(personalDetailsByCustomerId.getHouseNameNumber())));
        detailsforComparison.add(new UnattachedCardDetailsComparator("Street",
                StringUtil.convertNulltoEmptyString(holderdetails.getStreet()),
                StringUtil.convertNulltoEmptyString(personalDetailsByCustomerId.getStreet())));
        detailsforComparison.add(new UnattachedCardDetailsComparator("PostCode",
                StringUtil.convertNulltoEmptyString(holderdetails.getPostcode()),
                StringUtil.convertNulltoEmptyString(personalDetailsByCustomerId.getPostcode())));
        detailsforComparison.add(new UnattachedCardDetailsComparator("FirstName",
                StringUtil.convertNulltoEmptyString(holderdetails.getFirstName()),
                StringUtil.convertNulltoEmptyString(personalDetailsByCustomerId.getFirstName())));
        detailsforComparison.add(new UnattachedCardDetailsComparator("LastName",
                StringUtil.convertNulltoEmptyString(holderdetails.getLastName()),
                StringUtil.convertNulltoEmptyString(personalDetailsByCustomerId.getLastName())));

        JsonResponse result = new JsonResponse();
        result.setHotlisted(isHotlisted);
        result.setComparison(detailsforComparison);
        return result;
    }

    @Override
    public void saveOysterCardRecord(AddUnattachedCardCmdImpl cmd, CustomerDTO customer) {
        String cardNumber = cmd.getCardNumber();
        CardDTO card = new CardDTO();
        card.setCardNumber(cardNumber);
        card.setCustomerId(customer.getId());
        cardOysterOnlineService.createOrUpdate(card);
    }
    
    @Override
    public CustomerDTO createNewCustomer(AddUnattachedCardCmdImpl cmd, BindingResult result) {
    	CustomerDTO customer = new CustomerDTO();
    	customer.setId(Long.valueOf(cmd.getCustomerId()));
    	customer = customerDataService.createOrUpdate(customer);
        customerValidator.validate(customer, result);
    	return customer;
    }

    @Override
    public PersonalDetailsCmdImpl retrieveOysterDetailsByCustomerID(Long customerId) {

        PersonalDetailsCmdImpl personalDetailsByCustomerId = new PersonalDetailsCmdImpl();
        personalDetailsByCustomerId = personalDetailsService.getPersonalDetailsByCustomerId(customerId);

        return personalDetailsByCustomerId;
    }

}
