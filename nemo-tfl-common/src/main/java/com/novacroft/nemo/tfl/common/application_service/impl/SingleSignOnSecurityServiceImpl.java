package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.OYSTER_APP;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.SSO_AUTHENTICATION_FAIL_MESSAGE_CODE;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSessionService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnTokenService;
import com.novacroft.nemo.tfl.common.constant.CookieConstant;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.SingleSignOnDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

@Service(value = "singleSignOnSecurityService")
public class SingleSignOnSecurityServiceImpl extends BaseService implements SingleSignOnSecurityService {
    
    protected static final String SPRING_SECURITY_CONTEXT_ATTRIBUTE = "SPRING_SECURITY_CONTEXT";
    protected static final String SPRING_SECURITY_SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
    protected static final String ANONYMOUS_USER = "anonymousUser";
    protected static final Integer SALT_LENGTH = 32;
    
    @Autowired
    protected SingleSignOnTokenService singleSignOnTokenService;
    @Autowired
    protected SingleSignOnDataService singleSignOnDataService;
    @Autowired
    protected NemoUserContext nemoUserContext;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected TokenGenerator tokenGenerator;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected SingleSignOnSessionService singleSignOnSessionService;
    
    @Override
    public void login(String username, String password, 
                    HttpServletRequest request, HttpServletResponse response) {
        SingleSignOnResponseDTO ssoResponseDTO = null;
        Cookie tokenCookie = singleSignOnTokenService.requestToken(username, password, OYSTER_APP, request.getRequestedSessionId());
        String returnedToken = (tokenCookie == null ? "" : tokenCookie.getValue());
        Boolean userLoggedIn = Boolean.FALSE;
        if (StringUtils.isNotBlank(returnedToken)) {
            response.addCookie(tokenCookie);
            Object jsonUserDetail = singleSignOnTokenService.validateTokenAndRetrieveUserDetails(returnedToken);
            userLoggedIn = autoLogin(returnedToken, jsonUserDetail, request);
        }
        
        if (!userLoggedIn.booleanValue()){
            request.getSession(true).setAttribute(CookieConstant.TOKEN_FIELD_NAME, null);
            String errorMessage = (ssoResponseDTO == null ? getContent(SSO_AUTHENTICATION_FAIL_MESSAGE_CODE) : ssoResponseDTO.getErrorMessage()); 
            throw new BadCredentialsException(errorMessage);
        }
    }
    
    protected SingleSignOnResponseDTO createSingleSignOnResponseDTO(Object jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        else {
            Gson gson = new Gson();
            String receivedJson = gson.toJson(jsonObject);
            return gson.fromJson(receivedJson, SingleSignOnResponseDTO.class);
        }
    }
    
    protected void saveTokenAsPassword(String username, String newPassword) {
        CustomerDTO localCustomerDto = customerDataService.findByUsernameOrEmail(username);
        String newSalt = this.tokenGenerator.createSalt(SALT_LENGTH);
        String generatedPassword = passwordEncoder.encode(newSalt + newPassword);
        localCustomerDto.setSalt(newSalt);
        localCustomerDto.setPassword(generatedPassword);
        customerDataService.createOrUpdate(localCustomerDto);
    }
    
    @Override
    public Object checkSessionActive(String token, String sessionId) {
        return singleSignOnTokenService.checkIfSessionActive(token, OYSTER_APP, sessionId);
    }

    @Override
    public Boolean autoLogin(String token, Object jsonUserDetail, HttpServletRequest request) {
        SingleSignOnResponseDTO ssoResponseDTO = createSingleSignOnResponseDTO(jsonUserDetail);

        if (ssoResponseDTO != null && BooleanUtils.isTrue(ssoResponseDTO.getIsValid())) {
            updateUserDetailAndLogin(token, ssoResponseDTO, request);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    @Override
    public void singleSignOut(String token) {
        HttpSession httpSession = singleSignOnSessionService.removeSessionByToken(token);
        if (httpSession != null) {
            httpSession.invalidate();
        }
    }
    
    protected void updateUserDetailAndLogin(String token, SingleSignOnResponseDTO ssoResponseDTO, HttpServletRequest request) {
        String ssoUserName = ssoResponseDTO.getUser().getUser().getUserAccount().getUserName();
        nemoUserContext.setUserName(ssoUserName);
        
        singleSignOnDataService.checkAndUpdateLocalData(ssoResponseDTO);
        
        saveTokenAsPassword(ssoUserName, token);
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(ssoUserName, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_ATTRIBUTE, SecurityContextHolder.getContext());
        
        singleSignOnSessionService.addSessionByToken(token, request.getSession());
    }
}
