<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="id" required="true" type="java.lang.String"%>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean"%>
<%@attribute name="hint" required="false" type="java.lang.Boolean"%>
<%@attribute name="size" required="false" type="java.lang.Integer"%>
<%@attribute name="row" required="false" type="java.lang.Integer"%>
<%@attribute name="colspan" required="false" type="java.lang.Integer"%>
<%@attribute name="val" required="true" type="java.lang.String"%>

${row != currentrow ? '<tr>' : ''}
    <td><label for="${pageName}${pageName ne ' ' ? '.' : ''}${id}" class="no-float "><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}"/>${mandatory ? '*' : ''} :</label></td> 
    <td colspan="${colspan}"><form:checkbox path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" cssClass="no-float shaded-input " value="${val}" /></td>
${currentrow > 0 && currentrow != row ? '</tr>' : ''}

<% request.setAttribute("currentrow", row); %>
<% request.setAttribute("row", row); %>
