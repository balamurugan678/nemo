package com.novacroft.nemo.tfl.common.security;

import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_REDIRECT_URL;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.SINGLE_SIGN_ON_AUTHENTICATION_FLAG;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.SINGLE_SIGN_ON_REDIRECT_URL;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;

public class SingleSignOnAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    
    @Autowired
    protected SingleSignOnSecurityService singleSignOnSecurityService;
    @Autowired
    protected SystemParameterService systemParameterService;
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
                    ServletException {
        Boolean isSingleSignOn = systemParameterService.getBooleanParameterValue(SINGLE_SIGN_ON_AUTHENTICATION_FLAG.code());
        if (isSingleSignOn) {
            String redirectUrl = buildRedirectUrl(request);
            if (redirectUrl != null) {
                response.sendRedirect(response.encodeRedirectURL(redirectUrl));
                return;
            }
        }
        super.commence(request, response, authException);
    }
    
    protected String buildRedirectUrl(HttpServletRequest request) {
        String redirectValue = systemParameterService.getParameterValue(SINGLE_SIGN_ON_REDIRECT_URL.code());
        if (StringUtils.isBlank(redirectValue)) {
            return null;
        }
        
        StringBuilder redirectUrl = new StringBuilder()
                                            .append(redirectValue)
                                            .append("?").append(PARAMETER_REDIRECT_URL).append("=")
                                            .append(request.getServletPath());
                                            
        return redirectUrl.toString();
    }
}
