<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.LOST_OR_STOLEN_CARD, Page.CONFIRMATION}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
    
    <div class="message-container confirmation-container csc-module">
        <div class="content">
            <p><spring:message code="${pageName}.messageLineOne.text" arguments="${cardNumber}"/></p>
            <p><spring:message code="${pageName}.messageLineTwo.text" arguments="${refundAmount}"/></p>
            <p><spring:message code="${pageName}.messageLineThree.text" arguments="${refundCaseNumber }"/></p>
        </div>
    </div>
</div> 
    <tiles:insertAttribute name="myAccount"/>
</div>

