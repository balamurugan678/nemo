<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="pre" required="false" type="java.lang.String" %>
<%@attribute name="text" required="false" type="java.lang.String" %>
<%@attribute name="headingCssClass" required="false" type="java.lang.String" %>


<h3 id="${pageName}${pageName ne ' ' ? '.' : ''}${id}-head3" class="${headingCssClass}">
<c:if test="${empty value}">
<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${HEADING}"/>
</c:if>
<c:if test="${!empty value}">
${pre} ${text}
</c:if>
</h3>
