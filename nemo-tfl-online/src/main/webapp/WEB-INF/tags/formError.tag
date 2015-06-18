<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="errorCssClass" required="false" type="java.lang.String" %>

<form:errors cssClass="field-validation-error ${errorCssClass}"/>
