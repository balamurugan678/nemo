<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="email" required="false" type="java.lang.String" %>

<c:if test="${email ne ''}">
    <c:set var="label"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}email.${LABEL}"/></c:set>
    <c:set var="abbr"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}email.${ABBR}"/></c:set>

    <span class="hide-text icon email-icon">${label}</span><abbr title="${label}">${abbr}</abbr><span>: <a id="${id}"
                                                                                                           href="mailto:${email}">${email}</a></span><br/>
</c:if>
