<%@ include file="/WEB-INF/jspf/pageCommon.jspf" %>

<!doctype html>
<head>
    <title><tiles:getAsString name="pageTitle"/></title>
    <link rel="stylesheet" href="styles/default.css">
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>
