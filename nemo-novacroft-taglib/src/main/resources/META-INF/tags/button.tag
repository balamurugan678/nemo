<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="buttonType" required="true" type="java.lang.String" %>
<%@attribute name="buttonCssClass" required="false" type="java.lang.String" %>
<%@attribute name="targetAction" required="false" type="java.lang.String" %>
<%@attribute name="onclick" required="false" type="java.lang.String" %>

<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${BUTTON}.${TIP}"/></c:set>

<button id="${id}-${buttonType}-button" type="${buttonType}" class="button ${buttonCssClass}" title="${tip}"
        name="<%= PageParameter.TARGET_ACTION %>"
        value="${targetAction}" onclick="${onclick}"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${BUTTON}.${LABEL}"/></button>
