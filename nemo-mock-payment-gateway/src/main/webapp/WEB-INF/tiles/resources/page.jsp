<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<head>
    <title>nemo-mock-payment-gateway</title>
    <script src="/nemo-mock-payment-gateway/scripts/jquery.min.js"></script>
    <script src="/nemo-mock-payment-gateway/scripts/jquery-ui.js"></script>
    <link href="/nemo-mock-payment-gateway/styles/default.css" rel="stylesheet">
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>
