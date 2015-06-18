<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="labelId" required="true" type="java.lang.String" %>
<%@attribute name="displayValue" required="false" type="java.lang.String" %>

<span class="info-line">
<span class="label">
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${labelId}.${LABEL}"/>
</span>
<span class="info">
    <c:out value="${displayValue}"/>
</span>
</span>
