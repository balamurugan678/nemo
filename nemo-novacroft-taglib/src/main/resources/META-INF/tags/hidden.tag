<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>

<form:hidden path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}"/>
