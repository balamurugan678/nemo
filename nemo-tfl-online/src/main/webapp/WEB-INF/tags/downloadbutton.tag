<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="buttonType" required="true" type="java.lang.String" %>
<%@attribute name="buttonCssClass" required="false" type="java.lang.String" %>
<%@attribute name="targetAction" required="false" type="java.lang.String" %>

<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${BUTTON}.${TIP}"/></c:set>

<button id="${id}-${buttonType}-button" type="${buttonType}" class="icon document-icon vertically-centred ${buttonCssClass}" title="${tip}"
        name="<%= PageParameter.TARGET_ACTION %>"
        value="${targetAction}"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${BUTTON}.${LABEL}"/></button>
