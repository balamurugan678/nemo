<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs
        pageNames='<%=new String[]{Page.DASHBOARD, Page.PAYMENT_INCOMPLETE}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <div>
            <to:warningBox id="paymentIncomplete"/>
            <to:linkButtonBlock links='<%=new String[]{"payment", "dashboard"}%>'/>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>
