package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.common.utils.StringUtil.PATH_SEPARATOR;
import static com.novacroft.nemo.common.utils.StringUtil.QUESTION_MARK;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.AMPERSAND_SEPARATOR;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.NAME_VALUE_SEPARATOR;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.ONLINE_SYSTEM_BASE_URI;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.SINGLE_SIGN_ON_BASE_URL;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.SINGLE_SIGN_ON_SERVER_ID;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.DeactivatedWebAccountOnlineLoginValidator;

/**
 * Security controller
 */
@Controller
public class SecurityController extends OnlineBaseController {
    
    protected static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    protected static final String SAVED_REQUEST_ATTRIBUTE = "SPRING_SECURITY_SAVED_REQUEST";
    protected static final String INDEX_JSP_PAGE = "index.jsp";
    
    @Autowired
    protected DeactivatedWebAccountOnlineLoginValidator deactivatedWebAccountOnlineLoginValidator;    
    @Autowired
    protected SingleSignOnSecurityService singleSignOnSecurityService;

    @RequestMapping(PageUrl.LOGIN)
    public ModelAndView login(@ModelAttribute(PageCommand.LOGIN) LoginCmdImpl cmd, Errors errors, 
                    HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isSingleSignOnAuthenticationOn()) {
                singleSignOnSecurityService.login(cmd.getUsername(), cmd.getPassword(), request, response);
            }
            else {
                deactivatedWebAccountOnlineLoginValidator.validate(cmd, errors);
                if (errors.hasErrors()) {
                    return new ModelAndView(PageView.OYSTER_HOME, PageCommand.LOGIN, cmd);
                }

                this.securityService.login(cmd.getUsername(), cmd.getPassword(), request);
            }
        } catch (BadCredentialsException e) {
            resetCredentials(cmd);
            errors.rejectValue(PASSWORD, ContentCode.BAD_CREDENTIALS.errorCode());
            return new ModelAndView(PageView.OYSTER_HOME, PageCommand.LOGIN, cmd);
        }
        
        return new ModelAndView(getRedirectViewWithoutExposedAttributes(isBlank(getSavedRequest(request)) ? PageUrl.DASHBOARD
                        : getSavedRequest(request)));
    }
    
    @RequestMapping(PageUrl.AUTO_LOGIN)
    public ModelAndView autoLogin(@RequestParam(value = "redirectUrl") String redirectUrl,
                    @CookieValue(value = "token", required=false) String token,
                    HttpServletRequest request) {
        if (BooleanUtils.isNotTrue(isSingleSignOnAuthenticationOn()) || StringUtils.isBlank(token)) {
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
        }
        
        String redirectPage = StringUtils.substringAfter(redirectUrl, PATH_SEPARATOR);
        if (StringUtils.equals(redirectPage, INDEX_JSP_PAGE)) {
            redirectPage = PageUrl.OYSTER_HOME;
        }
        
        Object jsonUserDetail = singleSignOnSecurityService.checkSessionActive(token, request.getRequestedSessionId());
        if (!singleSignOnSecurityService.autoLogin(token, jsonUserDetail, request)) {
            redirectPage = PageUrl.OYSTER_HOME;
        }
        
        return new ModelAndView(getRedirectViewWithoutExposedAttributes(redirectPage));
    }
    

    @RequestMapping(value=PageUrl.LOGOUT)
    public ModelAndView logout(HttpServletRequest request) {
        if (isSingleSignOnAuthenticationOn()) {
            String redirectUrl = getSingleSignOutUrl(request.getRequestedSessionId());
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(redirectUrl));
        }
        else {
            securityService.logout(request);
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
        }
    }
    
    @RequestMapping(value=PageUrl.SINGLE_SIGN_OUT, method=RequestMethod.POST)
    @ResponseBody
    public void postLogout(@RequestParam("appId") String appId, @CookieValue("token") String token) {
        String expectedServerID = systemParameterService.getParameterValue(SINGLE_SIGN_ON_SERVER_ID.code());
        if (StringUtils.equals(expectedServerID, appId)) {
            singleSignOnSecurityService.singleSignOut(token);
        }
    }

    protected String getSavedRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null != session) {
            SavedRequest savedRequest = (SavedRequest) session.getAttribute(SAVED_REQUEST_ATTRIBUTE);
            if (null != savedRequest) {
                return savedRequest.getRedirectUrl();
            }
        }
        return null;
    }

    protected void resetCredentials(LoginCmdImpl cmd) {
        cmd.setUsername(EMPTY);
        cmd.setPassword(EMPTY);
    }
    
    protected String getSingleSignOutUrl(String sessionId) {
        String ssoBaseUrl = systemParameterService.getParameterValue(SINGLE_SIGN_ON_BASE_URL.code());
        String onlineBaseUrl = systemParameterService.getParameterValue(ONLINE_SYSTEM_BASE_URI.code());
        StringBuilder urlBuilder = new StringBuilder().append(ssoBaseUrl)
                                                      .append(PATH_SEPARATOR)
                                                      .append(PageUrl.LOGOUT)
                                                      .append(QUESTION_MARK)
                                                      .append(PageAttribute.RETURN_URL)
                                                      .append(NAME_VALUE_SEPARATOR)
                                                      .append(onlineBaseUrl)
                                                      .append(AMPERSAND_SEPARATOR)
                                                      .append(PageParameter.SESSION_ID)
                                                      .append(NAME_VALUE_SEPARATOR)
                                                      .append(sessionId);
        
        return urlBuilder.toString();
    }
    
}
