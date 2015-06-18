package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

import java.util.List;

/**
 * Customer data service specification
 */
public interface CustomerDataService extends BaseDataService<Customer, CustomerDTO> {

    CustomerDTO findById(long id);

    @Override
    CustomerDTO createOrUpdate(CustomerDTO dto);

    List<CustomerDTO> findByFirstName(String firstName, boolean exact);

    List<CustomerDTO> findByLastName(String lastName, boolean exact);

    List<CustomerDTO> findByFirstNameAndLastName(String firstName, String lastName, boolean exact);

    List<CustomerDTO> findByFirstNameAndLastName(String firstName, String lastName, boolean exact, int startCount,
                                                 int endCount);

    List<CustomerDTO> findByFirstName(String firstName, boolean exact, int startCount, int endCount);

    CustomerDTO findByCardNumber(String cardNumber);

    CustomerDTO findByUsernameOrEmail(String usernameOrEmail);

    CustomerDTO findByCardId(Long cardId);

    CustomerDTO findByCustomerId(Long customerId);

    List<CustomerDTO> findByExternalUserId(Long externalUserId);

    CustomerDTO findByExternalIdAndExternalUserId(Long externalId, Long externalUserId);

    CustomerDTO findByTflMasterId(Long tflMasterId);

    List<CustomerSearchResultDTO> findByCriteria(CustomerSearchArgumentsDTO customerSearchArgumentsDTO);
}
