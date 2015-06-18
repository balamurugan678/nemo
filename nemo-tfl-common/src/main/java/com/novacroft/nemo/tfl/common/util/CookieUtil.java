package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.common.constant.DateConstant.HTTP_HEAD_EXPIRE_DATE_FORMAT;
import static com.novacroft.nemo.common.utils.StringUtil.SEMI_COLON;
import static com.novacroft.nemo.tfl.common.constant.CookieConstant.SET_COOKIE_HEADER_KEY;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.NAME_VALUE_SEPARATOR;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.startsWith;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;

import com.novacroft.nemo.common.utils.DateUtil;

public final class CookieUtil {
    
    private static final int COOKIE_NAME_INDEX = 0;
    private static final int COOKIE_EXPIRE_INDEX = 1;
    private static final int COOKIE_PATH_INDEX = 2;

    public static Cookie extractCookieFromHeader(HttpHeaders header, String cookieName) {
        List<String> cookies = header.get(SET_COOKIE_HEADER_KEY);
        if (CollectionUtils.isEmpty(cookies)) {
            return null;
        }
        
        String cookieField = null;
        for (String cookie : cookies) {
            if (startsWith(cookie, cookieName)) {
                cookieField = cookie;
                break;
            }
        }
        
        return (cookieField == null ? null : createCookie(cookieField));
    }
    
    protected static Cookie createCookie(String cookieAttributes) {
        String[] attributes = split(cookieAttributes, SEMI_COLON);
        String nameValuePair = attributes[COOKIE_NAME_INDEX];
        String[] parts = split(nameValuePair, NAME_VALUE_SEPARATOR);
        Cookie cookie = new Cookie(parts[0], parts[1]);
        
        String expiresPair = attributes[COOKIE_EXPIRE_INDEX];
        parts = split(expiresPair, NAME_VALUE_SEPARATOR);
        Date expireDate = DateUtil.parse(parts[1], HTTP_HEAD_EXPIRE_DATE_FORMAT);
        if (expireDate != null) {
            long maxAge = 0L;
            if (DateUtil.isBefore(new Date(), expireDate)) {
                maxAge = DateUtil.getDateDiffInSecondsAsLong(DateTime.now(), new DateTime(expireDate));
            }
            cookie.setMaxAge(Long.valueOf(maxAge).intValue());
        }
        
        String pathPair = attributes[COOKIE_PATH_INDEX];
        parts = split(pathPair, NAME_VALUE_SEPARATOR);
        cookie.setPath(parts[1]);
        
        return cookie;
    }
    
    private CookieUtil() {}
}
