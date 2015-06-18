<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="buttonType" required="false" type="java.lang.String" %>
<%@attribute name="buttonCssClass" required="false" type="java.lang.String" %>
<%@attribute name="targetAction" required="false" type="java.lang.String" %>
<%@attribute name="secondaryId" required="false" type="java.lang.String" %>
<%@attribute name="buttonDisable" required="false" type="java.lang.String" %>
<%@attribute name="onclick" required="false" type="java.lang.String" %>

<c:if test="${empty secondaryId}"><c:set var="secondaryId" value="${id}" /></c:if>
<c:if test="${buttonType == null}"><c:set var="buttonType" value="button" /></c:if>
<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${BUTTON}.${TIP}"/></c:set>

<button id="${secondaryId}-${buttonType}" type="${buttonType}" class="button ${buttonCssClass}" title="${tip}" ${(buttonDisable == null) ? '' : 'disabled'}
        name="<%= PageParameter.TARGET_ACTION %>"
        value="${targetAction}" onclick="${onclick}"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${BUTTON}.${LABEL}"/></button>
