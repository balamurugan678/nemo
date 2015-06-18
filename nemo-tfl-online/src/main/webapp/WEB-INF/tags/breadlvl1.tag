<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="title" required="true" type="java.lang.String" %>

<div class="r">
    <div class="breadcrumb-container">
        <ul class="breadcrumbs clearfix">
            <li class="home"><a href="#"><span class='icon home-icon hide-text'><span>Home</span></span></a></li>
            <li><span class="last-breadcrumb">${title}</span></li>
        </ul>
    </div>
</div>
