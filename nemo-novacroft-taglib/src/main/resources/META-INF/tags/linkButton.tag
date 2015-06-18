<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="linkArguments" required="false" type="java.lang.String[]" %>

<a id="${id}-linkButton" href="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${URL}"/>"
   title="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/>"
   class="container link-button">
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${LINK}" arguments="${linkArguments}"/>
    <span class="icon right-arrow vertically-centred"></span>
</a>
