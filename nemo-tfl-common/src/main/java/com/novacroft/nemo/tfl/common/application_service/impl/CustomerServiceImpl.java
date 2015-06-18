package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.CUSTOMER_DEACTIVATED;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.constant.ContactType;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.constant.ContactNameType;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Customer service implementation
 */
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected ContactDataService contactDataService;
    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CustomerPreferencesDataService customerPreferencesDataService;

    public CustomerServiceImpl() {
        super();
    }

    @Override
    @Transactional
    public CustomerDTO addCustomer(CommonOrderCardCmd cmd) {
        assert (cmd != null);
        AddressDTO address = createAddress(cmd);
        assert (null != address.getId());
        CustomerDTO customer = createCustomer(cmd, address.getId());
        assert (null != customer.getId());
        createContacts(cmd, customer.getId());
        return customer;
    }

    @Override
    public boolean isUsernameAlreadyUsed(String username) {
        boolean existUserName = false;
        try {
            if (username != null) {
                CustomerDTO customer = customerDataService.findByUsernameOrEmail(username);
                if (customer != null) {
                    existUserName = true;
                }
            }
        } catch (DataServiceException dse) {
            existUserName = true;
        }
        return existUserName;
    }

    @Override
    public boolean isEmailAddressAlreadyUsed(String emailAddress) {
        CustomerDTO customer = null;
        boolean existEmailAddress = false;
        try {
            if (emailAddress != null) {
                customer = customerDataService.findByUsernameOrEmail(emailAddress);
                if (customer != null) {
                    existEmailAddress = true;
                }
            }
        } catch (DataServiceException dse) {
            existEmailAddress = true;
        }
        return existEmailAddress;
    }

    @Override
    public CustomerDTO createCustomer(CommonOrderCardCmd cmd, Long addressId) {
        CustomerDTO customer = new CustomerDTO();
        assert (cmd != null);
        convert(cmd, customer);
        customer.setAddressId(addressId);
        initialiseSecurityCredentials(customer, cmd);
        return customerDataService.createOrUpdate(customer);
    }

    @Override
    public AddressDTO createAddress(AddressCmd cmd) {
        AddressDTO address = new AddressDTO();
        assert (cmd != null);
        convert(cmd, address);
        return addressDataService.createOrUpdate(address);
    }

    public void createContacts(CommonOrderCardCmd cmd, Long customerId) {
        assert (cmd != null && customerId != null);
        createContact(ContactNameType.Phone.name(), cmd.getHomePhone(), ContactType.HomePhone.name(), customerId);
        if (cmd.getMobilePhone() != null) {
            createContact(ContactNameType.Phone.toString(), cmd.getMobilePhone(), ContactType.MobilePhone.toString(),
                    customerId);
        }
    }

    public ContactDTO createContact(String name, String value, String type, Long customerId) {
        assert (name != null && value != null && type != null);
        ContactDTO contact = new ContactDTO(name, value, type, customerId);
        return contactDataService.createOrUpdate(contact);
    }

    @Override
    public CardDTO createCard(Long customerId, String cardNumber) {
        return createOrUpdateCard(customerId, cardNumber);
    }

    @Override
    public CardDTO createCardWithSecurityQuestion(Long customerId, String securityQuestion, String securityAnswer) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCustomerId(customerId);
        cardDTO.setSecurityQuestion(securityQuestion);
        cardDTO.setSecurityAnswer(securityAnswer);
        return createOrUpdateCard(cardDTO);
    }

    protected CardDTO createOrUpdateCard(Long customerId, String cardNumber) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCustomerId(customerId);
        cardDTO.setCardNumber(cardNumber);
        return createOrUpdateCard(cardDTO);
    }

    protected CardDTO createOrUpdateCard(Long customerId) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCustomerId(customerId);
        return createOrUpdateCard(cardDTO);
    }

    protected CardDTO createOrUpdateCard(CardDTO cardDTO) {
        return cardDataService.createOrUpdate(cardDTO);
    }

    @Override
    public void customerExists(CommonOrderCardCmd cmd, BindingResult errors) {
        List<CustomerSearchResultDTO> customers = customerDataService.findByCriteria(
                new CustomerSearchArgumentsDTO(true, null, null, cmd.getFirstName(), cmd.getLastName(), StringUtils.EMPTY, StringUtils.EMPTY, null, 1, 1));
        if (CollectionUtils.isNotEmpty(customers)) {
            CustomerSearchResultDTO customer = customers.iterator().next();
            if (cmd.getHouseNameNumber().equalsIgnoreCase(customer.getHouseNameNumber()) &&
                    cmd.getTown().equalsIgnoreCase(customer.getTown())) {
                errors.reject("customer.exists");
            }
        }
    }

    @Override
    public boolean checkGhostEmail(String emailAddress) {
        boolean foundGhostEmail = false;
        String startEmail = systemParameterService.getParameterValue(SystemParameterCode.GHOST_EMAIL_ADDDRESS_START.code());
        String endEmail = systemParameterService.getParameterValue(SystemParameterCode.GHOST_EMAIL_ADDDRESS_END.code());
        if (emailAddress.indexOf(startEmail) > -1 && emailAddress.indexOf(endEmail) > -1) {
            foundGhostEmail = true;
        }
        return foundGhostEmail;
    }

    /**
     * Create a ghost email address using the system parameters GHOST_EMAIL_ADDRESS_START and GHOST_EMAIL_ADDDRESS_END. The
     * email is constructed of
     * the ghost start, first character of the firstName , lastName, then finally the housenamenumber and ghost end email
     *
     * @param cmd PersonalDetailsCmd
     */
    @Override
    public String createGhostEmail(CommonOrderCardCmd cmd) {
        String startEmail = systemParameterService.getParameterValue(SystemParameterCode.GHOST_EMAIL_ADDDRESS_START.code());
        String endEmail = systemParameterService.getParameterValue(SystemParameterCode.GHOST_EMAIL_ADDDRESS_END.code());
        return startEmail + cmd.getFirstName().substring(0, 1) + cmd.getLastName() + cmd.getHouseNameNumber() + endEmail;
    }

    @Override
    @Transactional
    public List<CustomerSearchResultDTO> findCustomerByOrderNumber(Long orderNumber) {
        List<CustomerSearchResultDTO> customerSearchResultDTOs = null;
        try {
            OrderDTO order = orderDataService.findByOrderNumber(orderNumber);
            assert (order != null);
            CustomerDTO customer = customerDataService.findById(order.getCustomerId());
            assert (customer != null);
            AddressDTO address = addressDataService.findById(customer.getAddressId());
            assert (address != null);
            CustomerSearchResultDTO customerSearchResultDTO = new CustomerSearchResultDTO();
            customerSearchResultDTO.setId(customer.getId());
            customerSearchResultDTO.setFirstName(customer.getFirstName());
            customerSearchResultDTO.setLastName(customer.getLastName());
            customerSearchResultDTO.setHouseNameNumber(address.getHouseNameNumber());
            customerSearchResultDTO.setStreet(address.getStreet());
            customerSearchResultDTO.setTown(address.getTown());
            customerSearchResultDTO.setCounty(address.getCounty());
            customerSearchResultDTO.setCountry(address.getCountry() == null ? null : address.getCountry().getName());
            customerSearchResultDTO.setAddress();
            customerSearchResultDTO.setResultType("O");
            customerSearchResultDTO.setStatus("-");
            customerSearchResultDTO.setCalls(0);
            customerSearchResultDTO.setOysterNumber("-");
            customerSearchResultDTOs = new ArrayList<CustomerSearchResultDTO>();
            customerSearchResultDTOs.add(customerSearchResultDTO);

        } catch (AssertionError ae) {
            logger.error("Unable to find Order Number: " + orderNumber);
        }
        return customerSearchResultDTOs;
    }

    @Override
    public Boolean validateCustomerOwnsCard(Long customerId, Long cardId) {
        List<CardDTO> cards = cardDataService.findByCustomerId(customerId);
        for (CardDTO cardDTO : cards) {
            if (cardDTO.getId().equals(cardId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Long getPreferredStationId(Long customerId) {
        CustomerPreferencesDTO preferences = customerPreferencesDataService.findByCustomerId(customerId);
        return (preferences != null) ? preferences.getStationId() : null;
    }

    @Override
    public CustomerDTO deactivateCustomer(Long customerId) {
        CustomerDTO customerDTO = customerDataService.findById(customerId);
        customerDTO.setDeactivated(CUSTOMER_DEACTIVATED);
        return customerDataService.createOrUpdate(customerDTO);
    }

    /**
     * Will be obsoleted by single sign on
     *
     * @deprecated
     */
    @Deprecated
    protected void initialiseSecurityCredentials(CustomerDTO customerDTO, CommonOrderCardCmd cmd) {
        customerDTO.setUsername(cmd.getUsername());
        this.securityService.updatePasswordWithoutSavingCustomer(customerDTO, cmd.getNewPassword());
    }
}
