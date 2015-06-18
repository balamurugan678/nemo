<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs
        pageNames='<%=new String[]{Page.DASHBOARD, Page.CONFIRMATION}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <div>
            <to:informationBox id="paymentAccepted"/>
            <div class="box info-message borderless csc-module">
                <span class="bold"><to:label id="orderNumber" value="${cartCmd.cartDTO.order.orderNumber}"/></span>
            </div>
            <to:linkButtonBlock links='<%=new String[]{"dashboard"}%>'/>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>
