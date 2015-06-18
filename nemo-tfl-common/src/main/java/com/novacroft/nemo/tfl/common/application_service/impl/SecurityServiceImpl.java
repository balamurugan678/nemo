package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.AuthorizationConstant.AUTHORIZATION;
import static com.novacroft.nemo.common.constant.AuthorizationConstant.BASIC;
import static com.novacroft.nemo.common.constant.LocaleConstant.UTF_8_FORMAT;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.support.NemoUserContextImpl;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Security service implementation
 *
 * <p>Service to allow users to log in and out of the application.  Also allows update (for logged in user) and reset (for
 * forgotten password) of password.</p>
 */
@Service(value = "securityService")
public class SecurityServiceImpl implements SecurityService {
    protected static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
    protected static final String SPRING_SECURITY_CONTEXT_ATTRIBUTE = "SPRING_SECURITY_CONTEXT";
    protected static final String SPRING_SECURITY_SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
    protected static final Integer SALT_LENGTH = 32;
    public static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    protected AuthenticationProvider authenticationProvider;
    @Autowired
    protected AgentAuthenticationProviderServiceImpl agentAuthenticationProvider;
    @Autowired
    protected TokenGenerator tokenGenerator;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected NemoUserContext nemoUserContext;

    @Override
    public void login(String username, String password, HttpServletRequest request) {

        Authentication result = null;
        if (request.getParameter(PageParameter.AGENT_ID) != null && request.getParameter(PageParameter.CUSTOMER_ID) != null) {
            result = agentAuthenticationProvider
                    .authenticateAgent(new UsernamePasswordAuthenticationToken(username, password), request);
        } else {
            result = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        }
        SecurityContextHolder.getContext().setAuthentication(result);
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_ATTRIBUTE, SecurityContextHolder.getContext());
        this.nemoUserContext.setUserName(username);
    }

    @Override
    public void logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_ATTRIBUTE, null);
        request.getSession(true).setAttribute(SPRING_SECURITY_SAVED_REQUEST, null);
        this.nemoUserContext.setUserName(NemoUserContextImpl.DEFAULT_USER_NAME);
    }

    @Override
    public boolean isLoggedIn() {
        return !getLoggedInUsername().equalsIgnoreCase(ANONYMOUS_USER);
    }

    @Override
    public String getLoggedInUsername() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            username = authentication.getName();
        } else {
            username = "";
        }
        return username;
    }

    @Override
    public CustomerDTO getLoggedInCustomer() {
        return this.customerDataService.findByUsernameOrEmail(getLoggedInUsername());
    }

    @Override
    public String generateSalt() {
        return tokenGenerator.createSalt(SALT_LENGTH);
    }

    @Override
    public String hashPassword(String salt, String clearPassword) {
        return passwordEncoder.encode(salt + clearPassword);
    }

    @Override
    public CustomerDTO updatePassword(CustomerDTO customerDTO, String newPassword) {
        return customerDataService.createOrUpdate(updatePasswordWithoutSavingCustomer(customerDTO, newPassword));
    }

    @Override
    public CustomerDTO updatePasswordWithoutSavingCustomer(CustomerDTO customerDTO, String newPassword) {
        String newSalt = this.tokenGenerator.createSalt(SALT_LENGTH);
        customerDTO.setSalt(newSalt);
        customerDTO.setPassword(hashPassword(newSalt, newPassword));
        return customerDTO;
    }
    
    @Override
    public String getUsernameFromAuthorizationHeader(HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BASIC)) {
            Base64 base64 = new Base64();
            String base64Credentials = authorization.substring(BASIC.length()).trim();
            String credentials = new String(base64.decode(base64Credentials), Charset.forName(UTF_8_FORMAT));
            final String[] values = credentials.split(StringUtil.COLON, 2);
            return values[0];
        }
        return null;
    }       
}
