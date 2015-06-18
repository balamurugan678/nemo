<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>

<a id="${id}" href="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${URL}"/>"
   title="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/>">
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${LINK}"/>
</a>
