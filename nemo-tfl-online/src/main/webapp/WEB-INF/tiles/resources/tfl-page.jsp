<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<!doctype html>
<!--[if IE 7]>
<html xml:lang="en-GB" class="no-js lt-ie10 lt-ie9 lt-ie8">
<![endif]-->
<!--[if IE 8]>
<html xml:lang="en-GB" class="no-js lt-ie10 lt-ie9">
<![endif]-->
<!--[if IE 9]>
<html xml:lang="en-GB" class="no-js lt-ie10">
<![endif]-->
<!--[if gt IE 9]>
<html xml:lang="en-GB" lang="en-GB" class="no-js">
<!--<![endif]-->
<head>

    <%-- TfL UX --%>
    <meta charset="utf-8">
    <!--[if lt IE 9]>
        <script type="text/javascript" src="tfl-ux/js/libs/html5shiv.js"></script>
        <![endif]-->
    <script type="text/javascript" src="tfl-ux/js/libs/modernizr.js"></script>
    <link rel="stylesheet" href="tfl-ux/css/normalize.css">
    <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-small.css">
    <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-medium.css"
          media="screen and (min-width: 580px)">
    <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-large.css"
          media="screen and (min-width: 900px)">
    <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-ux.css">
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-medium.css">
    <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-large.css">
        <![endif]-->
    <!--[if lte IE 9]>
        <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-ie.css">
        <![endif]-->
    <!--[if IE 8]>
        <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-ie-8.css">
        <![endif]-->
    <link rel="stylesheet" href="tfl-ux/css/oo-corrections.css">
    <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/small.css" media="screen,print">
    <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/modules.css" media="screen,print">
    <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/medium.css"
          media="screen and (min-width: 580px),print">
    <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/large.css"
          media="screen and (min-width: 900px)">
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/medium.css">
    <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/large.css">
        <![endif]-->
    <!--[if lte IE 9]>
        <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/ie.css">
        <![endif]-->
    <link rel="stylesheet" href="tfl-ux/css/oo-styles-small.css">
    <link rel="stylesheet" href="tfl-ux/css/oo-styles-medium.css" media="screen and (min-width: 580px)">
    <link rel="stylesheet" href="tfl-ux/css/oo-styles-large.css" media="screen and (min-width: 900px)">
    <link rel="stylesheet" href="styles/nemo-tfl-online-ux.css">
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="tfl-ux/css/oo-styles-medium.css">
    <link rel="stylesheet" href="tfl-ux/css/oo-styles-large.css">
        <![endif]-->
    <!--[if lte IE 9]>
        <link rel="stylesheet" href="tfl-ux/css/oo-styles-ie.css">
        <![endif]-->

    <meta name="viewport" content="width=device-width, initial-scale = 1.0, maximum-scale=1.0, user-scalable=no"/>
    <script type="text/javascript">
        var h = document.documentElement;
        h.className = h.className.replace(/\bno-js\b/, '');
    </script>
    <noscript>
        <link rel="stylesheet" href="tfl-ux/assets/external/nwp/styles/core/unmin/no-js.css">
        <link rel="stylesheet" href="tfl-ux/assets/external/casc/styles/csc-styles-no-js.css">
        <link rel="stylesheet" href="tfl-ux/css/oo-styles-no-js.css">
    </noscript>
    <%-- TfL UX (end) --%>

    <%-- nemo --%>
        <link rel="stylesheet" href="styles/jquery.dataTables.css"/>
    <%-- nemo (end) --%>

	<script type="text/javascript" src="tfl-ux/js/libs/jquery.min.js"></script>
	<script src="scripts/jquery.dataTables.min.js"></script>

    <to:title/>
</head>

<body>
<div id="full-width-content">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="main"/>
    <tiles:insertAttribute name="footer"/>
</div>

<%-- TfL UX --%>
<script type="text/javascript" src="tfl-ux/js/libs/jquery.calendario.js"></script>
<script type="text/javascript" src="tfl-ux/assets/external/casc/scripts/csc-ux.js"></script>
<script type="text/javascript" src="tfl-ux/assets/external/casc/scripts/jquery.validate.min.js"></script>
<script type="text/javascript" src="tfl-ux/assets/external/casc/scripts/jquery.validate.unobtrusive.min.js"></script>
<!--[if lte IE 9]>
<script type="text/javascript" src="tfl-ux/assets/external/casc/scripts/ie-fixes.js"></script>
<script type="text/javascript" src="tfl-ux/assets/external/casc/scripts/jquery.cookie.js"></script>
<![endif]-->
<script type="text/javascript" src="tfl-ux/js/ie-script.js"></script>
<!-- SiteCatalyst code version: H.26. -->
<script type="text/javascript" src="tfl-ux/assets/external/nwp/scripts/core/min/init.combined.js"></script>
<%--
JT 9/5/2014: not sure what this for but it seems to hang all pages...
<script type="text/javascript">
    tfl.stats.account = 'tfl-prod,tfl-global';
</script>
<script type="text/javascript" src="tfl-ux/assets/external/nwp/scripts/core/min/stats.combined.js"></script>
--%>
<script type="text/javascript" src="tfl-ux/assets/external/nwp/scripts/core/min/global.combined.js"></script>
<script type="text/javascript" src="tfl-ux/assets/external/nwp/scripts/core/min/form.combined.js"></script>
<script type="text/javascript" src="tfl-ux/js/script.js"></script>
<%--
JT 9/5/2014: not sure what this for but it calls a PHP page!!!
<script type="text/javascript" src="tfl-ux/js/oyster-forms.js"></script>
--%>
<%-- TfL UX (end) --%>

<%-- nemo --%>
<script src="scripts/global.js"></script>
<%@include file="/WEB-INF/jspf/setUpJQueryValidatorPlugin.jspf" %>
<%-- nemo (end) --%>

</body>
</html>
