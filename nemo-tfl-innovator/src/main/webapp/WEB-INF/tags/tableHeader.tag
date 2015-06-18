<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="tableHeaderCssClass" required="false" type="java.lang.String" %>

<th id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" class="${tableHeaderCssClass} bold"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.tableheader"/></th>
