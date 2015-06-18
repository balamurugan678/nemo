
<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="id" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="href" required="false" type="java.lang.String"%>
<%@attribute name="secondaryId" required="false" type="java.lang.String" %>
<c:if test="${empty secondaryId}"><c:set var="secondaryId" value="${id}" /></c:if>

<a id="${secondaryId}" class="${cssClass}" href="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.url"/>${href}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.link"/></a>