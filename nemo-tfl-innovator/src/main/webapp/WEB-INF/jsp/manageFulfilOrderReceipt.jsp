<%@page import="com.novacroft.nemo.tfl.common.constant.ContentCode"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<div>
	<div id="confirmation">
		<strong><spring:message code="${pageName}.confirmation.text"
				arguments="${fulfilmentCmd.fulfiledOysterCardNumber}" /></strong><br />
		<spring:message code="${pageName}.receipt.text" />
		<a href="javascript:showReceiptPopUp();">click here.</a>
	</div>
</div>
<div class="clear"></div>
<script type="text/javascript">
    var pageName =  "<%=Page.FULFIL_ORDER_CONFIRMATION%>";
	var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="scripts/fulfilOrderReceipt.js"></script>