package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.services.constant.WebServiceFieldRequestParameters.REQUEST_ID_PARAMETER;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.validator.CommonAddressValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.WorkflowFields;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.ExternalUserDataService;
import com.novacroft.nemo.tfl.common.form_validator.CommonCustomerRegistrationValidator;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.services.application_service.CustomerService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.CustomerConverter;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;
import com.novacroft.nemo.tfl.services.util.ErrorUtil;
import com.novacroft.nemo.tfl.services.util.WebServiceResultUtil;

@Service("webserviceCustomerService")
public class CustomerServiceImpl extends BaseService implements CustomerService {
    static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected ContactDataService contactDataService;
    @Autowired
    protected CustomerConverter customerConverter;
    @Autowired
    protected CommonCustomerRegistrationValidator commonCustomerRegistrationValidator;
    @Autowired
    protected CommonAddressValidator commonAddressValidator;
    @Autowired
    protected CustomerNameValidator customerNameValidator;
    @Autowired
    protected ExternalUserDataService externalUserDataService;

    @Override
    public Customer getCustomerByExternalId(Long externalId) {
        try {
            CustomerDTO customerDTO = customerDataService.findByExternalId(externalId);
            AddressDTO addressDTO = addressDataService.findById(customerDTO.getAddressId());
            List<ContactDTO> contactDTOList = contactDataService.findPhoneNumbersByCustomerId(customerDTO.getId());
            return customerConverter.convertToCustomer(customerDTO, addressDTO, contactDTOList);
        } catch (Exception exception) {
            return createCustomerError();
        }
    }

    @Override
    public Customer createCustomer(Customer customer, String username) {
        customer = checkCustomerForNull(customer);
        if (customer.getErrors() != null) {
            return customer;
        }
        Errors errors = new BeanPropertyBindingResult(customer, WorkflowFields.CUSTOMER);

        AddressDTO addressDTO = customerConverter.convertToAddressDTO(customer);
        CustomerDTO customerDTO = customerConverter.convertToCustomerDTO(customer);
        customerDTO.setExternalUserId(getExternalUserIdForCustomer(username));

        validate(errors, customerDTO, addressDTO, true);
        if (errors.hasErrors()) {
            customer.setErrors(ErrorUtil.addValidationErrorsToList(customer.getErrors(), errors.getAllErrors()));
            return customer;
        }
        return createOrUpdateCustomer(customer, addressDTO, customerDTO, null);
    }

    protected Long getExternalUserIdForCustomer(String username) {
        return (username != null ? externalUserDataService.findExternalUserIdByUsername(username) : null);
    }

    @Override
    public Customer updateCustomer(Customer customer, Long externalId, String username) {
        customer = checkCustomerForNull(customer);
        if (customer.getErrors() != null) {
            return customer;
        }
        customer = checkCustomerId(customer, externalId);
        if (customer.getErrors() != null) {
            return customer;
        }
        if (isLoggedInUserAuthorizedToUpdateOrDeleteCustomer(externalId, username)) {
            try {
                Errors errors = new BeanPropertyBindingResult(customer, WorkflowFields.CUSTOMER);
                CustomerDTO customerToUpdate = customerDataService.findByExternalId(externalId);
                CustomerDTO updatedCustomer = customerConverter.convertToCustomerDTO(customer, customerToUpdate);
                AddressDTO updatedAddress = customerConverter.convertToAddressDTO(customer,
                                addressDataService.findById(customerToUpdate.getAddressId()));
                List<ContactDTO> contactDTOList = contactDataService.findPhoneNumbersByCustomerId(customerToUpdate.getId());
                contactDTOList = customerConverter.updateContactDTOs(customer, contactDTOList);
                validate(errors, updatedCustomer, updatedAddress, false);
                if (errors.hasErrors()) {
                    customer.setErrors(ErrorUtil.addValidationErrorsToList(customer.getErrors(), errors.getAllErrors()));
                    return customer;
                }
                return createOrUpdateCustomer(customer, updatedAddress, updatedCustomer, contactDTOList);
            } catch (Exception exception) {
                customer.setErrors(ErrorUtil.addErrorToList(customer.getErrors(), null, exception.getClass().getName(), exception.getMessage()));
                return customer;
            }
        } else {
            logger.error(String.format(PrivateError.CUSTOMER_NOT_AUTHORIZED.message(), externalId));
            customer.setErrors(ErrorUtil.addErrorToList(customer.getErrors(), externalId, null,
                            getContent(ContentCode.CUSTOMER_NOT_AUTHORIZED.errorCode())));
            return customer;
        }
    }

    protected Customer createOrUpdateCustomer(Customer customer, AddressDTO addressDTO, CustomerDTO customerDTO, List<ContactDTO> contactDTOs) {
        try {
            addressDTO = addressDataService.createOrUpdate(addressDTO);
            customerDTO.setAddressId(addressDTO != null ? addressDTO.getId() : null);
            customerDTO = customerDataService.createOrUpdate(customerDTO);
            if (contactDTOs == null) {
                contactDTOs = customerConverter.convertToContactDTOs(customer, (customerDTO != null ? customerDTO.getId() : null));
            }
            contactDTOs = contactDataService.createOrUpdateAll(contactDTOs);
            customer = customerConverter.convertToCustomer(customerDTO, addressDTO, contactDTOs);
        } catch (Exception exception) {
            customer.setErrors(ErrorUtil.addErrorToList(customer.getErrors(), null, exception.getClass().getName(), exception.getMessage()));
        }
        return customer;
    }

    protected Customer checkCustomerId(Customer customer, Long externalId) {
        if (customer.getId() == null) {
            customer.setErrors(ErrorUtil.addErrorToList(customer.getErrors(), null, REQUEST_ID_PARAMETER,
                            getContent(ContentCode.ID_NOT_POPULATED.errorCode())));
        } else if (!customer.getId().equals(externalId)) {
            customer.setErrors(ErrorUtil.addErrorToList(customer.getErrors(), null, REQUEST_ID_PARAMETER,
                            getContent(ContentCode.WEBSERVICE_CUSTOMER_UPDATE_ID_MISMATCH.errorCode())));
        }
        return customer;
    }

    protected Customer checkCustomerForNull(Customer customer) {
        if (customer == null) {
            customer = createCustomerError();
        }
        return customer;
    }

    protected Customer createCustomerError() {
        Customer customer = new Customer();
        customer.setErrors(ErrorUtil.addErrorToList(customer.getErrors(), null, ContentCode.CUSTOMER_OBJECT_NULL.errorCode(),
                        getContent(ContentCode.CUSTOMER_OBJECT_NULL.errorCode())));
        return customer;
    }

    protected void validate(Errors errors, CustomerDTO customerDTO, AddressDTO addressDTO, boolean isCustomerCreated) {
        if (isCustomerCreated) {
            commonCustomerRegistrationValidator.validate(customerDTO, errors);
        } else {
            customerNameValidator.validate(customerDTO, errors);
            commonCustomerRegistrationValidator.validateMandatoryField(errors, FIELD_EMAIL_ADDRESS);
        }
        commonAddressValidator.validate(addressDTO, errors);
    }

    @Override
    public WebServiceResult deleteCustomer(DeleteCustomer deleteCustomer, Long externalCustomerId, String username) {
        if (isLoggedInUserAuthorizedToUpdateOrDeleteCustomer(externalCustomerId, username)) {
            try {
                CustomerDTO customerToDelete = customerDataService.findByExternalId(externalCustomerId);
                if (customerToDelete != null) {
                    customerToDelete = customerConverter.convertToCustomerDTO(deleteCustomer, customerToDelete);
                    CustomerDTO customerDeleted = customerDataService.createOrUpdate(customerToDelete);
                    return WebServiceResultUtil.generateSuccessResult(customerDeleted.getExternalId());
                } else {
                    return WebServiceResultUtil.generateResult(null, externalCustomerId, WebServiceResultAttribute.CUSTOMER_NOT_FOUND.name(),
                                    getContent(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.contentCode()));
                }
            } catch (Exception exception) {
                return WebServiceResultUtil.generateResult(null, externalCustomerId, WebServiceResultAttribute.FAILURE.name(), exception.getMessage());
            }
        } else {
            logger.error(String.format(PrivateError.CUSTOMER_NOT_AUTHORIZED.message(), externalCustomerId));
            logger.error(String.format(PrivateError.CUSTOMER_NOT_AUTHORIZED.message(), externalCustomerId));
            return WebServiceResultUtil.generateResult(null, externalCustomerId, WebServiceResultAttribute.CUSTOMER_NOT_AUTHORIZED.name(),
                            getContent(WebServiceResultAttribute.CUSTOMER_NOT_AUTHORIZED.contentCode()));
        }
    }

    protected boolean isLoggedInUserAuthorizedToUpdateOrDeleteCustomer(Long externalId, String username) {
        Long externalUserId = getExternalUserIdForCustomer(username);
        return (customerDataService.findByExternalIdAndExternalUserId(externalId, externalUserId) != null);
    }

}
