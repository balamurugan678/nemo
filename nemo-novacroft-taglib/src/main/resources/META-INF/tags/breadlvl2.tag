<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="lvl1" required="true" type="java.lang.String" %>
<%@attribute name="lvl2" required="true" type="java.lang.String" %>

<div class="r">
    <div class="breadcrumb-container">
        <ul class="breadcrumbs clearfix">
            <li class="home"><a href="#"><span class='icon home-icon hide-text'><span>Home</span></span></a></li>
            <li><a href="#">${lvl1}</a></li>
            <li><span class="last-breadcrumb">${lvl2}</span></li>
        </ul>
    </div>
</div>
