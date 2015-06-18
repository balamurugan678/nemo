<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="id" required="true" type="java.lang.String" %>
<h2 id="${pageName}${pageName ne ' ' ? '.' : ''}${id}.header"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.header"/></h2>
