<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@attribute name="name" required="true" type="java.lang.String"%>
<% request.setAttribute("pageName", name); %>
<% request.setAttribute("currentrow", 0); %>
