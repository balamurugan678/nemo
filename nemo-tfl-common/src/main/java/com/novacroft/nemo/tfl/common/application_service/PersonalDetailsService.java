package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;

/**
 * Specification for personal details service
 */
public interface PersonalDetailsService {
    PersonalDetailsCmdImpl getPersonalDetails(String username);

    PersonalDetailsCmdImpl updatePersonalDetails(PersonalDetailsCmdImpl cmd);

    PersonalDetailsCmdImpl getPersonalDetailsByCustomerId(Long customerId);

    PersonalDetailsCmdImpl getPersonalDetailsByCardNumber(String cardNumber);
}
