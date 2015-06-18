<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="headingCssClass" required="false" type="java.lang.String" %>

<h4 id="${pageName}${pageName ne ' ' ? '.' : ''}${id}-head4" class="${headingCssClass}"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${HEADING}"/></h3>
