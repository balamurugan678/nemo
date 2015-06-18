<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>

<%@attribute name="errorCssClass" required="false" type="java.lang.String" %>
<%@attribute name="labelArguments" required="false" type="java.lang.String[]" %>

<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.tip"/></c:set>

<div class="styled-checkbox clearfix">
    <form:checkbox path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}"/>
    <label for="${pageName}${pageName ne ' ' ? '.' : ''}${id}" title="${tip}"><spring:message
            code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${LABEL}" arguments="${labelArguments}"/></label>
</div>
<form:errors path="${id}" cssClass="field-validation-error ${errorCssClass}"/>
