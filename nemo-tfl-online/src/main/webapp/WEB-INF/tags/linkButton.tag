<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="linkArguments" required="false" type="java.lang.String[]" %>
<%@attribute name="linkParameters" required="false" type="String" %>

<a id="${id}-linkButton" <c:if test="${empty linkParameters}">
href="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${URL}"/>"
</c:if>
<c:if test="${!empty linkParameters}">
href="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${URL}"/>?${linkParameters}"
</c:if>
   title="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/>"
   class="container boxed-link link-button">
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${LINK}" arguments="${linkArguments}"/>
    <span class="icon right-arrow vertically-centred"></span>
</a>