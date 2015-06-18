<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="id" required="true" type="java.lang.String"%>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean"%>
<%@attribute name="hint" required="false" type="java.lang.Boolean"%>
<%@attribute name="size" required="false" type="java.lang.Integer"%>
<%@attribute name="showLabel" required="false" type="java.lang.Boolean" %>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>

<c:set var="showLabel" value="${(empty showLabel) ? true : showLabel}" />

<c:if test="${showLabel eq true}">
<label for="${pageName}${pageName ne ' ' ? '.' : ''}${id}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}"/>${mandatory ? '*' : ''} :</label>
</c:if>
 
<form:checkbox path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" cssClass="shaded-input ${cssClass}" value="${val}" />
<c:if test="${hint}" >
<span style="color: #2070b6;" ><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.hint"/></span>
</c:if>
<form:errors path="${id}" cssClass="field-validation-error" />

