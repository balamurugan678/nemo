<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="phone" required="false" type="java.lang.String" %>
<%@attribute name="fax" required="false" type="java.lang.String" %>

<c:if test="${phone ne '' || fax ne ''}">
    <c:set var="phoneLabel"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}phone.${LABEL}"/></c:set>
    <c:set var="phoneAbbr"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}phone.${ABBR}"/></c:set>
    <c:set var="faxLabel"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}fax.${LABEL}"/></c:set>
    <c:set var="faxAbbr"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}fax.${ABBR}"/></c:set>

    <span class="hide-text icon phone-icon">${phoneLabel}</span>
    <c:if test="${phone ne ''}">
        <abbr title="${phoneLabel}">${phoneAbbr}</abbr><span>: ${phone}</span><br/>
    </c:if>
    <c:if test="${fax ne ''}">
        <abbr title="${faxLabel}">${faxAbbr}</abbr><span>: ${fax}</span><br/>
    </c:if>
</c:if>
