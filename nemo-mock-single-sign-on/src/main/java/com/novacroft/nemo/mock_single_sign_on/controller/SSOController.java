package com.novacroft.nemo.mock_single_sign_on.controller;

import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.POST_CHECK_ACTIVE_SESSION;
import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.POST_SIGNOUT;
import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.POST_UPDATE_USER_CUSTOMER;
import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.POST_VALIDATE_SSO;
import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.POST_VALIDATE_TOKEN;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ADDRESS_LINE_1;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ADDRESS_LINE_2;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ADDRESS_LINE_3;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ADDRESS_LINE_4;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ADDRESS_LINE_5;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ADDRESS_TYPE;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_APP;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ASSOCIATED_CUSTOMER_ID;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ASSOCIATED_SYSTEM_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_ASSOCIATED_USER_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_CITY;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_COUNTRY;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_CUSTOMER_ID;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_EMAIL_ADDRESS;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_FIRST_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_HOME_PHONE_NUMBER;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_HOUSE_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_HOUSE_NUMBER;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_LAST_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_MIDDLE_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_MOBILE_PHONE_NUMBER;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_POST_CODE;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_REDIRECT_URL;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_RETURN_URL;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_SECURITY_ANSWER;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_SECURITY_QUESTION;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_SESSION_ID;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_STREET_NAME;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_SUPPRESS_CUSTOMER_EMAIL;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TELEPHONE_PIN;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TFL_MARKETING_OPTIN;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TITLE_DESCRIPTION;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TITLE_ID;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TOC_MARKETING_OPTIN;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TOKEN;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_WORK_PHONE_NUMBER;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.domain.Address;
import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;
import com.novacroft.nemo.mock_single_sign_on.service.LogoutService;
import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;

@Controller
@Configuration
@PropertySource("classpath:nemo-mock-single-sign-on.properties")
public class SSOController {
    protected static final Logger logger = LoggerFactory.getLogger(SSOController.class);
    protected static final String JSON_MEDIA = "application/json";

    protected Map<String, String> applicationIdMap;
    protected Map<String, String> applicationLogoutMap;

    @Autowired
    protected LogonService logonService;
    @Autowired
    protected TokenCacheService tokenCacheService;
    @Autowired
    protected MasterCustomerDataService masterCustomerDataService;
    @Autowired
    protected MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter;
    @Autowired
    protected LogoutService logoutService;

    @Value("${apps}")
    private String appIdMap;
    @Value("${logouts}")
    private String appLogoutMap;

    @PostConstruct
    public void init() {
        applicationIdMap = new HashMap<String, String>(logonService.convert(appIdMap));
        applicationLogoutMap = new HashMap<>(logonService.convert(appLogoutMap));
    }

    @RequestMapping(value = POST_VALIDATE_SSO, method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public Object validateSSOResponse(@RequestParam(value = PARAMETER_TOKEN, required = true, defaultValue = "") String token) {

        return "validateSSOResponse - Not Implemented yet";
    }

    @RequestMapping(value = POST_VALIDATE_TOKEN, method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public Object validateSecurityToken(HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
        // userWrapper is a nested redundant User object, but that's how TFL have written the API!!!
        Cookie myCookie = logonService.processCookies(httpRequest);

        User userData = null;
        if (myCookie != null) {
            String username = tokenCacheService.getCachedUsername(myCookie.getValue());
            userData = masterCustomerDataService.findMasterCustomerByUsername(username);
        }
        return logonService.createResponse(userData);
    }

    @RequestMapping(value = POST_CHECK_ACTIVE_SESSION, method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public Object checkActiveSSOSession(@RequestParam(value = PARAMETER_TOKEN, required = true, defaultValue = "") String token,
                    @RequestParam(value = PARAMETER_APP, required = true, defaultValue = "") String appId,
                    @RequestParam(value = PARAMETER_REDIRECT_URL, required = true, defaultValue = "") String redirectURL,
                    @RequestParam(value = PARAMETER_SESSION_ID) String sessionId) {
        User userData = null;
        if (isAppValid(appId).booleanValue() && tokenCacheService.isTokenValid(token).booleanValue()) {
            tokenCacheService.saveAppToCache(token, appId, sessionId);
            String username = tokenCacheService.getCachedUsername(token);
            userData = masterCustomerDataService.findMasterCustomerByUsername(username);
        }
        return logonService.createResponse(userData);
    }

    @RequestMapping(value = POST_SIGNOUT, method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public void centralSignOut(@RequestParam(value = PARAMETER_SESSION_ID) String sessionId,
                    @RequestParam(value = PARAMETER_RETURN_URL, required = false) String returnURL, HttpServletResponse response) {
        String token = tokenCacheService.getCachedToken(sessionId);
        if (tokenCacheService.isTokenValid(token).booleanValue()) {
            List<String> apps = getLogoutUrls(token);
            tokenCacheService.clearTokenCache(token);
            logoutService.singleSignOut(apps, token);

            Cookie cookie = new Cookie("token", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        try {
            if (StringUtils.isBlank(returnURL)) {
                response.sendRedirect("Logout.htm");
            } else {
                response.sendRedirect(returnURL);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setAppIdMap(String appIdMap) {
        this.appIdMap = appIdMap;
    }

    public void setAppLogoutMap(String logoutMap) {
        appLogoutMap = logoutMap;
    }

    protected Boolean isAppValid(String app) {
        return StringUtils.isNotBlank(app) && applicationIdMap.containsKey(app);
    }

    protected List<String> getLogoutUrls(String token) {
        List<String> logoutUrls = new ArrayList<>();
        List<String> appIds = tokenCacheService.getCachedApps(token);
        for (String appId : appIds) {
            String appName = applicationIdMap.get(appId);
            if (applicationLogoutMap.containsKey(appName)) {
                logoutUrls.add(applicationLogoutMap.get(appName));
            }
        }
        return logoutUrls;
    }

    @RequestMapping(value = POST_UPDATE_USER_CUSTOMER, method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public Object updateUserDetail(@RequestParam(value = PARAMETER_CUSTOMER_ID) String customerId,
                    @RequestParam(value = PARAMETER_FIRST_NAME) String firstName, 
                    @RequestParam(value = PARAMETER_LAST_NAME) String lastName,
                    @RequestParam(value = PARAMETER_MIDDLE_NAME, required = false) String middleName,
                    @RequestParam(value = PARAMETER_TITLE_ID, required = false) Integer titleId,
                    @RequestParam(value = PARAMETER_TITLE_DESCRIPTION, required = false) String titleDescription,
                    @RequestParam(value = PARAMETER_HOME_PHONE_NUMBER, required = false) String homePhoneNumber,
                    @RequestParam(value = PARAMETER_WORK_PHONE_NUMBER, required = false) String workPhoneNumber,
                    @RequestParam(value = PARAMETER_MOBILE_PHONE_NUMBER, required = false) String mobilePhoneNumber,
                    @RequestParam(value = PARAMETER_EMAIL_ADDRESS, required = false) String email,
                    @RequestParam(value = PARAMETER_TFL_MARKETING_OPTIN, required = false) Integer tflOptInFlag,
                    @RequestParam(value = PARAMETER_TELEPHONE_PIN, required = false) String telephonePin,
                    @RequestParam(value = PARAMETER_TOC_MARKETING_OPTIN, required = false) Integer tocOptInFlag,
                    @RequestParam(value = PARAMETER_ADDRESS_LINE_1, required = false) String addressLine1,
                    @RequestParam(value = PARAMETER_ADDRESS_LINE_2, required = false) String addressLine2,
                    @RequestParam(value = PARAMETER_ADDRESS_LINE_3, required = false) String addressLine3,
                    @RequestParam(value = PARAMETER_ADDRESS_LINE_4, required = false) String addressLine4,
                    @RequestParam(value = PARAMETER_ADDRESS_LINE_5, required = false) String addressLine5,
                    @RequestParam(value = PARAMETER_HOUSE_NUMBER, required = false) String houseNumber,
                    @RequestParam(value = PARAMETER_HOUSE_NAME, required = false) String houseName,
                    @RequestParam(value = PARAMETER_STREET_NAME, required = false) String streetName,
                    @RequestParam(value = PARAMETER_CITY, required = false) String city,
                    @RequestParam(value = PARAMETER_POST_CODE, required = false) String postCode,
                    @RequestParam(value = PARAMETER_COUNTRY, required = false) String countryName,
                    @RequestParam(value = PARAMETER_ADDRESS_TYPE, required = false) String addressType,
                    @RequestParam(value = PARAMETER_SECURITY_ANSWER, required = false) String securityAnswer,
                    @RequestParam(value = PARAMETER_SECURITY_QUESTION, required = false) String securityQuestion,
                    @RequestParam(value = PARAMETER_ASSOCIATED_SYSTEM_NAME, required = false) String refSystemName,
                    @RequestParam(value = PARAMETER_SUPPRESS_CUSTOMER_EMAIL, required = false) Integer suppressEmailFlag,
                    @RequestParam(value = PARAMETER_ASSOCIATED_CUSTOMER_ID, required = false) String refCustomerId,
                    @RequestParam(value = PARAMETER_ASSOCIATED_USER_NAME, required = false) String refUserName) {
        Long masterCustomerId = Long.parseLong(customerId);
        User user = masterCustomerDataService.findMasterCustomerById(masterCustomerId);
        if (user != null) {
            try {
                Customer customer = user.getCustomer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);

                updateCustomerPersonalDetail(customer, middleName, titleId, titleDescription, homePhoneNumber, workPhoneNumber, mobilePhoneNumber,
                                email, tflOptInFlag, tocOptInFlag);

                Address address = customer.getAddress();
                updateCustomerAddress(address, houseNumber, houseName, streetName, city, postCode, countryName, addressLine1, addressLine2,
                                addressLine3, addressLine4, addressLine5, addressType);

                // TODO: security answer & system reference are waiting tfl answer

                masterCustomerDataService.updateMasterCustomer(user);

            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.debug("Fail to update user data.", e);
                user = null;
            }
        }
        return logonService.createResponse(user);
    }

    protected void updateCustomerPersonalDetail(Customer customer, String middleName, Integer titleId, String titleDescription,
                    String homePhoneNumber, String workPhoneNumber, String mobilePhoneNumber, String email, Integer tflOptInFlag, Integer tocOptInFlag)
                    throws IllegalAccessException, InvocationTargetException {
        updateBeanPropertyValue(customer, "middleName", middleName);
        updateBeanPropertyValue(customer, "title.id", titleId);
        updateBeanPropertyValue(customer, "title.description", titleDescription);
        updateBeanPropertyValue(customer, "homePhoneNumber", homePhoneNumber);
        updateBeanPropertyValue(customer, "workPhoneNumber", workPhoneNumber);
        updateBeanPropertyValue(customer, "mobilePhoneNumber", mobilePhoneNumber);
        updateBeanPropertyValue(customer, "emailAddress", email);
        Boolean isTflMarketingOptIn = BooleanUtils.toBooleanObject(tflOptInFlag);
        updateBeanPropertyValue(customer, "tfLMarketingOptIn", isTflMarketingOptIn);
        Boolean isTocMarketingOptIn = BooleanUtils.toBooleanObject(tocOptInFlag);
        updateBeanPropertyValue(customer, "tocMarketingOptIn", isTocMarketingOptIn);
    }

    protected void updateCustomerAddress(Address address, String houseNumber, String houseName, String streetName, String city, String postCode,
                    String countryName, String addressLine1, String addressLine2, String addressLine3, String addressLine4, String addressLine5,
                    String addressType) throws IllegalAccessException, InvocationTargetException {
        updateBeanPropertyValue(address, "houseNo", houseNumber);
        updateBeanPropertyValue(address, "houseName", houseName);
        updateBeanPropertyValue(address, "streetName", streetName);
        updateBeanPropertyValue(address, "city", city);
        updateBeanPropertyValue(address, "postCode", postCode);
        updateBeanPropertyValue(address, "country", countryName);
        updateBeanPropertyValue(address, "addressLine1", addressLine1);
        updateBeanPropertyValue(address, "addressLine2", addressLine2);
        updateBeanPropertyValue(address, "addressLine3", addressLine3);
        updateBeanPropertyValue(address, "addressLine4", addressLine4);
        updateBeanPropertyValue(address, "addressLine5", addressLine5);
        updateBeanPropertyValue(address, "addressType.description", addressType);
    }

    protected void updateBeanPropertyValue(Object bean, String propertyName, Object propertyValue) throws IllegalAccessException,
                    InvocationTargetException {
        if (propertyValue != null) {
            BeanUtils.setProperty(bean, propertyName, propertyValue);
        }
    }

}
