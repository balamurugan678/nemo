<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form modelAttribute="refundSearchCmd"
           cssClass="form form-with-tooltips" action="<%= PageUrl.INV_REFUND_SEARCH %>">
    <table class="display" summary="Display table">
        <tbody>
        <to:textTable id="caseNumber" size="10" row="1"/>
        <to:textTable id="agentLastName" size="10"/>
        <to:textTable id="agentFirstName" size="10"/>
        <to:textTable id="sapNumber" row="2" size="10"/>
        <to:textTable id="customerLastName" size="10"/>
        <to:textTable id="customerFirstName" size="10"/>
        <to:textTable id="cardNumber" row="3" size="10" />
        <to:textTable id="bacsNumber" size="10" />
        <to:textTable id="chequeNumber" size="10" />
        <to:checkboxTable id="exact" size="40" row="4" colspan="2" val="true"/>
        </tbody>
    </table>
    <div id="button-area">
        <to:button id="clearCriteria" />
        <to:imageButton id="search" imageCssClass="button-search"/>
        <to:loadingIcon />
    </div>
</form:form>

<div id="refunds">
	<to:header id="refund" />
	<to:datatableRefunds />
</div>

<script src="scripts/refundSearch.js"></script>
<script type="text/javascript">
var sAddress = "${pageContext.request.contextPath}";
var pageName = "<%=Page.INV_REFUND_SEARCH %>";
var refundSearchURL = "<%=PageUrl.INV_REFUND_SEARCH %>";
var refundCaseURL = "<%=PageUrl.INV_REFUND_CASE %>";</script>