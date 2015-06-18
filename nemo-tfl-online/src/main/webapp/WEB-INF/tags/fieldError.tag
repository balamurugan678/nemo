<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="path" required="true" type="java.lang.String" %>
<%@attribute name="errorCssClass" required="false" type="java.lang.String" %>

<form:errors path="${path}" cssClass="field-validation-error ${errorCssClass}"/>
