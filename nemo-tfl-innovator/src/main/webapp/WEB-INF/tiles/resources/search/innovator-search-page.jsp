<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <c:set var="pgname"><tiles:getAsString name="pagename"/></c:set>
    <to:page name="${pgname}"/>
    <to:title/>
    <link rel="stylesheet" href="styles/jquery.dataTables.css">
    <link rel="stylesheet" href="styles/jquery-ui.css"/>
    <link rel="stylesheet" href="styles/innovator.css">
    
    <script src="scripts/jquery.min.js"></script>
    <script src="scripts/jquery-ui.js"></script>
    <script src="scripts/jquery.dataTables.min.js"></script> 
    <script src="scripts/innovator.js"></script>
</head>
<body class="innovator">
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="main" />
	<tiles:insertAttribute name="footer" />
</body>
</html>
