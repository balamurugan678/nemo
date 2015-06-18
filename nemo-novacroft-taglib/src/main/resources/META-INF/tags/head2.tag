<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="headingCssClass" required="false" type="java.lang.String" %>
<%@attribute name="headingOverride" required="false" type="java.lang.String" %>

<h2 id="${pageName}${pageName ne ' ' ? '.' : ''}${id}-head2" class="${headingCssClass}">
    <% if (StringUtils.isBlank(headingOverride)) {%>
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${HEADING}"/>
    <% } else { %>
    <c:out value="${headingOverride}"/>
    <% } %>
</h2>
