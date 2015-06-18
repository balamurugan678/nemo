<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="headingCssClass" required="false" type="java.lang.String" %>

<h3 id="${pageName}${pageName ne ' ' ? '.' : ''}${id}-head2" class="${headingCssClass}"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${HEADING}"/></h3>
