package com.novacroft.nemo.mock_single_sign_on.converter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.mock_single_sign_on.command.UserDetailsCmd;
import com.novacroft.nemo.mock_single_sign_on.domain.AccountStatus;
import com.novacroft.nemo.mock_single_sign_on.domain.Address;
import com.novacroft.nemo.mock_single_sign_on.domain.AddressType;
import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.ProductService;
import com.novacroft.nemo.mock_single_sign_on.domain.RegisteredProdServiceStatus;
import com.novacroft.nemo.mock_single_sign_on.domain.RegisteredProductServices;
import com.novacroft.nemo.mock_single_sign_on.domain.Role;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityAnswer;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityQuestion;
import com.novacroft.nemo.mock_single_sign_on.domain.SystemName;
import com.novacroft.nemo.mock_single_sign_on.domain.SystemReferences;
import com.novacroft.nemo.mock_single_sign_on.domain.Title;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.domain.UserAccount;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;

@Component("userConverter")
public class UserConverter {
    private static final Integer COUNTRY_CODE_UK = 826;
    
    @Autowired
    protected CountryDataService countryDataService;

    public User convert(UserDetailsCmd cmd) {
        Customer customer = (Customer) Converter.convert(cmd, new Customer());
        customer.setAddress(createAddress(cmd));
        customer.setTitle(createTitle(cmd));
        createSecurityAnswers(customer.getSecurityAnswers(), cmd);
        setSystemReferences(customer.getSystemReferences());
        createMarketingBooleans(customer, cmd);
        User user = new User();
        user.setCustomer(customer);
        UserAccount userAccount = createUserAccount(cmd);
        user.setUserAccount(userAccount);
        return user;
    }

    protected Address createAddress(UserDetailsCmd cmd) {
        Address address = new Address();
        address.setAddressType(new AddressType(1L, cmd.getAddressType()));
        address.setHouseNo(cmd.getHouseNo());
        address.setHouseName(cmd.getHouseName());
        address.setStreetName(cmd.getStreetName());
        address.setCity(cmd.getCity());
        address.setPostCode(cmd.getPostCode());
        CountryDTO countryDTO = countryDataService.findCountryByCode(cmd.getCountry().getCode());
        address.setCountry(countryDTO.getName());
        address.setCountryCode(COUNTRY_CODE_UK);
        List<String> addressLines = AddressFormatUtil.formatAddress(address.getHouseNo(), address.getStreetName(), address.getCity(),
                        address.getPostCode(), address.getCountry());
        address.setAddressLine1(addressLines.get(0));
        address.setAddressLine3(cmd.getCounty());
        return address;
    }

    protected Title createTitle(UserDetailsCmd cmd) {
        Title title = new Title();
        title.setId(1);
        title.setDescription(cmd.getTitle());
        return title;
    }

    protected void createSecurityAnswers(List<SecurityAnswer> securityAnswers, UserDetailsCmd cmd) {
        SecurityQuestion question = new SecurityQuestion(1L, cmd.getSecurityQuestion());
        SecurityAnswer securityAnswer = new SecurityAnswer(1L, cmd.getSecurityAnswer(), question);
        securityAnswers.add(securityAnswer);
    }

    protected void setSystemReferences(List<SystemReferences> systemReferences) {
        SystemName systemName1 = new SystemName(1L, "Oyster");
        SystemReferences systemReferences1 = new SystemReferences(systemName1, "1859115", "USER1");

        SystemName systemName2 = new SystemName(2L, "CASC");
        SystemReferences systemReferences2 = new SystemReferences(systemName2, "1859115", null);

        SystemName systemName3 = new SystemName(3L, "FTPNotification");
        SystemReferences systemReferences3 = new SystemReferences(systemName3, "1859115", null);

        systemReferences.add(systemReferences1);
        systemReferences.add(systemReferences2);
        systemReferences.add(systemReferences3);
    }

    protected UserAccount createUserAccount(UserDetailsCmd cmd) {
        Role role = new Role(1L, "Customer", "");
        String active = "Active";
        UserAccount userAccount = new UserAccount(cmd.getUsername(), new AccountStatus("Active"), null);
        RegisteredProductServices registeredProdServices1 = new RegisteredProductServices(formatDateForJson("1383765820152"), new ProductService(3L,
                        "8ead5cf4-4624-4389-b90c-b1fd1937bf1f", "Oyster"), new RegisteredProdServiceStatus(1L, active));
        registeredProdServices1.getRoles().add(role);
        userAccount.getRegisteredProductServices().add(registeredProdServices1);

        RegisteredProductServices registeredProdServices2 = new RegisteredProductServices(formatDateForJson("1405955634743"), new ProductService(1L,
                        "88b73293-96a1-4131-97f5-20026b7fb2d9", "SSO"), new RegisteredProdServiceStatus(1L, active));
        registeredProdServices2.getRoles().add(role);
        userAccount.getRegisteredProductServices().add(registeredProdServices2);

        RegisteredProductServices registeredProdServices3 = new RegisteredProductServices(formatDateForJson("-62135596800000"), new ProductService(
                        4L, "a3ac81d4-80e8-4427-b348-a3d028dfdbe7", "Customer Self Care"), new RegisteredProdServiceStatus(1L, active));
        registeredProdServices3.getRoles().add(role);
        userAccount.getRegisteredProductServices().add(registeredProdServices3);
        return userAccount;
    }

    protected void createMarketingBooleans(Customer customer, UserDetailsCmd cmd) {
        customer.setTfLMarketingOptIn(cmd.getTflMarketingOptIn());
        customer.setTocMarketingOptIn(cmd.getTocMarketingOptIn());
    }

    protected String formatDateForJson(String date) {
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append("Date(");
        sb.append(date);
        sb.append(')');
        sb.append('/');
        return sb.toString();
    }

    public UserDetailsCmd convert(User user) {
        UserDetailsCmd cmd = (UserDetailsCmd) Converter.convert(user.getCustomer(), new UserDetailsCmd());
        cmd.setTitle(user.getCustomer().getTitle().getDescription());
        cmd.setUsername(user.getUserAccount().getUserName());
        cmd.setCustomerId(user.getCustomer().getCustomerId());
        setAddress(user.getCustomer(), cmd);
        setSecurityAnswer(user.getCustomer(), cmd);
        setMarketingBooleans(user.getCustomer(), cmd);
        return cmd;
    }

    protected void setAddress(Customer customer, UserDetailsCmd cmd) {
        Address address = customer.getAddress();
        cmd.setAddressType(address.getAddressType().getDescription());
        cmd.setHouseNo(address.getHouseNo());
        cmd.setHouseName(address.getHouseName());
        cmd.setStreetName(address.getStreetName());
        cmd.setCity(address.getCity());
        cmd.setCounty(address.getAddressLine3());
        CountryDTO countryDto = countryDataService.findCountryByName(address.getCountry());
        cmd.setCountry(countryDto);
        cmd.setPostCode(address.getPostCode());
    }

    protected void setSecurityAnswer(Customer customer, UserDetailsCmd cmd) {
        List<SecurityAnswer> securityAnswers = customer.getSecurityAnswers();
        SecurityAnswer securityAnswer = securityAnswers.get(0);
        cmd.setSecurityQuestion(securityAnswer.getSecurityQuestion().getQuestion());
        cmd.setSecurityAnswer(securityAnswer.getAnswer());
    }

    protected void setMarketingBooleans(Customer customer, UserDetailsCmd cmd) {
        cmd.setTflMarketingOptIn(Boolean.valueOf(customer.getTfLMarketingOptIn()));
        cmd.setTocMarketingOptIn(Boolean.valueOf(customer.getTocMarketingOptIn()));
    }
}
