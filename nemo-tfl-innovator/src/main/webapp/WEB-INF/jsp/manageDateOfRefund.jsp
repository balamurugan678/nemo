<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<div>
    <to:head3 id="dateOfRefund" headingCssClass="space-before-head"/>
    <to:label id="dateOfRefund" mandatory="true" />
    <div id="dateOfRefundValue">
    	<to:text id="dateOfRefund" mandatory="true" showLabel="false" cssClass="dateOfRefundValue"/>
    </div>
</div>
<div class="clear"></div>
<script type="text/javascript">
	var sAddress = "${pageContext.request.contextPath}";
    var pageName = "${pageName}";
</script>
<script src="scripts/failedCardRefundCart.js"></script>