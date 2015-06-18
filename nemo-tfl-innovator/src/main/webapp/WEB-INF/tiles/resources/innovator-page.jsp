<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="styles/jquery.dataTables.css">
    
    <link rel="stylesheet" href="styles/innovator.css">
    <link rel="stylesheet" href="styles/jquery-ui.css">
    
    <script src="scripts/jquery.min.js"></script>
    <script src="scripts/jquery-ui.js"></script>
    <script src="scripts/jquery.dataTables.min.js"></script>
    <script src="scripts/jquery.cookie.js"></script>
    <script src="scripts/innovator.js"></script>
    <script src="ContentForJavaScript.htm" type="text/javascript"></script>
    <script src="scripts/contentManagement.js" type="text/javascript"></script>
	<script src="scripts/global.js"></script>
	<%@include file="/WEB-INF/jspf/setUpJQueryValidatorPlugin.jspf" %>

</head>
<body class="innovator">
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>
