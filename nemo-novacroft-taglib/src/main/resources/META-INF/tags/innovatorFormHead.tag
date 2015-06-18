<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="traversal" required="false" type="java.lang.Boolean"%>
<div id="innovator-form">
    <div id="header-area">
        <div class="page-header-title">
            <span class="panel-top-left"></span>
            <span class="panel_top_middle"> 
               <span class="icon edit-icon"></span>
               <span>Edit Customer 50100024521</span>
            </span> 
            <span class="panel-top-right"></span>
        </div>
        <c:if test="${traversal}">
        <a name="pagetop"></a> 
        <span class="page-traversal-left">[<a href="#pagebottom">bottom</a>]</span>
        <span class="page-traversal-right">[<a href="#pagebottom">bottom</a>]</span>
        </c:if>
    </div>