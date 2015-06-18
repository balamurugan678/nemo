<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html ng-app="mainApp">
<head>
    <title>nemo-mock-cubic</title>

    <link rel="stylesheet" href="styles/default.css">
    <link rel="stylesheet" href="styles/jquery-ui.css">
    <link rel="stylesheet" href="styles/jquery.dataTables.css">

    <script src="scripts/jquery.min.js"></script>
    <script src="scripts/jquery-ui.js"></script>
    <script src="scripts/jquery.dataTables.min.js"></script>
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>
