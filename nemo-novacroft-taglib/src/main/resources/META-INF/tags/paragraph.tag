<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>

<p><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TEXT}"/></p>