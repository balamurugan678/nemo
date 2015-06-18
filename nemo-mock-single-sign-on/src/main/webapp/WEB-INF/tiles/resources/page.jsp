<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<head>
    <title>nemo-mock-single-sign-on</title>
    <script src="/nemo-mock-single-sign-on/scripts/jquery.min.js"></script>
    <script src="/nemo-mock-single-sign-on/scripts/jquery-ui.js"></script>
    <link href="/nemo-mock-single-sign-on/styles/default.css" rel="stylesheet" />
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>
