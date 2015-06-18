package com.novacroft.nemo.tfl.common.controller;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.controller.CommonController;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import static com.novacroft.nemo.common.utils.UriUrlUtil.getApplicationBaseUri;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * TfL Base controller
 */
public abstract class BaseController extends CommonController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    public static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected ApplicationContext applicationContext;

    @ModelAttribute(PageAttribute.LOGGED_IN_USERNAME)
    public String addLoggedInUsername() {
        return this.securityService.getLoggedInUsername();
    }

    @ModelAttribute(PageAttribute.IS_USER_LOGGED_IN)
    public Boolean addIsUserLoggedIn(@ModelAttribute(PageAttribute.LOGGED_IN_USERNAME) String username) {
        return isNotBlank(username) && !ANONYMOUS_USER.equals(username) &&
                isNotBlank(this.securityService.getLoggedInUsername());
    }

    @ModelAttribute
    public void addBaseUri(Model model, HttpServletRequest request) {
        model.addAttribute(PageAttribute.BASE_URI, getApplicationBaseUri(request));
    }

    public String getContent(String code, String... messageArguments) {
        return this.applicationContext.getMessage(code, messageArguments, null);
    }

    public String getContent(MessageSourceResolvable resolvable, Locale locale) {
        return this.applicationContext.getMessage(resolvable, locale);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String decodeURL(String url) {
        String decoded = "";
        try {
            decoded = URLDecoder.decode(url, SystemParameterCode.ENCODING.code());
        } catch (UnsupportedEncodingException e) {
            logger.error(CommonPrivateError.UNSUPPORTED_ENCODING.message(), e);
        }
        return decoded;
    }

    public void setAttachedFileNameOnResponseHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    public String getClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public RedirectView getRedirectViewWithoutExposedAttributes(String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

    public Object getFromSession(HttpSession session, String attributeName) {
        return session.getAttribute(attributeName);
    }

    public void deleteAttributeFromSession(HttpSession session, String attributeName) {
        session.removeAttribute(attributeName);
    }

    public void addAttributeToSession(HttpSession session, String attributeName, Object value) {
        session.setAttribute(attributeName, value);
    }

    public String getLoggedInUsername() {
        return this.securityService.getLoggedInUsername();
    }

    public void streamFile(HttpServletResponse response, OutputStream outputStream, String fileName, String mimeContentType,
                           byte[] fileContent) {
        response.setContentType(mimeContentType);
        setAttachedFileNameOnResponseHeader(response, fileName);
        try {
            outputStream.write(fileContent);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}
