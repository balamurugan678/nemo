<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="path" required="true" type="java.lang.String" %>
<%@attribute name="value" required="true" type="java.lang.String" %>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@attribute name="hint" required="false" type="java.lang.Boolean" %>

<%@attribute name="labelCssClass" required="false" type="java.lang.String" %>
<%@attribute name="radioButtonCssClass" required="false" type="java.lang.String" %>
<%@attribute name="hintCssClass" required="false" type="java.lang.String" %>

<c:set var="placeholder"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${PLACEHOLDER}"/></c:set>
<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/></c:set>
<div class="radiobutton" >
	<form:radiobutton path="${path}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" placeholder="${placeholder}"
		value="${value}" cssClass="radio${radioButtonCssClass}" title="${tip}" />
	<to:label id="${id}" mandatory="${mandatory}" labelCssClass="${labelCssClass}" showColon="false" />
</div>
<to:hint id="${id}" hint="${hint}" hintCssClass="${hintCssClass}"/>
