<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="id" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>

<a id="${id}" class="${cssClass}" href="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.url"/>"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.link"/></button>