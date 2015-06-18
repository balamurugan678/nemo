package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

/**
 * Customer service specification.
 */
public interface CustomerService {
    CustomerDTO addCustomer(CommonOrderCardCmd cmd);

    CustomerDTO createCustomer(CommonOrderCardCmd cmd, Long addressId);

    AddressDTO createAddress(AddressCmd cmd);

    CardDTO createCard(Long customerId, String cardNumber);

    boolean isUsernameAlreadyUsed(String username);

    boolean isEmailAddressAlreadyUsed(String emailAddress);

    void customerExists(CommonOrderCardCmd cmd, BindingResult res);

    boolean checkGhostEmail(String emailAddress);

    String createGhostEmail(CommonOrderCardCmd cmd);

    List<CustomerSearchResultDTO> findCustomerByOrderNumber(Long orderNumber);

    Boolean validateCustomerOwnsCard(Long customerId, Long cardId);

    Long getPreferredStationId(Long customerId);

    CustomerDTO deactivateCustomer(Long customerId);

    CardDTO createCardWithSecurityQuestion(Long customerId, String securityQuestion, String securityAnswer);
}
