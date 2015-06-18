<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="text" required="false" type="java.lang.String" %>
<%@attribute name="pre" required="false" type="java.lang.String" %>

<p>${pre}<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TEXT}"/>${text}</p>