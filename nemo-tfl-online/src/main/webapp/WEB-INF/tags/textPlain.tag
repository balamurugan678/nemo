<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="textCssClass" required="false" type="java.lang.String" %>

<span id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" class="${textCssClass}">
	<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TEXT}"/>
</span>
