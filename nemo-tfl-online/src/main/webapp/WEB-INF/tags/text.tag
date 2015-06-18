<%@include file="/WEB-INF/jspf/tagCommon.jspf"%>

<%@attribute name="id" required="true" type="java.lang.String"%>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean"%>
<%@attribute name="hint" required="false" type="java.lang.Boolean"%>
<%@attribute name="showLabel" required="false" type="java.lang.Boolean"%>
<%@attribute name="showColon" required="false" type="java.lang.Boolean"%>
<%@attribute name="maxlength" required="false" type="java.lang.String" %>
<%@attribute name="labelCssClass" required="false" type="java.lang.String"%>
<%@attribute name="inputCssClass" required="false" type="java.lang.String"%>
<%@attribute name="errorCssClass" required="false" type="java.lang.String"%>
<%@attribute name="cssInlineStyle" required="false" type="java.lang.String"%>
<%@attribute name="hintCssClass" required="false" type="java.lang.String"%>
<%@attribute name="floatCssClass" required="false" type="java.lang.String"%>
<%@attribute name="disabled" required="false" type="java.lang.Boolean"%>
<%@attribute name="readOnly" required="false" type="java.lang.Boolean"%>
<%@attribute name="htmlEscape" required="false" type="java.lang.Boolean"%>
<%@attribute name="htmlEscapeError" required="false" type="java.lang.Boolean"%>

<c:set var="placeholder">
	<spring:message
		code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${PLACEHOLDER}" />
</c:set>
<c:set var="tip">
	<spring:message
		code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}" />
</c:set>
<c:set var="showLabel" value="${(empty showLabel) ? true : showLabel}" />
<c:set var="htmlEscape" value="${(empty htmlEscape) ? true : htmlEscape}" />
<c:set var="htmlEscapeError" value="${(empty htmlEscapeError) ? true : htmlEscapeError}" />

<c:if test="${showLabel eq true}">
	<to:label id="${id}" mandatory="${mandatory}"
		labelCssClass="${labelCssClass}" showColon="${showColon}" />
</c:if>
<div class="form-control ${floatCssClass}">
	<form:input path="${id}"
		id="${pageName}${pageName ne ' ' ? '.' : ''}${id}"
		placeholder="${placeholder}" maxlength="${maxlength}"
		cssClass="shaded-input ${inputCssClass}" title="${tip}" 
		disabled="${disabled}" cssStyle="${cssInlineStyle}"
		readOnly="${readOnly}"/>
	<form:errors path="${id}" cssClass="field-validation-error ${errorCssClass}" htmlEscape="${htmlEscapeError}" cssStyle="${cssInlineStyle}"/>
	<to:hint id="${id}" hint="${hint}" hintCssClass="${hintCssClass}" />
</div>
